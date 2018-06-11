package com.song.flow.boot.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程步骤活动
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_step_activity")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowStepActivity extends BaseEntityType {

	@Id
	@Column(name = "activity_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String activityId;

	@Column(name = "step_id", updatable = false)
	private String stepId;

	@Column(name = "create_time", updatable = false)
	private String createTime;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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
