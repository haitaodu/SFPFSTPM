package com.smartflow.dto.facility;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author haita
 */
public class FacilityPageDTO {
    private int id;
    private String FacilityCode;
    private String Name;
    private String StationNumber;
    private String MaterialNumber;
    private String Brand;
    private String SupplierNumber;
    private String Model;
public  FacilityPageDTO(){};
    public FacilityPageDTO(int id, String facilityCode, String name, String stationNumber, String materialNumber, String brand, String supplierNumber, String model) {
        this.id = id;
        FacilityCode = facilityCode;
        Name = name;
        StationNumber = stationNumber;
        MaterialNumber = materialNumber;
        Brand = brand;
        SupplierNumber = supplierNumber;
        Model = model;
    }
@JsonProperty("Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @JsonProperty("StationNumber")
    public String getStationNumber() {
        return StationNumber;
    }

    public void setStationNumber(String stationNumber) {
        StationNumber = stationNumber;
    }
    @JsonProperty("MaterialNumber")
    public String getMaterialNumber() {
        return MaterialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        MaterialNumber = materialNumber;
    }
    @JsonProperty("Brand")
    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }
    @JsonProperty("SupplierNumber")
    public String getSupplierNumber() {
        return SupplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        SupplierNumber = supplierNumber;
    }
    @JsonProperty("Model")
    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }



}
