package com.song.flow.boot.model.entity.ticket;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.song.flow.boot.model.entity.BaseEntityType;

/**
 * 流程步骤活动
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "sys_flow_step_activity")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class SysFlowStepActivity extends BaseEntityType {

	@Id
	@Column(name = "activity_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String activityId;

	@Column(name = "step_id", updatable = false)
	private String stepId;

	@Column(name = "step_name", updatable = false)
	private String stepName;

	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Column(name = "address", updatable = false)
	private String address;

	@Column(name = "longitude", updatable = false)
	private double longitude;

	@Column(name = "latitude", updatable = false)
	private double latitude;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

}
