package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceAssignmentCalendarDTO {
	private List<Map<String,Object>> CalendarData;
	@JsonProperty("CalendarData")
	public List<Map<String, Object>> getCalendarData() {
		return CalendarData;
	}

	public void setCalendarData(List<Map<String, Object>> calendarData) {
		CalendarData = calendarData;
	}
	
	public static class CalendarData{
		private String TargetFacilityName;//设备名
		private String WorkPlanName;//工作计划名
		private String WorkPlanExcutionState;//计划颜色(是否变灰)
		private Integer WorkPlanId;//工作计划id
		private Integer FacilityId;//设备id
		@JsonProperty("TargetFacilityName")
		public String getTargetFacilityName() {
			return TargetFacilityName;
		}
		public void setTargetFacilityName(String targetFacilityName) {
			TargetFacilityName = targetFacilityName;
		}
		@JsonProperty("WorkPlanName")
		public String getWorkPlanName() {
			return WorkPlanName;
		}
		public void setWorkPlanName(String workPlanName) {
			WorkPlanName = workPlanName;
		}
		@JsonProperty("WorkPlanExcutionState")
		public String getWorkPlanExcutionState() {
			return WorkPlanExcutionState;
		}
		public void setWorkPlanExcutionState(String workPlanExcutionState) {
			WorkPlanExcutionState = workPlanExcutionState;
		}
		@JsonProperty("WorkPlanId")
		public Integer getWorkPlanId() {
			return WorkPlanId;
		}
		public void setWorkPlanId(Integer workPlanId) {
			WorkPlanId = workPlanId;
		}
		@JsonProperty("FacilityId")
		public Integer getFacilityId() {
			return FacilityId;
		}
		public void setFacilityId(Integer facilityId) {
			FacilityId = facilityId;
		}
		public CalendarData() {
			
		}
		public CalendarData(String targetFacilityName, String workPlanName, String workPlanExcutionState,
				Integer workPlanId, Integer facilityId) {
			TargetFacilityName = targetFacilityName;
			WorkPlanName = workPlanName;
			WorkPlanExcutionState = workPlanExcutionState;
			WorkPlanId = workPlanId;
			FacilityId = facilityId;
		}
		
		
	}

}
