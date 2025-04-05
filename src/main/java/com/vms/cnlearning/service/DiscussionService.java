package com.vms.cnlearning.service;

import com.vms.cnlearning.dto.DiscussionDTO;
import com.vms.cnlearning.entity.Discussion;

import java.util.List;

/**
 * 讨论区Service接口
 */
public interface DiscussionService {
    
    /**
     * 发布讨论帖
     * @param discussionDTO 讨论帖DTO
     * @param authorId 作者ID
     * @return 是否成功
     */
    boolean createDiscussion(DiscussionDTO discussionDTO, Integer authorId);
    
    /**
     * 获取讨论帖
     * @param postId 帖子ID
     * @return 讨论帖
     */
    Discussion getDiscussionById(Integer postId);
    
    /**
     * 根据类型获取讨论帖列表
     * @param type 讨论类型
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 讨论帖列表
     */
    List<Discussion> getDiscussionsByType(String type, int page, int pageSize);
    
    /**
     * 根据作者ID获取讨论帖列表
     * @param authorId 作者ID
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 讨论帖列表
     */
    List<Discussion> getDiscussionsByAuthorId(Integer authorId, int page, int pageSize);
    
    /**
     * 获取所有讨论帖列表
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 讨论帖列表
     */
    List<Discussion> getAllDiscussions(int page, int pageSize);
    
    /**
     * 获取讨论帖总数
     * @return 总数
     */
    long getDiscussionCount();
    
    /**
     * 根据类型获取讨论帖总数
     * @param type 讨论类型
     * @return 总数
     */
    long getDiscussionCountByType(String type);
} 