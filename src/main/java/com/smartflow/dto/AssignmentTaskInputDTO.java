package com.smartflow.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignmentTaskInputDTO {

	private Integer UserId;//我
	private List<Integer> TaskIdList;//任务id
	private Integer RoleGroupId;//角色组id
	private Integer StaffId;//人员id
	@JsonProperty("UserId")
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
	}
	@JsonProperty("TaskIdList")
	public List<Integer> getTaskIdList() {
		return TaskIdList;
	}
	public void setTaskIdList(List<Integer> taskIdList) {
		TaskIdList = taskIdList;
	}
	@JsonProperty("RoleGroupId")
	public Integer getRoleGroupId() {
		return RoleGroupId;
	}
	public void setRoleGroupId(Integer roleGroupId) {
		RoleGroupId = roleGroupId;
	}
	@JsonProperty("StuffId")
	public Integer getStaffId() {
		return StaffId;
	}
	public void setStaffId(Integer staffId) {
		StaffId = staffId;
	}
	
}
