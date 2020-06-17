package com.dung.phan.order.service.connector;

import org.springframework.http.ResponseEntity;

public interface ProductConnector {
    ResponseEntity<String> getProductDetail(String productId);
}
