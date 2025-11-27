package com.aicoding.flow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 流程引擎配置类
 * @author gll
 * dateTime 2025/11/27 15:31
 */
@Configuration
public class FlowEngineConfig {
    
    /**
     * 配置流程引擎状态管理器
     * @return 流程引擎状态管理器
     */
    @Bean
    public com.aicoding.flow.engine.status.FlowEngineStateManager flowEngineStateManager() {
        return new com.aicoding.flow.engine.status.FlowEngineStateManager();
    }
    
    /**
     * 配置流程引擎
     * @return 流程引擎
     */
    @Bean
    public com.aicoding.flow.engine.FlowEngine flowEngine() {
        return new com.aicoding.flow.engine.FlowEngine();
    }
}