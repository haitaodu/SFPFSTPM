package com.smartflow.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="tpm.TPMWorkPlan_Reminder")
public class TPMWorkPlan_Reminder {
	private Integer Id;
	private WorkPlan WorkPlan;//TPMWorkPlanId;
	private Reminder Reminder;//ReminderId;
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
	@ManyToOne
	@JoinColumn(name="TPMWorkPlanId")
	public WorkPlan getWorkPlan() {
		return WorkPlan;
	}
	public void setWorkPlan(WorkPlan workPlan) {
		WorkPlan = workPlan;
	}
	@ManyToOne
	@JoinColumn(name="ReminderId")
	public Reminder getReminder() {
		return Reminder;
	}
	public void setReminder(Reminder reminder) {
		Reminder = reminder;
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
