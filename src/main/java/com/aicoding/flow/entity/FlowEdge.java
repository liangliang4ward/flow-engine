package com.aicoding.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程边实体类
 * @author gll
 * dateTime 2025/11/27 15:15
 */
@Data
@TableName("flow_edge")
public class FlowEdge implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 边ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 边定义ID
     */
    private String edgeId;

    /**
     * 源节点ID
     */
    private String sourceNodeId;

    /**
     * 目标节点ID
     */
    private String targetNodeId;

    /**
     * 边名称
     */
    private String name;

    /**
     * 边类型：normal-普通边，condition-条件边
     */
    private String type;

    /**
     * 条件表达式
     */
    private String condition;

    /**
     * 排序号
     */
    private Integer orderNo;

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