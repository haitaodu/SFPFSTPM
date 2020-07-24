package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import org.w3c.dom.stylesheets.LinkStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddMaintenanceStepsInitOutputDTO {

	private List<Map<String,Object>> MaintenanceItemList;//维保项下拉框
	private List<Map<String,Object>> RoleGroupList;//角色下拉框
	private List<Map<String,Object>> MeasurementCategoryList;//测量类别下拉框
	private List<Map<String,Object>> BOMList;
	@JsonProperty("MaintenanceItemList")
	public List<Map<String, Object>> getMaintenanceItemList() {
		return MaintenanceItemList;
	}

	public void setMaintenanceItemList(List<Map<String, Object>> maintenanceItemList) {
		MaintenanceItemList = maintenanceItemList;
	}

	@JsonProperty("RoleInChargeList")
	public List<Map<String, Object>> getRoleGroupList() {
		return RoleGroupList;
	}

	public void setRoleGroupList(List<Map<String, Object>> roleGroupList) {
		RoleGroupList = roleGroupList;
	}
	@JsonProperty("MeasurementCategoryList")
	public List<Map<String, Object>> getMeasurementCategoryList() {
		return MeasurementCategoryList;
	}

	public void setMeasurementCategoryList(List<Map<String, Object>> measurementCategoryList) {
		MeasurementCategoryList = measurementCategoryList;
	}

	public AddMaintenanceStepsInitOutputDTO() {
		// TODO Auto-generated constructor stub
	}
	@JsonProperty("BOMList")
	public List<Map<String,Object>> getBOMList() {
		return BOMList;
	}

	public void setBOMList(List<Map<String,Object>> bOMList) {
		BOMList = bOMList;
	}

}
