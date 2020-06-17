package com.dung.phan.product.repository;

import com.dung.phan.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> getProductByDeletedFalse(Pageable pageRequest);

}
