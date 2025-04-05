package com.vms.cnlearning.config;

import com.vms.cnlearning.interceptor.JwtAuthInterceptor;
import com.vms.cnlearning.interceptor.RoleAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthInterceptor jwtAuthInterceptor;
    
    @Autowired
    private RoleAuthInterceptor roleAuthInterceptor;

    /**
     * 配置跨域请求
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }
    
    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // JWT认证拦截器
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/resource/**", "/test/submit", "/discussion")
                .excludePathPatterns("/login", "/register", "/test/**",
                        "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", 
                        "/api-docs/**", "/swagger-resources/**", "/webjars/**", 
                        "/doc.html");
        
        // 角色权限拦截器
        registry.addInterceptor(roleAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/test/**",
                        "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", 
                        "/api-docs/**", "/swagger-resources/**", "/webjars/**", 
                        "/doc.html");
    }
} 