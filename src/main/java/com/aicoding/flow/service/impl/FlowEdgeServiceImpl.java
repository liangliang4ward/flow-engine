package com.aicoding.flow.service.impl;

import com.aicoding.flow.entity.FlowEdge;
import com.aicoding.flow.mapper.FlowEdgeMapper;
import com.aicoding.flow.service.FlowEdgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流程边Service实现类
 * @author gll
 * dateTime 2025/11/27 15:27
 */
@Service
public class FlowEdgeServiceImpl extends ServiceImpl<FlowEdgeMapper, FlowEdge> implements FlowEdgeService {
    
    @Autowired
    private FlowEdgeMapper flowEdgeMapper;
    
    @Override
    public java.util.List<FlowEdge> getByDefinitionId(Long definitionId) {
        return flowEdgeMapper.selectByDefinitionId(definitionId);
    }
    
    @Override
    public java.util.List<FlowEdge> getByDefinitionIdAndSourceNodeId(Long definitionId, String sourceNodeId) {
        return flowEdgeMapper.selectByDefinitionIdAndSourceNodeId(definitionId, sourceNodeId);
    }
}
