package com.aicoding.flow.service;

import com.aicoding.flow.entity.FlowDefinition;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程定义Service接口
 * @author gll
 * dateTime 2025/11/27 15:20
 */
public interface FlowDefinitionService extends IService<FlowDefinition> {
    
    /**
     * 根据流程编码和版本查询流程定义
     * @param code 流程编码
     * @param version 流程版本
     * @return 流程定义
     */
    FlowDefinition getByCodeAndVersion(String code, Integer version);
    
    /**
     * 根据流程编码查询最新版本的流程定义
     * @param code 流程编码
     * @return 流程定义
     */
    FlowDefinition getLatestVersionByCode(String code);
    
    /**
     * 发布流程定义
     * @param flowDefinition 流程定义
     * @return 流程定义
     */
    FlowDefinition publish(FlowDefinition flowDefinition);
    
    /**
     * 停用流程定义
     * @param id 流程定义ID
     * @return 操作结果
     */
    boolean disable(Long id);
}
