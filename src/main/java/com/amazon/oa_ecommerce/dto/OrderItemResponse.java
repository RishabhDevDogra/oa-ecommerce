package com.amazon.oa_ecommerce.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
}
