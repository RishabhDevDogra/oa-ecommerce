package com.amazon.oa_ecommerce.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
    @NotNull(message = "Order items must not be null")
    @NotEmpty(message = "Order must have at least one item")
    private List<@NotNull OrderItemRequest> items;
}
