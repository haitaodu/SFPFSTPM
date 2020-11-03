package com.smartflow.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskListInputDTO {

	private Integer UserId;//人员id（当前登录用户）
	private String StartDateTime;//开始日期
	private String EndDateTime;//结束日期
	//private List<Map<String,Object>> State;//状态
	private List<Integer> State;//状态
//	private Integer FacilityId;//设备id
	private Integer PageIndex;
	private Integer PageSize;

	private List<Integer> FacilityIdList;//设备id
	private String AssignmentTaskSheet;//任务单号
	public TaskListInputDTO() {
		
	}
	@JsonProperty("UserId")
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
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
//	@JsonProperty("State")
//	public List<Map<String, Object>> getState() {
//		return State;
//	}
//	public void setState(List<Map<String, Object>> state) {
//		State = state;
//	}
	@JsonProperty("State")
	public List<Integer> getState() {
		return State;
	}
	public void setState(List<Integer> state) {
		State = state;
	}
//	public void setFacilityId(Integer facilityId) {
//		FacilityId = facilityId;
//	}
//	@JsonProperty("FacilityId")
//	public Integer getFacilityId() {
//		return FacilityId;
//	}
	@JsonProperty("PageIndex")
	public Integer getPageIndex() {
		return PageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		PageIndex = pageIndex;
	}
	@JsonProperty("PageSize")
	public Integer getPageSize() {
		return PageSize;
	}
	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}
	@JsonProperty("FacilityIdList")
	public List<Integer> getFacilityIdList() {
		return FacilityIdList;
	}

	public void setFacilityIdList(List<Integer> facilityIdList) {
		FacilityIdList = facilityIdList;
	}
	@JsonProperty("AssignmentTaskSheet")
	public String getAssignmentTaskSheet() {
		return AssignmentTaskSheet;
	}

	public void setAssignmentTaskSheet(String assignmentTaskSheet) {
		AssignmentTaskSheet = assignmentTaskSheet;
	}
}
