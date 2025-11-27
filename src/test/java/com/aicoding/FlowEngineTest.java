package com.aicoding;

import com.aicoding.entity.FlowDefinition;
import com.aicoding.entity.FlowInstance;
import com.aicoding.service.FlowDefinitionService;
import com.aicoding.service.FlowInstanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 流程引擎测试类
 * @author gll
 * dateTime 2025/11/27 16:40
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FlowApplication.class)
public class FlowEngineTest {
    @Autowired
    private FlowDefinitionService flowDefinitionService;

    @Autowired
    private FlowInstanceService flowInstanceService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        if (jdbcTemplate == null) {
            System.out.println("jdbcTemplate为null，无法初始化数据库");
            return;
        }
        System.out.println("初始化数据库...");
        try {
            // 删除flow_definition表
            String dropFlowDefinitionTable = "DROP TABLE IF EXISTS flow_definition";
            jdbcTemplate.execute(dropFlowDefinitionTable);
            // 删除flow_instance表
            String dropFlowInstanceTable = "DROP TABLE IF EXISTS flow_instance";
            jdbcTemplate.execute(dropFlowInstanceTable);
            // 创建flow_definition表
            String createFlowDefinitionTable = "CREATE TABLE flow_definition (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "flow_id VARCHAR(255) NOT NULL, " +
                    "flow_name VARCHAR(255) NOT NULL, " +
                    "version INTEGER NOT NULL, " +
                    "flow_config TEXT NOT NULL, " +
                    "status INTEGER NOT NULL DEFAULT 0, " +
                    "create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            jdbcTemplate.execute(createFlowDefinitionTable);
            // 创建flow_instance表
            String createFlowInstanceTable = "CREATE TABLE flow_instance (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "instance_id VARCHAR(255) NOT NULL, " +
                    "flow_id VARCHAR(255) NOT NULL, " +
                    "version INTEGER NOT NULL, " +
                    "status INTEGER NOT NULL DEFAULT 0, " +
                    "variables TEXT, " +
                    "current_node_id VARCHAR(255), " +
                    "create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "finish_time TIMESTAMP" +
                    ")";
            jdbcTemplate.execute(createFlowInstanceTable);
            // 查看表结构
            System.out.println("flow_definition表结构:");
            List<Map<String, Object>> flowDefinitionColumns = jdbcTemplate.queryForList("PRAGMA table_info(flow_definition)");
            for (Map<String, Object> column : flowDefinitionColumns) {
                System.out.println(column);
            }
            System.out.println("flow_instance表结构:");
            List<Map<String, Object>> flowInstanceColumns = jdbcTemplate.queryForList("PRAGMA table_info(flow_instance)");
            for (Map<String, Object> column : flowInstanceColumns) {
                System.out.println(column);
            }
            System.out.println("数据库初始化成功");
        } catch (Exception e) {
            System.out.println("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testFlowEngine() throws Exception {
        // 初始化数据库表
        initDatabase();

        // 1. 创建流程定义
        FlowDefinition flowDefinition = new FlowDefinition();
        flowDefinition.setFlowId("test-flow");
        flowDefinition.setFlowName("测试流程");
        flowDefinition.setVersion(1);
        flowDefinition.setStatus(1); // 激活
        flowDefinition.setCreateTime(LocalDateTime.now());
        flowDefinition.setUpdateTime(LocalDateTime.now());

        // 流程JSON配置
        String flowConfig = "{\"nodes\": [{\"nodeId\": \"start\", \"nodeName\": \"开始节点\", \"nodeType\": \"start\"},{\"nodeId\": \"task1\", \"nodeName\": \"任务节点1\", \"nodeType\": \"task\"},{\"nodeId\": \"task2\", \"nodeName\": \"任务节点2\", \"nodeType\": \"task\"},{\"nodeId\": \"end\", \"nodeName\": \"结束节点\", \"nodeType\": \"end\"}],\"edges\": [{\"edgeId\": \"edge1\", \"sourceNodeId\": \"start\", \"targetNodeId\": \"task1\"},{\"edgeId\": \"edge2\", \"sourceNodeId\": \"task1\", \"targetNodeId\": \"task2\"},{\"edgeId\": \"edge3\", \"sourceNodeId\": \"task2\", \"targetNodeId\": \"end\"}]}";
        flowDefinition.setFlowConfig(flowConfig);

        // 打印flowDefinitionService和flowDefinition的内容
        System.out.println("flowDefinitionService: " + flowDefinitionService);
        System.out.println("flowDefinition: " + flowDefinition);

        // 打印jdbcTemplate的内容
        System.out.println("jdbcTemplate: " + jdbcTemplate);

        // 查看数据库配置
        if (jdbcTemplate != null && jdbcTemplate.getDataSource() != null) {
            System.out.println("数据库驱动类名: " + jdbcTemplate.getDataSource().getClass().getName());
        } else {
            System.out.println("jdbcTemplate或数据源为null");
        }

        // 查看数据库连接状态
        try {
            if (jdbcTemplate != null) {
                jdbcTemplate.execute("SELECT 1");
                System.out.println("数据库连接正常");
            } else {
                System.out.println("jdbcTemplate为null");
            }
        } catch (Exception e) {
            System.out.println("数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }

        // 查看数据库中的流程定义
        List<Map<String, Object>> flowDefinitionsBeforeSave = jdbcTemplate.queryForList("SELECT * FROM flow_definition");
        System.out.println("保存前数据库中的流程定义: " + flowDefinitionsBeforeSave);

        // 保存流程定义
        boolean saved = flowDefinitionService.save(flowDefinition);
        System.out.println("流程定义保存结果: " + saved);
        assertTrue(saved, "流程定义保存失败");

        // 查看数据库中的流程定义
        List<Map<String, Object>> flowDefinitionsAfterSave = jdbcTemplate.queryForList("SELECT * FROM flow_definition");
        System.out.println("保存后数据库中的流程定义: " + flowDefinitionsAfterSave);

        // 获取最新版本的流程定义
        FlowDefinition latestFlowDefinition = flowDefinitionService.getLatestVersion("test-flow");
        System.out.println("最新版本的流程定义: " + latestFlowDefinition);
        assertNotNull(latestFlowDefinition, "未找到最新版本的流程定义");

        // 2. 启动流程实例
        Map<String, Object> variables = new HashMap<>();
        variables.put("amount", 1000);

        FlowInstance instance = flowInstanceService.startInstance("test-flow", variables);
        System.out.println("启动流程实例结果: " + instance);

        // 3. 验证流程实例
        assertNotNull(instance);
        assertEquals("test-flow", instance.getFlowId());
        assertEquals(1, instance.getVersion());
        assertEquals(1, instance.getStatus()); // 运行中
        assertNotNull(instance.getStartTime());
        assertNotNull(instance.getFinishTime());

        System.out.println("流程引擎测试成功！");
    }

    @Test
    public void testFlowWithCondition() throws Exception {
        // 初始化数据库表
        initDatabase();

        // 1. 创建带条件的流程定义
        FlowDefinition flowDefinition = new FlowDefinition();
        flowDefinition.setFlowId("test-flow-with-condition");
        flowDefinition.setFlowName("带条件的测试流程");
        flowDefinition.setVersion(1);
        flowDefinition.setStatus(1); // 激活
        flowDefinition.setCreateTime(LocalDateTime.now());
        flowDefinition.setUpdateTime(LocalDateTime.now());

        // 流程JSON配置（带条件判断）
        String flowConfig = "{\"nodes\": [{\"nodeId\": \"start\", \"nodeName\": \"开始节点\", \"nodeType\": \"start\"},{\"nodeId\": \"task1\", \"nodeName\": \"任务节点1\", \"nodeType\": \"task\"},{\"nodeId\": \"task2\", \"nodeName\": \"任务节点2\", \"nodeType\": \"task\"},{\"nodeId\": \"task3\", \"nodeName\": \"任务节点3\", \"nodeType\": \"task\"},{\"nodeId\": \"end\", \"nodeName\": \"结束节点\", \"nodeType\": \"end\"}],\"edges\": [{\"edgeId\": \"edge1\", \"sourceNodeId\": \"start\", \"targetNodeId\": \"task1\"},{\"edgeId\": \"edge2\", \"sourceNodeId\": \"task1\", \"targetNodeId\": \"task2\", \"condition\": \"age >= 18\", \"conditionType\": \"script\"},{\"edgeId\": \"edge3\", \"sourceNodeId\": \"task1\", \"targetNodeId\": \"task3\", \"condition\": \"age < 18\", \"conditionType\": \"script\"},{\"edgeId\": \"edge4\", \"sourceNodeId\": \"task2\", \"targetNodeId\": \"end\"},{\"edgeId\": \"edge5\", \"sourceNodeId\": \"task3\", \"targetNodeId\": \"end\"}]}";
        flowDefinition.setFlowConfig(flowConfig);

        // 打印flowDefinitionService和flowDefinition的内容
        System.out.println("flowDefinitionService: " + flowDefinitionService);
        System.out.println("flowDefinition: " + flowDefinition);

        // 查看数据库连接状态
        try {
            jdbcTemplate.execute("SELECT 1");
            System.out.println("数据库连接正常");
        } catch (Exception e) {
            System.out.println("数据库连接失败: " + e.getMessage());
        }

        // 查看数据库中的流程定义
        List<Map<String, Object>> flowDefinitionsBeforeSave = jdbcTemplate.queryForList("SELECT * FROM flow_definition");
        System.out.println("保存前数据库中的流程定义: " + flowDefinitionsBeforeSave);

        // 保存流程定义
        boolean saved = flowDefinitionService.save(flowDefinition);
        System.out.println("流程定义保存结果: " + saved);
        assertTrue(saved, "流程定义保存失败");

        // 查看数据库中的流程定义
        List<Map<String, Object>> flowDefinitionsAfterSave = jdbcTemplate.queryForList("SELECT * FROM flow_definition");
        System.out.println("保存后数据库中的流程定义: " + flowDefinitionsAfterSave);

        // 获取最新版本的流程定义
        FlowDefinition latestFlowDefinition = flowDefinitionService.getLatestVersion("test-flow-with-condition");
        System.out.println("最新版本的流程定义: " + latestFlowDefinition);
        assertNotNull(latestFlowDefinition, "未找到最新版本的流程定义");

        // 2. 启动流程实例（age >= 18）
        Map<String, Object> variables1 = new HashMap<>();
        variables1.put("age", 20);

        FlowInstance instance1 = flowInstanceService.startInstance("test-flow-with-condition", variables1);
        System.out.println("启动带条件的流程实例结果(age >= 18): " + instance1);
        assertNotNull(instance1);
        assertEquals(1, instance1.getStatus()); // 运行中

        // 3. 启动流程实例（age < 18）
        Map<String, Object> variables2 = new HashMap<>();
        variables2.put("age", 16);

        FlowInstance instance2 = flowInstanceService.startInstance("test-flow-with-condition", variables2);
        System.out.println("启动带条件的流程实例结果(age < 18): " + instance2);
        assertNotNull(instance2);
        assertEquals(1, instance2.getStatus()); // 运行中

        System.out.println("带条件的流程引擎测试成功！");
    }
}
