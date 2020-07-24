package com.smartflow.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
@Entity
@Table(name="core.Roles_Users")
public class Roles_Users {
	private Integer Id;
	private Role Role;//RoleId
	private UserModel User;//UserId
	private Date CreationDateTime;
	private Integer CreatorId;
	@Id
	@GeneratedValue
	@Column(unique=true,nullable=false)
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@ManyToOne
	@JoinColumn(name="RoleId",nullable=false)
	public Role getRole() {
		return Role;
	}
	public void setRole(Role role) {
		Role = role;
	}
	@ManyToOne
	@JoinColumn(name="UserId",nullable=false)
	public UserModel getUser() {
		return User;
	}
	public void setUser(UserModel user) {
		User = user;
	}
	@Column(nullable=false)
	public Date getCreationDateTime() {
		return CreationDateTime;
	}
	public void setCreationDateTime(Date creationDateTime) {
		CreationDateTime = creationDateTime;
	}
	@Column(nullable=false)
	public Integer getCreatorId() {
		return CreatorId;
	}
	public void setCreatorId(Integer creatorId) {
		CreatorId = creatorId;
	}
	
}
