package com.vms.cnlearning.service;

import com.vms.cnlearning.dto.LoginRequest;
import com.vms.cnlearning.dto.RegisterRequest;
import com.vms.cnlearning.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 登录成功返回用户，失败返回null
     */
    User login(LoginRequest loginRequest);

    /**
     * 用户注册
     * @param registerRequest 注册请求
     * @return 注册成功返回true，失败返回false
     */
    boolean register(RegisterRequest registerRequest);
} 