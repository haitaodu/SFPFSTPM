package com.smartflow.dto.MaintenanceTaskPlan;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import javax.print.DocFlavor;

public class MaintenancePreviewDTO {
    Integer Id;
    String MaintenanceItemName;
    String Position;
    String MaterialNumber;
    String ItemName;
    long WorkLength;
    String RoleName;
    String MeasurementType;
    BigDecimal MeasurementStandardUpperLimit;
    BigDecimal MeasurementStandardLowerLimit;
    BigDecimal MeasurementStandardMiddleValue;
    String GuideDocURI;

    public MaintenancePreviewDTO(Integer id, String maintenanceItemName, String designator, String materialNumber, String name, long workDurationTime, String roleName, String measurementType, BigDecimal USL, BigDecimal LSL, BigDecimal normalValue, String guideDocURI) {
        Id = id;
        MaintenanceItemName = maintenanceItemName;
        Position = designator;
        MaterialNumber = materialNumber;
        ItemName = name;
        WorkLength = workDurationTime;
        RoleName = roleName;
        MeasurementType = measurementType;
        this.MeasurementStandardUpperLimit = USL;
        this.MeasurementStandardLowerLimit = LSL;
        MeasurementStandardMiddleValue = normalValue;
        GuideDocURI = guideDocURI;
    }
    public MaintenancePreviewDTO(){};
public MaintenancePreviewDTO(Integer id2, String name, String designator, String materialNumber2, String name2,
			long workDurationSec, String roleName2, Integer measurementType2, BigDecimal usl, BigDecimal lsl,
			BigDecimal nromalValue, String guideDocURI2) {
		// TODO Auto-generated constructor stub
	}
@JsonProperty("Id")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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
    public String getName() {
        return ItemName;
    }

    public void setName(String name) {
        ItemName = name;
    }
    @JsonProperty("WorkLength")
    public float getWorkDurationTime() {
        return WorkLength;
    }

    public void setWorkDurationTime(long workDurationTime) {
        WorkLength = workDurationTime;
    }
    @JsonProperty("RoleName")
    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
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
