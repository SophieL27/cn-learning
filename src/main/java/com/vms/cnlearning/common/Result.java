package com.vms.cnlearning.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code; // 1成功，0失败
    private String msg;   // 提示信息
    private T data;       // 数据

    // 成功返回结果
    public static <T> Result<T> success(String msg) {
        return new Result<>(1, msg, null);
    }

    // 成功返回结果带数据
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(1, msg, data);
    }

    // 失败返回结果
    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, null);
    }
} 