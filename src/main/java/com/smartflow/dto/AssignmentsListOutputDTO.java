package com.smartflow.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignmentsListOutputDTO {

	private Integer WorkOrderId;//Id
	private String AssignNumber;//派工单号
	private String Name;//名称
	private Integer VersionNumber;//版本号
	private String PeriodicType;//周期类型
	private String State;//状态
	private String TargetFacilityName;//目标设备名
	private String RoleInCharge;//负责角色
	private Date LastUpdateDate;//最后修改日期
	@JsonProperty("Id")
	public Integer getWorkOrderId() {
		return WorkOrderId;
	}

	public void setWorkOrderId(Integer workOrderId) {
		WorkOrderId = workOrderId;
	}
	@JsonProperty("AssignNumber")
	public String getAssignNumber() {
		return AssignNumber;
	}

	public void setAssignNumber(String assignNumber) {
		AssignNumber = assignNumber;
	}
	@JsonProperty("Name")
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	@JsonProperty("VersionNumber")
	public Integer getVersionNumber() {
		return VersionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		VersionNumber = versionNumber;
	}
	@JsonProperty("PeriodicType")
	public String getPeriodicType() {
		return PeriodicType;
	}

	public void setPeriodicType(String periodicType) {
		PeriodicType = periodicType;
	}
	@JsonProperty("State")
	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}
	@JsonProperty("TargetFacilityName")
	public String getTargetFacilityName() {
		return TargetFacilityName;
	}

	public void setTargetFacilityName(String targetFacilityName) {
		TargetFacilityName = targetFacilityName;
	}
	@JsonProperty("RoleInCharge")
	public String getRoleInCharge() {
		return RoleInCharge;
	}

	public void setRoleInCharge(String roleInCharge) {
		RoleInCharge = roleInCharge;
	}
	@JsonProperty("LastUpdateDate")
	public Date getLastUpdateDate() {
		return LastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		LastUpdateDate = lastUpdateDate;
	}

	public AssignmentsListOutputDTO() {
		
	}

	public AssignmentsListOutputDTO(Integer workOrderId, String assignNumber, String name, Integer versionNumber,
			String periodicType, String state, String targetFacilityName, String roleInCharge, Date lastUpdateDate) {
		WorkOrderId = workOrderId;
		AssignNumber = assignNumber;
		Name = name;
		VersionNumber = versionNumber;
		PeriodicType = periodicType;
		State = state;
		TargetFacilityName = targetFacilityName;
		RoleInCharge = roleInCharge;
		LastUpdateDate = lastUpdateDate;
	}

}
