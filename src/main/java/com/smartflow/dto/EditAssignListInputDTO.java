package com.smartflow.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditAssignListInputDTO {

	private Integer WorkOrderId;
	private Integer TargetFacilityId;//目标设备id
	private Integer RoleInChargeId;//责任角色id
	private String DateTime;//时间
	//private String AssignNumber;//派工单号
	//private String Name;//名称
	private Integer UserId;//编辑人
	//private Integer WorkType;//派工类型
	//private Integer LeaderId;//负责人id
	private List<EditMaintenanceStepsInputDTO> MaintenanceStepsList;
	@JsonProperty("WorkOrderId")
	public Integer getWorkOrderId() {
		return WorkOrderId;
	}
	public void setWorkOrderId(Integer workOrderId) {
		WorkOrderId = workOrderId;
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
	public List<EditMaintenanceStepsInputDTO> getMaintenanceStepsList() {
		return MaintenanceStepsList;
	}
	public void setMaintenanceStepsList(List<EditMaintenanceStepsInputDTO> maintenanceStepsList) {
		MaintenanceStepsList = maintenanceStepsList;
	}
	
	public EditAssignListInputDTO() {
		// TODO Auto-generated constructor stub
	}
}
