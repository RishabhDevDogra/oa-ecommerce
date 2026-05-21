package com.amazon.oa_ecommerce.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
}
