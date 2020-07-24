package com.smartflow.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditAssignListInitDTO {

	private Integer Id;//派工单id
	private Integer TargetFacilityId;//目标设备
//	private Integer RoleGroupId;//角色id
	private Integer RoleInChargeId;
	private List<Map<String,Object>> TargetFacilityList;//目标设备下拉框
	private List<Map<String,Object>> RoleInChargeList;//负责角色下拉框
	private Date DateTime;//时间
	private List<EditAssignListRowDTO> TDto;//实体
	@JsonProperty("Id")
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	@JsonProperty("DateTime")
	public Date getDateTime() {
		return DateTime;
	}

	public void setDateTime(Date dateTime) {
		DateTime = dateTime;
	}
	
	@JsonProperty("TargetFacilityId")
	public Integer getTargetFacilityId() {
		return TargetFacilityId;
	}

	public void setTargetFacilityId(Integer targetFacilityId) {
		TargetFacilityId = targetFacilityId;
	}
	@JsonProperty("RoleInChargeId")
	public Integer getRoleInChargeId() {
		return RoleInChargeId;
	}

	public void setRoleInChargeId(Integer roleInChargeId) {
		RoleInChargeId = roleInChargeId;
	}

	@JsonProperty("TargetFacilityList")
	public List<Map<String, Object>> getTargetFacilityList() {
		return TargetFacilityList;
	}

	public void setTargetFacilityList(List<Map<String, Object>> targetFacilityList) {
		TargetFacilityList = targetFacilityList;
	}
	@JsonProperty("RoleInChargeList")
	public List<Map<String, Object>> getRoleInChargeList() {
		return RoleInChargeList;
	}

	public void setRoleInChargeList(List<Map<String, Object>> roleInChargeList) {
		RoleInChargeList = roleInChargeList;
	}
	@JsonProperty("TDto")
	public List<EditAssignListRowDTO> getTDto() {
		return TDto;
	}

	public void setTDto(List<EditAssignListRowDTO> tDto) {
		TDto = tDto;
	}

	public EditAssignListInitDTO() {
		// TODO Auto-generated constructor stub
	}

	public static class EditAssignListRowDTO{
		private Integer WorkItemId;//Id
		private String ItemName;//项目名
		private Integer BOMId;//bomId
		//private String BOMName;//BOM
		private String Position;//位置
		private String MaterialNumber;//物料号
		//private Integer MaintenanceItemId;//维保项Id
		private String MaintenanceItemName;//维保项
		private BigDecimal WorkLength;//工作时长
		private Date EstimatedStartTime;//预估开始时间
		private String Staff;//分配给谁
		//private Integer RoleInChargeId;//负责角色id
		private String RoleInCharge;//负责角色
		private Boolean IsRequireMeasure;//是否需要测量
		//private Integer MeasurementCategoryId;//测量类别Id
		private String MeasurementCategory;//测量类别
		private BigDecimal MeasurementStandardMiddleValue;//中值
		private BigDecimal MeasurementStandardUpperLimit;//最高值USL		
		private BigDecimal MeasurementStandardLowerLimit;//最低值LSL
		private String DescriptionDocument;//说明文档
		@JsonProperty("Id")
		public Integer getWorkItemId() {
			return WorkItemId;
		}
		public void setWorkItemId(Integer workItemId) {
			WorkItemId = workItemId;
		}
		@JsonProperty("BOMId")
		public Integer getBOMId() {
			return BOMId;
		}
		public void setBOMId(Integer bOMId) {
			BOMId = bOMId;
		}
//		@JsonProperty("BOMName")
//		public String getBOMName() {
//			return BOMName;
//		}
//		public void setBOMName(String bOMName) {
//			BOMName = bOMName;
//		}		
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
//		@JsonProperty("MaintenanceItemId")
//		public Integer getMaintenanceItemId() {
//			return MaintenanceItemId;
//		}
//		public void setMaintenanceItemId(Integer maintenanceItemId) {
//			MaintenanceItemId = maintenanceItemId;
//		}
		@JsonProperty("MaintenanceItemName")
		public String getMaintenanceItemName() {
			return MaintenanceItemName;
		}
		public void setMaintenanceItemName(String maintenanceItemName) {
			MaintenanceItemName = maintenanceItemName;
		}
		@JsonProperty("EstimatedStartTime")
		public Date getEstimatedStartTime() {
			return EstimatedStartTime;
		}
		public void setEstimatedStartTime(Date estimatedStartTime) {
			EstimatedStartTime = estimatedStartTime;
		}
		@JsonProperty("Staff")
		public String getStaff() {
			return Staff;
		}
		public void setStaff(String staff) {
			Staff = staff;
		}
		@JsonProperty("WorkLength")
		public BigDecimal getWorkLength() {
			return WorkLength;
		}
		public void setWorkLength(BigDecimal workLength) {
			WorkLength = workLength;
		}
//		@JsonProperty("RoleInChargeId")
//		public Integer getRoleInChargeId() {
//			return RoleInChargeId;
//		}
//		public void setRoleInChargeId(Integer roleInChargeId) {
//			RoleInChargeId = roleInChargeId;
//		}
		@JsonProperty("RoleInCharge")
		public String getRoleInCharge() {
			return RoleInCharge;
		}
		public void setRoleInCharge(String roleInCharge) {
			RoleInCharge = roleInCharge;
		}
		@JsonProperty("IsRequireMeasure")
		public Boolean getIsRequireMeasure() {
			return IsRequireMeasure;
		}
		public void setIsRequireMeasure(Boolean isRequireMeasure) {
			IsRequireMeasure = isRequireMeasure;
		}
//		@JsonProperty("MeasurementCategoryId")
//		public Integer getMeasurementCategoryId() {
//			return MeasurementCategoryId;
//		}
//		public void setMeasurementCategoryId(Integer measurementCategoryId) {
//			MeasurementCategoryId = measurementCategoryId;
//		}
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
		public EditAssignListRowDTO() {
			
		}
//		public EditAssignListRowDTO(Integer workItemId, String itemName, Integer bOMHeadId, String position,
//				String materialNumber, Integer maintenanceItemId, String maintenanceItemName, BigDecimal workLength, Integer roleInChargeId, String roleInCharge,
//				Boolean isRequireMeasure, Integer measurementCategoryId, String measurementCategory, BigDecimal measurementStandardMiddleValue,
//				BigDecimal measurementStandardUpperLimit, BigDecimal measurementStandardLowerLimit,
//				String descriptionDocument) {
//			WorkItemId = workItemId;
//			ItemName = itemName;
//			BOMHeadId = bOMHeadId;
//			Position = position;
//			MaterialNumber = materialNumber;
//			MaintenanceItemId = maintenanceItemId;
//			MaintenanceItemName = maintenanceItemName;
//			WorkLength = workLength;
//			RoleInChargeId = roleInChargeId;
//			RoleInCharge = roleInCharge;
//			IsRequireMeasure = isRequireMeasure;
//			MeasurementCategoryId = measurementCategoryId;
//			MeasurementCategory = measurementCategory;
//			MeasurementStandardMiddleValue = measurementStandardMiddleValue;
//			MeasurementStandardUpperLimit = measurementStandardUpperLimit;
//			MeasurementStandardLowerLimit = measurementStandardLowerLimit;
//			DescriptionDocument = descriptionDocument;
//		}
		/**
		 * @param workItemId
		 * @param itemName
		 * @param bOMId
		 * @param position
		 * @param materialNumber
		 * @param maintenanceItemName
		 * @param workLength
		 * @param roleInCharge
		 * @param isRequireMeasure
		 * @param measurementCategory
		 * @param measurementStandardMiddleValue
		 * @param measurementStandardUpperLimit
		 * @param measurementStandardLowerLimit
		 * @param descriptionDocument
		 */
		public EditAssignListRowDTO(Integer workItemId, String itemName, Integer bOMId, String position,
				String materialNumber, String maintenanceItemName, BigDecimal workLength, String roleInCharge,
				Boolean isRequireMeasure, String measurementCategory, BigDecimal measurementStandardMiddleValue,
				BigDecimal measurementStandardUpperLimit, BigDecimal measurementStandardLowerLimit,
				String descriptionDocument) {
			WorkItemId = workItemId;
			ItemName = itemName;
			BOMId = bOMId;
			Position = position;
			MaterialNumber = materialNumber;
			MaintenanceItemName = maintenanceItemName;
			WorkLength = workLength;
			RoleInCharge = roleInCharge;
			IsRequireMeasure = isRequireMeasure;
			MeasurementCategory = measurementCategory;
			MeasurementStandardMiddleValue = measurementStandardMiddleValue;
			MeasurementStandardUpperLimit = measurementStandardUpperLimit;
			MeasurementStandardLowerLimit = measurementStandardLowerLimit;
			DescriptionDocument = descriptionDocument;
		}
		
	
		
	}

}
