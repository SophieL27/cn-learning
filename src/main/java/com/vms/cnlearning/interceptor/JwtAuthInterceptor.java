package com.vms.cnlearning.interceptor;

import com.vms.cnlearning.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT认证拦截器
 */
@Component
@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
            
            try {
                // 验证token有效性
                String username = jwtUtil.extractUsername(token);
                if (StringUtils.hasText(username) && !jwtUtil.isTokenExpired(token)) {
                    // 将用户信息存入request属性
                    request.setAttribute("userId", jwtUtil.extractClaim(token, claims -> claims.get("userId", Integer.class)));
                    request.setAttribute("username", username);
                    request.setAttribute("role", jwtUtil.extractClaim(token, claims -> claims.get("role", String.class)));
                    return true;
                }
            } catch (Exception e) {
                log.error("Token验证失败", e);
            }
        }
        
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"code\":0,\"msg\":\"未授权\"}");
        return false;
    }
} 