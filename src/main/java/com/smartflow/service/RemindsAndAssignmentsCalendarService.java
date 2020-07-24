package com.smartflow.service;

import java.util.List;

import com.smartflow.dto.CalendarDataDTO;
import com.smartflow.dto.ConfirmUnifiedArrangementsDTO;
import com.smartflow.model.TPMWorkPlan_Reminder;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.model.WorkPlanExcutionState;

public interface RemindsAndAssignmentsCalendarService {
	
	/**
	 * 获取日历数据
	 * @return
	 */
	public List<CalendarDataDTO> getAssignmentsCalendar();
	/**
	 * 获取日历颜色
	 * @param tpmWorkPlanReminderId
	 * @return
	 */
	public String getCalendarStateByTPMWorkPlanReminderId(Integer tpmWorkPlanReminderId);
	/**
	 * 根据WorkPlanId查询角色组id
	 * @param workPlanId
	 * @return
	 */
	public Integer getRoleGroupIdByWorkPlanId(Integer workPlanId);
	

	/**
	 * 根据WorkItem表插入WorkOrderItem
	 * @param workOrderItem
	 */
	public void addWorkOrderItemByWorkItem(WorkOrderItem workOrderItem);
	
	/**
	 * 添加WorkPlanExcutionState
	 * @param workPlanExcutionState
	 */
	public void addWorkPlanExcutionState(WorkPlanExcutionState workPlanExcutionState);
	
	/**
	 * 根据workItemId通过TPMWorkPlan_ReminderId查询WorkOrderId
	 * @param workItemId
	 * @return
	 */
	public List<Integer> getWorkOrderIdFromTPMWorkPlan_ReminderIdByWorkItemId(List<Integer> workItemId);
	
	/**
	 * 添加WorkOrder、WorkOrderItem、WorkPlanExcutionState表
	 * @param confirmUnifiedArrangementsDTO
	 */
	public void addWorkOrderAndWorkOrderItemAndWorkPlanExcutionState(ConfirmUnifiedArrangementsDTO confirmUnifiedArrangementsDTO);
	
	/**
	 * 根据workItemId查询FacilityId
	 * @param workItemId
	 * @return
	 */
	public Integer getFacilityIdByWorkItemId(List<Integer> workItemId);
	
	/**
	 * 根据workItemId查询WorkItem
	 * @param workItemIdList
	 * @return
	 */
	public List<WorkItem> getWorkItemByWorkItemIdList(List<Integer> workItemIdList);
	/**
	 * 添加TPMWorkPlan_Reminder
	 * @param tpmWorkPlan_Reminder
	 */
	public void addTPMWorkPlan_Reminder(TPMWorkPlan_Reminder tpmWorkPlan_Reminder);
	
	/**
	 * 根据workItemIdList查询workPlanId
	 * @param workItemIdList
	 * @return
	 */
	public List<Integer> getWorkPlanIdByWorkItemIdList(List<Integer> workItemIdList);
	
	/**
	 * 根据工作计划id查询ReminderId
	 * @param workPlanId
	 * @return
	 */
	public List<Integer> getReminderIdByWorkPlanId(List<Integer> workPlanIdList);
	/**
	 * 根据工作计划id查询TPMWorkPlan_Reminder
	 * @param workPlanIdList
	 * @return
	 */
	public List<TPMWorkPlan_Reminder> getTPMWorkPlan_ReminderByWorkPlanId(List<Integer> workPlanIdList);

}
