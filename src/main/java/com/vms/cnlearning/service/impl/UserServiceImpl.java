package com.vms.cnlearning.service.impl;

import com.vms.cnlearning.dto.LoginRequest;
import com.vms.cnlearning.dto.RegisterRequest;
import com.vms.cnlearning.entity.User;
import com.vms.cnlearning.mapper.UserMapper;
import com.vms.cnlearning.service.UserService;
import com.vms.cnlearning.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 登录成功返回用户，失败返回null
     */
    @Override
    public User login(LoginRequest loginRequest) {
        // 根据用户名查找用户
        User user = userMapper.findByUsername(loginRequest.getUsername());
        
        // 验证用户存在且密码匹配且角色匹配
        if (user != null && 
            PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getPasswordHash()) &&
            user.getRole().equals(loginRequest.getRole())) {
            return user;
        }
        
        return null;
    }

    /**
     * 用户注册
     * @param registerRequest 注册请求
     * @return 注册成功返回true，失败返回false
     */
    @Override
    @Transactional
    public boolean register(RegisterRequest registerRequest) {
        // 检查用户名是否已存在
        if (userMapper.checkUsernameExists(registerRequest.getUsername()) > 0) {
            return false;
        }
        
        // 创建新用户
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPasswordHash(PasswordUtil.hashPassword(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        
        // 插入用户
        return userMapper.insert(user) > 0;
    }
} 