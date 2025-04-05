package com.vms.cnlearning.controller;

import com.vms.cnlearning.annotation.RequireRole;
import com.vms.cnlearning.common.PageResult;
import com.vms.cnlearning.common.Result;
import com.vms.cnlearning.dto.TestSubmitDTO;
import com.vms.cnlearning.entity.Test;
import com.vms.cnlearning.enums.RoleEnum;
import com.vms.cnlearning.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
@Slf4j
@Tag(name = "学习评估", description = "包括测试题获取、提交答案等功能")
public class TestController {

    @Autowired
    private TestService testService;
    
    /**
     * 提交测试答案
     * @param testSubmitDTO 测试提交DTO
     * @return 测试结果
     */
    @PostMapping("/submit")
    @RequireRole({RoleEnum.STUDENT, RoleEnum.TEACHER, RoleEnum.ADMIN})
    @Operation(
        summary = "提交测试答案", 
        description = "用户提交测试答案并获取分数",
        security = @SecurityRequirement(name = "JWT"),
        responses = {
            @ApiResponse(responseCode = "200", description = "提交成功"),
            @ApiResponse(responseCode = "400", description = "参数错误"),
            @ApiResponse(responseCode = "403", description = "权限不足")
        }
    )
    public Result<Map<String, Object>> submitTest(
            @Parameter(description = "测试提交信息", required = true)
            @RequestBody TestSubmitDTO testSubmitDTO) {
        log.info("提交测试答案: userId={}, 答案数量={}", testSubmitDTO.getUserId(), 
                testSubmitDTO.getAnswers() != null ? testSubmitDTO.getAnswers().size() : 0);
        
        // 参数校验
        if (testSubmitDTO.getUserId() == null || testSubmitDTO.getAnswers() == null || testSubmitDTO.getAnswers().isEmpty()) {
            return Result.error("参数错误，用户ID和答案不能为空");
        }
        
        // 调用服务计算得分
        int score = testService.submitAnswers(testSubmitDTO);
        
        // 封装结果
        Map<String, Object> data = new HashMap<>();
        data.put("score", score);
        
        log.info("测试结果: userId={}, score={}", testSubmitDTO.getUserId(), score);
        return Result.success("提交成功", data);
    }
    
    /**
     * 获取指定章节的测试题列表
     * @param chapter 章节
     * @return 测试题列表
     */
    @GetMapping("/chapter/{chapter}")
    @RequireRole({RoleEnum.STUDENT, RoleEnum.TEACHER, RoleEnum.ADMIN})
    @Operation(
        summary = "获取章节测试题", 
        description = "获取指定章节的所有测试题",
        security = @SecurityRequirement(name = "JWT"),
        responses = {
            @ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "403", description = "权限不足")
        }
    )
    public Result<List<Test>> getTestsByChapter(
            @Parameter(description = "章节名称", required = true)
            @PathVariable String chapter) {
        log.info("获取章节测试题: chapter={}", chapter);
        List<Test> tests = testService.getTestsByChapter(chapter);
        return Result.success("获取成功", tests);
    }
    
    /**
     * 获取所有测试题
     * @return 测试题列表
     */
    @GetMapping("/list")
    @RequireRole({RoleEnum.TEACHER, RoleEnum.ADMIN})
    @Operation(
        summary = "获取所有测试题", 
        description = "获取系统中所有的测试题（仅教师和管理员可用）",
        security = @SecurityRequirement(name = "JWT"),
        responses = {
            @ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "403", description = "权限不足")
        }
    )
    public Result<List<Test>> getAllTests() {
        log.info("获取所有测试题");
        List<Test> tests = testService.getAllTests();
        return Result.success("获取成功", tests);
    }
} 