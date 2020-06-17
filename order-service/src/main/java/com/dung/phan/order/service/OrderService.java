package com.dung.phan.order.service;

import com.dung.phan.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Orders> getOrderList(Pageable pageRequest);
}
