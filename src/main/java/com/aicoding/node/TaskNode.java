package com.aicoding.node;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 任务节点
 * @author gll
 * dateTime 2025/11/27 15:55
 */
@Component("task")
public class TaskNode extends AbstractNode {
    @Override
    public boolean execute() throws Exception {
        beforeExecute();
        try {
            // 任务节点逻辑：执行具体任务
            System.out.println("任务节点执行: " + nodeConfig.getNodeName());
            
            // 示例：处理节点配置中的参数
            Map<String, Object> config = nodeConfig.getConfig();
            if (config != null && !config.isEmpty()) {
                System.out.println("节点配置参数: " + config);
            }
            
            // 示例：修改流程变量
            if (variables != null) {
                variables.put("taskNodeExecuted", true);
            }
            
            return true;
        } finally {
            afterExecute();
        }
    }
}
