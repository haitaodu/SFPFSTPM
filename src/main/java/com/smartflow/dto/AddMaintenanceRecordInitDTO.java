package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddMaintenanceRecordInitDTO {

	private List<Map<String,Object>> StaffList;//人员
	private List<Map<String,Object>> MaintenanceResultList;//维保结果
	public AddMaintenanceRecordInitDTO() {
		
	}
	@JsonProperty("StuffList")
	public List<Map<String, Object>> getStaffList() {
		return StaffList;
	}
	public void setStaffList(List<Map<String, Object>> staffList) {
		StaffList = staffList;
	}
	@JsonProperty("MaintenanceResultList")
	public List<Map<String, Object>> getMaintenanceResultList() {
		return MaintenanceResultList;
	}
	public void setMaintenanceResultList(List<Map<String, Object>> maintenanceResultList) {
		MaintenanceResultList = maintenanceResultList;
	}
	

}
