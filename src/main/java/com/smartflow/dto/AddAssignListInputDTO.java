package com.smartflow.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddAssignListInputDTO {

	private Integer TargetFacilityId;//目标设备id
	private Integer RoleInChargeId;//责任角色id
	private String DateTime;//时间
	//private String AssignNumber;//派工单号
	//private String Name;//名称
	private Integer UserId;//创建人
	//private Integer WorkType;//派工类型
	//private Integer LeaderId;//负责人id
	private List<AddMaintenanceStepsInputDTO> MaintenanceStepsList;
	public AddAssignListInputDTO() {
		// TODO Auto-generated constructor stub
	}
	@JsonProperty("TargetFacilityId")
	public Integer getTargetFacilityId() {
		return TargetFacilityId;
	}
	public void setTargetFacilityId(Integer targetFacilityId) {
		TargetFacilityId = targetFacilityId;
	}
	@JsonProperty("RoleInChargeId")
	public Integer getRoleInChargeId() {
		return RoleInChargeId;
	}
	public void setRoleInChargeId(Integer roleInChargeId) {
		RoleInChargeId = roleInChargeId;
	}
	@JsonProperty("DateTime")
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	/*@JsonProperty("AssignNumber")
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
	}*/
	@JsonProperty("UserId")
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
	}
	/*@JsonProperty("WorkType")
	public Integer getWorkType() {
		return WorkType;
	}
	public void setWorkType(Integer workType) {
		WorkType = workType;
	}
	@JsonProperty("LeaderId")
	public Integer getLeaderId() {
		return LeaderId;
	}
	public void setLeaderId(Integer leaderId) {
		LeaderId = leaderId;
	}*/
	@JsonProperty("MaintenanceStepsList")
	public List<AddMaintenanceStepsInputDTO> getMaintenanceStepsList() {
		return MaintenanceStepsList;
	}
	public void setMaintenanceStepsList(List<AddMaintenanceStepsInputDTO> maintenanceStepsList) {
		MaintenanceStepsList = maintenanceStepsList;
	}
	
}
