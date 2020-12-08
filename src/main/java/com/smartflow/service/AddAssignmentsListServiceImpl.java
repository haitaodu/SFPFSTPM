package com.smartflow.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartflow.dao.AddAssignmentsListDao;
import com.smartflow.dao.RemindsAndAssignmentsCalendarDao;
import com.smartflow.dto.AddAssignListInputDTO;
import com.smartflow.dto.AddMaintenanceStepsInputDTO;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.Material;
import com.smartflow.model.Role;
import com.smartflow.model.UserModel;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.util.PropertyUtil;
import com.smartflow.util.StringUtil;
@Service
public class AddAssignmentsListServiceImpl implements AddAssignmentsListService {
	@Autowired
	AddAssignmentsListDao addAssignmentsListDao;

	@Autowired
	RemindsAndAssignmentsCalendarDao remindsAndAssignmentsCalendarDao;

	@Override
	public List<Map<String, Object>> getMaintenanceItemList() {
		return addAssignmentsListDao.getMaintenanceItemList();
	}

	@Override
	public Integer getBOMHeadIdByFacilityId(Integer facilityId) {
		return addAssignmentsListDao.getBOMHeadIdByFacilityId(facilityId);
	}

	@Transactional
	@Override
	public void addWorkOrderAndMaintenanceSteps(AddAssignListInputDTO addAssignListInputDTO) {
		try{
			WorkOrder workOrder = new WorkOrder();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sdfFormatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			workOrder.setName(sdf.format(new Date()));
			workOrder.setWorkType(null);
//			FacilityModel facility = new FacilityModel();
//			facility.setId(addAssignListInputDTO.getTargetFacilityId());
//			workOrder.setFacility(facility);
			UserModel user = this.getUserByUserId(addAssignListInputDTO.getUserId());
			workOrder.setUser(user);
			workOrder.setState(1);
			Role role = this.getRoleByRoleId(addAssignListInputDTO.getRoleInChargeId());
			workOrder.setRole(role);
			workOrder.setCreatorId(addAssignListInputDTO.getUserId());
			workOrder.setCreationDateTime(sdfFormatDate.parse(addAssignListInputDTO.getDateTime()));
			workOrder.setEditorId(addAssignListInputDTO.getUserId());
			workOrder.setEditDateTime(new Date());
			workOrder.setStatus(1);
			remindsAndAssignmentsCalendarDao.addWorkOrder(workOrder);
			if(addAssignListInputDTO.getMaintenanceStepsList() != null && !addAssignListInputDTO.getMaintenanceStepsList().isEmpty()){
				for (AddMaintenanceStepsInputDTO maintenanceSteps : addAssignListInputDTO.getMaintenanceStepsList()) {
					WorkOrderItem workOrderItem = new WorkOrderItem();
					if(addAssignListInputDTO.getTargetFacilityId() != null){
						workOrderItem.setWorkOrder(workOrder);
						workOrderItem.setWorkItemName(maintenanceSteps.getItemName());
						workOrderItem.setDesignator(maintenanceSteps.getPosition());
						if(StringUtil.IsNotBlank(maintenanceSteps.getMaterialNumber())){
							workOrderItem.setMaterialNumber(maintenanceSteps.getMaterialNumber());
						}
						workOrderItem.setMaintenanceItemName(maintenanceSteps.getMaintenanceItemId());
						Date startDateTime;
						if(StringUtil.IsNotBlank(maintenanceSteps.getEstimatedStartTime())){
							startDateTime = sdfFormatDate.parse(maintenanceSteps.getEstimatedStartTime());	
						}else{
							startDateTime = sdfFormatDate.parse(addAssignListInputDTO.getDateTime());
						}
						workOrderItem.setEstimatedStartTime(startDateTime);
						BigDecimal workLength = maintenanceSteps.getWorkLength();
						if(workLength != null){
							long labourTimeSec = workLength.multiply(new BigDecimal(60*60)).longValue();//秒
							workOrderItem.setLabourTimeSec(labourTimeSec);
							long endDateTime = startDateTime.getTime() + labourTimeSec * 1000;
							workOrderItem.setEstimatedEndTime(new Date(endDateTime));
						}	
						Role assignedToRole = this.getRoleIdByRoleName(maintenanceSteps.getRoleInCharge());
						workOrderItem.setRole(assignedToRole);
						UserModel assignedToWorker = this.getUserByUserName(maintenanceSteps.getStaff());//getUserByUserId(maintenanceSteps.getStaffId());
						workOrderItem.setUser(assignedToWorker);
						workOrderItem.setMeasurementType(maintenanceSteps.getMeasurementCategoryId() == null ? null : PropertyUtil.getMeasurementCategoryId(maintenanceSteps.getMeasurementCategoryId()));
						workOrderItem.setUSL(maintenanceSteps.getMeasurementStandardUpperLimit());
						workOrderItem.setLSL(maintenanceSteps.getMeasurementStandardLowerLimit());
						workOrderItem.setNormalValue(maintenanceSteps.getMeasurementStandardMiddleValue());
						workOrderItem.setStatus(1);
						workOrderItem.setGuideDocURI(maintenanceSteps.getDescriptionDocument());//文档路径
						remindsAndAssignmentsCalendarDao.addWorkOrderItemByWorkItem(workOrderItem);
					}
				}				
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	//	@Override
	//	public List<Integer> getWorkPlanIdByFacilityId(Integer facilityId) {
	//		return addAssignmentsListDao.getWorkPlanIdByFacilityId(facilityId);
	//	}

	@Override
	public Integer getMaterialIdByMaterialNumber(String materialNumber) {
		return addAssignmentsListDao.getMaterialIdByMaterialNumber(materialNumber);
	}


	//	@Override
	//	public Material getMaterialByMaterialNumber(String materialNumber) {
	//		return addAssignmentsListDao.getMaterialByMaterialNumber(materialNumber);
	//	}

	@Override
	public Integer getMaintenanceItemIdByMaintenanceItem(String maintenanceItemName) {
		return addAssignmentsListDao.getMaintenanceItemIdByMaintenanceItem(maintenanceItemName);
	}

	@Override
	public Role getRoleIdByRoleName(String roleName) {
		return addAssignmentsListDao.getRoleIdByRoleName(roleName);
	}

	@Override
	public Role getRoleByRoleId(Integer roleId) {
		return addAssignmentsListDao.getRoleByRoleId(roleId);
	}

	@Override
	public UserModel getUserByUserId(Integer userId) {
		return addAssignmentsListDao.getUserByUserId(userId);
	}

	@Override
	public UserModel getUserByUserName(String userName) {
		return addAssignmentsListDao.getUserByUserName(userName);
	}
}
