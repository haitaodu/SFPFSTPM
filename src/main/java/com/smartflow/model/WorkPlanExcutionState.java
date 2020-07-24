package com.smartflow.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="tpm.WorkPlanExcutionState")
public class WorkPlanExcutionState {
	private Integer Id;
	private WorkOrder WorkOrder;//TPMWorkOrderId
	private TPMWorkPlan_Reminder TPMWorkPlan_Reminder;//TPMWorkPlan_ReminderId;
	private Integer State;
	private Date CreationDateTime;
	private Date StateChangeDateTime;
	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@ManyToOne
	@JoinColumn(name="TPMWorkOrderId")
	public WorkOrder getWorkOrder() {
		return WorkOrder;
	}
	public void setWorkOrder(WorkOrder workOrder) {
		WorkOrder = workOrder;
	}
	@ManyToOne
	@JoinColumn(name="TPMWorkPlan_ReminderId")
	public TPMWorkPlan_Reminder getTPMWorkPlan_Reminder() {
		return TPMWorkPlan_Reminder;
	}
	public void setTPMWorkPlan_Reminder(TPMWorkPlan_Reminder tPMWorkPlan_Reminder) {
		TPMWorkPlan_Reminder = tPMWorkPlan_Reminder;
	}
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	public Date getCreationDateTime() {
		return CreationDateTime;
	}
	public void setCreationDateTime(Date creationDateTime) {
		CreationDateTime = creationDateTime;
	}
	public Date getStateChangeDateTime() {
		return StateChangeDateTime;
	}
	public void setStateChangeDateTime(Date stateChangeDateTime) {
		StateChangeDateTime = stateChangeDateTime;
	}
	
}
