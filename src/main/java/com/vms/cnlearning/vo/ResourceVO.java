package com.vms.cnlearning.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 资源VO类，用于返回给前端
 */
@Data
public class ResourceVO {
    private Integer resId;
    private String title;
    private String type;
    private String uploader;
    private LocalDateTime createdAt;
} 