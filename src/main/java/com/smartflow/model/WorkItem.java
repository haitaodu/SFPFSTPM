package com.smartflow.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="tpm.WorkItem")
public class WorkItem {

	private Integer Id;
	private WorkPlan WorkPlan;//WorkPlanId;
	private String Name;
	private String Designator;
	private Material Material;//MaterialId;
	private MaintenanceItem MaintenanceItem;//MaintenanceItemId;
	private long WorkDurationSec;
	private Role Role;//ActionOwnerGroupId;
	private Integer MeasurementType;
	private BigDecimal USL;
	private BigDecimal LSL;
	private BigDecimal NormalValue;
	private Integer State;
	private String GuideDocURI;
	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@ManyToOne
	@JoinColumn(name="WorkPlanId")
	public WorkPlan getWorkPlan() {
		return WorkPlan;
	}
	public void setWorkPlan(WorkPlan workPlan) {
		WorkPlan = workPlan;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDesignator() {
		return Designator;
	}
	public void setDesignator(String designator) {
		Designator = designator;
	}
	@ManyToOne
	@JoinColumn(name="MaterialId")
	public Material getMaterial() {
		return Material;
	}
	public void setMaterial(Material material) {
		Material = material;
	}
	@ManyToOne
	@JoinColumn(name="MaintenanceItemId")
	public MaintenanceItem getMaintenanceItem() {
		return MaintenanceItem;
	}
	public void setMaintenanceItem(MaintenanceItem maintenanceItem) {
		MaintenanceItem = maintenanceItem;
	}
	public long getWorkDurationSec() {
		return WorkDurationSec;
	}
	public void setWorkDurationSec(long workDurationSec) {
		WorkDurationSec = workDurationSec;
	}
	@ManyToOne
	@JoinColumn(name="ActionOwnerGroupId")
	public Role getRole() {
		return Role;
	}
	public void setRole(Role role) {
		Role = role;
	}
	public Integer getMeasurementType() {
		return MeasurementType;
	}
	public void setMeasurementType(Integer measurementType) {
		MeasurementType = measurementType;
	}
	public BigDecimal getUSL() {
		return USL;
	}
	public void setUSL(BigDecimal uSL) {
		USL = uSL;
	}
	public BigDecimal getLSL() {
		return LSL;
	}
	public void setLSL(BigDecimal lSL) {
		LSL = lSL;
	}

	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	public String getGuideDocURI() {
		return GuideDocURI;
	}
	public void setGuideDocURI(String guideDocURI) {
		GuideDocURI = guideDocURI;
	}
	public BigDecimal getNormalValue() {
		return NormalValue;
	}
	public void setNormalValue(BigDecimal normalValue) {
		NormalValue = normalValue;
	}
	
}
