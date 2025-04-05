package com.vms.cnlearning.service;

import com.vms.cnlearning.entity.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 资源服务接口
 */
public interface ResourceService {

    /**
     * 上传资源
     * @param title 资源标题
     * @param type 资源类型
     * @param file 文件
     * @param uploaderId 上传者ID
     * @return 上传结果
     */
    boolean uploadResource(String title, String type, MultipartFile file, Integer uploaderId);
    
    /**
     * 获取资源列表
     * @param type 资源类型（可选）
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 分页资源列表
     */
    List<Resource> getResourceList(String type, int page, int pageSize);
    
    /**
     * 获取资源总数
     * @param type 资源类型（可选）
     * @return 资源总数
     */
    long getResourceCount(String type);
} 