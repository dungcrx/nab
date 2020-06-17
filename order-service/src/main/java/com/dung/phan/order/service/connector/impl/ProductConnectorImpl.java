package com.dung.phan.order.service.connector.impl;

import com.dung.phan.order.service.connector.ProductConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ProductConnectorImpl implements ProductConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> getProductDetail(String productId) {
        try {
            String productUrl = "http://localhost:8080/api/v1/product/%s";
            return restTemplate.getForEntity(String.format(productUrl, productId), String.class);
        } catch (Exception exception){
            if(exception instanceof HttpServerErrorException || exception instanceof HttpClientErrorException)
                log.error("You having an issue {}",  ((HttpStatusCodeException) exception).getResponseBodyAsString());
            return null;
        }
    }
}
