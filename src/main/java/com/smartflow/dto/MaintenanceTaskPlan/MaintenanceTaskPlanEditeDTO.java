package com.smartflow.dto.MaintenanceTaskPlan;

public class MaintenanceTaskPlanEditeDTO {
    String TargetFacility;
    String RoleName;
    String State;
    
    String MaintenanceItemName;
    String Position;
    String MaterialNumber;
    String ItemName;
    float WorkLength;
    String MeasurementType;
    float MeasurementStandardUpperLimit;
    float MeasurementStandardLowerLimit;
    float MeasurementStandardMiddleValue;
    String GuideDocURI;
    String PlanName;
    Integer PlanType;

    public MaintenanceTaskPlanEditeDTO(String targetFacility, String roleName, String state, String maintenanceItemName, String designator, String materialNumber, String name, float workDurationTime, String measurementType, float USL, float LSL, float normalValue, String guideDocURI, String planName, Integer planType) {
        TargetFacility = targetFacility;
        RoleName = roleName;
        State = state;
       
        MaintenanceItemName = maintenanceItemName;
        Position = designator;
        MaterialNumber = materialNumber;
        ItemName = name;
        WorkLength = workDurationTime;
        MeasurementType = measurementType;
        this.MeasurementStandardUpperLimit = USL;
        this.MeasurementStandardLowerLimit = LSL;
        MeasurementStandardMiddleValue = normalValue;
        GuideDocURI = guideDocURI;
        PlanName = planName;
        PlanType = planType;
    }

    public String getTargetFacility() {
        return TargetFacility;
    }

    public void setTargetFacility(String targetFacility) {
        TargetFacility = targetFacility;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

 

    public String getMaintenanceItemName() {
        return MaintenanceItemName;
    }

    public void setMaintenanceItemName(String maintenanceItemName) {
        MaintenanceItemName = maintenanceItemName;
    }

    public String getDesignator() {
        return Position;
    }

    public void setDesignator(String designator) {
        Position = designator;
    }

    public String getMaterialNumber() {
        return MaterialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        MaterialNumber = materialNumber;
    }

    public String getName() {
        return ItemName;
    }

    public void setName(String name) {
        ItemName = name;
    }

    public float getWorkDurationTime() {
        return WorkLength;
    }

    public void setWorkDurationTime(float workDurationTime) {
        WorkLength = workDurationTime;
    }

    public String getMeasurementType() {
        return MeasurementType;
    }

    public void setMeasurementType(String measurementType) {
        MeasurementType = measurementType;
    }

    public float getUSL() {
        return MeasurementStandardUpperLimit;
    }

    public void setUSL(float USL) {
        this.MeasurementStandardUpperLimit = USL;
    }

    public float getLSL() {
        return MeasurementStandardLowerLimit;
    }

    public void setLSL(float LSL) {
        this.MeasurementStandardLowerLimit = LSL;
    }

    public float getNormalValue() {
        return MeasurementStandardMiddleValue;
    }

    public void setNormalValue(float normalValue) {
        MeasurementStandardMiddleValue = normalValue;
    }

    public String getGuideDocURI() {
        return GuideDocURI;
    }

    public void setGuideDocURI(String guideDocURI) {
        GuideDocURI = guideDocURI;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public Integer getPlanType() {
        return PlanType;
    }

    public void setPlanType(Integer planType) {
        PlanType = planType;
    }
}
