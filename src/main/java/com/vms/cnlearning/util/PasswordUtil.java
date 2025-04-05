package com.vms.cnlearning.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 密码工具类
 */
public class PasswordUtil {

    /**
     * 使用SHA-256对密码进行哈希处理
     * @param password 原始密码
     * @return 哈希后的密码
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("哈希密码时出错", e);
        }
    }

    /**
     * 验证密码是否匹配
     * @param rawPassword 原始密码
     * @param hashedPassword 哈希后的密码
     * @return 是否匹配
     */
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        String hashedInput = hashPassword(rawPassword);
        return hashedInput.equals(hashedPassword);
    }
} 