package com.smartflow.dto.maintenancetaskplan;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class TaskPlanSaveOutputDTO {
	Integer CreatorId;
//	boolean PeriodicType;//周期类型
//	List<PeriodicTypeDTO> PeriodicTypeList;//周期类型实体
	@NotEmpty(message = "{taskPlan.PeriodicTypeList.required}")
	List<Integer> PeriodicTypeList;//周期类型
	String TemporaryDate;//临时的日期
	String PlanName;
	@NotEmpty(message = "{taskPlan.TargetFacilityId.required}")
	List<Integer> TargetFacilityId;
	@NotNull(message = "{taskPlan.State.required}")
	Integer State;
	//Integer PlanTypeId;
	@NotNull(message = "{taskPlan.MainRoleId.required}")
	Integer MainRoleId;
//	List<TaskPlanStepOutPutDTO> TaskPlanStepOutPutDTOs;
	@NotNull(message = "{taskPlan.MaintenanceItemId.required}")
	Integer MaintenanceItemId;

	@JsonProperty("PlanName")
	public String getPlanName() {
		return PlanName;
	}
	@JsonProperty("TargetFacilityId")
	public List<Integer> getTargetFacilityId() {
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
	@JsonProperty("TemporaryDate")
	public String getTemporaryDate() {
		return TemporaryDate;
	}

	public void setTemporaryDate(String temporaryDate) {
		TemporaryDate = temporaryDate;
	}

	@JsonProperty("MainRoleId")
	public Integer getMainRoleId() {
		return MainRoleId;
	}
//	@JsonProperty("TaskPlanStepOutPutDTOs")
//	public List<TaskPlanStepOutPutDTO> getTaskPlanStepOutPutDTOs() {
//		return TaskPlanStepOutPutDTOs;
//	}

	public void setPlanName(String planName) {
		this.PlanName = planName;
	}
	public void setTargetFacilityId(List<Integer> targetFacilityId) {
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
//	public void setTaskPlanStepOutPutDTOs(List<TaskPlanStepOutPutDTO> taskPlanStepOutPutDTOs) {
//		this.TaskPlanStepOutPutDTOs = taskPlanStepOutPutDTOs;
//	}

	public void setId(Integer id) {
	}
//	@JsonProperty("PeriodicType")
//	public boolean getPeriodicType() {
//		return PeriodicType;
//	}
//	public void setPeriodicType(boolean periodicType) {
//		PeriodicType = periodicType;
//	}
//	@JsonProperty("PeriodicTypeList")
//	public List<PeriodicTypeDTO> getPeriodicTypeList() {
//		return PeriodicTypeList;
//	}
//	public void setPeriodicTypeList(List<PeriodicTypeDTO> periodicTypeList) {
//		PeriodicTypeList = periodicTypeList;
//	}
	@JsonProperty("PeriodicTypeList")
	public List<Integer> getPeriodicTypeList() {
		return PeriodicTypeList;
	}

	public void setPeriodicTypeList(List<Integer> periodicTypeList) {
		PeriodicTypeList = periodicTypeList;
	}

	@JsonProperty("CreatorId")
	public Integer getCreatorId() {
		return CreatorId;
	}
	public void setCreatorId(Integer creatorId) {
		CreatorId = creatorId;
	}


	@JsonProperty("MaintenanceItemId")
	public Integer getMaintenanceItemId() {
		return MaintenanceItemId;
	}

	public void setMaintenanceItemId(@NotNull Integer maintenanceItemId) {
		MaintenanceItemId = maintenanceItemId;
	}
}
