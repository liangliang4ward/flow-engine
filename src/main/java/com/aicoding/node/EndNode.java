package com.aicoding.node;

import org.springframework.stereotype.Component;

/**
 * 结束节点
 * @author gll
 * dateTime 2025/11/27 15:52
 */
@Component("endNode")
public class EndNode extends AbstractNode {
    @Override
    public boolean execute() throws Exception {
        beforeExecute();
        try {
            // 结束节点逻辑：记录流程结束信息
            System.out.println("结束节点执行: " + nodeConfig.getNodeName());
            return true;
        } finally {
            afterExecute();
        }
    }
}
