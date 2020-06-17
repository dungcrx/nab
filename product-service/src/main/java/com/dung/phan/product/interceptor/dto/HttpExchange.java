package com.dung.phan.product.interceptor.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HttpExchange {

    private String url;
    private String method;
    private Long dateTimeStamp;
    private String body;
}