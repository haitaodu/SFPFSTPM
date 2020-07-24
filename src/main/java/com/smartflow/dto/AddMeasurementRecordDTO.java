package com.smartflow.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddMeasurementRecordDTO {

	private BigDecimal MeasurementValue;//测量值
	private BigDecimal AvgValue;//平均值
	private BigDecimal MidValue;//中值
	private BigDecimal MaxValue;//最大值
	private BigDecimal MinValue;//最小值
	private BigDecimal ProfileValue;//曲线值
	private String TextContext;//文本内容
	private String picture;
	private String upfile;
	public AddMeasurementRecordDTO() {
		
	}
	@JsonProperty("MeasurementValue")
	public BigDecimal getMeasurementValue() {
		return MeasurementValue;
	}
	public void setMeasurementValue(BigDecimal measurementValue) {
		MeasurementValue = measurementValue;
	}
	@JsonProperty("AvgValue")
	public BigDecimal getAvgValue() {
		return AvgValue;
	}
	public void setAvgValue(BigDecimal avgValue) {
		AvgValue = avgValue;
	}
	@JsonProperty("MidValue")
	public BigDecimal getMidValue() {
		return MidValue;
	}
	public void setMidValue(BigDecimal midValue) {
		MidValue = midValue;
	}
	@JsonProperty("MaxValue")
	public BigDecimal getMaxValue() {
		return MaxValue;
	}
	public void setMaxValue(BigDecimal maxValue) {
		MaxValue = maxValue;
	}
	@JsonProperty("MinValue")
	public BigDecimal getMinValue() {
		return MinValue;
	}
	public void setMinValue(BigDecimal minValue) {
		MinValue = minValue;
	}
	@JsonProperty("ProfileValue")
	public BigDecimal getProfileValue() {
		return ProfileValue;
	}
	public void setProfileValue(BigDecimal profileValue) {
		ProfileValue = profileValue;
	}
	@JsonProperty("TextContext")
	public String getTextContext() {
		return TextContext;
	}
	public void setTextContext(String textContext) {
		TextContext = textContext;
	}
	@JsonProperty("picture")
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	@JsonProperty("upfile")
	public String getUpfile() {
		return upfile;
	}
	public void setUpfile(String upfile) {
		this.upfile = upfile;
	}
	

}
