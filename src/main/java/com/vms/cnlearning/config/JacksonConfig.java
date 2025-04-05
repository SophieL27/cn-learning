package com.vms.cnlearning.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Jackson配置类
 */
@Configuration
public class JacksonConfig {
    
    /**
     * 创建ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
} 