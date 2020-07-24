package com.smartflow.dto.MaintenanceItem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceItemAddDTO {
	String Name;
    Integer TypeId;
    Integer StateId;
    @JsonProperty("Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
    @JsonProperty("TypeId")
	public Integer getTypeId() {
		return TypeId;
	}
	public void setTypeId(Integer typeId) {
		TypeId = typeId;
	}
	  @JsonProperty("StateId")
	public Integer getStateId() {
		return StateId;
	}
	public void setStateId(Integer stateId) {
		StateId = stateId;
	}
}
