package com.aicoding.flow.mapper;

import com.aicoding.flow.entity.FlowInstance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程实例Mapper接口
 * @author gll
 * dateTime 2025/11/27 15:17
 */
@Mapper
public interface FlowInstanceMapper extends BaseMapper<FlowInstance> {
    
    /**
     * 根据业务ID和业务类型查询流程实例
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 流程实例
     */
    FlowInstance selectByBusinessIdAndType(String businessId, String businessType);
    
    /**
     * 根据流程定义ID查询流程实例列表
     * @param definitionId 流程定义ID
     * @return 流程实例列表
     */
    java.util.List<FlowInstance> selectByDefinitionId(Long definitionId);
}