package com.aicoding.parser;

import com.aicoding.model.FlowConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.Map;

import java.io.IOException;

/**
 * JSON流程定义解析器
 * @author gll
 * dateTime 2025/11/27 15:40
 */
@Component
public class FlowJsonParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将JSON字符串解析为FlowConfig对象
     * @param json JSON字符串
     * @return FlowConfig对象
     * @throws IOException 解析异常
     */
    public FlowConfig parse(String json) throws IOException {
        return objectMapper.readValue(json, FlowConfig.class);
    }

    /**
     * 将JSON字符串解析为指定类型的对象
     * @param json JSON字符串
     * @param clazz 目标类型
     * @param <T> 目标类型
     * @return 目标类型的对象
     * @throws IOException 解析异常
     */
    public <T> T parse(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * 将FlowConfig对象转换为JSON字符串
     * @param flowConfig FlowConfig对象
     * @return JSON字符串
     * @throws IOException 转换异常
     */
    public String toJson(FlowConfig flowConfig) throws IOException {
        return objectMapper.writeValueAsString(flowConfig);
    }

    /**
     * 将Map对象转换为JSON字符串
     * @param map Map对象
     * @return JSON字符串
     * @throws IOException 转换异常
     */
    public String toJson(Map<String, Object> map) throws IOException {
        return objectMapper.writeValueAsString(map);
    }
}
