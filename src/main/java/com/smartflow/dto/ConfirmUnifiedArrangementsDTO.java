package com.smartflow.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmUnifiedArrangementsDTO {

	//private List<Integer> WorkPlanId;//工作计划id
	//private List<Integer> WorkOrderItemId;//工作项id
	private List<Integer> WorkItemId;//工作项id
	private String ArrangementDate;//安排日期
	private Integer RoleGroupId;//角色组id
	private Integer UserId;
	
//	@JsonProperty("WorkPlanId")
//	public List<Integer> getWorkPlanId() {
//		return WorkPlanId;
//	}
//	public void setWorkPlanId(List<Integer> workPlanId) {
//		WorkPlanId = workPlanId;
//	}
	@JsonProperty("WorkOrderItemId")
	public void setWorkItemId(List<Integer> workItemId) {
		WorkItemId = workItemId;
	}
	public List<Integer> getWorkItemId() {
		return WorkItemId;
	}
	@JsonProperty("ArrangementDate")
	public String getArrangementDate() {
		return ArrangementDate;
	}
	public void setArrangementDate(String arrangementDate) {
		ArrangementDate = arrangementDate;
	}
	@JsonProperty("RoleGroupId")
	public Integer getRoleGroupId() {
		return RoleGroupId;
	}


	public void setRoleGroupId(Integer roleGroupId) {
		RoleGroupId = roleGroupId;
	}

	@JsonProperty("UserId")
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
	}
	public ConfirmUnifiedArrangementsDTO() {
		// TODO Auto-generated constructor stub
	}

}
