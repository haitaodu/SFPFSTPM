package com.smartflow.dto.Facility;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacilityAddDTO {
	
    int   Id;
	Integer UserId;
	String FacilityCode;
    String Name;
    Integer StationId;
    Integer BOMHeadId;
    String Brand;
    Integer SupplierId;
    String Model;
    Integer State;
    Date InstalledDate;
    Date ManufacturingDate;
    Date ServiceExpirationDate;
    public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public void setSupplierId(Integer supplierId) {
		SupplierId = supplierId;
	}

	
    public FacilityAddDTO(){};
    public FacilityAddDTO(String facilityCode, String name, Integer stationId, 
    		Integer BOMHeadId, String brand, Integer supplierId,
    		String model, Integer state, Date installedDate,
    		Date manufacturingDate, Date serviceExpirationDate,Integer userId) {
        FacilityCode = facilityCode;
        Name = name;
        StationId = stationId;
        this.BOMHeadId = BOMHeadId;
        Brand = brand;
        SupplierId = supplierId;
        Model = model;
        State = state;
        InstalledDate = installedDate;
        ManufacturingDate = manufacturingDate;
        ServiceExpirationDate = serviceExpirationDate;
        UserId=userId;
    }
    @JsonProperty("UserId")
    public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
	}
	   @JsonProperty("BOMHeadId")
    public Integer getBOMHeadId() {
        return BOMHeadId;
    }

    public void setBOMHeadId(Integer BOMHeadId) {
        this.BOMHeadId = BOMHeadId;
    }
    @JsonProperty("FacilityCode")
    public String getFacilityCode() {
        return FacilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        FacilityCode = facilityCode;
    }
    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    @JsonProperty("StationId")
    public Integer getStationId() {
        return StationId;
    }

    public void setStationId(Integer stationId) {
        StationId = stationId;
    }


    @JsonProperty("Brand")
    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }
    @JsonProperty("SupplierId")
    public Integer getSupplierId() {
        return SupplierId;
    }

    public void setSupplierNumber(Integer supplierId) {
        SupplierId = supplierId;
    }
    @JsonProperty("Model")
    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }
    @JsonProperty("State")
    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }
    @JsonProperty("InstalledDate")
    public Date getInstalledDate() {
        return InstalledDate;
    }

    public void setInstalledDate(Date installedDate) {
        InstalledDate = installedDate;
    }
    @JsonProperty("ManufacturingDate")
    public Date getManufacturingDate() {
        return ManufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        ManufacturingDate = manufacturingDate;
    }
    @JsonProperty("ServiceExpirationDate")
    public Date getServiceExpirationDate() {
        return ServiceExpirationDate;
    }

    public void setServiceExpirationDate(Date serviceExpirationDate) {
        ServiceExpirationDate = serviceExpirationDate;
    }





}
