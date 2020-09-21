package com.smartflow.dto.maintenanceitem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceItemEditeDTO {
	
    Integer Id;
    Integer TypeId;
    Integer StateId;
    String Name;
    public MaintenanceItemEditeDTO(String name, Integer id, Integer typeId, Integer stateId) {
        Name = name;
        Id = id;
        TypeId=typeId;
        StateId=stateId;
    }
    public MaintenanceItemEditeDTO(){};
    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    @JsonProperty("Id")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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
