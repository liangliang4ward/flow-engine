package com.aicoding.flow.service.impl;

import com.aicoding.flow.entity.FlowInstance;
import com.aicoding.flow.mapper.FlowInstanceMapper;
import com.aicoding.flow.service.FlowInstanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程实例Service实现类
 * @author gll
 * dateTime 2025/11/27 15:23
 */
@Service
public class FlowInstanceServiceImpl extends ServiceImpl<FlowInstanceMapper, FlowInstance> implements FlowInstanceService {
    
    @Autowired
    private FlowInstanceMapper flowInstanceMapper;
    
    @Override
    public FlowInstance getByBusinessIdAndType(String businessId, String businessType) {
        return flowInstanceMapper.selectByBusinessIdAndType(businessId, businessType);
    }
    
    @Override
    public java.util.List<FlowInstance> getByDefinitionId(Long definitionId) {
        return flowInstanceMapper.selectByDefinitionId(definitionId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FlowInstance start(String definitionCode, String businessId, String businessType, String variables) {
        // TODO: 实现流程启动逻辑
        return null;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pause(Long id) {
        FlowInstance flowInstance = getById(id);
        if (flowInstance == null) {
            return false;
        }
        // 设置暂停状态
        flowInstance.setStatus(3);
        return updateById(flowInstance);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resume(Long id) {
        FlowInstance flowInstance = getById(id);
        if (flowInstance == null) {
            return false;
        }
        // 设置运行状态
        flowInstance.setStatus(1);
        return updateById(flowInstance);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(Long id) {
        FlowInstance flowInstance = getById(id);
        if (flowInstance == null) {
            return false;
        }
        // 设置取消状态
        flowInstance.setStatus(4);
        return updateById(flowInstance);
    }
}
