package com.smartflow.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="core.Reminder")
public class Reminder {
	private Integer Id;
	private String Name;
	private Integer State;
	private Date StartDateTime;
	private Date EndDateTime;
	private String CRONExpression;
	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
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
	
}
