package com.aicoding.engine;

import com.aicoding.entity.FlowDefinition;
import com.aicoding.entity.FlowInstance;
import com.aicoding.entity.NodeInstance;
import com.aicoding.model.FlowConfig;
import com.aicoding.model.NodeConfig;
import com.aicoding.model.EdgeConfig;
import com.aicoding.node.AbstractNode;
import com.aicoding.parser.FlowJsonParser;
import com.aicoding.condition.ConditionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 流程执行引擎核心类
 * @author gll
 * dateTime 2025/11/27 16:10
 */
@Component
public class FlowEngine {
    @Autowired
    private FlowJsonParser flowJsonParser;

    @Autowired
    private Map<String, AbstractNode> nodeMap;

    @Autowired
    private Map<String, ConditionHandler> conditionHandlerMap;

    /**
     * 创建流程实例
     * @param flowDefinition 流程定义
     * @param variables 流程变量
     * @return 流程实例
     * @throws IOException JSON解析异常
     */
    public FlowInstance createInstance(FlowDefinition flowDefinition, Map<String, Object> variables) throws IOException {
        FlowInstance instance = new FlowInstance();
        instance.setInstanceId(UUID.randomUUID().toString());
        instance.setFlowId(flowDefinition.getFlowId());
        instance.setVersion(flowDefinition.getVersion());
        instance.setStatus(0); // 运行中
        instance.setVariables(flowJsonParser.toJson(variables));
        instance.setCreateTime(LocalDateTime.now());

        // 解析流程配置，设置当前节点为开始节点
        FlowConfig flowConfig = flowJsonParser.parse(flowDefinition.getFlowConfig());
        NodeConfig startNode = flowConfig.getNodes().stream()
                .filter(node -> "start".equals(node.getNodeType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("流程定义中没有开始节点"));
        instance.setCurrentNodeId(startNode.getNodeId());

        return instance;
    }

    /**
     * 启动流程实例
     * @param instance 流程实例
     * @param flowDefinition 流程定义
     * @throws Exception 执行异常
     */
    public void startInstance(FlowInstance instance, FlowDefinition flowDefinition) throws Exception {
        executeInstance(instance, flowDefinition);
    }

    /**
    /**
     * 执行流程实例
     * @param instance 流程实例
     * @param flowDefinition 流程定义
     * @throws Exception 执行异常
     */
    private void executeInstance(FlowInstance instance, FlowDefinition flowDefinition) throws Exception {
        FlowConfig flowConfig = flowJsonParser.parse(flowDefinition.getFlowConfig());
        Map<String, Object> variables = flowJsonParser.parse(instance.getVariables(), Map.class);

        while (true) {
            String currentNodeId = instance.getCurrentNodeId();
            if (currentNodeId == null) {
                break;
            }

            // 获取当前节点配置
            NodeConfig currentNodeConfig = flowConfig.getNodes().stream()
                    .filter(node -> currentNodeId.equals(node.getNodeId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("节点配置不存在: " + currentNodeId));

            // 创建节点实例
            NodeInstance nodeInstance = createNodeInstance(instance, currentNodeConfig);

            // 执行节点
            AbstractNode node = getNodeByType(currentNodeConfig.getNodeType());
            node.init(currentNodeConfig, nodeInstance, variables);
            boolean success = node.execute();

            if (!success) {
                throw new RuntimeException("节点执行失败: " + currentNodeConfig.getNodeName());
            }

            // 更新节点实例状态
            updateNodeInstanceStatus(nodeInstance, 2); // 已完成

            // 如果是结束节点，流程结束
            if ("end".equals(currentNodeConfig.getNodeType())) {
                instance.setStatus(1); // 已完成
                instance.setFinishTime(LocalDateTime.now());
                break;
            }

            // 查找下一个节点
            List<EdgeConfig> outgoingEdges = flowConfig.getEdges().stream()
                    .filter(edge -> currentNodeId.equals(edge.getSourceNodeId()))
                    .collect(Collectors.toList());

            // 处理条件判断，选择下一个节点
            List<EdgeConfig> matchingEdges = outgoingEdges.stream()
                    .filter(edge -> evaluateCondition(edge, variables))
                    .collect(Collectors.toList());

            if (matchingEdges.isEmpty()) {
                throw new RuntimeException("没有找到匹配的连接线: " + currentNodeId);
            }

            // 目前只支持单一路径，后续可扩展并行处理
            EdgeConfig nextEdge = matchingEdges.get(0);
            instance.setCurrentNodeId(nextEdge.getTargetNodeId());
        }

        // 更新流程实例
        instance.setUpdateTime(LocalDateTime.now());
        instance.setVariables(flowJsonParser.toJson(variables));
    }

    /**
     * 创建节点实例
     * @param instance 流程实例
     * @param nodeConfig 节点配置
     * @return 节点实例
     */
    private NodeInstance createNodeInstance(FlowInstance instance, NodeConfig nodeConfig) {
        NodeInstance nodeInstance = new NodeInstance();
        nodeInstance.setNodeInstanceId(UUID.randomUUID().toString());
        nodeInstance.setInstanceId(instance.getInstanceId());
        nodeInstance.setNodeId(nodeConfig.getNodeId());
        nodeInstance.setNodeName(nodeConfig.getNodeName());
        nodeInstance.setNodeType(nodeConfig.getNodeType());
        nodeInstance.setStatus(1); // 执行中
        nodeInstance.setStartTime(LocalDateTime.now());
        return nodeInstance;
    }

    /**
     * 更新节点实例状态
     * @param nodeInstance 节点实例
     * @param status 状态
     */
    private void updateNodeInstanceStatus(NodeInstance nodeInstance, int status) {
        nodeInstance.setStatus(status);
        nodeInstance.setEndTime(LocalDateTime.now());
        if (nodeInstance.getStartTime() != null) {
            nodeInstance.setDuration(java.time.Duration.between(nodeInstance.getStartTime(), nodeInstance.getEndTime()).toMillis());
        }
    }

    /**
     * 根据节点类型获取节点实例
     * @param nodeType 节点类型
     * @return 节点实例
     */
    private AbstractNode getNodeByType(String nodeType) {
        String beanName = nodeType + "Node";
        AbstractNode node = nodeMap.get(beanName);
        if (node == null) {
            throw new IllegalArgumentException("不支持的节点类型: " + nodeType);
        }
        return node;
    }

    /**
     * 执行条件判断
     * @param edge 连接线配置
     * @param variables 流程变量
     * @return 判断结果
     * @throws Exception 执行异常
     */
    private boolean evaluateCondition(EdgeConfig edge, Map<String, Object> variables) throws Exception {
        if (edge.getCondition() == null || edge.getCondition().trim().isEmpty()) {
            return true;
        }

        String conditionType = edge.getConditionType() != null ? edge.getConditionType() : "script";
        ConditionHandler handler = conditionHandlerMap.get(conditionType + "ConditionHandler");
        if (handler == null) {
            throw new IllegalArgumentException("不支持的条件类型: " + conditionType);
        }

        return handler.evaluate(edge.getCondition(), variables);
    }
}
