package com.vms.cnlearning.service.impl;

import com.github.pagehelper.PageHelper;
import com.vms.cnlearning.entity.Resource;
import com.vms.cnlearning.mapper.ResourceMapper;
import com.vms.cnlearning.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 资源服务实现类
 */
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Value("${upload.allowed-types}")
    private String allowedTypes;

    /**
     * 上传资源
     * @param title 资源标题
     * @param type 资源类型
     * @param file 文件
     * @param uploaderId 上传者ID
     * @return 上传结果
     */
    @Override
    @Transactional
    public boolean uploadResource(String title, String type, MultipartFile file, Integer uploaderId) {
        if (file.isEmpty()) {
            log.error("文件上传失败：文件为空");
            return false;
        }
        
        // 验证文件类型
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        if (!isValidFileType(extension)) {
            log.error("文件上传失败：不支持的文件类型 {}", extension);
            return false;
        }
        
        try {
            // 创建上传目录
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            // 生成唯一文件名
            String uniqueFilename = generateUniqueFilename(originalFilename);
            String filePath = uploadPath + File.separator + uniqueFilename;
            
            // 保存文件
            Path targetLocation = uploadDir.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation);
            
            // 保存资源记录到数据库
            Resource resource = new Resource();
            resource.setTitle(title);
            resource.setType(type);
            resource.setFilePath(filePath);
            resource.setUploaderId(uploaderId);
            
            int rows = resourceMapper.insert(resource);
            
            log.info("文件上传成功：{}, 资源ID: {}", filePath, resource.getResId());
            return rows > 0;
            
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return false;
        }
    }

    /**
     * 获取资源列表
     * @param type 资源类型（可选）
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 分页资源列表
     */
    @Override
    public List<Resource> getResourceList(String type, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return resourceMapper.selectList(type);
    }

    /**
     * 获取资源总数
     * @param type 资源类型（可选）
     * @return 资源总数
     */
    @Override
    public long getResourceCount(String type) {
        // 这里利用PageHelper获取总数
        PageHelper.startPage(1, 1);
        List<Resource> list = resourceMapper.selectList(type);
        return PageHelper.count(() -> resourceMapper.selectList(type));
    }
    
    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        String extension = "";
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = filename.substring(lastDotIndex + 1).toLowerCase();
        }
        return extension;
    }
    
    /**
     * 验证文件类型是否允许
     */
    private boolean isValidFileType(String extension) {
        if (!StringUtils.hasText(extension)) {
            return false;
        }
        List<String> allowedTypesList = Arrays.asList(allowedTypes.split(","));
        return allowedTypesList.contains(extension.toLowerCase());
    }
    
    /**
     * 生成唯一文件名
     */
    private String generateUniqueFilename(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String datePrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        
        return datePrefix + "_" + uuid + "." + extension;
    }
} 