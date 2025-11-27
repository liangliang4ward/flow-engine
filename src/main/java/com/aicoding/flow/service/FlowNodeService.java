package com.aicoding.flow.service;

import com.aicoding.flow.entity.FlowNode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程节点Service接口
 * @author gll
 * dateTime 2025/11/27 15:24
 */
public interface FlowNodeService extends IService<FlowNode> {
    
    /**
     * 根据流程实例ID查询流程节点列表
     * @param instanceId 流程实例ID
     * @return 流程节点列表
     */
    java.util.List<FlowNode> getByInstanceId(Long instanceId);
    
    /**
     * 根据流程实例ID和节点定义ID查询流程节点
     * @param instanceId 流程实例ID
     * @param nodeId 节点定义ID
     * @return 流程节点
     */
    FlowNode getByInstanceIdAndNodeId(Long instanceId, String nodeId);
    
    /**
     * 根据流程实例ID查询当前待执行的节点
     * @param instanceId 流程实例ID
     * @return 流程节点列表
     */
    java.util.List<FlowNode> getPendingNodesByInstanceId(Long instanceId);
    
    /**
     * 完成节点执行
     * @param instanceId 流程实例ID
     * @param nodeId 节点定义ID
     * @param result 执行结果
     * @return 操作结果
     */
    boolean complete(Long instanceId, String nodeId, String result);
    
    /**
     * 跳过节点执行
     * @param instanceId 流程实例ID
     * @param nodeId 节点定义ID
     * @return 操作结果
     */
    boolean skip(Long instanceId, String nodeId);
}
