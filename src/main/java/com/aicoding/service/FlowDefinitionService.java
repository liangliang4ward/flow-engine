package com.aicoding.service;

import com.aicoding.entity.FlowDefinition;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程定义Service接口
 * @author gll
 * dateTime 2025/11/27 16:26
 */
public interface FlowDefinitionService extends IService<FlowDefinition> {
    /**
     * 根据流程ID和版本获取流程定义
     * @param flowId 流程ID
     * @param version 版本
     * @return 流程定义
     */
    FlowDefinition getByFlowIdAndVersion(String flowId, Integer version);

    /**
     * 获取流程定义的最新版本
     * @param flowId 流程ID
     * @return 流程定义
     */
    FlowDefinition getLatestVersion(String flowId);
}
