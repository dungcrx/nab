package com.dung.phan.order.service.impl;

import com.dung.phan.order.model.Orders;
import com.dung.phan.order.repository.OrderRepository;
import com.dung.phan.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Page<Orders> getOrderList(Pageable pageRequest) {
        return orderRepository.findAll(pageRequest);
    }
}
