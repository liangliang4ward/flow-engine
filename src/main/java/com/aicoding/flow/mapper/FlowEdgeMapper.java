package com.aicoding.flow.mapper;

import com.aicoding.flow.entity.FlowEdge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程边Mapper接口
 * @author gll
 * dateTime 2025/11/27 15:19
 */
@Mapper
public interface FlowEdgeMapper extends BaseMapper<FlowEdge> {
    
    /**
     * 根据流程定义ID查询流程边列表
     * @param definitionId 流程定义ID
     * @return 流程边列表
     */
    java.util.List<FlowEdge> selectByDefinitionId(Long definitionId);
    
    /**
     * 根据流程定义ID和源节点ID查询流程边列表
     * @param definitionId 流程定义ID
     * @param sourceNodeId 源节点ID
     * @return 流程边列表
     */
    java.util.List<FlowEdge> selectByDefinitionIdAndSourceNodeId(Long definitionId, String sourceNodeId);
}