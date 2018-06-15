package org.flow.boot.process.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程步骤页面元素
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_page_item")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowPageItem {

	@Id
	@Column(name = "item_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String itemId;

	@Column(name = "name")
	private String name;

	@Column(name = "html", columnDefinition = "TEXT")
	private String html;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
