package com.smartflow.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDetailOutputDTO {

	private Integer TaskId;//任务Id
	private String TargetFacility;//目标设备
	private String ItemName;//项目名
	private String Position;//位置
	private Date AssignDateTime;//分配日期
	private Boolean IsRequireMeasure;//是否要求测量
	private String 	MaintenanceDocumentName;//	维保文档
	private String 	MaintenanceDocumentPath;//	维保文档
	private String State;//状态
	@JsonProperty("TaskId")
	public Integer getTaskId() {
		return TaskId;
	}
	public void setTaskId(Integer taskId) {
		TaskId = taskId;
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
	@JsonProperty("AssignDateTime")
	public Date getAssignDateTime() {
		return AssignDateTime;
	}
	public void setAssignDateTime(Date assignDateTime) {
		AssignDateTime = assignDateTime;
	}
	@JsonProperty("IsRequireMeasure")
	public Boolean getIsRequireMeasure() {
		return IsRequireMeasure;
	}
	public void setIsRequireMeasure(Boolean isRequireMeasure) {
		IsRequireMeasure = isRequireMeasure;
	}
	@JsonProperty("MaintenanceDocumentName")
	public String getMaintenanceDocumentName() {
		return MaintenanceDocumentName;
	}
	public void setMaintenanceDocumentName(String maintenanceDocumentName) {
		MaintenanceDocumentName = maintenanceDocumentName;
	}
	@JsonProperty("MaintenanceDocumentPath")
	public String getMaintenanceDocumentPath() {
		return MaintenanceDocumentPath;
	}
	public void setMaintenanceDocumentPath(String maintenanceDocumentPath) {
		MaintenanceDocumentPath = maintenanceDocumentPath;
	}
	@JsonProperty("State")
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public TaskDetailOutputDTO() {
		
	}
	public TaskDetailOutputDTO(Integer taskId, String targetFacility, String itemName, String position,
			Date assignDateTime, Boolean isRequireMeasure, String maintenanceDocumentName,
			String maintenanceDocumentPath, String state) {
		TaskId = taskId;
		TargetFacility = targetFacility;
		ItemName = itemName;
		Position = position;
		AssignDateTime = assignDateTime;
		IsRequireMeasure = isRequireMeasure;
		MaintenanceDocumentName = maintenanceDocumentName;
		MaintenanceDocumentPath = maintenanceDocumentPath;
		State = state;
	}
	
}
