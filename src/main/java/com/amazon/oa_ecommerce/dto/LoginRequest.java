package com.amazon.oa_ecommerce.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
