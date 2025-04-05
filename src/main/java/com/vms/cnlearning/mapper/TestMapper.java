package com.vms.cnlearning.mapper;

import com.vms.cnlearning.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 测试Mapper接口
 */
@Mapper
public interface TestMapper {
    
    /**
     * 根据ID查询测试题
     * @param questionId 题目ID
     * @return 测试题
     */
    Test selectById(Integer questionId);
    
    /**
     * 根据章节查询测试题列表
     * @param chapter 章节
     * @return 测试题列表
     */
    List<Test> selectByChapter(@Param("chapter") String chapter);
    
    /**
     * 查询所有测试题
     * @return 测试题列表
     */
    List<Test> selectAll();
} 