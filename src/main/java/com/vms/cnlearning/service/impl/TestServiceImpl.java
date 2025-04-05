package com.vms.cnlearning.service.impl;

import com.vms.cnlearning.dto.TestAnswerDTO;
import com.vms.cnlearning.dto.TestSubmitDTO;
import com.vms.cnlearning.entity.Test;
import com.vms.cnlearning.mapper.TestMapper;
import com.vms.cnlearning.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 测试Service实现类
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    
    @Autowired
    private TestMapper testMapper;
    
    /**
     * 提交测试答案并计算分数
     * @param testSubmitDTO 提交的答案
     * @return 得分（百分制）
     */
    @Override
    public int submitAnswers(TestSubmitDTO testSubmitDTO) {
        // 获取所有提交的答案
        List<TestAnswerDTO> answers = testSubmitDTO.getAnswers();
        if (answers == null || answers.isEmpty()) {
            return 0;
        }
        
        // 获取所有提交的题目ID
        List<Integer> questionIds = answers.stream()
                .map(TestAnswerDTO::getQuestionId)
                .collect(Collectors.toList());
        
        // 创建答案映射（题目ID -> 选择的答案）
        Map<Integer, String> userAnswers = new HashMap<>();
        for (TestAnswerDTO answer : answers) {
            userAnswers.put(answer.getQuestionId(), answer.getSelected());
        }
        
        // 计算得分
        int correctCount = 0;
        for (Integer questionId : questionIds) {
            Test test = testMapper.selectById(questionId);
            if (test != null) {
                String correctAns = test.getCorrectAns();
                String userAns = userAnswers.get(questionId);
                
                if (correctAns.equals(userAns)) {
                    correctCount++;
                }
            }
        }
        
        // 计算百分比得分（四舍五入到整数）
        int totalQuestions = questionIds.size();
        int score = (int) Math.round((double) correctCount / totalQuestions * 100);
        
        log.info("用户ID：{}，提交了{}道题，正确{}道，得分：{}", 
                testSubmitDTO.getUserId(), totalQuestions, correctCount, score);
        
        return score;
    }
    
    /**
     * 获取测试题
     * @param questionId 题目ID
     * @return 测试题
     */
    @Override
    public Test getTestById(Integer questionId) {
        return testMapper.selectById(questionId);
    }
    
    /**
     * 根据章节获取测试题
     * @param chapter 章节
     * @return 测试题列表
     */
    @Override
    public List<Test> getTestsByChapter(String chapter) {
        return testMapper.selectByChapter(chapter);
    }
    
    /**
     * 获取所有测试题
     * @return 测试题列表
     */
    @Override
    public List<Test> getAllTests() {
        return testMapper.selectAll();
    }
} 