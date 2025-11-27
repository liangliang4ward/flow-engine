package com.aicoding.model;

import lombok.Data;

/**
 * 连接线配置类
 * @author gll
 * dateTime 2025/11/27 15:35
 */
@Data
public class EdgeConfig {
    /**
     * 连接线ID
     */
    private String edgeId;

    /**
     * 源节点ID
     */
    private String sourceNodeId;

    /**
     * 目标节点ID
     */
    private String targetNodeId;

    /**
     * 条件表达式
     */
    private String condition;

    /**
     * 条件类型：script-脚本表达式，rule-规则引擎
     */
    private String conditionType;
}
