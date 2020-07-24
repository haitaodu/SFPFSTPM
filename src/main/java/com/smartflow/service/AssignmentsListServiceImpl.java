package com.smartflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartflow.dao.AssignmentsListDao;
import com.smartflow.dto.AssignmentsListOutputDTO;
import com.smartflow.dto.GetAssignmentsListConditionDTO;
import com.smartflow.dto.MaintenanceContentOutputDTO;
import com.smartflow.dto.WorkAssignmentRecordOutputDTO;
@Service
public class AssignmentsListServiceImpl implements AssignmentsListService {

	@Autowired
	AssignmentsListDao assignmentsListDao;
	@Override
	public Integer getTotalCountWorkOrderListByCondition(
			GetAssignmentsListConditionDTO getAssignmentsListConditionDTO) {
		return assignmentsListDao.getTotalCountWorkOrderListByCondition(getAssignmentsListConditionDTO);
	}
	@Override
	public List<AssignmentsListOutputDTO> getWorkOrderListByCondition(
			GetAssignmentsListConditionDTO getAssignmentsListConditionDTO) {
		return assignmentsListDao.getWorkOrderListByCondition(getAssignmentsListConditionDTO);
	}
	@Transactional
	@Override
	public void updateWorkOrderItemByWorkOrderId(Integer workOrderId) {
		assignmentsListDao.updateWorkOrderItemByWorkOrderId(workOrderId);
	}
	@Transactional
	@Override
	public void updateWorkOrderByWorkOrderId(Integer workOrderId) {
		assignmentsListDao.updateWorkOrderByWorkOrderId(workOrderId);
	}
	@Override
	public List<MaintenanceContentOutputDTO> getWorkOrderItemByWorkOrderId(Integer workOrderId) {
		return assignmentsListDao.getWorkOrderItemByWorkOrderId(workOrderId);
	}

	@Override
	public List<WorkAssignmentRecordOutputDTO> getWorkAssignmentRecordByWorkItemId(Integer workOrderItemId) {
		return assignmentsListDao.getWorkAssignmentRecordByWorkItemId(workOrderItemId);
	}

}
