package com.smartflow.service;

import java.util.List;

import com.smartflow.model.FacilityModel;
import com.smartflow.model.MaintenanceItem;
import com.smartflow.model.Role;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
/**
 * 编辑派工单
 * @author smartflow
 *
 */
public interface EditAssignmentsListService {
	/**
	 * 根据workOrderId查询派工单
	 * @param workOrderId
	 * @return
	 */
	public WorkOrder getWorkOrderById(Integer workOrderId);
	/**
	 * 根据workItemId查询WorkItem
	 * @param workItemId
	 * @return
	 */
//	public WorkItem getWorkItemByWorkItemId(Integer workItemId);

	/**
	 * 根据设备id获取设备信息
	 * @param facilityId
	 * @return
	 */
	public FacilityModel getFacilityByFacilityId(Integer facilityId);
	/**
	 * 根据workPlanIdList查询WorkItem
	 * @param workPlanIdList
	 * @return
	 */
	public List<WorkItem> getWorkItemByWorkPlanId(List<Integer> workPlanIdList);
	/**
	 * 修改派工单
	 * @param workOrder
	 */
	public void updateWorkOrder(WorkOrder workOrder);
	/**
	 * 新增WorkItem
	 * @param workItem
	 */
//	public void addWorkItem(WorkItem workItem);
	/**
	 * 修改WorkItem的状态为-1（已删除）
	 * @param workItem
	 */
//	public void DeleteWorkItem(WorkItem workItem);
	/**
	 * 根据角色名称获取角色
	 * @param roleName 角色名称
	 * @return 角色
	 */
	public Role getRoleByRoleName(String roleName);
	/**
	 * 根据维保项名称查询维保项
	 * @param maintenanceItemName 维保项名称
	 * @return 维保项
	 */
//	public maintenanceitem getMaintenanceItemByMaintenanceName(String maintenanceItemName);
	/**
	 * 根据workorderId查询WorkOrderItem
	 * @param workOrderId
	 * @return
	 */
	public List<WorkOrderItem> getWorkOrderItemListByWorkOrderId(Integer workOrderId);
	
	/**
	 * 添加WorkOrderItem
	 * @param workOrderItem
	 */
	public void addWorkOrderItem(WorkOrderItem workOrderItem);
}
