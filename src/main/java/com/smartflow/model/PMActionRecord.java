package com.smartflow.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tpm.PMActionRecord")
public class PMActionRecord {
	private Integer Id;
	private WorkOrderItem WorkOrderItem;//WorkOrderItemId;
	private Integer ActionSequence;
	private UserModel Worker;//WorkerId;
	private Date ActionStartTime;
	private Date ActionEndTime;
	private Integer ActionResult;
	private Integer MeasurementDataRecordId;//MeasurementDataRecordId;
	private UserModel Reviewer;//ReviewerId;
	private Date ReviewDateTime;
	private Integer ReviewResult;
	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@ManyToOne
	@JoinColumn(name="WorkOrderItemId")
	public WorkOrderItem getWorkOrderItem() {
		return WorkOrderItem;
	}
	public void setWorkOrderItem(WorkOrderItem workOrderItem) {
		WorkOrderItem = workOrderItem;
	}
	public Integer getActionSequence() {
		return ActionSequence;
	}
	public void setActionSequence(Integer actionSequence) {
		ActionSequence = actionSequence;
	}
	@ManyToOne
	@JoinColumn(name="WorkerId")
	public UserModel getWorker() {
		return Worker;
	}
	public void setWorker(UserModel worker) {
		Worker = worker;
	}
	public Date getActionStartTime() {
		return ActionStartTime;
	}
	public void setActionStartTime(Date actionStartTime) {
		ActionStartTime = actionStartTime;
	}
	public Date getActionEndTime() {
		return ActionEndTime;
	}
	public void setActionEndTime(Date actionEndTime) {
		ActionEndTime = actionEndTime;
	}
	public Integer getActionResult() {
		return ActionResult;
	}
	public void setActionResult(Integer actionResult) {
		ActionResult = actionResult;
	}
	//@OneToOne(mappedBy="PMActionRecord")
	//@JoinColumn(name="MeasurementDataRecordId")
	public Integer getMeasurementDataRecordId() {
		return MeasurementDataRecordId;
	}
	public void setMeasurementDataRecordId(Integer measurementDataRecordId) {
		MeasurementDataRecordId = measurementDataRecordId;
	}
	@ManyToOne
	@JoinColumn(name="ReviewerId")
	public UserModel getReviewer() {
		return Reviewer;
	}	
	public void setReviewer(UserModel reviewer) {
		Reviewer = reviewer;
	}
	public Date getReviewDateTime() {
		return ReviewDateTime;
	}
	public void setReviewDateTime(Date reviewDateTime) {
		ReviewDateTime = reviewDateTime;
	}
	public Integer getReviewResult() {
		return ReviewResult;
	}
	public void setReviewResult(Integer reviewResult) {
		ReviewResult = reviewResult;
	}

	
}
