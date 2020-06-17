package com.dung.phan.order.service;

import com.dung.phan.order.exception.ResourceNotFoundException;
import com.dung.phan.order.model.OrderDetail;
import com.dung.phan.order.model.OrderRequest;
import io.reactivex.Single;


public interface OrderDetailService {

    OrderDetail getOrderById(long orderId) throws ResourceNotFoundException;
    Single<OrderDetail> deleteOrderById(long orderId) throws ResourceNotFoundException;
    Single<OrderDetail> createOrder(OrderRequest request);
    Single<OrderDetail> updateOrder(OrderDetail orderDetail, long orderId) throws ResourceNotFoundException;

}
