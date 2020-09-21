package com.smartflow.dto.facility;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FacilityDetailDTO {
    String FacilityCode;
    String Name;
    String StationNumber;
    String MaterialNumber;
    String Brand;
    String SupplierNumber;
    String Model;
    Date InstalledDate;
    Date ManufacturingDate;
    Date ServiceExpirationDate;
    String State;
    String RunningState;
    String CurrentPartSerialNumber;
    String LastPartSerialNumber;
    Date CurrentPartInputDateTime;
    Date LastPartOutputDateTime;
    String CurrentPartType;
    String CurrentPartCode;
    String CurrentJobName;
    String CurrentJobCode;
    String WorkingOperatorCode;
    String IPV4Address;
    String RoutingCode;
    Date UpdateDateTime;
    String BOM;
    String SerialNumber;
   List<Map<String, Object>> BOMItemList;
   
   @JsonProperty("BOMItemList")
    public List<Map<String, Object>> getBOMItemList() {
	return BOMItemList;
}
public void setBOMItemList(List<Map<String, Object>> bOMItemList) {
	BOMItemList = bOMItemList;
}
	@JsonProperty("SerialNumber")
    public String getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}
	@JsonProperty("LastPartCycleTimeSec")
	public Float getLastPartCycleTimeSec() {
		return LastPartCycleTimeSec;
	}
	public void setLastPartCycleTimeSec(Float lastPartCycleTimeSec) {
		LastPartCycleTimeSec = lastPartCycleTimeSec;
	}

	Float LastPartCycleTimeSec;
    public FacilityDetailDTO() {};
    public FacilityDetailDTO(String facilityCode, String name, String stationNumber, String materialNumber, String brand, String supplierNumber, String model, Date installedDate, Date manufacturingDate, Date serviceExpirationDate, String state, String runningState, String currentPartSerialNumber, String lastPartSerialNumber, Date currentPartInputDateTime, Date lastPartOutputDateTime, String currentPartType, String currentPartCode, String currentJobName, String currentJobCode, String workingOperatorCode, String IPV4Address, String routingCode, Date updateDateTime, String BOM,Float lastPartCycleTimeSec,String serialNumber) {
        FacilityCode = facilityCode;
        Name = name;
        StationNumber = stationNumber;
        MaterialNumber = materialNumber;
        Brand = brand;
        SupplierNumber = supplierNumber;
        Model = model;
        InstalledDate = installedDate;
        ManufacturingDate = manufacturingDate;
        ServiceExpirationDate = serviceExpirationDate;
        State = state;
        RunningState = runningState;
        CurrentPartSerialNumber = currentPartSerialNumber;
        LastPartSerialNumber = lastPartSerialNumber;
        CurrentPartInputDateTime = currentPartInputDateTime;
        LastPartOutputDateTime = lastPartOutputDateTime;
        CurrentPartType = currentPartType;
        CurrentPartCode = currentPartCode;
        CurrentJobName = currentJobName;
        CurrentJobCode = currentJobCode;
        WorkingOperatorCode = workingOperatorCode;
        this.IPV4Address = IPV4Address;
        RoutingCode = routingCode;
        UpdateDateTime = updateDateTime;
        this.BOM = BOM;
        LastPartCycleTimeSec=lastPartCycleTimeSec;
        SerialNumber=serialNumber;
    }

  
    @JsonProperty("BOM")
    public String getBOM() {
        return BOM;
    }

    public void setBOM(String BOM) {
        this.BOM = BOM;
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
    @JsonProperty("State")
    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
    @JsonProperty("RunningState")
    public String getRunningState() {
        return RunningState;
    }

    public void setRunningState(String runningState) {
        RunningState = runningState;
    }
    @JsonProperty("CurrentPartSerialNumber")
    public String getCurrentPartSerialNumber() {
        return CurrentPartSerialNumber;
    }

    public void setCurrentPartSerialNumber(String currentPartSerialNumber) {
        CurrentPartSerialNumber = currentPartSerialNumber;
    }
    @JsonProperty("LastPartSerialNumber")
    public String getLastPartSerialNumber() {
        return LastPartSerialNumber;
    }

    public void setLastPartSerialNumber(String lastPartSerialNumber) {
        LastPartSerialNumber = lastPartSerialNumber;
    }
    @JsonProperty("CurrentPartInputDateTime")
    public Date getCurrentPartInputDateTime() {
        return CurrentPartInputDateTime;
    }

    public void setCurrentPartInputDateTime(Date currentPartInputDateTime) {
        CurrentPartInputDateTime = currentPartInputDateTime;
    }
    @JsonProperty("LastPartOutputDateTime")
    public Date getLastPartOutputDateTime() {
        return LastPartOutputDateTime;
    }

    public void setLastPartOutputDateTime(Date lastPartOutputDateTime) {
        LastPartOutputDateTime = lastPartOutputDateTime;
    }
    @JsonProperty("CurrentPartType")
    public String getCurrentPartType() {
        return CurrentPartType;
    }

    public void setCurrentPartType(String currentPartType) {
        CurrentPartType = currentPartType;
    }
    @JsonProperty("CurrentPartCode")
    public String getCurrentPartCode() {
        return CurrentPartCode;
    }

    public void setCurrentPartCode(String currentPartCode) {
        CurrentPartCode = currentPartCode;
    }
    @JsonProperty("CurrentJobName")
    public String getCurrentJobName() {
        return CurrentJobName;
    }

    public void setCurrentJobName(String currentJobName) {
        CurrentJobName = currentJobName;
    }
    @JsonProperty("CurrentJobCode")
    public String getCurrentJobCode() {
        return CurrentJobCode;
    }

    public void setCurrentJobCode(String currentJobCode) {
        CurrentJobCode = currentJobCode;
    }
    @JsonProperty("WorkingOperatorCode")
    public String getWorkingOperatorCode() {
        return WorkingOperatorCode;
    }

    public void setWorkingOperatorCode(String workingOperatorCode) {
        WorkingOperatorCode = workingOperatorCode;
    }
    @JsonProperty("IPV4Address")
    public String getIPV4Address() {
        return IPV4Address;
    }

    public void setIPV4Address(String IPV4Address) {
        this.IPV4Address = IPV4Address;
    }
    @JsonProperty("RoutingCode")
    public String getRoutingCode() {
        return RoutingCode;
    }

    public void setRoutingCode(String routingCode) {
        RoutingCode = routingCode;
    }
    @JsonProperty("UpdateDateTime")
    public Date getUpdateDateTime() {
        return UpdateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        UpdateDateTime = updateDateTime;
    }



}
