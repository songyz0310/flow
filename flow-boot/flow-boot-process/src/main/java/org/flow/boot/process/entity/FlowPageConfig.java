package org.flow.boot.process.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程步骤页面配置
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_page_config")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowPageConfig {

	@Id
	@Column(name = "config_id", length = 32)
	@GeneratedValue(generator = "jpa-uuid")
	private String configId;

	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "rank")
	private int rank;

	@Column(name = "table_value", length = 64)
	private String tableValue;

	@Column(name = "default_value", length = 64)
	private String defaultValue;

	@Column(name = "reg", length = 64)
	private String reg;

	@Column(name = "reg_tip", length = 64)
	private String reg_tip;

	@Column(name = "required")
	private boolean required;

	@Column(name = "list_value", length = 1024)
	private String listValue;

	@Column(name = "attachments", length = 1024)
	private String attachments;

	@Column(name = "page_id", updatable = false, length = 32)
	private String pageId;

	@Column(name = "item_id", updatable = false, length = 32)
	private String itemId;

	@Column(name = "parent_fpc_id", updatable = false, length = 32)
	private String parentFpcId;

	/*********************************************/

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
