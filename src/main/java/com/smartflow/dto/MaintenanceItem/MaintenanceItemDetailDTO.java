package com.smartflow.dto.MaintenanceItem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceItemDetailDTO {
    String Type;
    String Name;

    public MaintenanceItemDetailDTO(String type, String name) {
        Type = type;
        Name = name;
    }

    public MaintenanceItemDetailDTO(){};
    @JsonProperty("Type")
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
