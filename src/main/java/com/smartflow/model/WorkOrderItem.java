package com.smartflow.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tpm.WorkOrderItem")
public class WorkOrderItem {

	private Integer Id;
	private WorkOrder WorkOrder;//WorkOrderId;
	private Role Role;//AssignedToRoleId
	private UserModel User;//AssignedToWorkerId;
	private String MaintenanceItemName;
	private String WorkItemName;
	private String Designator;
	private String MaterialNumber;
	private String GuideDocURI;
	private Long LabourTimeSec;
	private Date EstimatedStartTime;
	private Date EstimatedEndTime;
	private Integer FinalResult;
	private Integer MeasurementType;
	private BigDecimal USL;
	private BigDecimal LSL;
	private BigDecimal NormalValue;
	private Integer Status;
	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="WorkOrderId")
	public WorkOrder getWorkOrder() {
		return WorkOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		WorkOrder = workOrder;
	}
	
	@ManyToOne
	@JoinColumn(name="AssignedToRoleId")
	public Role getRole() {
		return Role;
	}

	public void setRole(Role role) {
		Role = role;
	}

	@ManyToOne
	@JoinColumn(name="AssignedToWorkerId")
	public UserModel getUser() {
		return User;
	}

	public void setUser(UserModel user) {
		User = user;
	}
	
	public String getMaintenanceItemName() {
		return MaintenanceItemName;
	}

	public void setMaintenanceItemName(String maintenanceItemName) {
		MaintenanceItemName = maintenanceItemName;
	}

	public String getWorkItemName() {
		return WorkItemName;
	}

	public void setWorkItemName(String workItemName) {
		WorkItemName = workItemName;
	}

	public String getDesignator() {
		return Designator;
	}

	public void setDesignator(String designator) {
		Designator = designator;
	}

	public String getMaterialNumber() {
		return MaterialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		MaterialNumber = materialNumber;
	}

	public String getGuideDocURI() {
		return GuideDocURI;
	}

	public void setGuideDocURI(String guideDocURI) {
		GuideDocURI = guideDocURI;
	}

	public Long getLabourTimeSec() {
		return LabourTimeSec;
	}

	public void setLabourTimeSec(Long labourTimeSec) {
		LabourTimeSec = labourTimeSec;
	}

	public Date getEstimatedStartTime() {
		return EstimatedStartTime;
	}

	public void setEstimatedStartTime(Date estimatedStartTime) {
		EstimatedStartTime = estimatedStartTime;
	}

	public Date getEstimatedEndTime() {
		return EstimatedEndTime;
	}

	public void setEstimatedEndTime(Date estimatedEndTime) {
		EstimatedEndTime = estimatedEndTime;
	}

	public Integer getFinalResult() {
		return FinalResult;
	}

	public void setFinalResult(Integer finalResult) {
		FinalResult = finalResult;
	}

	public Integer getMeasurementType() {
		return MeasurementType;
	}

	public void setMeasurementType(Integer measurementType) {
		MeasurementType = measurementType;
	}

	public BigDecimal getUSL() {
		return USL;
	}

	public void setUSL(BigDecimal uSL) {
		USL = uSL;
	}

	public BigDecimal getLSL() {
		return LSL;
	}

	public void setLSL(BigDecimal lSL) {
		LSL = lSL;
	}

	public BigDecimal getNormalValue() {
		return NormalValue;
	}

	public void setNormalValue(BigDecimal normalValue) {
		NormalValue = normalValue;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public WorkOrderItem() {
		
	}

	/**
	 * @param id
	 * @param workOrder
	 * @param user
	 * @param workItemName
	 * @param designator
	 * @param estimatedStartTime
	 * @param estimatedEndTime
	 * @param status
	 */
	public WorkOrderItem(Integer id, WorkOrder workOrder, UserModel user, String workItemName,
			String designator, Date estimatedStartTime, Date estimatedEndTime, Integer status) {
		Id = id;
		WorkOrder = workOrder;
		User = user;
		WorkItemName = workItemName;
		Designator = designator;
		EstimatedStartTime = estimatedStartTime;
		EstimatedEndTime = estimatedEndTime;
		Status = status;
	}

	
	
}
