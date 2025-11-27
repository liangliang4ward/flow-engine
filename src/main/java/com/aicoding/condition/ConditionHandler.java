package com.aicoding.condition;

import java.util.Map;

/**
 * 条件判断处理器接口
 * @author gll
 * dateTime 2025/11/27 16:02
 */
public interface ConditionHandler {
    /**
     * 执行条件判断
     * @param condition 条件表达式
     * @param variables 流程变量
     * @return 判断结果
     * @throws Exception 执行异常
     */
    boolean evaluate(String condition, Map<String, Object> variables) throws Exception;

    /**
     * 获取支持的条件类型
     * @return 条件类型
     */
    String getConditionType();
}
