package com.smartflow.dto.maintenancetaskplan;

public class TaskPlanHeadDto {
	String PlanName;
	String TargetFacility;
	String State;
	String PlanType;
	String MainRole;
	public TaskPlanHeadDto() {};
	
	public TaskPlanHeadDto(String planName, String targetFacility, String state, String planType, String mainRole) {
		super();
		PlanName = planName;
		TargetFacility = targetFacility;
		State = state;
		PlanType = planType;
		MainRole = mainRole;
	}

	public String getPlanName() {
		return PlanName;
	}
	public void setPlanName(String planName) {
		PlanName = planName;
	}
	public String getTargetFacility() {
		return TargetFacility;
	}
	public void setTargetFacility(String targetFacility) {
		TargetFacility = targetFacility;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getPlanType() {
		return PlanType;
	}
	public void setPlanType(String planType) {
		PlanType = planType;
	}
	public String getMainRole() {
		return MainRole;
	}
	public void setMainRole(String mainRole) {
		MainRole = mainRole;
	}
	

}
