package com.vms.cnlearning.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 讨论区实体类
 */
@Data
public class Discussion {
    private Integer postId;
    private String title;
    private String content;
    private Integer authorId;
    private String type;
    private LocalDateTime createdAt;
    
    // 关联属性（非数据库字段）
    private String authorName;
} 