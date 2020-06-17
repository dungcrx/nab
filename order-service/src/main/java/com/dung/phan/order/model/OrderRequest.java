package com.dung.phan.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private double amount;
    private String customerAddress;
    private String customerEmail;
    private String customerName;
    private String customerPhone;
    private Integer orderNum;
    private double price;
    private int quantity;
    private String productId;
}
