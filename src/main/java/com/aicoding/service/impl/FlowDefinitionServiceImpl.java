package com.aicoding.service.impl;

import com.aicoding.entity.FlowDefinition;
import com.aicoding.mapper.FlowDefinitionMapper;
import com.aicoding.service.FlowDefinitionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 流程定义Service实现类
 * @author gll
 * dateTime 2025/11/27 16:28
 */
@Service
public class FlowDefinitionServiceImpl extends ServiceImpl<FlowDefinitionMapper, FlowDefinition> implements FlowDefinitionService {
    @Override
    public FlowDefinition getByFlowIdAndVersion(String flowId, Integer version) {
        QueryWrapper<FlowDefinition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flow_id", flowId)
                .eq("version", version);
        return getOne(queryWrapper);
    }

    @Override
    public FlowDefinition getLatestVersion(String flowId) {
        QueryWrapper<FlowDefinition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flow_id", flowId)
                .orderByDesc("version");
        return getOne(queryWrapper);
    }
}
