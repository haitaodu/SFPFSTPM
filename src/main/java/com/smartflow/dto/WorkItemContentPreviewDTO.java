package com.smartflow.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 工作项内容预览
 * @author smartflow
 *
 */
public class WorkItemContentPreviewDTO {

	private Integer WorkItemId;//Id
	private String ItemName;//项目名
	private String Position;//位置
	private String MaterialNumber;//物料号
	private String MaintenanceItemName;//维保项名称
	private BigDecimal WorkLength;//工作时长
	private String RoleInCharge;//负责角色
	private String MeasurementCategory;//测量类别
	private BigDecimal MeasurementStandardUpperLimit;//测量标准上限
	private BigDecimal MeasurementStandardMiddleValue;//测量标准中值
	private BigDecimal MeasurementStandardLowerLimit;//测量标准下限
	private String DescriptionDocument;//说明文档
	@JsonProperty("Id")
	public Integer getWorkItemId() {
		return WorkItemId;
	}
	public void setWorkItemId(Integer workItemId) {
		WorkItemId = workItemId;
	}
	@JsonProperty("ItemName")
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
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
	@JsonProperty("MaintenanceItemName")
	public String getMaintenanceItemName() {
		return MaintenanceItemName;
	}
	public void setMaintenanceItemName(String maintenanceItemName) {
		MaintenanceItemName = maintenanceItemName;
	}
	@JsonProperty("WorkLength")
	public BigDecimal getWorkLength() {
		return WorkLength;
	}
	public void setWorkLength(BigDecimal workLength) {
		WorkLength = workLength;
	}
	@JsonProperty("RoleInCharge")
	public String getRoleInCharge() {
		return RoleInCharge;
	}
	public void setRoleInCharge(String roleInCharge) {
		RoleInCharge = roleInCharge;
	}
	@JsonProperty("MeasurementCategory")
	public String getMeasurementCategory() {
		return MeasurementCategory;
	}	
	public void setMeasurementCategory(String measurementCategory) {
		MeasurementCategory = measurementCategory;
	}
	@JsonProperty("MeasurementStandardUpperLimit")
	public BigDecimal getMeasurementStandardUpperLimit() {
		return MeasurementStandardUpperLimit;
	}
	public void setMeasurementStandardUpperLimit(BigDecimal measurementStandardUpperLimit) {
		MeasurementStandardUpperLimit = measurementStandardUpperLimit;
	}
	@JsonProperty("MeasurementStandardMiddleValue")
	public BigDecimal getMeasurementStandardMiddleValue() {
		return MeasurementStandardMiddleValue;
	}
	public void setMeasurementStandardMiddleValue(BigDecimal measurementStandardMiddleValue) {
		MeasurementStandardMiddleValue = measurementStandardMiddleValue;
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
	public WorkItemContentPreviewDTO() {
		
	}
	public WorkItemContentPreviewDTO(Integer workItemId, String itemName, String position, String materialNumber,
			String maintenanceItemName, BigDecimal workLength, String roleInCharge, String measurementCategory,
			BigDecimal measurementStandardUpperLimit, BigDecimal measurementStandardMiddleValue,
			BigDecimal measurementStandardLowerLimit, String descriptionDocument) {
		WorkItemId = workItemId;
		ItemName = itemName;
		Position = position;
		MaterialNumber = materialNumber;
		MaintenanceItemName = maintenanceItemName;
		WorkLength = workLength;
		RoleInCharge = roleInCharge;
		MeasurementCategory = measurementCategory;
		MeasurementStandardUpperLimit = measurementStandardUpperLimit;
		MeasurementStandardMiddleValue = measurementStandardMiddleValue;
		MeasurementStandardLowerLimit = measurementStandardLowerLimit;
		DescriptionDocument = descriptionDocument;
	}

	

	

}
