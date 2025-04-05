package com.vms.cnlearning.dto;

import lombok.Data;

/**
 * 单个测试题答案DTO
 */
@Data
public class TestAnswerDTO {
    private Integer questionId;
    private String selected;
} 