package com.smartflow.model;

import java.util.Date;

import javax.persistence.*;

/**
 * @author haita
 */
@Entity
@Table(name="tpm.WorkPlan")
public class WorkPlan {
	private Integer Id;
	private String Name;
	private Integer Version;
	private Integer Type;
	private Integer State;
	private Integer CreatorId;
	private Date CreationDateTime;
	private Integer EditorId;
	private Date EditDateTime;
	private Integer TargetFacilityId;
	private Role Role;

	private Integer MaintenanceItemId;
	private String FacilityId;

	public WorkPlan(){};

	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Integer getVersion() {
		return Version;
	}
	public void setVersion(Integer version) {
		Version = version;
	}
	public Integer getType() {
		return Type;
	}
	public void setType(Integer type) {
		Type = type;
	}
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	public Integer getCreatorId() {
		return CreatorId;
	}
	public void setCreatorId(Integer creatorId) {
		CreatorId = creatorId;
	}
	public Date getCreationDateTime() {
		return CreationDateTime;
	}
	public void setCreationDateTime(Date creationDateTime) {
		CreationDateTime = creationDateTime;
	}
	public Integer getEditorId() {
		return EditorId;
	}
	public void setEditorId(Integer editorId) {
		EditorId = editorId;
	}
	public Date getEditDateTime() {
		return EditDateTime;
	}
	public void setEditDateTime(Date editDateTime) {
		EditDateTime = editDateTime;
	}

	@Column(name="TargetFacilityId")
	public Integer getTargetFacilityId() {
		return TargetFacilityId;
	}

	public void setTargetFacilityId(Integer targetFacilityId) {
		TargetFacilityId = targetFacilityId;
	}


	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="ActionOwnerRoleId")
	public Role getRole() {
		return Role;
	}
	public void setRole(Role role) {
		Role = role;
	}
	@Column(name="MaintenanceItemId")
	public Integer getMaintenanceItemId() {
		return MaintenanceItemId;
	}

	public void setMaintenanceItemId(Integer maintenanceItemId) {
		MaintenanceItemId = maintenanceItemId;
	}

	@Column(name="FacilityId")
	public String getFacilityId() {
		return FacilityId;
	}

	public void setFacilityId(String facilityId) {
		FacilityId = facilityId;
	}

	@Override
	public String toString() {
		return "WorkPlan{" +
				"Id=" + Id +
				", Name='" + Name + '\'' +
				", Version=" + Version +
				", Type=" + Type +
				", State=" + State +
				", CreatorId=" + CreatorId +
				", CreationDateTime=" + CreationDateTime +
				", EditorId=" + EditorId +
				", EditDateTime=" + EditDateTime +
				", FacilityId=" + FacilityId +
				", Role=" + Role +
				'}';
	}
}
