package com.vms.cnlearning.service.impl;

import com.github.pagehelper.PageHelper;
import com.vms.cnlearning.dto.DiscussionDTO;
import com.vms.cnlearning.entity.Discussion;
import com.vms.cnlearning.mapper.DiscussionMapper;
import com.vms.cnlearning.service.DiscussionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 讨论区Service实现类
 */
@Service
@Slf4j
public class DiscussionServiceImpl implements DiscussionService {

    @Autowired
    private DiscussionMapper discussionMapper;
    
    /**
     * 发布讨论帖
     * @param discussionDTO 讨论帖DTO
     * @param authorId 作者ID
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean createDiscussion(DiscussionDTO discussionDTO, Integer authorId) {
        Discussion discussion = new Discussion();
        discussion.setTitle(discussionDTO.getTitle());
        discussion.setContent(discussionDTO.getContent());
        discussion.setType(discussionDTO.getType());
        discussion.setAuthorId(authorId);
        
        int rows = discussionMapper.insert(discussion);
        
        if (rows > 0) {
            log.info("用户ID：{}，成功发布讨论帖：{}", authorId, discussion.getPostId());
            return true;
        } else {
            log.error("用户ID：{}，发布讨论帖失败", authorId);
            return false;
        }
    }
    
    /**
     * 获取讨论帖
     * @param postId 帖子ID
     * @return 讨论帖
     */
    @Override
    public Discussion getDiscussionById(Integer postId) {
        return discussionMapper.selectById(postId);
    }
    
    /**
     * 根据类型获取讨论帖列表
     * @param type 讨论类型
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 讨论帖列表
     */
    @Override
    public List<Discussion> getDiscussionsByType(String type, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return discussionMapper.selectByType(type);
    }
    
    /**
     * 根据作者ID获取讨论帖列表
     * @param authorId 作者ID
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 讨论帖列表
     */
    @Override
    public List<Discussion> getDiscussionsByAuthorId(Integer authorId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return discussionMapper.selectByAuthorId(authorId);
    }
    
    /**
     * 获取所有讨论帖列表
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 讨论帖列表
     */
    @Override
    public List<Discussion> getAllDiscussions(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return discussionMapper.selectAll();
    }
    
    /**
     * 获取讨论帖总数
     * @return 总数
     */
    @Override
    public long getDiscussionCount() {
        return PageHelper.count(() -> discussionMapper.selectAll());
    }
    
    /**
     * 根据类型获取讨论帖总数
     * @param type 讨论类型
     * @return 总数
     */
    @Override
    public long getDiscussionCountByType(String type) {
        return PageHelper.count(() -> discussionMapper.selectByType(type));
    }
} 