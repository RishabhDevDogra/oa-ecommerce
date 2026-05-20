package com.amazon.oa_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
}