package com.smartflow.model;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "core.facility")
public class FacilityModel {

    Integer Id;
    String FacilityCode;
    String Name;
    BOMHeadModel BOMHead;
    StationModel Station;
    SupplierModel Supplier;
    String Brand;
    String Model;
    Date ManufacturingDate;
    Date InstalledDate;
    Date ServiceExpirationDate;
    Integer State;
    UserModel Creator;
    Date CreateDateTime;
    Date EditDateTime;
    UserModel Editor;
    //Set<WorkOrder> workOrders = new HashSet<>();
public FacilityModel(){};
    public FacilityModel(String facilityCode, String name, BOMHeadModel BOMHead, StationModel station, SupplierModel supplier, String brand, String model, Date manufacturingDate, Date installedDate, Date serviceExpirationDate, Integer state, UserModel creator, Date createDateTime, UserModel editor, Date editDateTime) {
        FacilityCode = facilityCode;
        Name = name;
        this.BOMHead =BOMHead;
        Station = station;
        Supplier = supplier;
        Brand = brand;
        Model = model;
        ManufacturingDate = manufacturingDate;
        InstalledDate = installedDate;
        ServiceExpirationDate = serviceExpirationDate;
        State = state;
        Creator = creator;
        CreateDateTime = createDateTime;
        Editor = editor;
        EditDateTime = editDateTime;

    }
    @Id
    @GeneratedValue
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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
    @ManyToOne
    @JoinColumn(name="BOMHeadId")
    public BOMHeadModel getBOMHead() {
        return BOMHead;
    }
    public void setBOMHead(BOMHeadModel BOMHead) {
        this.BOMHead = BOMHead;
    }



    @ManyToOne
    @JoinColumn(name="StationId")
    public StationModel getStation() {
        return Station;
    }

    public void setStation(StationModel station) {
        Station = station;
    }



    @ManyToOne
    @JoinColumn(name="SupplierId")
    public SupplierModel getSupplier() {
        return Supplier;
    }

    public void setSupplier(SupplierModel supplier) {
        Supplier = supplier;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }
//    @Temporal(TemporalType.DATE)
    public Date getManufacturingDate() {
        return ManufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        ManufacturingDate = manufacturingDate;
    }
//    @Temporal(TemporalType.DATE)
    public Date getInstalledDate() {
        return InstalledDate;
    }

    public void setInstalledDate(Date installedDate) {
        InstalledDate = installedDate;
    }
//    @Temporal(TemporalType.DATE)
    public Date getServiceExpirationDate() {
        return ServiceExpirationDate;
    }

    public void setServiceExpirationDate(Date serviceExpirationDate) {
        ServiceExpirationDate = serviceExpirationDate;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }



    @ManyToOne
    @JoinColumn(name="CreatorId")
    public UserModel getCreator() {
        return Creator;
    }

    public void setCreator(UserModel creator) {
        Creator = creator;
    }

    public Date getCreateDateTime() {
        return CreateDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        CreateDateTime = createDateTime;
    }



    @ManyToOne
    @JoinColumn(name="EditorId")

    public UserModel getEditor() {
        return Editor;
    }

    public void setEditor(UserModel editor) {
        Editor = editor;
    }
    public Date getEditDateTime() {
        return EditDateTime;
    }

    public void setEditDateTime(Date editDateTime) {
        EditDateTime = editDateTime;
    }
//    @JsonIgnore
//    @OneToMany(targetEntity=WorkOrder.class,fetch=FetchType.EAGER)
//    @JoinColumn(name="TargetFacilityId")
//	public Set<WorkOrder> getWorkOrders() {
//		return workOrders;
//	}
//	public void setWorkOrders(Set<WorkOrder> workOrders) {
//		this.workOrders = workOrders;
//	}

}
