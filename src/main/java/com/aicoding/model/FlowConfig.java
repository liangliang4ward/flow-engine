package com.aicoding.model;

import lombok.Data;

import java.util.List;

/**
 * 流程配置根对象
 * @author gll
 * dateTime 2025/11/27 15:25
 */
@Data
public class FlowConfig {
    /**
     * 流程ID
     */
    private String flowId;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 流程版本
     */
    private Integer version;

    /**
     * 节点列表
     */
    private List<NodeConfig> nodes;

    /**
     * 连接线列表
     */
    private List<EdgeConfig> edges;
}
