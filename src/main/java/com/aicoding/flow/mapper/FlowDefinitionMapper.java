package com.aicoding.flow.mapper;

import com.aicoding.flow.entity.FlowDefinition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程定义Mapper接口
 * @author gll
 * dateTime 2025/11/27 15:16
 */
@Mapper
public interface FlowDefinitionMapper extends BaseMapper<FlowDefinition> {
    
    /**
     * 根据流程编码和版本查询流程定义
     * @param code 流程编码
     * @param version 流程版本
     * @return 流程定义
     */
    FlowDefinition selectByCodeAndVersion(String code, Integer version);
    
    /**
     * 根据流程编码查询最新版本的流程定义
     * @param code 流程编码
     * @return 流程定义
     */
    FlowDefinition selectLatestVersionByCode(String code);
}