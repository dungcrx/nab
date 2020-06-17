package com.dung.phan.product.service;

import com.dung.phan.product.exception.ResourceNotFoundException;
import com.dung.phan.product.model.Product;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageRequest);
    Product getProductById(long productId) throws ResourceNotFoundException;
    Single<Product> deleteProductById(long productId) throws ResourceNotFoundException;
    Single<Product> createProduct(Product product);
    Single<Product> updateProduct(Product product, long productId) throws ResourceNotFoundException;
}
