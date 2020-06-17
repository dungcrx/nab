package com.dung.phan.product.service.impl;

import com.dung.phan.product.exception.ResourceNotFoundException;
import com.dung.phan.product.model.Product;
import com.dung.phan.product.repository.ProductRepository;
import com.dung.phan.product.service.ProductService;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<Product> getAllProducts(Pageable pageRequest) {
        return productRepository.getProductByDeletedFalse(pageRequest);
    }

    @Override
    public Product getProductById(long id) throws ResourceNotFoundException {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found for this id :: " + id));
    }

    @Override
    public Single<Product> deleteProductById(long id) throws ResourceNotFoundException {
        return Single.create(singleSubscriber -> {
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isPresent()) {
                Product product = optional.get();
                product.setDeleted(true);
                singleSubscriber.onSuccess(productRepository.save(product));
            } else {
                singleSubscriber.onError(new ResourceNotFoundException("Product not found for this id :: " + id));
            }
        });
    }

    @Override
    public Single<Product> createProduct(Product product) {
        return Single.create(singleSubscriber -> {
            Product newProduct = productRepository.save(product);
            if (newProduct == null) {
                singleSubscriber.onError(new EntityNotFoundException());
            } else {
                singleSubscriber.onSuccess(newProduct);
            }
        });
    }

    @Override
    public Single<Product> updateProduct(Product productDetail, long productId) throws ResourceNotFoundException {
        return Single.create(singleEmitter -> {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setCode(productDetail.getCode());
                product.setName(productDetail.getName());
                product.setPrice(productDetail.getPrice());
                singleEmitter.onSuccess(productRepository.save(product));
            } else {
                singleEmitter.onError(new EntityNotFoundException("productId " + productId));
            }
        });

    }
}
