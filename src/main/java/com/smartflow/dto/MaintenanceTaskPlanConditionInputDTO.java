package com.smartflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MaintenanceTaskPlanConditionInputDTO {
    private Integer PageIndex;
    private Integer PageSize;
    private String MaintenanceTaskPlanName;
    private List<Integer> FacilityIdList;
    private Integer State;
    @JsonProperty("PageIndex")
    public Integer getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        PageIndex = pageIndex;
    }
    @JsonProperty("PageSize")
    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }
    @JsonProperty("MaintenanceTaskPlanName")
    public String getMaintenanceTaskPlanName() {
        return MaintenanceTaskPlanName;
    }

    public void setMaintenanceTaskPlanName(String maintenanceTaskPlanName) {
        MaintenanceTaskPlanName = maintenanceTaskPlanName;
    }
    @JsonProperty("FacilityIdList")
    public List<Integer> getFacilityIdList() {
        return FacilityIdList;
    }

    public void setFacilityIdList(List<Integer> facilityIdList) {
        FacilityIdList = facilityIdList;
    }
    @JsonProperty("State")
    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }
}
