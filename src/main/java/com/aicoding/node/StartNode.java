package com.aicoding.node;

import org.springframework.stereotype.Component;

/**
 * 开始节点
 * @author gll
 * dateTime 2025/11/27 15:50
 */
@Component("startNode")
public class StartNode extends AbstractNode {
    @Override
    public boolean execute() throws Exception {
        beforeExecute();
        try {
            // 开始节点逻辑：记录流程开始信息
            System.out.println("开始节点执行: " + nodeConfig.getNodeName());
            return true;
        } finally {
            afterExecute();
        }
    }
}
