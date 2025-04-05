package com.vms.cnlearning.controller;

import com.vms.cnlearning.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统测试控制器
 */
@RestController
@Slf4j
@Tag(name = "系统测试", description = "系统功能测试接口")
public class SystemTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private UserMapper userMapper;

    /**
     * 测试应用是否正常运行
     */
    @GetMapping("/test")
    @Operation(
        summary = "系统状态检查", 
        description = "检查系统是否正常运行",
        responses = {
            @ApiResponse(responseCode = "200", description = "系统正常")
        }
    )
    public Map<String, Object> test() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "计算机网络用户管理系统运行正常");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
    
    /**
     * 测试数据库连接是否正常
     */
    @GetMapping("/test/db")
    public Map<String, Object> testDb() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 测试数据库连接
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
            
            // 测试Mapper
            Integer userCount = userMapper.checkUsernameExists("admin001");
            
            result.put("status", "success");
            result.put("message", "数据库连接正常");
            result.put("userCount", count);
            result.put("adminExists", userCount > 0 ? "是" : "否");
            result.put("timestamp", System.currentTimeMillis());
            
            log.info("数据库测试成功，用户数量: {}", count);
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "数据库连接异常: " + e.getMessage());
            result.put("timestamp", System.currentTimeMillis());
            
            log.error("数据库测试失败", e);
        }
        
        return result;
    }
}

 