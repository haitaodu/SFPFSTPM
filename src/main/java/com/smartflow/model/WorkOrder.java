package com.smartflow.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tpm.WorkOrder")
public class WorkOrder {

	private Integer Id;
	private String Name;
	private Integer WorkType;
	private FacilityModel Facility;//TargetFacilityId;
	private Role role;//LeaderRoleId
	private UserModel user;//LeaderId;
	private Integer State;
	private Integer CreatorId;
	private Date CreationDateTime;
	private Integer EditorId;
	private Date EditDateTime;
	private Integer Status;
	private Set<WorkOrderItem> WorkOrderItems = new HashSet<>();
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


	public Integer getWorkType() {
		return WorkType;
	}


	public void setWorkType(Integer workType) {
		WorkType = workType;
	}

	@OneToOne
	@JoinColumn(name="TargetFacilityId")
	public FacilityModel getFacility() {
		return Facility;
	}


	public void setFacility(FacilityModel facility) {
		Facility = facility;
	}
	@ManyToOne
	@JoinColumn(name="LeaderRoleId")
	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}

	
	@ManyToOne
	@JoinColumn(name="LeaderId")
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
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


	public Integer getStatus() {
		return Status;
	}


	public void setStatus(Integer status) {
		Status = status;
	}

	@JsonIgnore
	@OneToMany(targetEntity=WorkOrderItem.class,fetch=FetchType.LAZY)
	@JoinColumn(name="WorkOrderId")
	public Set<WorkOrderItem> getWorkOrderItems() {
		return WorkOrderItems;
	}


	public void setWorkOrderItems(Set<WorkOrderItem> workOrderItems) {
		WorkOrderItems = workOrderItems;
	}


	public WorkOrder() {
		
	}

}
