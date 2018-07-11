package org.flow.boot.process.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.flow.boot.common.enums.process.ItemType;
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
	@Column(name = "item_id", length = 32)
	@GeneratedValue(generator = "jpa-uuid")
	private String itemId;

	@Column(name = "item_name", length = 32)
	private String itemName;

	@Column(name = "item_type", length = 32)
	@Enumerated(EnumType.STRING)
	private ItemType itemType;

	@Column(name = "item_icon", length = 255)
	private String itemIcon;

	@Column(name = "prev_html", columnDefinition = "TEXT")
	private String prevHtml;

	@Column(name = "web_html", columnDefinition = "TEXT")
	private String webHtml;

	@Column(name = "app_html", columnDefinition = "TEXT")
	private String appHtml;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "is_custom")
	private boolean isCustom;

	@Column(name = "tenant_id", length = 32)
	private String tenantId;

	/*******************************************/

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public String getItemIcon() {
		return itemIcon;
	}

	public void setItemIcon(String itemIcon) {
		this.itemIcon = itemIcon;
	}

	public String getPrevHtml() {
		return prevHtml;
	}

	public void setPrevHtml(String prevHtml) {
		this.prevHtml = prevHtml;
	}

	public String getWebHtml() {
		return webHtml;
	}

	public void setWebHtml(String webHtml) {
		this.webHtml = webHtml;
	}

	public String getAppHtml() {
		return appHtml;
	}

	public void setAppHtml(String appHtml) {
		this.appHtml = appHtml;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCustom() {
		return isCustom;
	}

	public void setCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
