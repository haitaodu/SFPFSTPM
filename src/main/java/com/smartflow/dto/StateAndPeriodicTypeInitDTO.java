package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateAndPeriodicTypeInitDTO {
	private List<Map<String,Object>> StateList;//状态
	private List<Map<String,Object>> PeriodicTypeList;//周期类型
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

	public StateAndPeriodicTypeInitDTO() {
		// TODO Auto-generated constructor stub
	}

}
