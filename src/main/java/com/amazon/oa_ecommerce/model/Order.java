package com.amazon.oa_ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private Long userId;
    private List<OrderItem> items;
    private OrderStatus status;
    private Double totalAmount;
    private LocalDateTime createdAt;
}