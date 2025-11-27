package com.aicoding.service;

import com.aicoding.entity.FlowInstance;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 流程实例Service接口
 * @author gll
 * dateTime 2025/11/27 16:30
 */
public interface FlowInstanceService extends IService<FlowInstance> {
    /**
     * 启动流程实例
     * @param flowId 流程ID
     * @param variables 流程变量
     * @return 流程实例
     * @throws Exception 执行异常
     */
    FlowInstance startInstance(String flowId, Map<String, Object> variables) throws Exception;

    /**
     * 启动流程实例（指定版本）
     * @param flowId 流程ID
     * @param version 版本
     * @param variables 流程变量
     * @return 流程实例
     * @throws Exception 执行异常
     */
    FlowInstance startInstance(String flowId, Integer version, Map<String, Object> variables) throws Exception;
}
