package com.smartflow.dto.MaintenanceTaskPlan;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskPlanEditeOutputDTO {
	Integer Id;
	Integer EditorId;	
	boolean PeriodicType;//周期类型
	List<PeriodicTypeDTO> PeriodicTypeList;//周期类型实体
	String PlanName;
	Integer TargetFacilityId;
	Integer State;
	//Integer PlanTypeId;
	Integer MainRoleId;
	List<TaskPlanStepOutPutDTO> TaskPlanStepOutPutDTOs;

	@JsonProperty("EditorId")
	public Integer getEditorId() {
		return EditorId;
	}
	public void setEditorId(Integer editorId) {
		EditorId = editorId;
	}
	@JsonProperty("PeriodicType")
	public boolean isPeriodicType() {
		return PeriodicType;
	}
	public void setPeriodicType(boolean periodicType) {
		PeriodicType = periodicType;
	}
	@JsonProperty("PeriodicTypeList")
	public List<PeriodicTypeDTO> getPeriodicTypeList() {
		return PeriodicTypeList;
	}
	public void setPeriodicTypeList(List<PeriodicTypeDTO> periodicTypeList) {
		PeriodicTypeList = periodicTypeList;
	}
	@JsonProperty("PlanName")
	public String getPlanName() {
		return PlanName;
	}
	public void setPlanName(String planName) {
		PlanName = planName;
	}
	@JsonProperty("TargetFacilityId")
	public Integer getTargetFacilityId() {
		return TargetFacilityId;
	}
	public void setTargetFacilityId(Integer targetFacilityId) {
		TargetFacilityId = targetFacilityId;
	}
	@JsonProperty("State")
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	@JsonProperty("MainRoleId")
	public Integer getMainRoleId() {
		return MainRoleId;
	}
	public void setMainRoleId(Integer mainRoleId) {
		MainRoleId = mainRoleId;
	}
	@JsonProperty("TaskPlanStepOutPutDTOs")
	public List<TaskPlanStepOutPutDTO> getTaskPlanStepOutPutDTOs() {
		return TaskPlanStepOutPutDTOs;
	}
	public void setTaskPlanStepOutPutDTOs(List<TaskPlanStepOutPutDTO> taskPlanStepOutPutDTOs) {
		TaskPlanStepOutPutDTOs = taskPlanStepOutPutDTOs;
	}
	@JsonProperty("Id")
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
}
