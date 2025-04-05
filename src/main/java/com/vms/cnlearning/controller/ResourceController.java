package com.vms.cnlearning.controller;

import com.vms.cnlearning.common.PageResult;
import com.vms.cnlearning.common.Result;
import com.vms.cnlearning.entity.Resource;
import com.vms.cnlearning.service.ResourceService;
import com.vms.cnlearning.vo.ResourceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源控制器
 */
@RestController
@Slf4j
@Tag(name = "学习资源", description = "包括资源上传、资源列表获取等功能")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;
    
    /**
     * 上传资源
     */
    @PostMapping("/resource/upload")
    @Operation(
        summary = "上传资源", 
        description = "上传视频或课件类型的学习资源",
        security = @SecurityRequirement(name = "JWT"),
        responses = {
            @ApiResponse(responseCode = "200", description = "上传成功"),
            @ApiResponse(responseCode = "400", description = "参数错误"),
            @ApiResponse(responseCode = "403", description = "权限不足")
        }
    )
    public Result<Void> uploadResource(
            @Parameter(description = "资源标题", required = true) @RequestParam("title") String title,
            @Parameter(description = "资源类型，可选值：视频、课件", required = true) @RequestParam("type") String type,
            @Parameter(description = "资源文件", required = true) @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        
        log.info("上传资源请求: title={}, type={}, fileSize={}", title, type, file.getSize());
        
        // 从请求属性中获取用户ID
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            log.warn("上传资源失败: 未获取到用户ID");
            return Result.error("未登录或登录已过期");
        }
        
        // 验证资源类型
        if (!"视频".equals(type) && !"课件".equals(type)) {
            log.warn("上传资源失败: 不支持的资源类型 {}", type);
            return Result.error("资源类型必须是'视频'或'课件'");
        }
        
        // 调用服务上传资源
        boolean success = resourceService.uploadResource(title, type, file, userId);
        
        if (success) {
            log.info("上传资源成功: title={}, type={}", title, type);
            return Result.success("资源上传成功");
        } else {
            log.warn("上传资源失败: title={}, type={}", title, type);
            return Result.error("资源上传失败");
        }
    }
    
    /**
     * 获取资源列表
     */
    @GetMapping("/resources")
    @Operation(
        summary = "获取资源列表", 
        description = "分页获取资源列表，可按类型筛选",
        responses = {
            @ApiResponse(responseCode = "200", description = "获取成功")
        }
    )
    public Result<PageResult<ResourceVO>> getResourceList(
            @Parameter(description = "资源类型，可选值：视频、课件") 
            @RequestParam(value = "type", required = false) String type,
            @Parameter(description = "页码，默认为1") 
            @RequestParam(value = "page", defaultValue = "1") int page,
            @Parameter(description = "每页大小，默认为10") 
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        
        log.info("获取资源列表: type={}, page={}, pageSize={}", type, page, pageSize);
        
        // 获取资源列表
        List<Resource> resources = resourceService.getResourceList(type, page, pageSize);
        
        // 获取总数
        long total = resourceService.getResourceCount(type);
        
        // 转换为VO对象
        List<ResourceVO> voList = resources.stream().map(resource -> {
            ResourceVO vo = new ResourceVO();
            BeanUtils.copyProperties(resource, vo);
            return vo;
        }).collect(Collectors.toList());
        
        // 封装分页结果
        PageResult<ResourceVO> pageResult = new PageResult<>(total, voList);
        
        log.info("获取资源列表成功: 总数={}, 当前页数量={}", total, voList.size());
        return Result.success("success", pageResult);
    }
} 