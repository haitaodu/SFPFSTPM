package com.smartflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartflow.dao.MyRoleGroupTaskListDao;
import com.smartflow.dto.AssignmentTaskInitOutputDTO.AssignmentTaskInitOutputRowDTO;
import com.smartflow.dto.RoleGroupTaskListInputDTO;
import com.smartflow.dto.RoleGroupTaskListOutputRowDTO;

@Service
public class MyRoleGroupTaskListSerivceImpl implements MyRoleGroupTaskListSerivce {
	@Autowired 
	MyRoleGroupTaskListDao myRoleGroupTaskListDao;

	@Override
	public List<String> getVisitButtonListByUserId(Integer userId) {
		return myRoleGroupTaskListDao.getVisitButtonListByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> getRoleGroupListByUserId(Integer userId) {
		return myRoleGroupTaskListDao.getRoleGroupListByUserId(userId);
	}
	@Override
	public List<Map<String, Object>> getRoleGroupList() {
		return myRoleGroupTaskListDao.getRoleGroupList();
	}
	@Override
	public Integer getTotalCountRoleGroupTaskListByCondition(RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO) {
		return myRoleGroupTaskListDao.getTotalCountRoleGroupTaskListByCondition(roleGroupTaskListConditionDTO);
	}
	@Override
	public List<RoleGroupTaskListOutputRowDTO> getRoleGroupTaskListByCondition(
			RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO) {
		return myRoleGroupTaskListDao.getRoleGroupTaskListByCondition(roleGroupTaskListConditionDTO);
	}
	@Override
	public Integer getStatusByWorkOrderItemId(Integer workOrderItemId) {
		return myRoleGroupTaskListDao.getStatusByWorkOrderItemId(workOrderItemId);
	}
	@Transactional
	@Override
	public void updateStatusAndUserIdByWorkOrderItemId(Integer workOrderItemId, Integer userId) {
		myRoleGroupTaskListDao.updateStatusAndUserIdByWorkOrderItemId(workOrderItemId, userId);
	}
	@Transactional
	@Override
	public void updateStatusAndUserIdAndRoleIdByWorkOrderItemId(Integer workOrderItemId, Integer userId, Integer roleId) {
		myRoleGroupTaskListDao.updateStatusAndUserIdAndRoleIdByWorkOrderItemId(workOrderItemId, userId, roleId);
	}
	@Override
	public List<Map<String,Object>> getUserList(Integer roleId) {
		return myRoleGroupTaskListDao.getUserList(roleId);
	}
	@Override
	public List<AssignmentTaskInitOutputRowDTO> getAssignmentTaskInitDTOByWorkOrderItemId(
			List<Integer> workOrderItemIdList) {
		return myRoleGroupTaskListDao.getAssignmentTaskInitDTOByWorkOrderItemId(workOrderItemIdList);
	}
}
