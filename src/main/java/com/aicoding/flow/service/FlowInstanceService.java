package com.aicoding.flow.service;

import com.aicoding.flow.entity.FlowInstance;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 流程实例Service接口
 * @author gll
 * dateTime 2025/11/27 15:22
 */
public interface FlowInstanceService extends IService<FlowInstance> {
    
    /**
     * 根据业务ID和业务类型查询流程实例
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 流程实例
     */
    FlowInstance getByBusinessIdAndType(String businessId, String businessType);
    
    /**
     * 根据流程定义ID查询流程实例列表
     * @param definitionId 流程定义ID
     * @return 流程实例列表
     */
    java.util.List<FlowInstance> getByDefinitionId(Long definitionId);
    
    /**
     * 启动流程实例
     * @param definitionCode 流程定义编码
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @param variables 流程变量
     * @return 流程实例
     */
    FlowInstance start(String definitionCode, String businessId, String businessType, String variables);
    
    /**
     * 暂停流程实例
     * @param id 流程实例ID
     * @return 操作结果
     */
    boolean pause(Long id);
    
    /**
     * 恢复流程实例
     * @param id 流程实例ID
     * @return 操作结果
     */
    boolean resume(Long id);
    
    /**
     * 取消流程实例
     * @param id 流程实例ID
     * @return 操作结果
     */
    boolean cancel(Long id);
}
