package com.smartflow.dto.MaintenanceItem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceItemPageDTO {
Integer Id;
String Name;
String Type;
public MaintenanceItemPageDTO(){};

    public MaintenanceItemPageDTO(Integer id, String name, String type) {
        Id = id;
        Name = name;
        Type = type;
    }
    @JsonProperty("Id")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    @JsonProperty("Type")
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
