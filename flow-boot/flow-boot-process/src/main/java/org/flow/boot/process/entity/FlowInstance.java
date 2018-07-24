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
 * 流程实例
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "tb_flow_instance")
public class FlowInstance {

    @Id
    @Column(name = "instance_id", columnDefinition = "varchar(64) COMMENT '流程实例主键（引擎返回）'")
    private String instanceId;

    @Column(name = "process_id", updatable = false, columnDefinition = "varchar(64) NOT NULL COMMENT '流程ID'")
    private String processId;

    @Column(name = "entity_type", updatable = false, columnDefinition = "int(3) NOT NULL COMMENT '实体类型：0（工单）'")
    @Enumerated(EnumType.ORDINAL)
    private EntityType entityType;

    @Column(name = "entity_id", updatable = false, columnDefinition = "varchar(64) NOT NULL COMMENT '实体ID'")
    private String entityId;

    @Column(name = "status", columnDefinition = "int(1) NOT NULL COMMENT '实例状态： 0（启动）,1（运行）,2（结束）'")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "create_time", updatable = false, columnDefinition = "datetime NOT NULL COMMENT '创建时间'")
    private Date createTime;

    @Column(name = "update_time", columnDefinition = "datetime NOT NULL COMMENT '更新时间'")
    private Date updateTime;

    @Column(name = "step_id", columnDefinition = "varchar(32) DEFAULT NULL COMMENT '步骤ID'")
    private String stepId;

    /**********************************************/

    public static enum Status {
        STARTED, // 启动
        RUNNING, // 运行
        STOPED,// 结束
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
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

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

}
