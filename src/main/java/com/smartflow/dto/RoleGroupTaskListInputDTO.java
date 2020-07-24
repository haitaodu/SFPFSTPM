package com.smartflow.dto;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleGroupTaskListInputDTO {
	private Integer UserId;//人员id(查找我的任务是需要我的id)
	private String StartDateTime;//开始时间
	private String EndDateTime;//结束时间
	private List<Map<String,Object>> State;//状态
	private Integer RoleGroupId;//角色组id
	private Integer PageIndex;
	private Integer PageSize;
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
	@JsonProperty("State")
	public List<Map<String, Object>> getState() {
		return State;
	}
	public void setState(List<Map<String, Object>> state) {
		State = state;
	}
	@JsonProperty("RoleGroupId")
	public Integer getRoleGroupId() {
		return RoleGroupId;
	}
	public void setRoleGroupId(Integer roleGroupId) {
		RoleGroupId = roleGroupId;
	}	
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


	public static class TaskIdList{
		private List<Integer> TaskIdList;
		private Integer UserId;//人员id(我)
		@JsonProperty("TaskIdList")
		public List<Integer> getTaskIdList() {
			return TaskIdList;
		}

		public void setTaskIdList(List<Integer> taskIdList) {
			TaskIdList = taskIdList;
		}
		@JsonProperty("UserId")
		public Integer getUserId() {
			return UserId;
		}

		public void setUserId(Integer userId) {
			UserId = userId;
		}
	}
}
