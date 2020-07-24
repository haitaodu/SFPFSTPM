package com.smartflow.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="core.FacilityCurrentStatus")
public class FacilityCurrentStatus {
	Integer Id;
	Integer FacilityId;
	Integer RunningStatus;
	String  CurrentPartSerialNumber;
	String  LastPartSerialNumber;
	Date CurrentPartInputDateTime;
	Date LastPartOutputDateTime;
	float LastPartCycleTimeSec;
	String CurrentPartType;
	String CurrentPartCode;
	String CurrentJobName;
	String CurrentJobCode;
	String WorkingOperatorCode;
	String IPV4Address;
	String RoutingCode;
	Date UpdateDateTime;
	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getFacilityId() {
		return FacilityId;
	}
	public void setFacilityId(Integer facilityId) {
		FacilityId = facilityId;
	}
	public Integer getRunningStatus() {
		return RunningStatus;
	}
	public void setRunningStatus(Integer runningStatus) {
		RunningStatus = runningStatus;
	}
	public String getCurrentPartSerialNumber() {
		return CurrentPartSerialNumber;
	}
	public void setCurrentPartSerialNumber(String currentPartSerialNumber) {
		CurrentPartSerialNumber = currentPartSerialNumber;
	}
	public String getLastPartSerialNumber() {
		return LastPartSerialNumber;
	}
	public void setLastPartSerialNumber(String lastPartSerialNumber) {
		LastPartSerialNumber = lastPartSerialNumber;
	}
	public Date getCurrentPartInputDateTime() {
		return CurrentPartInputDateTime;
	}
	public void setCurrentPartInputDateTime(Date currentPartInputDateTime) {
		CurrentPartInputDateTime = currentPartInputDateTime;
	}
	public Date getLastPartOutputDateTime() {
		return LastPartOutputDateTime;
	}
	public void setLastPartOutputDateTime(Date lastPartOutputDateTime) {
		LastPartOutputDateTime = lastPartOutputDateTime;
	}
	public float getLastPartCycleTimeSec() {
		return LastPartCycleTimeSec;
	}
	public void setLastPartCycleTimeSec(float lastPartCycleTimeSec) {
		LastPartCycleTimeSec = lastPartCycleTimeSec;
	}
	public String getCurrentPartType() {
		return CurrentPartType;
	}
	public void setCurrentPartType(String currentPartType) {
		CurrentPartType = currentPartType;
	}
	public String getCurrentPartCode() {
		return CurrentPartCode;
	}
	public void setCurrentPartCode(String currentPartCode) {
		CurrentPartCode = currentPartCode;
	}
	public String getCurrentJobName() {
		return CurrentJobName;
	}
	public void setCurrentJobName(String currentJobName) {
		CurrentJobName = currentJobName;
	}
	public String getCurrentJobCode() {
		return CurrentJobCode;
	}
	public void setCurrentJobCode(String currentJobCode) {
		CurrentJobCode = currentJobCode;
	}
	public String getWorkingOperatorCode() {
		return WorkingOperatorCode;
	}
	public void setWorkingOperatorCode(String workingOperatorCode) {
		WorkingOperatorCode = workingOperatorCode;
	}
	public String getIPV4Address() {
		return IPV4Address;
	}
	public void setIPV4Address(String iPV4Address) {
		IPV4Address = iPV4Address;
	}
	public String getRoutingCode() {
		return RoutingCode;
	}
	public void setRoutingCode(String routingCode) {
		RoutingCode = routingCode;
	}
	public Date getUpdateDateTime() {
		return UpdateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		UpdateDateTime = updateDateTime;
	}

}
