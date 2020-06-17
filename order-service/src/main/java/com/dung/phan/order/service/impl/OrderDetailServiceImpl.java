package com.dung.phan.order.service.impl;

import com.dung.phan.order.exception.ResourceNotFoundException;
import com.dung.phan.order.model.OrderDetail;
import com.dung.phan.order.model.OrderRequest;
import com.dung.phan.order.model.Orders;
import com.dung.phan.order.model.Status;
import com.dung.phan.order.repository.OrderDetailRepository;
import com.dung.phan.order.repository.OrderRepository;
import com.dung.phan.order.service.OrderDetailService;
import com.dung.phan.order.service.connector.ProductConnector;
import io.reactivex.Single;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired @Setter
    private OrderDetailRepository orderDetailRepository;
    @Autowired @Setter
    private ProductConnector productConnector;
    @Autowired @Setter
    private OrderRepository orderRepository;

    @Override
    public OrderDetail getOrderById(long orderId) throws ResourceNotFoundException {
        return orderDetailRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("could not found for this id :: " + orderId) );
    }

    @Override
    @Transactional
    public Single<OrderDetail> deleteOrderById(long orderId){

        return Single.create(singleSubscriber -> {
            Optional<OrderDetail> optional = orderDetailRepository.findById(orderId);
            if (optional.isPresent()) {
                OrderDetail orderDetail = optional.get();
                orderDetail.setDeleted(true);
                singleSubscriber.onSuccess(orderDetailRepository.save(orderDetail));
            } else {
                singleSubscriber.onError(new ResourceNotFoundException("could not found for this id :: " + orderId));
            }
        });
    }
    @Transactional
    @Override
    public Single<OrderDetail> createOrder(OrderRequest request) {
        return Single.create(singleSubscriber -> {
            ResponseEntity<String> productId = productConnector.getProductDetail(request.getProductId());
            if(productId == null || productId.getStatusCode() != HttpStatus.OK) {
                singleSubscriber.onError(new ResourceNotFoundException("could not found for the product id :: " + request.getProductId()));
            }
            Orders order = Orders.builder()
                    .amount(request.getAmount())
                    .customerAddress(request.getCustomerAddress())
                    .customerName(request.getCustomerName())
                    .customerPhone(request.getCustomerPhone())
                    .customerEmail(request.getCustomerEmail())
                    .build();
            order = orderRepository.save(order);
            OrderDetail newOder =  OrderDetail.builder()
                    .amount(request.getAmount())
                    .price(request.getPrice())
                    .orderId(order.getId())
                    .productId(request.getProductId())
                    .quantity(request.getQuantity())
                    .status(Status.OPENING)
                    .build();
            newOder = orderDetailRepository.save(newOder);
            if (newOder == null) {
                singleSubscriber.onError(new EntityNotFoundException());
            } else {
                singleSubscriber.onSuccess(newOder);
            }
        });
    }

    @Override
    @Transactional
    public Single<OrderDetail> updateOrder(OrderDetail orderDetail, long orderId) throws ResourceNotFoundException {
        return Single.create(singleEmitter -> {
            Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(orderId);
            if (orderDetailOptional.isPresent()) {
                OrderDetail order = orderDetailOptional.get();
                order.setAmount(orderDetail.getAmount());
                order.setPrice(orderDetail.getPrice());
                singleEmitter.onSuccess(orderDetailRepository.save(orderDetail));
            } else {
                singleEmitter.onError(new EntityNotFoundException("orderId  " + orderId));
            }
        });
    }
}
