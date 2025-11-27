package com.aicoding.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 流程引擎启动类
 * @author gll
 * dateTime 2025/11/27 15:33
 */
@SpringBootApplication
@MapperScan("com.aicoding.flow.mapper")
public class FlowEngineApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FlowEngineApplication.class, args);
    }
}