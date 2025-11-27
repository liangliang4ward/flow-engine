package com.aicoding.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程定义实体类
 * @author gll
 * dateTime 2025/11/27 15:12
 */
@Data
@TableName("flow_definition")
public class FlowDefinition implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流程定义名称
     */
    private String name;

    /**
     * 流程定义编码
     */
    private String code;

    /**
     * 流程定义版本
     */
    private Integer version;

    /**
     * 流程定义描述
     */
    private String description;

    /**
     * 流程定义JSON配置
     */
    private String config;

    /**
     * 状态：0-草稿，1-发布，2-停用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;
}