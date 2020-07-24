package com.smartflow.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditMaintenanceStepsInitOutputDTO {

	private List<Map<String,Object>> MaintenanceItemList;//维保项下拉框
	private List<Map<String,Object>> RoleGroupList;//角色下拉框
	private List<Map<String,Object>> MeasurementCategoryList;//测量类别下拉框
	private EditMaintenanceStepsRowDTO TDto;//实体
	@JsonProperty("MaintenanceItemList")
	public List<Map<String, Object>> getMaintenanceItemList() {
		return MaintenanceItemList;
	}

	public void setMaintenanceItemList(List<Map<String, Object>> maintenanceItemList) {
		MaintenanceItemList = maintenanceItemList;
	}

	@JsonProperty("RoleGroupList")
	public List<Map<String, Object>> getRoleGroupList() {
		return RoleGroupList;
	}


	public void setRoleGroupList(List<Map<String, Object>> roleGroupList) {
		RoleGroupList = roleGroupList;
	}

	@JsonProperty("MeasurementCategoryList")
	public List<Map<String, Object>> getMeasurementCategoryList() {
		return MeasurementCategoryList;
	}


	public void setMeasurementCategoryList(List<Map<String, Object>> measurementCategoryList) {
		MeasurementCategoryList = measurementCategoryList;
	}
	
	@JsonProperty("TDto")
	public EditMaintenanceStepsRowDTO getTDto() {
		return TDto;
	}

	public void setTDto(EditMaintenanceStepsRowDTO tDto) {
		TDto = tDto;
	}

	public static class EditMaintenanceStepsRowDTO{
		private Integer WorkItemId;//Id
		private String ItemName;//项目名
		private List<String> BOM;//从BOM中选取
		private String Position;//位置
		private String MaterialNumber;//物料号
		private Integer MaintenanceItemId;//维保项id
		private String MaintenanceItemName;//维保项名称
		private BigDecimal WorkLength;//工作时长
		private Integer RoleGroupId;//角色		
		private Boolean IsRequireMeasure;//是否要求测量
		private Integer MeasurementCategoryId;//测量类别
		private BigDecimal MeasurementStandardMiddleValue;//中值
		private BigDecimal MeasurementStandardUpperLimit;//最高值USL		
		private BigDecimal MeasurementStandardLowerLimit;//最低值LSL
		private String DescriptionDocument;//说明文档
		@JsonProperty("WorkItemId")
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
		@JsonProperty("BOM")
		public List<String> getBOM() {
			return BOM;
		}
		public void setBOM(List<String> bOM) {
			BOM = bOM;
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
		@JsonProperty("MaintenanceItemId")
		public Integer getMaintenanceItemId() {
			return MaintenanceItemId;
		}
		public void setMaintenanceItemId(Integer maintenanceItemId) {
			MaintenanceItemId = maintenanceItemId;
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
		@JsonProperty("RoleGroupId")
		public Integer getRoleGroupId() {
			return RoleGroupId;
		}
		public void setRoleGroupId(Integer roleGroupId) {
			RoleGroupId = roleGroupId;
		}
		@JsonProperty("IsRequireMeasure")
		public Boolean getIsRequireMeasure() {
			return IsRequireMeasure;
		}
		public void setIsRequireMeasure(Boolean isRequireMeasure) {
			IsRequireMeasure = isRequireMeasure;
		}
		@JsonProperty("MeasurementCategoryId")
		public Integer getMeasurementCategoryId() {
			return MeasurementCategoryId;
		}
		public void setMeasurementCategoryId(Integer measurementCategoryId) {
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
		public EditMaintenanceStepsRowDTO() {
			
		}
		public EditMaintenanceStepsRowDTO(Integer workItemId, String itemName, String position, String materialNumber,
				Integer maintenanceItemId, String maintenanceItemName, BigDecimal workLength, Integer roleGroupId, Boolean isRequireMeasure,
				Integer measurementCategoryId, BigDecimal measurementStandardMiddleValue,
				BigDecimal measurementStandardUpperLimit, BigDecimal measurementStandardLowerLimit,
				String descriptionDocument) {
			WorkItemId = workItemId;
			ItemName = itemName;
			Position = position;
			MaterialNumber = materialNumber;
			MaintenanceItemId = maintenanceItemId;
			MaintenanceItemName = maintenanceItemName;
			WorkLength = workLength;
			RoleGroupId = roleGroupId;
			IsRequireMeasure = isRequireMeasure;
			MeasurementCategoryId = measurementCategoryId;
			MeasurementStandardMiddleValue = measurementStandardMiddleValue;
			MeasurementStandardUpperLimit = measurementStandardUpperLimit;
			MeasurementStandardLowerLimit = measurementStandardLowerLimit;
			DescriptionDocument = descriptionDocument;
		}

		
	}


	public EditMaintenanceStepsInitOutputDTO() {
		// TODO Auto-generated constructor stub
	}

}
