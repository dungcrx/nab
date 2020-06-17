package com.dung.phan.order.controller;

import com.dung.phan.order.model.Orders;
import com.dung.phan.order.service.OrderService;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public Observable<Orders> getListOrder(@PageableDefault(page = 0, size = 10)
                                            @SortDefault.SortDefaults
                                                    ({@SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                                    Pageable pageRequest) {
        return Observable.fromIterable(orderService.getOrderList(pageRequest))
                .doOnComplete(() -> log.info("Finished the get List items"))
                .doOnError(ex -> log.error("Could not get list item because {} ", ex.getMessage()));
    }

}
