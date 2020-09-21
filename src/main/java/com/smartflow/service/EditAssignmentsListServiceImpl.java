package com.smartflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartflow.dao.EditAssignmentsListDao;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.MaintenanceItem;
import com.smartflow.model.Role;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
@Service
public class EditAssignmentsListServiceImpl implements EditAssignmentsListService {
	@Autowired
	EditAssignmentsListDao editAssignmentsListDao;
	@Override
	public WorkOrder getWorkOrderById(Integer workOrderId) {
		return editAssignmentsListDao.getWorkOrderById(workOrderId);
	}


	@Override
	public FacilityModel getFacilityByFacilityId(Integer facilityId) {
		return editAssignmentsListDao.getFacilityByFacilityId(facilityId);
	}
	
	@Override
	public List<WorkItem> getWorkItemByWorkPlanId(List<Integer> workPlanIdList) {
		return editAssignmentsListDao.getWorkItemByWorkPlanId(workPlanIdList);
	}

//	@Override
//	public WorkItem getWorkItemByWorkItemId(Integer workItemId) {
//		return editAssignmentsListDao.getWorkItemByWorkItemId(workItemId);
//	}
	@Transactional
	@Override
	public void updateWorkOrder(WorkOrder workOrder) {
		editAssignmentsListDao.updateWorkOrder(workOrder);
	}
//	@Transactional
//	@Override
//	public void addWorkItem(WorkItem workItem) {
//		editAssignmentsListDao.addWorkItem(workItem);
//	}
//	@Transactional
//	@Override
//	public void DeleteWorkItem(WorkItem workItem) {
//		editAssignmentsListDao.DeleteWorkItem(workItem);
//	}

	@Override
	public Role getRoleByRoleName(String roleName) {
		return editAssignmentsListDao.getRoleByRoleName(roleName);
	}

//	@Override
//	public maintenanceitem getMaintenanceItemByMaintenanceName(String maintenanceItemName) {
//		return editAssignmentsListDao.getMaintenanceItemByMaintenanceName(maintenanceItemName);
//	}


	@Override
	public List<WorkOrderItem> getWorkOrderItemListByWorkOrderId(Integer workOrderId) {
		return editAssignmentsListDao.getWorkOrderItemListByWorkOrderId(workOrderId);
	}

	@Transactional
	@Override
	public void addWorkOrderItem(WorkOrderItem workOrderItem) {
		editAssignmentsListDao.addWorkOrderItem(workOrderItem);
	}


}
