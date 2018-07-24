package org.flow.boot.process.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.flow.boot.common.enums.EntityType;
import org.hibernate.annotations.GenericGenerator;

/**
 * 流程步骤页面配置
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_page")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowPage {

    @Id
    @Column(name = "page_id", columnDefinition = "varchar(32) COMMENT '页面ID'")
    @GeneratedValue(generator = "jpa-uuid")
    private String pageId;

    @Column(name = "page_name", columnDefinition = "varchar(32) NOT NULL COMMENT '页面名称'")
    private String pageName;

    @Column(name = "entity_type", updatable = false, columnDefinition = "int(3) NOT NULL COMMENT '实体类型：0（工单）'")
    @Enumerated(EnumType.ORDINAL)
    protected EntityType entityType;

    /*********************************************/

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

}
