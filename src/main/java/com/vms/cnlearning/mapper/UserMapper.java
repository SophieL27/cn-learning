package com.vms.cnlearning.mapper;

import com.vms.cnlearning.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);
    
    /**
     * 插入用户
     */
    int insert(User user);
    
    /**
     * 检查用户名是否存在
     */
    int checkUsernameExists(String username);
} 