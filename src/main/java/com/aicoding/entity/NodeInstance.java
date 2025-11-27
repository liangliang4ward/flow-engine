package com.aicoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 节点实例实体类
 * @author gll
 * dateTime 2025/11/27 15:20
 */
@Data
@TableName("node_instance")
public class NodeInstance {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 节点实例ID
     */
    private String nodeInstanceId;

    /**
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点类型
     */
    private String nodeType;

    /**
     * 节点状态：0-待执行，1-执行中，2-已完成，3-已跳过，4-异常
     */
    private Integer status;

    /**
     * 节点变量
     */
    private String variables;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 执行时长（毫秒）
     */
    private Long duration;
}
