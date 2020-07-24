package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateAndDeviceInitDTO {

	private List<Map<String,Object>> StateList;//状态
	private List<Map<String,Object>> DeviceList;//设备
	@JsonProperty("StateList")
	public List<Map<String, Object>> getStateList() {
		return StateList;
	}
	public void setStateList(List<Map<String, Object>> stateList) {
		StateList = stateList;
	}
	@JsonProperty("DeviceList")
	public List<Map<String, Object>> getDeviceList() {
		return DeviceList;
	}
	public void setDeviceList(List<Map<String, Object>> deviceList) {
		DeviceList = deviceList;
	}
	
}
