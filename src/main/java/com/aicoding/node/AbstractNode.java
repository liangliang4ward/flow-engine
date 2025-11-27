package com.aicoding.node;

import com.aicoding.entity.NodeInstance;
import com.aicoding.model.NodeConfig;
import lombok.Data;

import java.util.Map;

/**
 * 节点类型抽象类
 * @author gll
 * dateTime 2025/11/27 15:45
 */
@Data
public abstract class AbstractNode {
    /**
     * 节点配置
     */
    protected NodeConfig nodeConfig;

    /**
     * 节点实例
     */
    protected NodeInstance nodeInstance;

    /**
     * 流程变量
     */
    protected Map<String, Object> variables;

    /**
     * 初始化节点
     * @param nodeConfig 节点配置
     * @param nodeInstance 节点实例
     * @param variables 流程变量
     */
    public void init(NodeConfig nodeConfig, NodeInstance nodeInstance, Map<String, Object> variables) {
        this.nodeConfig = nodeConfig;
        this.nodeInstance = nodeInstance;
        this.variables = variables;
    }

    /**
     * 执行节点
     * @return 执行结果
     * @throws Exception 执行异常
     */
    public abstract boolean execute() throws Exception;

    /**
     * 节点执行前处理
     */
    protected void beforeExecute() {
        // 默认空实现，子类可重写
    }

    /**
     * 节点执行后处理
     */
    protected void afterExecute() {
        // 默认空实现，子类可重写
    }

    /**
     * 节点超时处理
     */
    public void onTimeout() {
        // 默认空实现，子类可重写
    }

    /**
     * 节点异常处理
     * @param e 异常对象
     */
    public void onException(Exception e) {
        // 默认空实现，子类可重写
    }
}
