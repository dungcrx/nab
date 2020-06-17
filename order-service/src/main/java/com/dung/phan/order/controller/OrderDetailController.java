package com.dung.phan.order.controller;

import com.dung.phan.order.exception.ResourceNotFoundException;
import com.dung.phan.order.model.OrderDetail;
import com.dung.phan.order.model.OrderRequest;
import com.dung.phan.order.model.Orders;
import com.dung.phan.order.service.OrderDetailService;
import com.dung.phan.order.service.OrderService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/orderdetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
    @GetMapping("/{id}")
    public Observable<OrderDetail> getOrderDetailById(@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException {
        return Observable.just(orderId)
                .map(id -> orderDetailService.getOrderById(orderId))
                .doOnError(ex -> log.error(" Could not get item id = {}", ex.getMessage()))
                .doOnComplete(() -> log.info(" finishing get item is = {}", orderId));
    }

    @PostMapping("/create")
    public Single<OrderDetail> createOrder(@Valid @RequestBody OrderRequest request) {
        return orderDetailService.createOrder(request)
                .doOnSuccess(newOrder -> log.info("Create new order with id= {}",newOrder.getId()))
                .doOnError(ex -> log.error("Count not create new order {}",ex.getMessage()));
    }
    @PutMapping("/{id}")
    public Single<OrderDetail> updateOrder(@PathVariable(value = "id") Long orderId,
                                         @Valid @RequestBody OrderDetail orderDetail) throws ResourceNotFoundException {

        return orderDetailService.updateOrder(orderDetail, orderId)
                .doOnSuccess(newOrder -> log.info("Create new order with id= {}",newOrder.getId()))
                .doOnError(ex -> log.error("Count not create new order {}",ex.getMessage()));
    }

    @DeleteMapping("/{id}")
    public Single<OrderDetail> deleteOrder(@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException {
        return orderDetailService.deleteOrderById(orderId)
                .doOnSuccess(newOrder -> log.info("Deleted with id= {}",newOrder.getId()))
                .doOnError(ex -> log.error("Count not Deleted Order {}",ex.getMessage()));
    }


}
