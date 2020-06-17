package com.dung.phan.order.repository;

import com.dung.phan.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findAll(Pageable pageRequest);
}
