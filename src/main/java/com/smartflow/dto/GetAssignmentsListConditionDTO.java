package com.smartflow.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAssignmentsListConditionDTO {

	private String AssignNumber;//派工单号
	private String StartDateTime;//开始日期
	private String EndDateTime;//结束日期
	private String TargetFacilityName;//目标设备名
	private String PersonInCharge;//负责人
	//private Boolean DisplayCompletedItem;//显示已完成项
	private List<Integer> State;//状态
	private Integer PeriodicType;//周期类型
	private Integer PageSize;
	private Integer PageIndex;
	@JsonProperty("PageSize")
	public Integer getPageSize() {
		return PageSize;
	}

	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}
	@JsonProperty("PageIndex")
	public Integer getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		PageIndex = pageIndex;
	}
	@JsonProperty("AssignNumber")
	public String getAssignNumber() {
		return AssignNumber;
	}

	public void setAssignNumber(String assignNumber) {
		AssignNumber = assignNumber;
	}
	@JsonProperty("StartDateTime")
	public String getStartDateTime() {
		return StartDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		StartDateTime = startDateTime;
	}
	@JsonProperty("EndDateTime")
	public String getEndDateTime() {
		return EndDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		EndDateTime = endDateTime;
	}
	@JsonProperty("TargetFacilityName")
	public String getTargetFacilityName() {
		return TargetFacilityName;
	}

	public void setTargetFacilityName(String targetFacilityName) {
		TargetFacilityName = targetFacilityName;
	}
	@JsonProperty("PersonInCharge")
	public String getPersonInCharge() {
		return PersonInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		PersonInCharge = personInCharge;
	}
//	@JsonProperty("DisplayCompletedItem")
//	public Boolean getDisplayCompletedItem() {
//		return DisplayCompletedItem;
//	}
//
//	public void setDisplayCompletedItem(Boolean displayCompletedItem) {
//		DisplayCompletedItem = displayCompletedItem;
//	}

	@JsonProperty("State")
	public List<Integer> getState() {
		return State;
	}

	public void setState(List<Integer> state) {
		State = state;
	}
	@JsonProperty("PeriodicType")
	public Integer getPeriodicType() {
		return PeriodicType;
	}
	public void setPeriodicType(Integer periodicType) {
		PeriodicType = periodicType;
	}

	public GetAssignmentsListConditionDTO() {
		
	}

}
