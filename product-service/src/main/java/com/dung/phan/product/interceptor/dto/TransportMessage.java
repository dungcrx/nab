package com.dung.phan.product.interceptor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class TransportMessage {

    private Map<String, String> header;

    private String body;
}
