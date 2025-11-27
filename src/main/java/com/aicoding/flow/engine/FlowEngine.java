package com.aicoding.flow.engine;

import com.aicoding.flow.entity.FlowDefinition;
import com.aicoding.flow.entity.FlowInstance;
import com.aicoding.flow.entity.FlowNode;
import com.aicoding.flow.entity.FlowEdge;
import com.aicoding.flow.service.FlowDefinitionService;
import com.aicoding.flow.service.FlowInstanceService;
import com.aicoding.flow.service.FlowNodeService;
import com.aicoding.flow.service.FlowEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程引擎核心类
 * @author gll
 * dateTime 2025/11/27 15:28
 */
@Component
public class FlowEngine {
    
    @Autowired
    private FlowDefinitionService flowDefinitionService;
    
    @Autowired
    private FlowInstanceService flowInstanceService;
    
    @Autowired
    private FlowNodeService flowNodeService;
    
    @Autowired
    private FlowEdgeService flowEdgeService;
    
    /**
     * 启动流程
     * @param definitionCode 流程定义编码
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @param variables 流程变量
     * @return 流程实例
     */
    @Transactional(rollbackFor = Exception.class)
    public FlowInstance startFlow(String definitionCode, String businessId, String businessType, String variables) {
        // 1. 根据流程编码查询最新版本的流程定义
        FlowDefinition flowDefinition = flowDefinitionService.getLatestVersionByCode(definitionCode);
        if (flowDefinition == null) {
            throw new RuntimeException("流程定义不存在：" + definitionCode);
        }
        
        // 2. 创建流程实例
        FlowInstance flowInstance = new FlowInstance();
        flowInstance.setDefinitionId(flowDefinition.getId());
        flowInstance.setDefinitionName(flowDefinition.getName());
        flowInstance.setDefinitionCode(flowDefinition.getCode());
        flowInstance.setDefinitionVersion(flowDefinition.getVersion());
        flowInstance.setBusinessId(businessId);
        flowInstance.setBusinessType(businessType);
        flowInstance.setVariables(variables);
        flowInstance.setStatus(1); // 运行中
        flowInstance.setCreateTime(new java.util.Date());
        flowInstanceService.save(flowInstance);
        
        // 3. 解析流程定义JSON，创建流程节点
        // TODO: 解析流程定义JSON，创建流程节点
        
        // 4. 执行流程的第一个节点
        // TODO: 执行流程的第一个节点
        
        return flowInstance;
    }
    
    /**
     * 执行流程节点
     * @param instanceId 流程实例ID
     * @param nodeId 节点定义ID
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean executeNode(Long instanceId, String nodeId) {
        // 1. 根据流程实例ID和节点定义ID查询流程节点
        FlowNode flowNode = flowNodeService.getByInstanceIdAndNodeId(instanceId, nodeId);
        if (flowNode == null) {
            throw new RuntimeException("流程节点不存在：" + instanceId + ", " + nodeId);
        }
        
        // 2. 设置节点执行状态为执行中
        flowNode.setStatus(1);
        flowNode.setStartTime(new java.util.Date());
        flowNodeService.updateById(flowNode);
        
        // 3. 执行节点逻辑
        // TODO: 执行节点逻辑
        
        // 4. 设置节点执行状态为已完成
        flowNode.setStatus(2);
        flowNode.setEndTime(new java.util.Date());
        flowNodeService.updateById(flowNode);
        
        // 5. 根据流程边的条件判断，确定下一个节点
        // TODO: 根据流程边的条件判断，确定下一个节点
        
        // 6. 执行下一个节点
        // TODO: 执行下一个节点
        
        return true;
    }
    
    /**
     * 完成流程节点
     * @param instanceId 流程实例ID
     * @param nodeId 节点定义ID
     * @param result 执行结果
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean completeNode(Long instanceId, String nodeId, String result) {
        // 1. 完成节点执行
        boolean success = flowNodeService.complete(instanceId, nodeId, result);
        if (!success) {
            return false;
        }
        
        // 2. 根据流程边的条件判断，确定下一个节点
        // TODO: 根据流程边的条件判断，确定下一个节点
        
        // 3. 执行下一个节点
        // TODO: 执行下一个节点
        
        return true;
    }
    
    /**
     * 跳过流程节点
     * @param instanceId 流程实例ID
     * @param nodeId 节点定义ID
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean skipNode(Long instanceId, String nodeId) {
        // 1. 跳过节点执行
        boolean success = flowNodeService.skip(instanceId, nodeId);
        if (!success) {
            return false;
        }
        
        // 2. 根据流程边的条件判断，确定下一个节点
        // TODO: 根据流程边的条件判断，确定下一个节点
        
        // 3. 执行下一个节点
        // TODO: 执行下一个节点
        
        return true;
    }
}