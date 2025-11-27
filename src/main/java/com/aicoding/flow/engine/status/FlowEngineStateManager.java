package com.aicoding.flow.engine.status;

import org.springframework.stereotype.Component;

/**
 * 流程引擎状态管理器
 * @author gll
 * dateTime 2025/11/27 15:30
 */
@Component
public class FlowEngineStateManager {
    
    private FlowEngineStatus currentStatus = FlowEngineStatus.INIT;
    
    /**
     * 获取当前状态
     * @return 当前状态
     */
    public FlowEngineStatus getCurrentStatus() {
        return currentStatus;
    }
    
    /**
     * 启动流程引擎
     */
    public synchronized void start() {
        if (currentStatus == FlowEngineStatus.INIT || currentStatus == FlowEngineStatus.STOPPED) {
            currentStatus = FlowEngineStatus.RUNNING;
            // TODO: 启动流程引擎的相关资源
        }
    }
    
    /**
     * 暂停流程引擎
     */
    public synchronized void pause() {
        if (currentStatus == FlowEngineStatus.RUNNING) {
            currentStatus = FlowEngineStatus.PAUSED;
            // TODO: 暂停流程引擎的相关资源
        }
    }
    
    /**
     * 恢复流程引擎
     */
    public synchronized void resume() {
        if (currentStatus == FlowEngineStatus.PAUSED) {
            currentStatus = FlowEngineStatus.RUNNING;
            // TODO: 恢复流程引擎的相关资源
        }
    }
    
    /**
     * 停止流程引擎
     */
    public synchronized void stop() {
        if (currentStatus == FlowEngineStatus.RUNNING || currentStatus == FlowEngineStatus.PAUSED) {
            currentStatus = FlowEngineStatus.STOPPED;
            // TODO: 停止流程引擎的相关资源
        }
    }
    
    /**
     * 检查流程引擎是否正在运行
     * @return 是否正在运行
     */
    public boolean isRunning() {
        return currentStatus == FlowEngineStatus.RUNNING;
    }
    
    /**
     * 检查流程引擎是否已经暂停
     * @return 是否已经暂停
     */
    public boolean isPaused() {
        return currentStatus == FlowEngineStatus.PAUSED;
    }
    
    /**
     * 检查流程引擎是否已经停止
     * @return 是否已经停止
     */
    public boolean isStopped() {
        return currentStatus == FlowEngineStatus.STOPPED;
    }
}