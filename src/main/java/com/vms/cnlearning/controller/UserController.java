package com.vms.cnlearning.controller;

import com.vms.cnlearning.common.Result;
import com.vms.cnlearning.dto.LoginRequest;
import com.vms.cnlearning.dto.RegisterRequest;
import com.vms.cnlearning.entity.User;
import com.vms.cnlearning.service.UserService;
import com.vms.cnlearning.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        log.info("用户登录请求：用户名={}, 角色={}", loginRequest.getUsername(), loginRequest.getRole());
        
        // 参数校验
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null || loginRequest.getRole() == null) {
            log.warn("登录失败：请求参数不完整");
            return Result.error("用户名、密码和角色不能为空");
        }
        
        // 调用服务进行登录验证
        User user = userService.login(loginRequest);
        
        if (user != null) {
            // 生成JWT令牌
            String token = jwtUtil.generateToken(user);
            
            // 返回令牌
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("userId", user.getUserId().toString());
            data.put("username", user.getUsername());
            data.put("role", user.getRole());
            
            log.info("用户登录成功：用户名={}, 角色={}", user.getUsername(), user.getRole());
            return Result.success("登录成功", data);
        } else {
            log.warn("用户登录失败：用户名={}, 角色={}", loginRequest.getUsername(), loginRequest.getRole());
            return Result.error("用户名、密码或角色不正确");
        }
    }

    /**
     * 用户注册
     * @param registerRequest 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegisterRequest registerRequest) {
        log.info("用户注册请求：用户名={}, 角色={}", registerRequest.getUsername(), registerRequest.getRole());
        
        // 参数校验
        if (registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getRole() == null) {
            log.warn("注册失败：请求参数不完整");
            return Result.error("用户名、密码和角色不能为空");
        }
        
        // 校验角色是否合法（只能是学生、教师或管理员）
        if (!registerRequest.getRole().equals("学生") && 
            !registerRequest.getRole().equals("教师") && 
            !registerRequest.getRole().equals("管理员")) {
            log.warn("注册失败：角色不合法，role={}", registerRequest.getRole());
            return Result.error("角色必须是学生、教师或管理员");
        }
        
        // 调用服务进行注册
        boolean success = userService.register(registerRequest);
        
        if (success) {
            log.info("用户注册成功：用户名={}, 角色={}", registerRequest.getUsername(), registerRequest.getRole());
            return Result.success("注册成功");
        } else {
            log.warn("用户注册失败：用户名已存在，username={}", registerRequest.getUsername());
            return Result.error("注册失败，用户名已存在");
        }
    }
} 