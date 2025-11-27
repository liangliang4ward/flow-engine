-- 创建数据库
CREATE DATABASE IF NOT EXISTS flow_engine DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE flow_engine;

-- 流程定义表
CREATE TABLE IF NOT EXISTS flow_definition (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(255) NOT NULL COMMENT '流程名称',
    code VARCHAR(255) NOT NULL COMMENT '流程编码',
    version INT NOT NULL COMMENT '流程版本',
    config JSON COMMENT '流程配置',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-草稿，1-发布，2-停用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(255) COMMENT '创建人',
    update_by VARCHAR(255) COMMENT '更新人',
    UNIQUE KEY uk_code_version (code, version)
) COMMENT '流程定义表';

-- 流程实例表
CREATE TABLE IF NOT EXISTS flow_instance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    definition_id BIGINT NOT NULL COMMENT '流程定义ID',
    definition_name VARCHAR(255) NOT NULL COMMENT '流程定义名称',
    definition_code VARCHAR(255) NOT NULL COMMENT '流程定义编码',
    definition_version INT NOT NULL COMMENT '流程定义版本',
    business_id VARCHAR(255) NOT NULL COMMENT '业务ID',
    business_type VARCHAR(255) NOT NULL COMMENT '业务类型',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待启动，1-运行中，2-已完成，3-已暂停，4-已取消',
    current_node VARCHAR(255) COMMENT '当前节点',
    variables JSON COMMENT '流程变量',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(255) COMMENT '创建人',
    update_by VARCHAR(255) COMMENT '更新人',
    FOREIGN KEY (definition_id) REFERENCES flow_definition(id)
) COMMENT '流程实例表';

-- 流程节点表
CREATE TABLE IF NOT EXISTS flow_node (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    instance_id BIGINT NOT NULL COMMENT '流程实例ID',
    node_definition_id VARCHAR(255) NOT NULL COMMENT '节点定义ID',
    name VARCHAR(255) NOT NULL COMMENT '节点名称',
    type VARCHAR(255) NOT NULL COMMENT '节点类型',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待执行，1-执行中，2-已完成，3-已跳过，4-执行失败',
    execution_order INT NOT NULL COMMENT '执行顺序',
    params JSON COMMENT '节点参数',
    result JSON COMMENT '执行结果',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (instance_id) REFERENCES flow_instance(id)
) COMMENT '流程节点表';

-- 流程边表
CREATE TABLE IF NOT EXISTS flow_edge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    definition_id BIGINT NOT NULL COMMENT '流程定义ID',
    source_node_id VARCHAR(255) NOT NULL COMMENT '源节点ID',
    target_node_id VARCHAR(255) NOT NULL COMMENT '目标节点ID',
    name VARCHAR(255) COMMENT '边名称',
    type VARCHAR(255) NOT NULL COMMENT '边类型',
    condition VARCHAR(255) COMMENT '条件表达式',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(255) COMMENT '创建人',
    update_by VARCHAR(255) COMMENT '更新人',
    FOREIGN KEY (definition_id) REFERENCES flow_definition(id)
) COMMENT '流程边表';