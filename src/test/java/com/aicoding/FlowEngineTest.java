package com.aicoding;

import com.aicoding.entity.FlowDefinition;
import com.aicoding.entity.FlowInstance;
import com.aicoding.service.FlowDefinitionService;
import com.aicoding.service.FlowInstanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 流程引擎测试类
 * @author gll
 * dateTime 2025/11/27 16:40
 */
@SpringBootTest
public class FlowEngineTest {
    @Autowired
    private FlowDefinitionService flowDefinitionService;

    @Autowired
    private FlowInstanceService flowInstanceService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        // 删除flow_definition表
        String dropFlowDefinitionTable = "DROP TABLE IF EXISTS flow_definition";
        jdbcTemplate.execute(dropFlowDefinitionTable);

        // 删除flow_instance表
        String dropFlowInstanceTable = "DROP TABLE IF EXISTS flow_instance";
        jdbcTemplate.execute(dropFlowInstanceTable);

        // 创建flow_definition表
        String createFlowDefinitionTable = "CREATE TABLE IF NOT EXISTS flow_definition (id INTEGER PRIMARY KEY AUTOINCREMENT, flow_id VARCHAR(50) NOT NULL, flow_name VARCHAR(100) NOT NULL, version INTEGER NOT NULL, flow_config TEXT NOT NULL, status INTEGER NOT NULL, create_time DATETIME NOT NULL, update_time DATETIME NOT NULL)";
        jdbcTemplate.execute(createFlowDefinitionTable);

        // 创建flow_instance表
        String createFlowInstanceTable = "CREATE TABLE IF NOT EXISTS flow_instance (id INTEGER PRIMARY KEY AUTOINCREMENT, instance_id VARCHAR(50) NOT NULL, flow_id VARCHAR(50) NOT NULL, version INTEGER NOT NULL, variables TEXT, current_node_id VARCHAR(50), status INTEGER NOT NULL, create_time DATETIME NOT NULL, start_time DATETIME, finish_time DATETIME)";
        jdbcTemplate.execute(createFlowInstanceTable);
    }

    @Test
    public void testFlowEngine() throws Exception {
        // 1. 创建流程定义
        FlowDefinition flowDefinition = new FlowDefinition();
        flowDefinition.setFlowId("test_flow_001");
        flowDefinition.setFlowName("测试流程");
        flowDefinition.setVersion(1);
        flowDefinition.setStatus(1); // 激活
        flowDefinition.setCreateTime(LocalDateTime.now());
        flowDefinition.setUpdateTime(LocalDateTime.now());

        // 流程JSON配置
        String flowConfig = "{\"flowId\": \"test_flow_001\",\"flowName\": \"测试流程\",\"version\": 1,\"nodes\": [{\"nodeId\": \"start\", \"nodeName\": \"开始节点\", \"nodeType\": \"start\"},{\"nodeId\": \"task1\", \"nodeName\": \"任务节点1\", \"nodeType\": \"task\"},{\"nodeId\": \"task2\", \"nodeName\": \"任务节点2\", \"nodeType\": \"task\"},{\"nodeId\": \"end\", \"nodeName\": \"结束节点\", \"nodeType\": \"end\"}],\"edges\": [{\"edgeId\": \"edge1\", \"sourceNodeId\": \"start\", \"targetNodeId\": \"task1\"},{\"edgeId\": \"edge2\", \"sourceNodeId\": \"task1\", \"targetNodeId\": \"task2\"},{\"edgeId\": \"edge3\", \"sourceNodeId\": \"task2\", \"targetNodeId\": \"end\"}]}";
        flowDefinition.setFlowConfig(flowConfig);

        // 保存流程定义
        flowDefinitionService.save(flowDefinition);

        // 2. 启动流程实例
        Map<String, Object> variables = new HashMap<>();
        variables.put("param1", "value1");
        variables.put("param2", 123);

        FlowInstance instance = flowInstanceService.startInstance("test_flow_001", variables);

        // 3. 验证流程实例
        assertNotNull(instance);
        assertEquals("test_flow_001", instance.getFlowId());
        assertEquals(1, instance.getVersion());
        assertEquals(1, instance.getStatus()); // 已完成
        assertNotNull(instance.getFinishTime());

        System.out.println("流程引擎测试成功！");
    }

    @Test
    public void testFlowWithCondition() throws Exception {
        // 1. 创建带条件的流程定义
        FlowDefinition flowDefinition = new FlowDefinition();
        flowDefinition.setFlowId("test_flow_002");
        flowDefinition.setFlowName("带条件的测试流程");
        flowDefinition.setVersion(1);
        flowDefinition.setStatus(1); // 激活
        flowDefinition.setCreateTime(LocalDateTime.now());
        flowDefinition.setUpdateTime(LocalDateTime.now());

        // 流程JSON配置（带条件判断）
        String flowConfig = "{\"flowId\": \"test_flow_002\",\"flowName\": \"带条件的测试流程\",\"version\": 1,\"nodes\": [{\"nodeId\": \"start\", \"nodeName\": \"开始节点\", \"nodeType\": \"start\"},{\"nodeId\": \"task1\", \"nodeName\": \"任务节点1\", \"nodeType\": \"task\"},{\"nodeId\": \"task2\", \"nodeName\": \"任务节点2\", \"nodeType\": \"task\"},{\"nodeId\": \"task3\", \"nodeName\": \"任务节点3\", \"nodeType\": \"task\"},{\"nodeId\": \"end\", \"nodeName\": \"结束节点\", \"nodeType\": \"end\"}],\"edges\": [{\"edgeId\": \"edge1\", \"sourceNodeId\": \"start\", \"targetNodeId\": \"task1\"},{\"edgeId\": \"edge2\", \"sourceNodeId\": \"task1\", \"targetNodeId\": \"task2\", \"condition\": \"age >= 18\", \"conditionType\": \"script\"},{\"edgeId\": \"edge3\", \"sourceNodeId\": \"task1\", \"targetNodeId\": \"task3\", \"condition\": \"age < 18\", \"conditionType\": \"script\"},{\"edgeId\": \"edge4\", \"sourceNodeId\": \"task2\", \"targetNodeId\": \"end\"},{\"edgeId\": \"edge5\", \"sourceNodeId\": \"task3\", \"targetNodeId\": \"end\"}]}";
        flowDefinition.setFlowConfig(flowConfig);

        // 保存流程定义
        flowDefinitionService.save(flowDefinition);

        // 2. 启动流程实例（age >= 18）
        Map<String, Object> variables1 = new HashMap<>();
        variables1.put("age", 20);

        FlowInstance instance1 = flowInstanceService.startInstance("test_flow_002", variables1);
        assertNotNull(instance1);
        assertEquals(1, instance1.getStatus()); // 已完成

        // 3. 启动流程实例（age < 18）
        Map<String, Object> variables2 = new HashMap<>();
        variables2.put("age", 16);

        FlowInstance instance2 = flowInstanceService.startInstance("test_flow_002", variables2);
        assertNotNull(instance2);
        assertEquals(1, instance2.getStatus()); // 已完成

        System.out.println("带条件的流程引擎测试成功！");
    }
}
