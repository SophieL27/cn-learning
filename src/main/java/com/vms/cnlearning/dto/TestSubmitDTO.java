package com.vms.cnlearning.dto;

import lombok.Data;
import java.util.List;

/**
 * 测试提交DTO
 */
@Data
public class TestSubmitDTO {
    private Integer userId;
    private List<TestAnswerDTO> answers;
} 