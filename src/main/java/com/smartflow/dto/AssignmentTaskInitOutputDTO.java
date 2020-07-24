package com.smartflow.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignmentTaskInitOutputDTO {
	
	private List<AssignmentTaskInitOutputRowDTO> TaskList;//分配的任务列表
	private List<Map<String,Object>> RoleGroupList;//角色组
//	private List<Map<String,Object>> StuffList;//人员
	
	@JsonProperty("TaskList")
	public List<AssignmentTaskInitOutputRowDTO> getTaskList() {
		return TaskList;
	}
	public void setTaskList(List<AssignmentTaskInitOutputRowDTO> taskList) {
		TaskList = taskList;
	}
	@JsonProperty("RoleGroupList")
	public List<Map<String, Object>> getRoleGroupList() {
		return RoleGroupList;
	}
	public void setRoleGroupList(List<Map<String, Object>> roleGroupList) {
		RoleGroupList = roleGroupList;
	}
//	@JsonProperty("StuffList")
//	public List<Map<String, Object>> getStuffList() {
//		return StuffList;
//	}
//	public void setStuffList(List<Map<String, Object>> stuffList) {
//		StuffList = stuffList;
//	}


	public static class AssignmentTaskInitOutputRowDTO{		
		private Integer TaskId;//任务Id
		private String State;//状态
		private String AssignmentTaskSheet;//派工任务单
		private String TargetFacility;//目标设备
		private String ItemName;//项目名
		private String Position;//位置
		private String RoleInCharge;//负责角色
		private BigDecimal PredictTaskLength;//预估任务时长
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
		@JsonProperty("Position")
		public String getPosition() {
			return Position;
		}
		public void setPosition(String position) {
			Position = position;
		}
		@JsonProperty("RoleInCharge")
		public String getRoleInCharge() {
			return RoleInCharge;
		}
		public void setRoleInCharge(String roleInCharge) {
			RoleInCharge = roleInCharge;
		}
		@JsonProperty("PredictTaskLength")
		public BigDecimal getPredictTaskLength() {
			return PredictTaskLength;
		}		
		public void setPredictTaskLength(BigDecimal predictTaskLength) {
			PredictTaskLength = predictTaskLength;
		}
		
		public AssignmentTaskInitOutputRowDTO() {

		}
		public AssignmentTaskInitOutputRowDTO(Integer taskId, String state, String assignmentTaskSheet,
				String targetFacility, String itemName, String position, String roleInCharge,
				BigDecimal predictTaskLength) {
			TaskId = taskId;
			State = state;
			AssignmentTaskSheet = assignmentTaskSheet;
			TargetFacility = targetFacility;
			ItemName = itemName;
			Position = position;
			RoleInCharge = roleInCharge;
			PredictTaskLength = predictTaskLength;
		}

		
		
	}

}
