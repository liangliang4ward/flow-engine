package com.aicoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 流程实例实体类
 * @author gll
 * dateTime 2025/11/27 15:15
 */
@Data
@TableName("flow_instance")
public class FlowInstance {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 流程定义ID
     */
    private String flowId;

    /**
     * 流程版本
     */
    private Integer version;

    /**
     * 流程实例状态：0-运行中，1-已完成，2-已终止，3-异常
     */
    private Integer status;

    /**
     * 流程变量
     */
    private String variables;

    /**
     * 当前节点ID
     */
    private String currentNodeId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;
}
