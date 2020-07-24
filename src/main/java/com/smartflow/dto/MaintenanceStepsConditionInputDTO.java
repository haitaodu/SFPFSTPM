package com.smartflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceStepsConditionInputDTO {

	private Integer TargetFacilityId;//目标设备id
	private Integer RoleInChargeId;//责任角色id
	private String DateTime;//时间
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

	public MaintenanceStepsConditionInputDTO() {
		
	}

}
