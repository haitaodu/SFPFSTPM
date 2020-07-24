package com.smartflow.model;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="tpm.MeasurementDataRecord")
public class MeasurementDataRecord {
	private Integer Id;
	private PMActionRecord PMActionRecord;//PMActionRecordId;
	private BigDecimal Value;
	private BigDecimal AVGValue;
	private BigDecimal MEANValue;
	private BigDecimal MAXValue;
	private BigDecimal MINValue;
	private String ProfileValues;
	private String PlainText;
	private String DataFileURI;
	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PMActionRecordId")
	public PMActionRecord getPMActionRecord() {
		return PMActionRecord;
	}
	public void setPMActionRecord(PMActionRecord pMActionRecord) {
		PMActionRecord = pMActionRecord;
	}
	public BigDecimal getValue() {
		return Value;
	}
	public void setValue(BigDecimal value) {
		Value = value;
	}
	public BigDecimal getAVGValue() {
		return AVGValue;
	}
	public void setAVGValue(BigDecimal aVGValue) {
		AVGValue = aVGValue;
	}
	public BigDecimal getMEANValue() {
		return MEANValue;
	}
	public void setMEANValue(BigDecimal mEANValue) {
		MEANValue = mEANValue;
	}
	public BigDecimal getMAXValue() {
		return MAXValue;
	}
	public void setMAXValue(BigDecimal mAXValue) {
		MAXValue = mAXValue;
	}
	public BigDecimal getMINValue() {
		return MINValue;
	}
	public void setMINValue(BigDecimal mINValue) {
		MINValue = mINValue;
	}
	public String getProfileValues() {
		return ProfileValues;
	}
	public void setProfileValues(String profileValues) {
		ProfileValues = profileValues;
	}
	public String getPlainText() {
		return PlainText;
	}
	public void setPlainText(String plainText) {
		PlainText = plainText;
	}
	public String getDataFileURI() {
		return DataFileURI;
	}
	public void setDataFileURI(String dataFileURI) {
		DataFileURI = dataFileURI;
	}
	
}
