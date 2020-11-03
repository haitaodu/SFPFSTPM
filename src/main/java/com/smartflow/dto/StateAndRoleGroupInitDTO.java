package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateAndRoleGroupInitDTO {

	private List<Map<String,Object>> StateList;//状态下拉框
	private List<Map<String,Object>> RoleGroupList;//角色组下拉框
//	private boolean ButtonPermission;//按钮权限（可用/不可用）
	private List<Map<String,Object>> TargetFacilityList;
	@JsonProperty("StateList")
	public List<Map<String,Object>> getStateList() {
		return StateList;
	}
	public void setStateList(List<Map<String,Object>> stateList) {
		StateList = stateList;
	}
	@JsonProperty("RoleGroupList")
	public List<Map<String,Object>> getRoleGroupList() {
		return RoleGroupList;
	}
	public void setRoleGroupList(List<Map<String,Object>> roleGroupList) {
		RoleGroupList = roleGroupList;
	}
//	@JsonProperty("ButtonPermission")
//	public boolean isButtonPermission() {
//		return ButtonPermission;
//	}
//
//	public void setButtonPermission(boolean buttonPermission) {
//		ButtonPermission = buttonPermission;
//	}

	@JsonProperty("TargetFacilityList")
	public List<Map<String, Object>> getTargetFacilityList() {
		return TargetFacilityList;
	}

	public void setTargetFacilityList(List<Map<String, Object>> targetFacilityList) {
		TargetFacilityList = targetFacilityList;
	}
}
