package com.smartflow.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleGroupTaskListOutputRowDTO {

	private Integer TaskId;//任务Id
	private String State;//状态
	private String AssignmentTaskSheet;//派工任务单
	private String TargetFacility;//目标设备
	private String ItemName;//项目名
//	private String Position;//位置
	private String RoleInCharge;//负责角色
	private String Staff;//人员
//	private BigDecimal PredictTaskLength;//预估任务时长
	@JsonProperty("Id")
	public Integer getTaskId() {
		return TaskId;
	}
	public void setTaskId(Integer taskId) {
		TaskId = taskId;
	}
	@JsonProperty("State")
	public String getState() {
		return State;
	}	
	public void setState(String state) {
		State = state;
	}
	@JsonProperty("AssignmentTaskSheet")
	public String getAssignmentTaskSheet() {
		return AssignmentTaskSheet;
	}
	public void setAssignmentTaskSheet(String assignmentTaskSheet) {
		AssignmentTaskSheet = assignmentTaskSheet;
	}
	@JsonProperty("TargetFacility")
	public String getTargetFacility() {
		return TargetFacility;
	}
	public void setTargetFacility(String targetFacility) {
		TargetFacility = targetFacility;
	}
	@JsonProperty("ItemName")
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
//	@JsonProperty("Position")
//	public String getPosition() {
//		return Position;
//	}
//	public void setPosition(String position) {
//		Position = position;
//	}
	@JsonProperty("RoleInCharge")
	public String getRoleInCharge() {
		return RoleInCharge;
	}
	public void setRoleInCharge(String roleInCharge) {
		RoleInCharge = roleInCharge;
	}
	@JsonProperty("Staff")
	public String getStaff() {
		return Staff;
	}	
	public void setStaff(String staff) {
		Staff = staff;
	}
//	@JsonProperty("PredictTaskLength")
//	public BigDecimal getPredictTaskLength() {
//		return PredictTaskLength;
//	}
//	public void setPredictTaskLength(BigDecimal predictTaskLength) {
//		PredictTaskLength = predictTaskLength;
//	}
	
	public RoleGroupTaskListOutputRowDTO() {
		
	}
	public RoleGroupTaskListOutputRowDTO(Integer taskId, String state, String assignmentTaskSheet,
			String targetFacility, String itemName, //String position,
			String roleInCharge, String staff//,
			//BigDecimal predictTaskLength
			) {
		TaskId = taskId;
		State = state;
		AssignmentTaskSheet = assignmentTaskSheet;
		TargetFacility = targetFacility;
		ItemName = itemName;
//		Position = position;
		RoleInCharge = roleInCharge;
		Staff = staff;
//		PredictTaskLength = predictTaskLength;
	}
	

}
