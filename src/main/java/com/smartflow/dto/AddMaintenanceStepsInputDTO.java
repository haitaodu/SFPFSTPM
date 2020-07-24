package com.smartflow.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddMaintenanceStepsInputDTO {

	private Integer Id;//维保步骤Id
	private String ItemName;//项目名
	//private Integer BOMHeadId;//从BOM中选取
	private String Position;//位置
	private String MaterialNumber;//物料号
	private String MaintenanceItemId;//维保项
	private String EstimatedStartTime;//预估开始时间
	private BigDecimal WorkLength;//工作时长
	private String RoleInCharge;//角色
	private String Staff;//分配给谁
	
	private Boolean IsRequireMeasure;//是否要求测量
	private String MeasurementCategoryId;//测量类别
	private BigDecimal MeasurementStandardMiddleValue;//中值
	private BigDecimal MeasurementStandardUpperLimit;//最高值USL		
	private BigDecimal MeasurementStandardLowerLimit;//最低值LSL
	private String DescriptionDocument;//说明文档
	@JsonProperty("Id")
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	@JsonProperty("ItemName")
	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}
//	@JsonProperty("BOMHeadId")
//	public Integer getBOMHeadId() {
//		return BOMHeadId;
//	}
//
//	public void setBOMHeadId(Integer bOMHeadId) {
//		BOMHeadId = bOMHeadId;
//	}
	@JsonProperty("Position")
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	@JsonProperty("MaterialNumber")
	public String getMaterialNumber() {
		return MaterialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		MaterialNumber = materialNumber;
	}
	@JsonProperty("MaintenanceItemId")
	public String getMaintenanceItemId() {
		return MaintenanceItemId;
	}

	public void setMaintenanceItemId(String maintenanceItemId) {
		MaintenanceItemId = maintenanceItemId;
	}
	@JsonProperty("WorkLength")
	public BigDecimal getWorkLength() {
		return WorkLength;
	}

	public void setWorkLength(BigDecimal workLength) {
		WorkLength = workLength;
	}
	@JsonProperty("RoleGroupId")
	public String getRoleInCharge() {
		return RoleInCharge;
	}

	public void setRoleInCharge(String roleInCharge) {
		RoleInCharge = roleInCharge;
	}
	@JsonProperty("Stuff")
	public String getStaff() {
		return Staff;
	}

	public void setStaff(String staff) {
		Staff = staff;
	}
	
	@JsonProperty("EstimatedStartTime")
	public String getEstimatedStartTime() {
		return EstimatedStartTime;
	}
	public void setEstimatedStartTime(String estimatedStartTime) {
		EstimatedStartTime = estimatedStartTime;
	}

	@JsonProperty("IsRequireMeasure")
	public Boolean getIsRequireMeasure() {
		return IsRequireMeasure;
	}
	

	public void setIsRequireMeasure(Boolean isRequireMeasure) {
		IsRequireMeasure = isRequireMeasure;
	}
	@JsonProperty("MeasurementCategoryId")
	public String getMeasurementCategoryId() {
		return MeasurementCategoryId;
	}

	public void setMeasurementCategoryId(String measurementCategoryId) {
		MeasurementCategoryId = measurementCategoryId;
	}
	@JsonProperty("MeasurementStandardMiddleValue")
	public BigDecimal getMeasurementStandardMiddleValue() {
		return MeasurementStandardMiddleValue;
	}

	public void setMeasurementStandardMiddleValue(BigDecimal measurementStandardMiddleValue) {
		MeasurementStandardMiddleValue = measurementStandardMiddleValue;
	}
	@JsonProperty("MeasurementStandardUpperLimit")
	public BigDecimal getMeasurementStandardUpperLimit() {
		return MeasurementStandardUpperLimit;
	}

	public void setMeasurementStandardUpperLimit(BigDecimal measurementStandardUpperLimit) {
		MeasurementStandardUpperLimit = measurementStandardUpperLimit;
	}
	@JsonProperty("MeasurementStandardLowerLimit")
	public BigDecimal getMeasurementStandardLowerLimit() {
		return MeasurementStandardLowerLimit;
	}

	public void setMeasurementStandardLowerLimit(BigDecimal measurementStandardLowerLimit) {
		MeasurementStandardLowerLimit = measurementStandardLowerLimit;
	}
	@JsonProperty("DescriptionDocument")
	public String getDescriptionDocument() {
		return DescriptionDocument;
	}

	public void setDescriptionDocument(String descriptionDocument) {
		DescriptionDocument = descriptionDocument;
	}

	public AddMaintenanceStepsInputDTO() {
		// TODO Auto-generated constructor stub
	}

}
