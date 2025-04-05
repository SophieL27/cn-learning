package com.vms.cnlearning.controller;

import com.vms.cnlearning.annotation.RequireRole;
import com.vms.cnlearning.common.PageResult;
import com.vms.cnlearning.common.Result;
import com.vms.cnlearning.dto.DiscussionDTO;
import com.vms.cnlearning.entity.Discussion;
import com.vms.cnlearning.enums.RoleEnum;
import com.vms.cnlearning.service.DiscussionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讨论区控制器
 */
@RestController
@Slf4j
@Tag(name = "讨论互动", description = "包括发布讨论帖、查看讨论等功能")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;
    
    /**
     * 发布讨论帖
     * @param discussionDTO 讨论帖DTO
     * @param request HTTP请求
     * @return 操作结果
     */
    @PostMapping("/discussion")
    @RequireRole({RoleEnum.STUDENT, RoleEnum.TEACHER, RoleEnum.ADMIN})
    @Operation(
        summary = "发布讨论帖", 
        description = "发布课程讨论或实验答疑类型的讨论帖",
        security = @SecurityRequirement(name = "JWT"),
        responses = {
            @ApiResponse(responseCode = "200", description = "发布成功"),
            @ApiResponse(responseCode = "400", description = "参数错误"),
            @ApiResponse(responseCode = "403", description = "权限不足")
        }
    )
    public Result<Void> createDiscussion(
            @Parameter(description = "讨论帖信息", required = true)
            @RequestBody DiscussionDTO discussionDTO,
            HttpServletRequest request) {
        
        log.info("发布讨论帖: title={}, type={}", discussionDTO.getTitle(), discussionDTO.getType());
        
        // 从请求属性中获取用户ID
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            log.warn("发布讨论帖失败: 未获取到用户ID");
            return Result.error("未登录或登录已过期");
        }
        
        // 验证讨论类型
        String type = discussionDTO.getType();
        if (!"课程讨论".equals(type) && !"实验答疑".equals(type)) {
            log.warn("发布讨论帖失败: 不支持的讨论类型 {}", type);
            return Result.error("讨论类型必须是'课程讨论'或'实验答疑'");
        }
        
        // 标题和内容不能为空
        if (discussionDTO.getTitle() == null || discussionDTO.getTitle().isEmpty() ||
            discussionDTO.getContent() == null || discussionDTO.getContent().isEmpty()) {
            return Result.error("标题和内容不能为空");
        }
        
        // 调用服务发布讨论帖
        boolean success = discussionService.createDiscussion(discussionDTO, userId);
        
        if (success) {
            log.info("发布讨论帖成功: title={}, type={}, userId={}", 
                    discussionDTO.getTitle(), discussionDTO.getType(), userId);
            return Result.success("发帖成功");
        } else {
            log.warn("发布讨论帖失败: title={}, type={}, userId={}", 
                    discussionDTO.getTitle(), discussionDTO.getType(), userId);
            return Result.error("发帖失败");
        }
    }
    
    /**
     * 获取讨论帖列表
     * @param type 讨论类型（可选）
     * @param page 页码
     * @param pageSize 每页大小
     * @return 讨论帖列表
     */
    @GetMapping("/discussions")
    @RequireRole({RoleEnum.STUDENT, RoleEnum.TEACHER, RoleEnum.ADMIN})
    @Operation(
        summary = "获取讨论帖列表", 
        description = "分页获取讨论帖列表，可按类型筛选",
        security = @SecurityRequirement(name = "JWT"),
        responses = {
            @ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "403", description = "权限不足")
        }
    )
    public Result<PageResult<Discussion>> getDiscussions(
            @Parameter(description = "讨论类型，可选值：课程讨论、实验答疑") 
            @RequestParam(value = "type", required = false) String type,
            @Parameter(description = "页码，默认为1") 
            @RequestParam(value = "page", defaultValue = "1") int page,
            @Parameter(description = "每页大小，默认为10") 
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        
        log.info("获取讨论帖列表: type={}, page={}, pageSize={}", type, page, pageSize);
        
        List<Discussion> discussions;
        long total;
        
        // 根据类型查询不同的数据
        if (type != null && !type.isEmpty()) {
            discussions = discussionService.getDiscussionsByType(type, page, pageSize);
            total = discussionService.getDiscussionCountByType(type);
        } else {
            discussions = discussionService.getAllDiscussions(page, pageSize);
            total = discussionService.getDiscussionCount();
        }
        
        // 封装分页结果
        PageResult<Discussion> pageResult = new PageResult<>(total, discussions);
        
        log.info("获取讨论帖列表成功: 总数={}, 当前页数量={}", total, discussions.size());
        return Result.success("获取成功", pageResult);
    }
    
    /**
     * 获取指定讨论帖
     * @param postId 帖子ID
     * @return 讨论帖
     */
    @GetMapping("/discussion/{postId}")
    @RequireRole({RoleEnum.STUDENT, RoleEnum.TEACHER, RoleEnum.ADMIN})
    @Operation(
        summary = "获取指定讨论帖", 
        description = "获取指定ID的讨论帖详情",
        security = @SecurityRequirement(name = "JWT"),
        responses = {
            @ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "400", description = "讨论帖不存在"),
            @ApiResponse(responseCode = "403", description = "权限不足")
        }
    )
    public Result<Discussion> getDiscussion(
            @Parameter(description = "讨论帖ID", required = true)
            @PathVariable Integer postId) {
        log.info("获取讨论帖: postId={}", postId);
        
        Discussion discussion = discussionService.getDiscussionById(postId);
        
        if (discussion != null) {
            return Result.success("获取成功", discussion);
        } else {
            return Result.error("讨论帖不存在");
        }
    }
} 