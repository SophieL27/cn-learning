package com.vms.cnlearning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
public class User {
    private Integer userId;
    private String username;
    private String passwordHash;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime createdAt;
} 