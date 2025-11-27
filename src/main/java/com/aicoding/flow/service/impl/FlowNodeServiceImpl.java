package com.aicoding.flow.service.impl;

import com.aicoding.flow.entity.FlowNode;
import com.aicoding.flow.mapper.FlowNodeMapper;
import com.aicoding.flow.service.FlowNodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程节点Service实现类
 * @author gll
 * dateTime 2025/11/27 15:25
 */
@Service
public class FlowNodeServiceImpl extends ServiceImpl<FlowNodeMapper, FlowNode> implements FlowNodeService {
    
    @Autowired
    private FlowNodeMapper flowNodeMapper;
    
    @Override
    public java.util.List<FlowNode> getByInstanceId(Long instanceId) {
        return flowNodeMapper.selectByInstanceId(instanceId);
    }
    
    @Override
    public FlowNode getByInstanceIdAndNodeId(Long instanceId, String nodeId) {
        return flowNodeMapper.selectByInstanceIdAndNodeId(instanceId, nodeId);
    }
    
    @Override
    public java.util.List<FlowNode> getPendingNodesByInstanceId(Long instanceId) {
        return flowNodeMapper.selectPendingNodesByInstanceId(instanceId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean complete(Long instanceId, String nodeId, String result) {
        FlowNode flowNode = getByInstanceIdAndNodeId(instanceId, nodeId);
        if (flowNode == null) {
            return false;
        }
        // 设置完成状态
        flowNode.setStatus(2);
        // 设置执行结果
        flowNode.setResult(result);
        // 设置结束时间
        flowNode.setEndTime(new java.util.Date());
        return updateById(flowNode);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean skip(Long instanceId, String nodeId) {
        FlowNode flowNode = getByInstanceIdAndNodeId(instanceId, nodeId);
        if (flowNode == null) {
            return false;
        }
        // 设置跳过状态
        flowNode.setStatus(3);
        // 设置结束时间
        flowNode.setEndTime(new java.util.Date());
        return updateById(flowNode);
    }
}
