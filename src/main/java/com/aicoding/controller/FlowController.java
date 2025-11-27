package com.aicoding.controller;

import com.aicoding.entity.FlowDefinition;
import com.aicoding.entity.FlowInstance;
import com.aicoding.service.FlowDefinitionService;
import com.aicoding.service.FlowInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 流程控制器
 * @author gll
 * dateTime 2025/11/27 16:35
 */
@RestController
@RequestMapping("/flow")
public class FlowController {
    @Autowired
    private FlowDefinitionService flowDefinitionService;

    @Autowired
    private FlowInstanceService flowInstanceService;

    /**
     * 保存流程定义
     * @param flowDefinition 流程定义
     * @return 流程定义
     */
    @PostMapping("/definition")
    public FlowDefinition saveDefinition(@RequestBody FlowDefinition flowDefinition) {
        flowDefinitionService.save(flowDefinition);
        return flowDefinition;
    }

    /**
     * 根据ID获取流程定义
     * @param id 主键ID
     * @return 流程定义
     */
    @GetMapping("/definition/{id}")
    public FlowDefinition getDefinition(@PathVariable Long id) {
        return flowDefinitionService.getById(id);
    }

    /**
     * 根据流程ID获取最新版本的流程定义
     * @param flowId 流程ID
     * @return 流程定义
     */
    @GetMapping("/definition/latest/{flowId}")
    public FlowDefinition getLatestDefinition(@PathVariable String flowId) {
        return flowDefinitionService.getLatestVersion(flowId);
    }

    /**
     * 启动流程实例
     * @param flowId 流程ID
     * @param variables 流程变量
     * @return 流程实例
     * @throws Exception 执行异常
     */
    @PostMapping("/instance/start/{flowId}")
    public FlowInstance startInstance(@PathVariable String flowId, @RequestBody Map<String, Object> variables) throws Exception {
        return flowInstanceService.startInstance(flowId, variables);
    }

    /**
     * 启动流程实例（指定版本）
     * @param flowId 流程ID
     * @param version 版本
     * @param variables 流程变量
     * @return 流程实例
     * @throws Exception 执行异常
     */
    @PostMapping("/instance/start/{flowId}/{version}")
    public FlowInstance startInstance(@PathVariable String flowId, @PathVariable Integer version, @RequestBody Map<String, Object> variables) throws Exception {
        return flowInstanceService.startInstance(flowId, version, variables);
    }

    /**
     * 根据ID获取流程实例
     * @param id 主键ID
     * @return 流程实例
     */
    @GetMapping("/instance/{id}")
    public FlowInstance getInstance(@PathVariable Long id) {
        return flowInstanceService.getById(id);
    }

    /**
     * 根据实例ID获取流程实例
     * @param instanceId 实例ID
     * @return 流程实例
     */
    @GetMapping("/instance/byInstanceId/{instanceId}")
    public FlowInstance getInstanceByInstanceId(@PathVariable String instanceId) {
        return flowInstanceService.lambdaQuery().eq(FlowInstance::getInstanceId, instanceId).one();
    }
}
