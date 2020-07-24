package com.smartflow.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tpm.TPMWorkPlan_Reminder")
public class TPMWorkPlan_ReminderModel {
	private Integer Id;
	private Integer TPMWorkPlanId;//TPMWorkPlanId;
	private Integer ReminderId;//ReminderId;
	private Date AssignedDateTime;
	private Integer CreatorId;
	@Id
	@GeneratedValue
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	
	public Integer getTPMWorkPlanId() {
		return TPMWorkPlanId;
	}
	public void setTPMWorkPlanId(Integer tPMWorkPlanId) {
		TPMWorkPlanId = tPMWorkPlanId;
	}
	
	public Integer getReminderId() {
		return ReminderId;
	}
	public void setReminderId(Integer reminderId) {
		ReminderId = reminderId;
	}

	public Date getAssignedDateTime() {
		return AssignedDateTime;
	}
	public void setAssignedDateTime(Date assignedDateTime) {
		AssignedDateTime = assignedDateTime;
	}

	public Integer getCreatorId() {
		return CreatorId;
	}
	public void setCreatorId(Integer creatorId) {
		CreatorId = creatorId;
	}
	
}
