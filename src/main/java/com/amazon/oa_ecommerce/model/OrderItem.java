package com.amazon.oa_ecommerce.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderItem {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
}