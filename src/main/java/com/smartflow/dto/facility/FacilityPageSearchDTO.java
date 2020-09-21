package com.smartflow.dto.facility;

import javax.validation.constraints.NotNull;

/**
 * @author ：tao
 * @date ：Created in 2020/8/10 10:10
 */

public class FacilityPageSearchDTO {
    private String FacilityCode;
    private String Name;
    private String StationNumber;
    private String MaterialNumber;
    private String Brand;
    private String SupplierNumber;
    private String Model;
    @NotNull(message = "分页页码不为空")
    private Integer PageIndex;
    @NotNull(message = "分页大小不为空")
    private Integer PageSize;

    @Override
    public String toString() {
        return "FacilityPageSearchDTO{" +
                "FacilityCode='" + FacilityCode + '\'' +
                ", Name='" + Name + '\'' +
                ", StationNumber='" + StationNumber + '\'' +
                ", MaterialNumber='" + MaterialNumber + '\'' +
                ", Brand='" + Brand + '\'' +
                ", SupplierNumber='" + SupplierNumber + '\'' +
                ", Model='" + Model + '\'' +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                '}';
    }

    public String getFacilityCode() {
        return FacilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        FacilityCode = facilityCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStationNumber() {
        return StationNumber;
    }

    public void setStationNumber(String stationNumber) {
        StationNumber = stationNumber;
    }

    public String getMaterialNumber() {
        return MaterialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        MaterialNumber = materialNumber;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getSupplierNumber() {
        return SupplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        SupplierNumber = supplierNumber;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public Integer getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        PageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }
}
