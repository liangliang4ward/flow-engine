package com.aicoding.flow.engine.status;

/**
 * 流程引擎状态
 * @author gll
 * dateTime 2025/11/27 15:29
 */
public enum FlowEngineStatus {
    
    /**
     * 初始化
     */
    INIT(0, "初始化"),
    
    /**
     * 运行中
     */
    RUNNING(1, "运行中"),
    
    /**
     * 暂停
     */
    PAUSED(2, "暂停"),
    
    /**
     * 停止
     */
    STOPPED(3, "停止");
    
    private int code;
    private String desc;
    
    FlowEngineStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public static FlowEngineStatus getByCode(int code) {
        for (FlowEngineStatus status : FlowEngineStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}