package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddAssignListInitDTO {

	private List<Map<String,Object>> TargetFacilityList;//目标设备下拉框
	private List<Map<String,Object>> RoleInChargeList;//负责角色下拉框
	@JsonProperty("TargetFacilityList")
	public List<Map<String, Object>> getTargetFacilityList() {
		return TargetFacilityList;
	}

	public void setTargetFacilityList(List<Map<String, Object>> targetFacilityList) {
		TargetFacilityList = targetFacilityList;
	}
	@JsonProperty("RoleInChargeList")
	public List<Map<String, Object>> getRoleInChargeList() {
		return RoleInChargeList;
	}

	public void setRoleInChargeList(List<Map<String, Object>> roleInChargeList) {
		RoleInChargeList = roleInChargeList;
	}

	public AddAssignListInitDTO() {
		// TODO Auto-generated constructor stub
	}

}
