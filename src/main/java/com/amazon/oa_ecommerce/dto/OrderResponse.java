package com.amazon.oa_ecommerce.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private String status;
    private double totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
