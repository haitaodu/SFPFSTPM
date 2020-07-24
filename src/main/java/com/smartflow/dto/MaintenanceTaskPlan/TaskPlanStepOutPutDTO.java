package com.smartflow.dto.MaintenanceTaskPlan;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskPlanStepOutPutDTO {
	String BOMHeadId;	
	String MaintenanceItemNameId;
    String Position;
    String MaterialNumber;
	String ItemName;
    long WorkLength;
    String MeasurementTypeId;
    BigDecimal MeasurementStandardUpperLimit;
    BigDecimal MeasurementStandardLowerLimit;
    BigDecimal MeasurementStandardMiddleValue;
    String GuideDocURI;
    String RoleId;
    Integer Id;
    Boolean IsRequireMeasure;
    @JsonProperty("BOMHeadId")
    public String getBOMHeadId() {
		return BOMHeadId;
	}
	public void setBOMHeadId(String bOMHeadId) {
		BOMHeadId = bOMHeadId;
	}
    @JsonProperty("IsRequireMeasure")
    public Boolean getIsRequireMeasure() {
		return IsRequireMeasure;
	}
	public void setIsRequireMeasure(Boolean isRequireMeasure) {
		IsRequireMeasure = isRequireMeasure;
	}
	@JsonProperty("Id")
    public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@JsonProperty("MaintenanceItemNameId")
    public String getMaintenanceItemNameId() {
		return MaintenanceItemNameId;
	}
    @JsonProperty("Position")
	public String getPosition() {
		return Position;
	}
    @JsonProperty("MaterialNumber")
	public String getMaterialNumber() {
		return MaterialNumber;
	}
    @JsonProperty("ItemName")
	public String getItemName() {
		return ItemName;
	}
    @JsonProperty("WorkLength")
	public long getWorkLength() {
		return WorkLength;
	}
    @JsonProperty("MeasurementTypeId")
	public String getMeasurementTypeId() {
		return MeasurementTypeId;
	}
    @JsonProperty("MeasurementStandardUpperLimit")
	public BigDecimal getMeasurementStandardUpperLimit() {
		return MeasurementStandardUpperLimit;
	}
    @JsonProperty("MeasurementStandardLowerLimit")
	public BigDecimal getMeasurementStandardLowerLimit() {
		return MeasurementStandardLowerLimit;
	}
    @JsonProperty("MeasurementStandardMiddleValue")
	public BigDecimal getMeasurementStandardMiddleValue() {
		return MeasurementStandardMiddleValue;
	}
    @JsonProperty("GuideDocURI")
	public String getGuideDocURI() {
		return GuideDocURI;
	}
    @JsonProperty("RoleId")
	public String getRoleId() {
		return RoleId;
	}
	public void setMaintenanceItemNameId(String maintenanceItemNameId) {
		MaintenanceItemNameId = maintenanceItemNameId;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public void setMaterialNumber(String materialNumber) {
		MaterialNumber = materialNumber;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public void setWorkLength(long workLength) {
		WorkLength = workLength;
	}
	public void setMeasurementTypeId(String measurementTypeId) {
		MeasurementTypeId = measurementTypeId;
	}
	public void setMeasurementStandardUpperLimit(BigDecimal measurementStandardUpperLimit) {
		MeasurementStandardUpperLimit = measurementStandardUpperLimit;
	}
	public void setMeasurementStandardLowerLimit(BigDecimal measurementStandardLowerLimit) {
		MeasurementStandardLowerLimit = measurementStandardLowerLimit;
	}
	public void setMeasurementStandardMiddleValue(BigDecimal measurementStandardMiddleValue) {
		MeasurementStandardMiddleValue = measurementStandardMiddleValue;
	}
	public void setGuideDocURI(String guideDocURI) {
		GuideDocURI = guideDocURI;
	}
	public void setRoleId(String roleId) {
		RoleId = roleId;
	}
	
}
