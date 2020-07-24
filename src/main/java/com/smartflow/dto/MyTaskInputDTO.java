package com.smartflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyTaskInputDTO {

	private String StartDateTime;//开始日期
	private String EndDateTime;//结束日期
	private Integer State;//状态id
	private Integer DeviceId;//设备id
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
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	@JsonProperty("DeviceId")
	public Integer getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(Integer deviceId) {
		DeviceId = deviceId;
	}
	

}
