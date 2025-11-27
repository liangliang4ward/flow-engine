package com.aicoding.service.impl;

import com.aicoding.entity.FlowDefinition;
import com.aicoding.entity.FlowInstance;
import com.aicoding.engine.FlowEngine;
import com.aicoding.mapper.FlowInstanceMapper;
import com.aicoding.service.FlowDefinitionService;
import com.aicoding.service.FlowInstanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 流程实例Service实现类
 * @author gll
 * dateTime 2025/11/27 16:32
 */
@Service
public class FlowInstanceServiceImpl extends ServiceImpl<FlowInstanceMapper, FlowInstance> implements FlowInstanceService {
    @Autowired
    private FlowEngine flowEngine;

    @Autowired
    private FlowDefinitionService flowDefinitionService;

    @Override
    public FlowInstance startInstance(String flowId, Map<String, Object> variables) throws Exception {
        // 获取最新版本的流程定义
        FlowDefinition flowDefinition = flowDefinitionService.getLatestVersion(flowId);
        if (flowDefinition == null) {
            throw new IllegalArgumentException("流程定义不存在: " + flowId);
        }
        return startInstance(flowId, flowDefinition.getVersion(), variables);
    }

    @Override
    public FlowInstance startInstance(String flowId, Integer version, Map<String, Object> variables) throws Exception {
        // 获取指定版本的流程定义
        FlowDefinition flowDefinition = flowDefinitionService.getByFlowIdAndVersion(flowId, version);
        if (flowDefinition == null) {
            throw new IllegalArgumentException("流程定义不存在: " + flowId + ", 版本: " + version);
        }

        // 创建流程实例
        FlowInstance instance = flowEngine.createInstance(flowDefinition, variables);

        // 保存流程实例
        save(instance);

        // 启动流程实例
        flowEngine.startInstance(instance, flowDefinition);

        // 更新流程实例
        updateById(instance);

        return instance;
    }
}
