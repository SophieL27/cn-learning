package com.vms.cnlearning.dto;

import lombok.Data;

/**
 * 登录请求DTO
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
    private String role;
} 