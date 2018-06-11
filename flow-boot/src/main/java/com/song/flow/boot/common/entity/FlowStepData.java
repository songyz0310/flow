package com.song.flow.boot.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程步骤数据
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_step_data")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowStepData {

	@Id
	@Column(name = "data_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String dataId;

	@Column(name = "step_id", updatable = false)
	private String stepId;

	@Column(name = "spc_id")
	private String spcId;

	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Column(name = "update_time")
	private String updateTime;

	@Column(name = "value", columnDefinition = "TEXT")
	private String value;

}
