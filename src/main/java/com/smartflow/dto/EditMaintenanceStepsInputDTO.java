package com.smartflow.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditMaintenanceStepsInputDTO {

	private Integer WorkItemId;//Id
	private String ItemName;//项目名
	//private Integer BOMHeadId;//从BOM中选取
	//private String BOMName;//BOM
	private Integer BOMId;//该字段后端用不到
	private String Position;//位置
	private String MaterialNumber;//物料号
	//private Integer MaintenanceItemId;//维保项Id
	private String MaintenanceItemName;//维保项
	private String EstimatedStartTime;//预估开始时间
	private BigDecimal WorkLength;//工作时长
	//private Integer RoleGroupId;//角色		
	//private Integer RoleInChargeId;//角色id
	private String RoleInCharge;//角色
	private String Staff;//分配给谁
	private Boolean IsRequireMeasure;//是否要求测量
	//private Integer MeasurementCategoryId;//测量类别Id
	private String MeasurementCategory;//测量类别
	private BigDecimal MeasurementStandardMiddleValue;//中值
	private BigDecimal MeasurementStandardUpperLimit;//最高值USL		
	private BigDecimal MeasurementStandardLowerLimit;//最低值LSL
	private String DescriptionDocument;//说明文档
	public EditMaintenanceStepsInputDTO() {
		// TODO Auto-generated constructor stub
	}
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
	@JsonProperty("BOMId")
	public Integer getBOMId() {
		return BOMId;
	}
	public void setBOMId(Integer bOMId) {
		BOMId = bOMId;
	}
	//	@JsonProperty("BOMHeadId")
//	public Integer getBOMHeadId() {
//		return BOMHeadId;
//	}
//	public void setBOMHeadId(Integer bOMHeadId) {
//		BOMHeadId = bOMHeadId;
//	}
//	@JsonProperty("BOMName")
//	public String getBOMName() {
//		return BOMName;
//	}
//	public void setBOMName(String bOMName) {
//		BOMName = bOMName;
//	}
//	@JsonProperty("RoleInChargeId")
//	public Integer getRoleInChargeId() {
//		return RoleInChargeId;
//	}
//	public void setRoleInChargeId(Integer roleInChargeId) {
//		RoleInChargeId = roleInChargeId;
//	}
	@JsonProperty("RoleInCharge")
	public String getRoleInCharge() {
		return RoleInCharge;
	}
	public void setRoleInCharge(String roleInCharge) {
		RoleInCharge = roleInCharge;
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
//	@JsonProperty("MaintenanceItemId")
//	public Integer getMaintenanceItemId() {
//		return MaintenanceItemId;
//	}
//	public void setMaintenanceItemId(Integer maintenanceItemId) {
//		MaintenanceItemId = maintenanceItemId;
//	}
	@JsonProperty("MaintenanceItemName")
	public String getMaintenanceItemName() {
		return MaintenanceItemName;
	}
	public void setMaintenanceItemName(String maintenanceItemName) {
		MaintenanceItemName = maintenanceItemName;
	}
	
	@JsonProperty("Staff")
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
	@JsonProperty("WorkLength")
	public BigDecimal getWorkLength() {
		return WorkLength;
	}
	public void setWorkLength(BigDecimal workLength) {
		WorkLength = workLength;
	}
//	@JsonProperty("RoleGroupId")
//	public Integer getRoleGroupId() {
//		return RoleGroupId;
//	}
//	public void setRoleGroupId(Integer roleGroupId) {
//		RoleGroupId = roleGroupId;
//	}
	@JsonProperty("IsRequireMeasure")
	public Boolean getIsRequireMeasure() {
		return IsRequireMeasure;
	}
	public void setIsRequireMeasure(Boolean isRequireMeasure) {
		IsRequireMeasure = isRequireMeasure;
	}
//	@JsonProperty("MeasurementCategoryId")
//	public Integer getMeasurementCategoryId() {
//		return MeasurementCategoryId;
//	}
//	public void setMeasurementCategoryId(Integer measurementCategoryId) {
//		MeasurementCategoryId = measurementCategoryId;
//	}
	@JsonProperty("MeasurementCategory")
	public String getMeasurementCategory() {
		return MeasurementCategory;
	}
	public void setMeasurementCategory(String measurementCategory) {
		MeasurementCategory = measurementCategory;
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
}
