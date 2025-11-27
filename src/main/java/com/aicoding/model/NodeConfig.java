package com.aicoding.model;

import lombok.Data;

import java.util.Map;

/**
 * 节点配置类
 * @author gll
 * dateTime 2025/11/27 15:30
 */
@Data
public class NodeConfig {
    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点类型：start-开始节点，end-结束节点，task-任务节点，gateway-网关节点
     */
    private String nodeType;

    /**
     * 节点配置参数
     */
    private Map<String, Object> config;

    /**
     * 超时配置（毫秒）
     */
    private Long timeout;
}
