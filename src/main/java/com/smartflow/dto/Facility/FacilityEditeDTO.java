package com.smartflow.dto.Facility;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class FacilityEditeDTO {
    String FacilityCode;
    String Name;
    String StationId;
    String MaterialNumber;
    Integer Version;
    String Brand;
    String SupplierId;
    String Model;
    String State;
    Date InstalledDate;
    Date ManufacturingDate;
    Date ServiceExpirationDate;
    Integer Id;
    @JsonProperty("Id")
    public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public FacilityEditeDTO(){};
    public FacilityEditeDTO(String facilityCode, String name, String stationNumber, String materialNumber, String brand, String supplierNumber, String model, String state, Date installedDate, Date manufacturingDate, Date serviceExpirationDate) {
        FacilityCode = facilityCode;
        Name = name;
        StationId = stationNumber;
        MaterialNumber  = materialNumber;
        Brand = brand;
        SupplierId = supplierNumber;
        Model = model;
        State = state;
        InstalledDate = installedDate;
        ManufacturingDate = manufacturingDate;
        ServiceExpirationDate = serviceExpirationDate;
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
    public String getStationNumber() {
        return StationId;
    }

    public void setStationNumber(String stationNumber) {
    	StationId = stationNumber;
    }
    @JsonProperty("MaterialNumber")
    public String getMaterialNumber() {
		return MaterialNumber;
	}
	public void setMaterialNumber(String materialNumber) {
		MaterialNumber = materialNumber;
	}
	@JsonProperty("Version")
	public Integer getVersion() {
		return Version;
	}
	public void setVersion(Integer version) {
		Version = version;
	}
	@JsonProperty("Brand")
    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }
    @JsonProperty("SupplierId")
    public String getSupplierNumber() {
        return SupplierId;
    }

    public void setSupplierNumber(String supplierNumber) {
    	SupplierId = supplierNumber;
    }
    @JsonProperty("Model")
    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }
    @JsonProperty("State")
    public String getState() {
        return State;
    }

    public void setState(String state) {
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
