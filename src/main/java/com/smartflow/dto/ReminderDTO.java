package com.smartflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReminderDTO {
	String PeriodicName;
	String CRONExpression;
	@JsonProperty("PeriodicName")
	public String getPeriodicName() {
		return PeriodicName;
		
	}
	public ReminderDTO(String periodicName, String cRONExpression) {
		super();
		PeriodicName = periodicName;
		CRONExpression = cRONExpression;
	}
	public void setPeriodicName(String periodicName) {
		PeriodicName = periodicName;
	}
	@JsonProperty("CRONExpression")
	public String getCRONExpression() {
		return CRONExpression;
	}
	public void setCRONExpression(String cRONExpression) {
		CRONExpression = cRONExpression;
	}
	

}
