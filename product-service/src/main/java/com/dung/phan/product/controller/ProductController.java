package com.dung.phan.product.controller;

import com.dung.phan.product.exception.ResourceNotFoundException;
import com.dung.phan.product.model.Product;
import com.dung.phan.product.service.ProductService;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Observable<Product> getAllProducts(@PageableDefault(page = 0, size = 10)
                                                  @SortDefault.SortDefaults
                                                          ({@SortDefault(sort = "id", direction = Sort.Direction.DESC)})  Pageable pageRequest) {
        return Observable.fromIterable(productService.getAllProducts(pageRequest))
                .doOnComplete(() -> log.info("Finished the get List items"))
                .doOnError(ex -> log.error("Could not get list item because {} ", ex.getMessage()));
    }

    @GetMapping("{id}")
    public Observable<Product> getProductById(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
        return Observable.just(productId)
                .map(id -> productService.getProductById(id))
                .doOnError(ex -> log.error(" Could not get item id = {}", ex.getMessage()))
                .doOnComplete(() -> log.info(" finishing get item is = {}", productId));
    }

    @PostMapping
    public Single<Product> createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product)
                .doOnSuccess(newProduct -> log.info("Create new product with id= {}",newProduct.getId()))
                .doOnError(ex -> log.error("Count not create new Product {}",ex.getMessage()));
    }
    @PutMapping("/{id}")
    public Single<Product> updateProduct(@PathVariable(value = "id") Long productId,
                                          @Valid @RequestBody Product productDetail) throws ResourceNotFoundException {

        return productService.updateProduct(productDetail, productId)
                .doOnSuccess(newProduct -> log.info("Create new product with id= {}",newProduct.getId()))
                .doOnError(ex -> log.error("Count not create new Product {}",ex.getMessage()));
    }

    @DeleteMapping("/{id}")
    public Single<Product> deleteProduct(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
        return productService.deleteProductById(productId)
                .doOnSuccess(newProduct -> log.info("Deleted with id= {}",newProduct.getId()))
                .doOnError(ex -> log.error("Count not Deleted Product {}",ex.getMessage()));
    }
}
