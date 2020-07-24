package com.smartflow.dto;

import java.util.Date;

public class CalendarDataDTO {	
	private Date StartDateTime;
	private Date EndDateTime;
	private String CRONExpression;
	private Integer WorkPlan_ReminderId;
	private Integer WorkPlanId;
	private String WorkPlanName;
	private Integer FacilityId;
	private String FacilityName;

	public Date getStartDateTime() {
		return StartDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		StartDateTime = startDateTime;
	}
	public Date getEndDateTime() {
		return EndDateTime;
	}
	public void setEndDateTime(Date endDateTime) {
		EndDateTime = endDateTime;
	}
	public String getCRONExpression() {
		return CRONExpression;
	}
	public void setCRONExpression(String cRONExpression) {
		CRONExpression = cRONExpression;
	}
	public Integer getWorkPlan_ReminderId() {
		return WorkPlan_ReminderId;
	}
	public void setWorkPlan_ReminderId(Integer workPlan_ReminderId) {
		WorkPlan_ReminderId = workPlan_ReminderId;
	}
	public Integer getWorkPlanId() {
		return WorkPlanId;
	}
	public void setWorkPlanId(Integer workPlanId) {
		WorkPlanId = workPlanId;
	}
	public String getWorkPlanName() {
		return WorkPlanName;
	}
	public void setWorkPlanName(String workPlanName) {
		WorkPlanName = workPlanName;
	}
	public Integer getFacilityId() {
		return FacilityId;
	}
	public void setFacilityId(Integer facilityId) {
		FacilityId = facilityId;
	}
	public String getFacilityName() {
		return FacilityName;
	}
	public void setFacilityName(String facilityName) {
		FacilityName = facilityName;
	}
	
}
