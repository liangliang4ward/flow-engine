package com.aicoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 流程定义实体类
 * @author gll
 * dateTime 2025/11/27 15:10
 */
@Data
@TableName("flow_definition")
public class FlowDefinition {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流程定义ID
     */
    private String flowId;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 流程版本
     */
    private Integer version;

    /**
     * 流程JSON配置
     */
    private String flowConfig;

    /**
     * 流程状态：0-草稿，1-激活，2-停用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
