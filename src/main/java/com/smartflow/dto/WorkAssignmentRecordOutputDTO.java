package com.smartflow.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkAssignmentRecordOutputDTO {

	private Integer WorkItemId;//Id
	private String AssignNumber;//派工单号
	private String ItemName;//项目名
	private String Position;//位置
	private Date DateTime;//时间
	private String Assigner;//分配人
	private String AssignmentToRoles;//分配给角色
	private String Allottee;//分配给谁（受分配的人）
	
	@JsonProperty("Id")
	public Integer getWorkItemId() {
		return WorkItemId;
	}


	public void setWorkItemId(Integer workItemId) {
		WorkItemId = workItemId;
	}

	@JsonProperty("AssignNumber")
	public String getAssignNumber() {
		return AssignNumber;
	}


	public void setAssignNumber(String assignNumber) {
		AssignNumber = assignNumber;
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

	@JsonProperty("DateTime")
	public Date getDateTime() {
		return DateTime;
	}


	public void setDateTime(Date dateTime) {
		DateTime = dateTime;
	}

	@JsonProperty("Assigner")
	public String getAssigner() {
		return Assigner;
	}


	public void setAssigner(String assigner) {
		Assigner = assigner;
	}

	@JsonProperty("AssignmentToRoles")
	public String getAssignmentToRoles() {
		return AssignmentToRoles;
	}


	public void setAssignmentToRoles(String assignmentToRoles) {
		AssignmentToRoles = assignmentToRoles;
	}

	@JsonProperty("Allottee")
	public String getAllottee() {
		return Allottee;
	}


	public void setAllottee(String allottee) {
		Allottee = allottee;
	}


	public WorkAssignmentRecordOutputDTO() {
	
	}


	public WorkAssignmentRecordOutputDTO(Integer workItemId, String assignNumber, String itemName, String position,
			Date dateTime, String assigner, String assignmentToRoles, String allottee) {
		WorkItemId = workItemId;
		AssignNumber = assignNumber;
		ItemName = itemName;
		Position = position;
		DateTime = dateTime;
		Assigner = assigner;
		AssignmentToRoles = assignmentToRoles;
		Allottee = allottee;
	}
	

}
