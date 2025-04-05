package com.vms.cnlearning.controller;

import com.vms.cnlearning.common.PageResult;
import com.vms.cnlearning.common.Result;
import com.vms.cnlearning.entity.Resource;
import com.vms.cnlearning.service.ResourceService;
import com.vms.cnlearning.vo.ResourceVO;
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
public class ResourceController {

    @Autowired
    private ResourceService resourceService;
    
    /**
     * 上传资源
     */
    @PostMapping("/resource/upload")
    public Result<Void> uploadResource(
            @RequestParam("title") String title,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file,
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
    public Result<PageResult<ResourceVO>> getResourceList(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page", defaultValue = "1") int page,
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