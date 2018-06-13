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
@Table(name = "flow_step_page_config")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowStepPageConfig {

	@Id
	@Column(name = "config_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String configId;

	@Column(name = "name")
	private String name;

	@Column(name = "rank")
	private int rank;

	@Column(name = "step_id", updatable = false)
	private String stepId;

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

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

}
