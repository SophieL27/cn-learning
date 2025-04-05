package com.vms.cnlearning.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vms.cnlearning.annotation.RequireRole;
import com.vms.cnlearning.common.Result;
import com.vms.cnlearning.enums.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;

/**
 * 角色权限拦截器
 */
@Component
@Slf4j
public class RoleAuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        // 获取方法上的权限注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireRole requireRole = handlerMethod.getMethod().getAnnotation(RequireRole.class);
        
        // 如果方法上没有注解，则获取类上的注解
        if (requireRole == null) {
            requireRole = handlerMethod.getBeanType().getAnnotation(RequireRole.class);
        }
        
        // 如果没有权限注解，则不需要权限验证
        if (requireRole == null) {
            return true;
        }
        
        // 获取用户角色
        String userRole = (String) request.getAttribute("role");
        if (userRole == null) {
            handleUnauthorized(response, "未登录或登录已过期");
            return false;
        }
        
        // 获取权限注解中的角色
        RoleEnum[] allowedRoles = requireRole.value();
        RoleEnum userRoleEnum = RoleEnum.getByRoleName(userRole);
        
        // 检查用户角色是否在允许的角色中
        if (userRoleEnum != null && Arrays.asList(allowedRoles).contains(userRoleEnum)) {
            return true;
        } else {
            log.warn("权限不足: 用户角色={}, 需要角色={}", userRole, 
                    Arrays.stream(allowedRoles).map(RoleEnum::getRoleName).toArray());
            handleUnauthorized(response, "权限不足");
            return false;
        }
    }
    
    /**
     * 处理未授权的请求
     * @param response HTTP响应
     * @param message 错误消息
     */
    private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        
        Result<Void> result = Result.error(message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
} 