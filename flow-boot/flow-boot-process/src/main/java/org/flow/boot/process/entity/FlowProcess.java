package org.flow.boot.process.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.flow.boot.common.enums.EntityType;

/**
 * 流程
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "tb_flow_process")
public class FlowProcess {

    @Id
    @Column(name = "process_id", columnDefinition = "varchar(64) COMMENT '流程ID'")
    private String processId;

    @Column(name = "process_definition_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '流程定义主键（流程引擎返回）'")
    private String processDefinitionId;

    @Column(name = "process_key", columnDefinition = "varchar(32) DEFAULT NULL COMMENT '流程定义KEY'")
    private String processKey;

    @Column(name = "process_name", columnDefinition = "varchar(32) DEFAULT NULL COMMENT '流程定义名称'")
    private String processName;

    @Column(name = "file_path", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '流程zip文件路径'")
    private String filePath;

    @Column(name = "entity_type", columnDefinition = "int(3) DEFAULT NULL COMMENT '实体类型：0（工单）'")
    @Enumerated(EnumType.ORDINAL)
    private EntityType entityType;

    @Column(name = "status", columnDefinition = "int(1) DEFAULT NULL COMMENT '流程状态：0（草稿）,1（可用）,2（不可用）'")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "create_time", updatable = false, columnDefinition = "datetime DEFAULT NULL COMMENT '流程定义版本（流程引擎返回）'")
    private Date createTime;

    @Column(name = "update_time", columnDefinition = "datetime DEFAULT NULL COMMENT '流程定义版本（流程引擎返回）'")
    private Date updateTime;

    @Column(name = "version", columnDefinition = "int(3) DEFAULT NULL COMMENT '流程定义版本（流程引擎返回）'")
    private int version;

    @Column(name = "tenant_id", columnDefinition = "int(20) DEFAULT NULL COMMENT '租户ID'")
    private int tenantId;

    /**************************************/

    public static enum Status {
        ENABLED, // 可用
        DISABLED, // 不可用
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

}
