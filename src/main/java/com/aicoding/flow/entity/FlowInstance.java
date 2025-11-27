package com.aicoding.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程实例实体类
 * @author gll
 * dateTime 2025/11/27 15:13
 */
@Data
@TableName("flow_instance")
public class FlowInstance implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流程实例编码
     */
    private String code;

    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 流程定义名称
     */
    private String definitionName;

    /**
     * 流程定义编码
     */
    private String definitionCode;

    /**
     * 流程定义版本
     */
    private Integer definitionVersion;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 状态：0-待启动，1-运行中，2-已完成，3-已暂停，4-已取消，5-已超时
     */
    private Integer status;

    /**
     * 当前节点ID
     */
    private String currentNodeId;

    /**
     * 当前节点名称
     */
    private String currentNodeName;

    /**
     * 流程变量（JSON格式）
     */
    private String variables;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 超时时间
     */
    private Date timeoutTime;

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