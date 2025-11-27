package com.aicoding.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程节点实体类
 * @author gll
 * dateTime 2025/11/27 15:14
 */
@Data
@TableName("flow_node")
public class FlowNode implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 节点定义ID
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点类型：start-开始节点，end-结束节点，task-任务节点，gateway-网关节点，parallel-并行节点
     */
    private String nodeType;

    /**
     * 状态：0-待执行，1-执行中，2-已完成，3-已跳过，4-已超时，5-执行失败
     */
    private Integer status;

    /**
     * 执行顺序
     */
    private Integer orderNo;

    /**
     * 执行参数（JSON格式）
     */
    private String params;

    /**
     * 执行结果（JSON格式）
     */
    private String result;

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