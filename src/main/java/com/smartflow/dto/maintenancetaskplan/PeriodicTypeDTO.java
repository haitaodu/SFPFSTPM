package com.smartflow.dto.maintenancetaskplan;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PeriodicTypeDTO {
	String PeriodicName;//周期名
	Integer Id;
	String  key;
	String CRONExpression;//CRON表达式
	@JsonProperty("Id")
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@JsonProperty("key")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
    

	@JsonProperty("PeriodicName")
	public String getPeriodicName() {
		return PeriodicName;
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
