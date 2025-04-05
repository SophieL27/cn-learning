package com.vms.cnlearning.mapper;

import com.vms.cnlearning.entity.Discussion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 讨论区Mapper接口
 */
@Mapper
public interface DiscussionMapper {
    
    /**
     * 插入讨论帖
     * @param discussion 讨论帖对象
     * @return 影响行数
     */
    int insert(Discussion discussion);
    
    /**
     * 根据ID查询讨论帖
     * @param postId 帖子ID
     * @return 讨论帖
     */
    Discussion selectById(Integer postId);
    
    /**
     * 根据类型查询讨论帖列表
     * @param type 讨论类型
     * @return 讨论帖列表
     */
    List<Discussion> selectByType(@Param("type") String type);
    
    /**
     * 根据作者ID查询讨论帖列表
     * @param authorId 作者ID
     * @return 讨论帖列表
     */
    List<Discussion> selectByAuthorId(@Param("authorId") Integer authorId);
    
    /**
     * 查询所有讨论帖
     * @return 讨论帖列表
     */
    List<Discussion> selectAll();
} 