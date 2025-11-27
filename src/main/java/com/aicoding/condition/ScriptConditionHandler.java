package com.aicoding.condition;

import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * 脚本条件处理器
 * @author gll
 * dateTime 2025/11/27 16:05
 */
@Component("scriptConditionHandler")
public class ScriptConditionHandler implements ConditionHandler {
    private final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

    @Override
    public boolean evaluate(String condition, Map<String, Object> variables) throws Exception {
        if (condition == null || condition.trim().isEmpty()) {
            return true;
        }

        // 将流程变量放入脚本引擎
        if (variables != null && !variables.isEmpty()) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                scriptEngine.put(entry.getKey(), entry.getValue());
            }
        }

        // 执行脚本表达式
        Object result = scriptEngine.eval(condition);
        if (result instanceof Boolean) {
            return (Boolean) result;
        }

        throw new IllegalArgumentException("条件表达式执行结果不是布尔类型: " + result);
    }

    @Override
    public String getConditionType() {
        return "script";
    }
}
