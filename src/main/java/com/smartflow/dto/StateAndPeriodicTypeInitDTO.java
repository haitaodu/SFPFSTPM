package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateAndPeriodicTypeInitDTO {
	private List<Map<String,Object>> StateList;//状态
	private List<Map<String,Object>> PeriodicTypeList;//周期类型
//	private List<Map<String,Object>> MaintenanceItemList;//维保项
	private List<Map<String,Object>> TargetFacilityList;//目标设备下拉框
	@JsonProperty("StateList")
	public List<Map<String, Object>> getStateList() {
		return StateList;
	}

	public void setStateList(List<Map<String, Object>> stateList) {
		StateList = stateList;
	}
	@JsonProperty("PeriodicTypeList")
	public List<Map<String, Object>> getPeriodicTypeList() {
		return PeriodicTypeList;
	}

	public void setPeriodicTypeList(List<Map<String, Object>> periodicTypeList) {
		PeriodicTypeList = periodicTypeList;
	}
//	@JsonProperty("MaintenanceItemList")
//	public List<Map<String, Object>> getMaintenanceItemList() {
//		return MaintenanceItemList;
//	}
//
//	public void setMaintenanceItemList(List<Map<String, Object>> maintenanceItemList) {
//		MaintenanceItemList = maintenanceItemList;
//	}
	@JsonProperty("TargetFacilityList")
	public List<Map<String, Object>> getTargetFacilityList() {
		return TargetFacilityList;
	}

	public void setTargetFacilityList(List<Map<String, Object>> targetFacilityList) {
		TargetFacilityList = targetFacilityList;
	}

	public StateAndPeriodicTypeInitDTO() {
		// TODO Auto-generated constructor stub
	}

}
