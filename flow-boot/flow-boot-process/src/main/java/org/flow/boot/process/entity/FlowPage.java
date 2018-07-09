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
	@Column(name = "page_id", length = 32)
	@GeneratedValue(generator = "jpa-uuid")
	private String pageId;

	@Column(name = "page_name", length = 32)
	private String pageName;

	@Column(name = "entity_type", updatable = false, length = 32)
	@Enumerated(EnumType.STRING)
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
