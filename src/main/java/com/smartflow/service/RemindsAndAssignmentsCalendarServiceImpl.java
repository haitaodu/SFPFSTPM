package com.smartflow.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartflow.dao.AddAssignmentsListDao;
import com.smartflow.dao.EditAssignmentsListDao;
import com.smartflow.dao.RemindsAndAssignmentsCalendarDao;
import com.smartflow.dto.CalendarDataDTO;
import com.smartflow.dto.ConfirmUnifiedArrangementsDTO;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.Role;
import com.smartflow.model.TPMWorkPlan_Reminder;
import com.smartflow.model.UserModel;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.model.WorkPlanExcutionState;
import com.smartflow.util.StringUtil;

@Service
public class RemindsAndAssignmentsCalendarServiceImpl implements RemindsAndAssignmentsCalendarService {
	@Autowired
	RemindsAndAssignmentsCalendarDao remindsAndAssignmentsCalendardao;
	@Autowired
	EditAssignmentsListDao editAssignmentsListDao;
	@Autowired
	AddAssignmentsListDao addAssignmentsListDao;
	@Override
	public List<CalendarDataDTO> getAssignmentsCalendar() {
		return remindsAndAssignmentsCalendardao.getAssignmentsCalendar();
	}
	
	@Override
	public Integer getRoleGroupIdByWorkPlanId(Integer workPlanId) {
		return remindsAndAssignmentsCalendardao.getRoleGroupIdByWorkPlanId(workPlanId);
	}
	@Transactional
	@Override
	public void addWorkOrderItemByWorkItem(WorkOrderItem workOrderItem) {
		remindsAndAssignmentsCalendardao.addWorkOrderItemByWorkItem(workOrderItem);
	}
	@Transactional
	@Override
	public void addWorkPlanExcutionState(WorkPlanExcutionState workPlanExcutionState) {
		remindsAndAssignmentsCalendardao.addWorkPlanExcutionState(workPlanExcutionState);
	}
	@Override
	public List<Integer> getWorkOrderIdFromTPMWorkPlan_ReminderIdByWorkItemId(List<Integer> workItemId) {
		return remindsAndAssignmentsCalendardao.getWorkOrderIdFromTPMWorkPlan_ReminderIdByWorkItemId(workItemId);
	}
	@Override
	public Integer getFacilityIdByWorkItemId(List<Integer> workItemId) {
		return remindsAndAssignmentsCalendardao.getFacilityIdByWorkItemId(workItemId);
	}
	@Override
	public List<WorkItem> getWorkItemByWorkItemIdList(List<Integer> workItemIdList) {		
		return remindsAndAssignmentsCalendardao.getWorkItemByWorkItemIdList(workItemIdList);
	}
	@Override
	public void addTPMWorkPlan_Reminder(TPMWorkPlan_Reminder tpmWorkPlan_Reminder) {
		remindsAndAssignmentsCalendardao.addTPMWorkPlan_Reminder(tpmWorkPlan_Reminder);
	}
	@Override
	public List<Integer> getWorkPlanIdByWorkItemIdList(List<Integer> workItemIdList) {
		return remindsAndAssignmentsCalendardao.getWorkPlanIdByWorkItemIdList(workItemIdList);
	}
	@Override
	public List<Integer> getReminderIdByWorkPlanId(List<Integer> workPlanIdList) {
		return remindsAndAssignmentsCalendardao.getReminderIdByWorkPlanId(workPlanIdList);
	}
	@Override
	public List<TPMWorkPlan_Reminder> getTPMWorkPlan_ReminderByWorkPlanId(List<Integer> workPlanIdList) {
		return remindsAndAssignmentsCalendardao.getTPMWorkPlan_ReminderByWorkPlanId(workPlanIdList);
	}
	@Transactional
	@Override
	public void addWorkOrderAndWorkOrderItemAndWorkPlanExcutionState(
			ConfirmUnifiedArrangementsDTO confirmUnifiedArrangementsDTO) {
		try{
			Integer facilityId = this.getFacilityIdByWorkItemId(confirmUnifiedArrangementsDTO.getWorkItemId());
			WorkOrder workOrder = new WorkOrder();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			workOrder.setName(sdf.format(new Date()));
			workOrder.setWorkType(null);
			FacilityModel facility = editAssignmentsListDao.getFacilityByFacilityId(facilityId);
			workOrder.setFacility(facility);
	//		workOrder.setUser(user);
			workOrder.setState(1);
			Role role = addAssignmentsListDao.getRoleByRoleId(confirmUnifiedArrangementsDTO.getRoleGroupId());
			workOrder.setRole(role);
			workOrder.setCreatorId(confirmUnifiedArrangementsDTO.getUserId());
			workOrder.setCreationDateTime(StringUtil.formatDate(confirmUnifiedArrangementsDTO.getArrangementDate()));
			workOrder.setEditorId(confirmUnifiedArrangementsDTO.getUserId());
			workOrder.setEditDateTime(new Date());
			workOrder.setStatus(1);
			remindsAndAssignmentsCalendardao.addWorkOrder(workOrder);
			List<WorkItem> workItemList = remindsAndAssignmentsCalendardao.getWorkItemByWorkItemIdList(confirmUnifiedArrangementsDTO.getWorkItemId());		
			if(workItemList != null && !workItemList.isEmpty())	{
				for (WorkItem workItem : workItemList) {
					WorkOrderItem workOrderItem = new WorkOrderItem();
					workOrderItem.setWorkOrder(workOrder);
					workOrderItem.setRole(workItem.getRole());
					//workOrderItem.setUser();
					workOrderItem.setMaintenanceItemName(workItem.getMaintenanceItem() == null ? null : workItem.getMaintenanceItem().getName());
					workOrderItem.setWorkItemName(workItem.getName());
					workOrderItem.setDesignator(workItem.getDesignator());
					workOrderItem.setMaterialNumber(workItem.getMaterial() == null ? null : workItem.getMaterial().getMaterialNumber());
					workOrderItem.setGuideDocURI(workItem.getGuideDocURI());
					workOrderItem.setLabourTimeSec(workItem.getWorkDurationSec());
					if(StringUtil.IsNotBlank(confirmUnifiedArrangementsDTO.getArrangementDate())){
						Date startDateTime = StringUtil.formatDate(confirmUnifiedArrangementsDTO.getArrangementDate());
						long endDateTime = startDateTime.getTime() + workItem.getWorkDurationSec() * 1000;
//							if(workItem.getWorkDurationSec() != 0){
							workOrderItem.setEstimatedStartTime(startDateTime);
							workOrderItem.setEstimatedEndTime(new Date(endDateTime));
//							}
					}
					workOrderItem.setFinalResult(null);
					workOrderItem.setMeasurementType(workItem.getMeasurementType());
					workOrderItem.setUSL(workItem.getUSL());
					workOrderItem.setLSL(workItem.getLSL());
					workOrderItem.setNormalValue(workItem.getNormalValue());
					workOrderItem.setStatus(1);
					remindsAndAssignmentsCalendardao.addWorkOrderItemByWorkItem(workOrderItem);
				}
			}
			List<Integer> workPlanIdList = remindsAndAssignmentsCalendardao.getWorkPlanIdByWorkItemIdList(confirmUnifiedArrangementsDTO.getWorkItemId());
			if(workPlanIdList != null && !workPlanIdList.isEmpty()){
				List<TPMWorkPlan_Reminder> tPMWorkPlan_ReminderIdList = remindsAndAssignmentsCalendardao.getTPMWorkPlan_ReminderByWorkPlanId(workPlanIdList);
				if(tPMWorkPlan_ReminderIdList != null && !tPMWorkPlan_ReminderIdList.isEmpty()){
					for (TPMWorkPlan_Reminder tPMWorkPlan_Reminder : tPMWorkPlan_ReminderIdList) {
						WorkPlanExcutionState workPlanExcutionState = new WorkPlanExcutionState();
						workPlanExcutionState.setWorkOrder(workOrder);
						workPlanExcutionState.setTPMWorkPlan_Reminder(tPMWorkPlan_Reminder);
						workPlanExcutionState.setState(2);
						workPlanExcutionState.setCreationDateTime(StringUtil.formatDate(confirmUnifiedArrangementsDTO.getArrangementDate()));
						workPlanExcutionState.setStateChangeDateTime(new Date());
						remindsAndAssignmentsCalendardao.addWorkPlanExcutionState(workPlanExcutionState);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public String getCalendarStateByTPMWorkPlanReminderId(Integer tpmWorkPlanReminderId) {
		return remindsAndAssignmentsCalendardao.getCalendarStateByTPMWorkPlanReminderId(tpmWorkPlanReminderId);
	}



}
