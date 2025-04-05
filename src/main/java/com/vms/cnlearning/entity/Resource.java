package com.vms.cnlearning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 资源实体类
 */
@Data
public class Resource {
    private Integer resId;
    private String title;
    private String type;
    private String filePath;
    private Integer uploaderId;
    private LocalDateTime createdAt;
    
    // 关联属性（非数据库字段）
    private String uploader;
} 