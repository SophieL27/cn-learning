package com.vms.cnlearning.service;

import com.vms.cnlearning.dto.TestSubmitDTO;
import com.vms.cnlearning.entity.Test;

import java.util.List;

/**
 * 测试Service接口
 */
public interface TestService {
    
    /**
     * 提交测试答案并计算分数
     * @param testSubmitDTO 提交的答案
     * @return 得分（百分制）
     */
    int submitAnswers(TestSubmitDTO testSubmitDTO);
    
    /**
     * 获取测试题
     * @param questionId 题目ID
     * @return 测试题
     */
    Test getTestById(Integer questionId);
    
    /**
     * 根据章节获取测试题
     * @param chapter 章节
     * @return 测试题列表
     */
    List<Test> getTestsByChapter(String chapter);
    
    /**
     * 获取所有测试题
     * @return 测试题列表
     */
    List<Test> getAllTests();
} 