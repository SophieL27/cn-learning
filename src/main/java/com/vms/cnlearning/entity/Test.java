package com.vms.cnlearning.entity;

import lombok.Data;

/**
 * 测试题实体类
 */
@Data
public class Test {
    private Integer questionId;
    private String content;
    private String correctAns;
    private String chapter;
} 