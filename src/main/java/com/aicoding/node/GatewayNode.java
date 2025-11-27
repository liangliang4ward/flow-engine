package com.aicoding.node;

import org.springframework.stereotype.Component;

/**
 * 网关节点
 * @author gll
 * dateTime 2025/11/27 15:58
 */
@Component("gatewayNode")
public class GatewayNode extends AbstractNode {
    @Override
    public boolean execute() throws Exception {
        beforeExecute();
        try {
            // 网关节点逻辑：处理条件判断和并行分支
            System.out.println("网关节点执行: " + nodeConfig.getNodeName());
            
            // 网关节点本身不执行具体业务逻辑，主要用于流程分支控制
            return true;
        } finally {
            afterExecute();
        }
    }
}
