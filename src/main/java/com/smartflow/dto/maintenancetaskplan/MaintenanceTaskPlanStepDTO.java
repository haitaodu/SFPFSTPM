package com.smartflow.dto.maintenancetaskplan;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceTaskPlanStepDTO {
 
   
   
    Integer Id;
    String RoleName;
    String MaintenanceItemName;
    String Position;
    String MaterialNumber;
    String ItemName;
    long WorkLength;
    String MeasurementType;
    BigDecimal MeasurementStandardUpperLimit;
    @JsonProperty("Id")
    public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}


	BigDecimal MeasurementStandardLowerLimit;
    BigDecimal MeasurementStandardMiddleValue;
    String GuideDocURI;
    public MaintenanceTaskPlanStepDTO( String rolename, String maintenanceItemName, String designator, String materialNumber, String projectname, float workDurationTime, String measurementType, BigDecimal USL, BigDecimal LSL, BigDecimal normalValue, String guideDocURI,Integer id) {
        MaintenanceItemName = maintenanceItemName;
        Position = designator;
        MaterialNumber = materialNumber;
        ItemName = projectname;
        WorkLength = (long) workDurationTime;
        MeasurementType = measurementType;
        this.MeasurementStandardUpperLimit = USL;
        this.MeasurementStandardLowerLimit = LSL;
        MeasurementStandardMiddleValue = normalValue;
        GuideDocURI = guideDocURI;
        RoleName=rolename;
        Id=id;
    }
    public MaintenanceTaskPlanStepDTO(){};
    @JsonProperty("RoleName")
    public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}


    @JsonProperty("MaintenanceItemName")
    public String getMaintenanceItemName() {
        return MaintenanceItemName;
    }

    public void setMaintenanceItemName(String maintenanceItemName) {
        MaintenanceItemName = maintenanceItemName;
    }
    @JsonProperty("Position")
    public String getDesignator() {
        return Position;
    }

    public void setDesignator(String designator) {
        Position = designator;
    }
    @JsonProperty("MaterialNumber")
    public String getMaterialNumber() {
        return MaterialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        MaterialNumber = materialNumber;
    }
    @JsonProperty("ItemName")
    public String getProjectName() {
        return ItemName;
    }

    public void setPlanName(String projectname) {
        ItemName = projectname;
    }
    @JsonProperty("WorkLength")
    public float getWorkDurationTime() {
        return WorkLength;
    }

    public void setWorkDurationTime(long workDurationTime) {
        WorkLength = workDurationTime;
    }
    @JsonProperty("MeasurementType")
    public String getMeasurementType() {
        return MeasurementType;
    }

    public void setMeasurementType(String measurementType) {
        MeasurementType = measurementType;
    }
    @JsonProperty("MeasurementStandardUpperLimit")
    public BigDecimal getUSL() {
        return MeasurementStandardUpperLimit;
    }

    public void setUSL(BigDecimal USL) {
        this.MeasurementStandardUpperLimit = USL;
    }
    @JsonProperty("MeasurementStandardLowerLimit")
    public BigDecimal getLSL() {
        return MeasurementStandardLowerLimit;
    }

    public void setLSL(BigDecimal LSL) {
        this.MeasurementStandardLowerLimit = LSL;
    }
    @JsonProperty("MeasurementStandardMiddleValue")
    public BigDecimal getNormalValue() {
        return MeasurementStandardMiddleValue;
    }

    public void setNormalValue(BigDecimal normalValue) {
        MeasurementStandardMiddleValue = normalValue;
    }
    @JsonProperty("GuideDocURI")
    public String getGuideDocURI() {
        return GuideDocURI;
    }

    public void setGuideDocURI(String guideDocURI) {
        GuideDocURI = guideDocURI;
    }


}
