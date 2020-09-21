package com.smartflow.dto.maintenancetaskplan;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceTaskPlanPageDTO {
    Integer Id;
    String Name;
    Integer VersionNumber;
    String PeriodicType;
    String State;
    String TargetFacilityName;
    String RoleInCharge;
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
@JsonProperty("VersionNumber")
    public Integer getVersion() {
        return VersionNumber;
    }

    public void setVersion(Integer version) {
        VersionNumber = version;
    }
@JsonProperty("PeriodicType'")
    public String getType() {
        return PeriodicType;
    }

    public void setType(String type) {
        PeriodicType = type;
    }
@JsonProperty("State")
    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
@JsonProperty("TargetFacilityName")
    public String getFacilityName() {
        return TargetFacilityName;
    }

    public void setFacilityName(String facilityName) {
        TargetFacilityName = facilityName;
    }
@JsonProperty("RoleInCharge")
    public String getRoleName() {
        return RoleInCharge;
    }

    public void setRoleName(String roleName) {
        RoleInCharge = roleName;
    }
@JsonProperty("LastEditeDate")
    public Date getEditeDate() {
        return LastEditeDate;
    }

    public void setEditeDate(Date editeDate) {
        LastEditeDate = editeDate;
    }

    Date LastEditeDate;

    public MaintenanceTaskPlanPageDTO(Integer id, String name, Integer version, String type, String state, String facilityName, String roleName, Date editeDate) {
        Id = id;
        Name = name;
        VersionNumber = version;
        PeriodicType = type;
        State = state;
        TargetFacilityName = facilityName;
        RoleInCharge = roleName;
        LastEditeDate = editeDate;
    }
    public MaintenanceTaskPlanPageDTO(){};


}
