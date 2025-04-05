package com.vms.cnlearning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用程序主类
 */
@SpringBootApplication
@MapperScan("com.vms.cnlearning.mapper")
@EnableTransactionManagement
public class CnLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(CnLearningApplication.class, args);
        System.out.println("===============================================");
        System.out.println("    计算机网络学习平台已启动");
        System.out.println("    - 用户管理");
        System.out.println("    - 学习评估");
        System.out.println("    - 讨论互动");
        System.out.println("===============================================");
    }

}
