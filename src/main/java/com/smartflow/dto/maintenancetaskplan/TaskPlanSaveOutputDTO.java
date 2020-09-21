package com.smartflow.dto.maintenancetaskplan;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskPlanSaveOutputDTO {
	Integer CreatorId;
	boolean PeriodicType;//周期类型
	List<PeriodicTypeDTO> PeriodicTypeList;//周期类型实体
	String PlanName;
	Integer TargetFacilityId;
	Integer State;
	//Integer PlanTypeId;
	Integer MainRoleId;
	List<TaskPlanStepOutPutDTO> TaskPlanStepOutPutDTOs;
	@JsonProperty("PlanName")
	public String getPlanName() {
		return PlanName;
	}
	@JsonProperty("TargetFacilityId")
	public Integer getTargetFacilityId() {
		return TargetFacilityId;
	}
	@JsonProperty("State")
	public Integer getState() {
		return State;
	}
	/*
	@JsonProperty("PlanTypeId")
	public Integer getPlanTypeId() {
		return PlanTypeId;
	}
	*/
	@JsonProperty("MainRoleId")
	public Integer getMainRoleId() {
		return MainRoleId;
	}
	@JsonProperty("TaskPlanStepOutPutDTOs")
	public List<TaskPlanStepOutPutDTO> getTaskPlanStepOutPutDTOs() {
		return TaskPlanStepOutPutDTOs;
	}

	public void setPlanName(String planName) {
		this.PlanName = planName;
	}
	public void setTargetFacilityId(Integer targetFacilityId) {
		this.TargetFacilityId = targetFacilityId;
	}
	public void setState(Integer state) {
		this.State = state;
	}
	/*
	public void setPlanTypeId(Integer planTypeId) {
		this.PlanTypeId = planTypeId;
	}
	*/
	public void setMainRoleId(Integer mainRoleId) {
		this.MainRoleId = mainRoleId;
	}
	public void setTaskPlanStepOutPutDTOs(List<TaskPlanStepOutPutDTO> taskPlanStepOutPutDTOs) {
		this.TaskPlanStepOutPutDTOs = taskPlanStepOutPutDTOs;
	}

	public void setId(Integer id) {
	}
	@JsonProperty("PeriodicType")
	public boolean getPeriodicType() {
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
	@JsonProperty("CreatorId")
	public Integer getCreatorId() {
		return CreatorId;
	}
	public void setCreatorId(Integer creatorId) {
		CreatorId = creatorId;
	}

}
