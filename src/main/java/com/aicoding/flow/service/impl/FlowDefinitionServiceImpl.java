package com.aicoding.flow.service.impl;

import com.aicoding.flow.entity.FlowDefinition;
import com.aicoding.flow.mapper.FlowDefinitionMapper;
import com.aicoding.flow.service.FlowDefinitionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程定义Service实现类
 * @author gll
 * dateTime 2025/11/27 15:21
 */
@Service
public class FlowDefinitionServiceImpl extends ServiceImpl<FlowDefinitionMapper, FlowDefinition> implements FlowDefinitionService {
    
    @Autowired
    private FlowDefinitionMapper flowDefinitionMapper;
    
    @Override
    public FlowDefinition getByCodeAndVersion(String code, Integer version) {
        return flowDefinitionMapper.selectByCodeAndVersion(code, version);
    }
    
    @Override
    public FlowDefinition getLatestVersionByCode(String code) {
        return flowDefinitionMapper.selectLatestVersionByCode(code);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FlowDefinition publish(FlowDefinition flowDefinition) {
        // 设置发布状态
        flowDefinition.setStatus(1);
        // 保存流程定义
        saveOrUpdate(flowDefinition);
        return flowDefinition;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disable(Long id) {
        FlowDefinition flowDefinition = getById(id);
        if (flowDefinition == null) {
            return false;
        }
        // 设置停用状态
        flowDefinition.setStatus(2);
        return updateById(flowDefinition);
    }
}
