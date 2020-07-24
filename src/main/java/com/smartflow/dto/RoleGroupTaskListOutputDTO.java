package com.smartflow.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleGroupTaskListOutputDTO {
	
	private List<RoleGroupTaskListOutputRowDTO> RoleGroupTaskListOutputDTOList;

	@JsonProperty("RoleGroupTaskListOutputDTOList")
	public List<RoleGroupTaskListOutputRowDTO> getRoleGroupTaskListOutputDTOList() {
		return RoleGroupTaskListOutputDTOList;
	}

	public void setRoleGroupTaskListOutputDTOList(List<RoleGroupTaskListOutputRowDTO> roleGroupTaskListOutputDTOList) {
		RoleGroupTaskListOutputDTOList = roleGroupTaskListOutputDTOList;
	}
	

}
