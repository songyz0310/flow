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
	@Column(name = "config_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String configId;

	@Column(name = "name")
	private String name;

	@Column(name = "rank")
	private int rank;

	@Column(name = "page_id", updatable = false)
	private String pageId;

	@Column(name = "item_id", updatable = false)
	private String itemId;

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
