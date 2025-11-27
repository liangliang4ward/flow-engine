package com.aicoding.flow.service;

import com.aicoding.flow.entity.FlowEdge;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程边Service接口
 * @author gll
 * dateTime 2025/11/27 15:26
 */
public interface FlowEdgeService extends IService<FlowEdge> {
    
    /**
     * 根据流程定义ID查询流程边列表
     * @param definitionId 流程定义ID
     * @return 流程边列表
     */
    java.util.List<FlowEdge> getByDefinitionId(Long definitionId);
    
    /**
     * 根据流程定义ID和源节点ID查询流程边列表
     * @param definitionId 流程定义ID
     * @param sourceNodeId 源节点ID
     * @return 流程边列表
     */
    java.util.List<FlowEdge> getByDefinitionIdAndSourceNodeId(Long definitionId, String sourceNodeId);
}
