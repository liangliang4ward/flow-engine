package com.aicoding.flow.mapper;

import com.aicoding.flow.entity.FlowNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程节点Mapper接口
 * @author gll
 * dateTime 2025/11/27 15:18
 */
@Mapper
public interface FlowNodeMapper extends BaseMapper<FlowNode> {
    
    /**
     * 根据流程实例ID查询流程节点列表
     * @param instanceId 流程实例ID
     * @return 流程节点列表
     */
    java.util.List<FlowNode> selectByInstanceId(Long instanceId);
    
    /**
     * 根据流程实例ID和节点定义ID查询流程节点
     * @param instanceId 流程实例ID
     * @param nodeId 节点定义ID
     * @return 流程节点
     */
    FlowNode selectByInstanceIdAndNodeId(Long instanceId, String nodeId);
    
    /**
     * 根据流程实例ID查询当前待执行的节点
     * @param instanceId 流程实例ID
     * @return 流程节点列表
     */
    java.util.List<FlowNode> selectPendingNodesByInstanceId(Long instanceId);
}