package com.smartflow.dao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.smartflow.service.StationService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.smartflow.dto.AssignmentsListOutputDTO;
import com.smartflow.dto.GetAssignmentsListConditionDTO;
import com.smartflow.dto.MaintenanceContentOutputDTO;
import com.smartflow.dto.WorkAssignmentRecordOutputDTO;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.UserModel;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.model.WorkPlan;
import com.smartflow.util.PropertyUtil;
import com.smartflow.util.StringUtil;
import com.smartflow.util.StringUtil.User;
@Repository
public class AssignmentsListDaoImpl implements AssignmentsListDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplate;
	@Autowired
	MyRoleGroupTaskListDao myRoleGroupTaskListDao;
	@Autowired
	StationService stationService;

	@Override
	public Integer getTotalCountWorkOrderListByCondition(
			GetAssignmentsListConditionDTO getAssignmentsListConditionDTO) {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from WorkOrder wo where wo.state != -1 ";
		try{
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getStartDateTime()) && StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getEndDateTime())){
				hql += " and wo.creationDateTime between :StartDateTime and :EndDateTime ";
			}
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getTargetFacilityName())){
				hql += " and facility.name = '"+getAssignmentsListConditionDTO.getTargetFacilityName()+"' ";
			}
			if(getAssignmentsListConditionDTO.getPeriodicType() != null){
				hql += " and WorkType = "+getAssignmentsListConditionDTO.getPeriodicType();
			}
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getPersonInCharge())){
				hql += " and user.userName = '"+getAssignmentsListConditionDTO.getPersonInCharge()+"' ";
			}
//			if(getAssignmentsListConditionDTO.getDisplayCompletedItem() != null && getAssignmentsListConditionDTO.getDisplayCompletedItem() == true){
//				hql += " and Status = 5 ";				
//			}else{
//				hql += " and Status != 5 ";
//			}
			if(getAssignmentsListConditionDTO.getState() != null && !getAssignmentsListConditionDTO.getState().isEmpty()){				
				hql += " and status in (:status) ";
			}
			Query query = session.createQuery(hql);
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getStartDateTime()) && StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getEndDateTime())){
				query.setDate("StartDateTime", StringUtil.formatDateTime(getAssignmentsListConditionDTO.getStartDateTime()));
				query.setDate("EndDateTime", StringUtil.formatDateTime(getAssignmentsListConditionDTO.getEndDateTime()));
			}
			if(getAssignmentsListConditionDTO.getState() != null && !getAssignmentsListConditionDTO.getState().isEmpty()){
				query.setParameterList("status", getAssignmentsListConditionDTO.getState());
			}
			return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public List<AssignmentsListOutputDTO> getWorkOrderListByCondition(GetAssignmentsListConditionDTO getAssignmentsListConditionDTO) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkOrder wo where wo.state != -1 ";
		try{
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getAssignNumber())){
				hql += " and wo.name = '"+getAssignmentsListConditionDTO.getAssignNumber()+"'";
			}
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getStartDateTime()) && StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getEndDateTime())){
				hql += " and wo.creationDateTime between :StartDateTime and :EndDateTime ";
			}
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getTargetFacilityName())){
				hql += " and facility.name like '%"+getAssignmentsListConditionDTO.getTargetFacilityName()+"%' ";
			}
			if(getAssignmentsListConditionDTO.getPeriodicType() != null){
				hql += " and WorkType = "+getAssignmentsListConditionDTO.getPeriodicType();
			}
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getPersonInCharge())){
				hql += " and user.userName like '%"+getAssignmentsListConditionDTO.getPersonInCharge()+"%'";
			}
//			if(getAssignmentsListConditionDTO.getDisplayCompletedItem() != null && getAssignmentsListConditionDTO.getDisplayCompletedItem() == true){
//				hql += " and Status = 5 ";				
//			}else{
//				hql += " and Status != 5 ";
//			}
			if(getAssignmentsListConditionDTO.getState() != null && !getAssignmentsListConditionDTO.getState().isEmpty()){				
				hql += " and status in (:status) ";
			}
			Query query = session.createQuery(hql);
			if(StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getStartDateTime()) && StringUtil.IsNotBlank(getAssignmentsListConditionDTO.getEndDateTime())){
				query.setDate("StartDateTime", StringUtil.formatDateTime(getAssignmentsListConditionDTO.getStartDateTime()));
				query.setDate("EndDateTime", StringUtil.formatDateTime(getAssignmentsListConditionDTO.getEndDateTime()));
			}
			if(getAssignmentsListConditionDTO.getState() != null && !getAssignmentsListConditionDTO.getState().isEmpty()){
				query.setParameterList("status", getAssignmentsListConditionDTO.getState());
			}
			query.setFirstResult((getAssignmentsListConditionDTO.getPageIndex()-1)*getAssignmentsListConditionDTO.getPageSize());
			query.setMaxResults(getAssignmentsListConditionDTO.getPageSize());
			List<AssignmentsListOutputDTO> assignmentsListOutputDTOList = new ArrayList<>();
			List<WorkOrder> workOrderList = query.list();
			if(workOrderList != null && !workOrderList.isEmpty()){
				for (WorkOrder workOrder : workOrderList) {
					AssignmentsListOutputDTO assignmentsListOutputDTO = new AssignmentsListOutputDTO();
					assignmentsListOutputDTO.setWorkOrderId(workOrder.getId());
					assignmentsListOutputDTO.setAssignNumber(workOrder.getName());
					assignmentsListOutputDTO.setName(workOrder.getName());
					assignmentsListOutputDTO.setVersionNumber(1);
					assignmentsListOutputDTO.setPeriodicType(workOrder.getWorkType() == null ? null : PropertyUtil.getPeriodicTypeNameByTypeId(workOrder.getWorkType()));
					assignmentsListOutputDTO.setState(workOrder.getStatus() == null ? null : PropertyUtil.getStatusName(workOrder.getStatus()));
					List<Integer> facilityIdList = Arrays.stream(workOrder.getFacilityId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
					assignmentsListOutputDTO.setTargetFacilityName(stationService.getFacilityNameByFacilityIdList(facilityIdList));
//					if(workOrder.getUser() != null){
//						List<String> roleNameList = myRoleGroupTaskListDao.getRoleNameByUserId(workOrder.getUser() == null ? null : workOrder.getUser().getId());
//						if(roleNameList != null){
//							assignmentsListOutputDTO.setRoleInCharge(StringUtils.collectionToDelimitedString(roleNameList, ","));
//						}
//					}
					assignmentsListOutputDTO.setRoleInCharge(workOrder.getRole() == null ? null : workOrder.getRole().getRoleName());
					assignmentsListOutputDTO.setLastUpdateDate(workOrder.getEditDateTime());
					assignmentsListOutputDTOList.add(assignmentsListOutputDTO);
				}
			}
			return assignmentsListOutputDTOList;
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<>();
		}finally{
			session.close();
		}
	}
	
	@Override
	public void updateWorkOrderItemByWorkOrderId(Integer workOrderId) {
		Session session = sessionFactory.openSession();
		String hql = "update WorkOrderItem set status = -1 where workOrder.id = "+workOrderId;
		try{
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	@Override
	public void updateWorkOrderByWorkOrderId(Integer workOrderId) {
		Session session = sessionFactory.openSession();
		String hql = "update WorkOrder set State = -1,EditDateTime = GETDATE() where Id = "+workOrderId;
		try{
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	@Override
	public List<MaintenanceContentOutputDTO> getWorkOrderItemByWorkOrderId(Integer workOrderId) {
		String hql = "from WorkOrderItem where workOrder.id = "+workOrderId+" and status != -1";
		Session session = sessionFactory.openSession();
		try{
			Query query = session.createQuery(hql);
			List<WorkOrderItem> workOrderItemList = query.list();
			List<MaintenanceContentOutputDTO> maintenanceContentOutputDTOList = new ArrayList<>();
			if(workOrderItemList != null && !workOrderItemList.isEmpty()){
				for (WorkOrderItem workOrderItem : workOrderItemList) {
					MaintenanceContentOutputDTO maintenanceContentOutputDTO = new MaintenanceContentOutputDTO();
					maintenanceContentOutputDTO.setWorkItemId(workOrderItem.getId());
					maintenanceContentOutputDTO.setItemName(workOrderItem.getWorkItemName());
					maintenanceContentOutputDTO.setPosition(workOrderItem.getDesignator());
					maintenanceContentOutputDTO.setMaterialNumber(workOrderItem.getMaterialNumber());
					maintenanceContentOutputDTO.setMaintenanceItemName(workOrderItem.getMaintenanceItemName());
					maintenanceContentOutputDTO.setWorkLength(workOrderItem.getLabourTimeSec() == null ? null : new BigDecimal(workOrderItem.getLabourTimeSec()/60));
//					UserModel user = workOrderItem.getUser();
//					String roleName = null;
//					if(user != null){
//						List<String> roleNameList = myRoleGroupTaskListDao.getRoleNameByUserId(user.getId());
//						if(roleNameList != null && !roleNameList.isEmpty()){
//							roleName = StringUtils.collectionToDelimitedString(roleNameList, ",");
//						}
//					}
					maintenanceContentOutputDTO.setRoleInCharge(workOrderItem.getRole() == null ? null : workOrderItem.getRole().getRoleName());
					maintenanceContentOutputDTO.setMeasurementCategory(workOrderItem.getMeasurementType() == null ? null : PropertyUtil.getMeasurementCategory(workOrderItem.getMeasurementType()));
					maintenanceContentOutputDTO.setMeasurementStandardUpperLimit(workOrderItem.getUSL());
					maintenanceContentOutputDTO.setMeasurementStandardMiddleValue(workOrderItem.getNormalValue());
					maintenanceContentOutputDTO.setMeasurementStandardLowerLimit(workOrderItem.getLSL());
					maintenanceContentOutputDTO.setDescriptionDocument(workOrderItem.getGuideDocURI());
					maintenanceContentOutputDTOList.add(maintenanceContentOutputDTO);
				}
				return maintenanceContentOutputDTOList;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public List<WorkAssignmentRecordOutputDTO> getWorkAssignmentRecordByWorkItemId(Integer workOrderItemId) {
		try{
			WorkOrderItem workOrderItem = hibernateTemplate.get(WorkOrderItem.class, workOrderItemId);
			List<WorkAssignmentRecordOutputDTO> workAssignmentRecordOutputDTOList =  new ArrayList<>();
			if(workOrderItem != null){
				WorkAssignmentRecordOutputDTO workAssignmentRecordOutputDTO = new WorkAssignmentRecordOutputDTO();
				workAssignmentRecordOutputDTO.setWorkItemId(workOrderItem.getId());
				workAssignmentRecordOutputDTO.setAssignNumber(workOrderItem.getWorkOrder().getName());
				workAssignmentRecordOutputDTO.setItemName(workOrderItem.getWorkItemName());
				workAssignmentRecordOutputDTO.setPosition(workOrderItem.getDesignator());
				workAssignmentRecordOutputDTO.setDateTime(workOrderItem.getEstimatedStartTime());
				workAssignmentRecordOutputDTO.setAssigner(workOrderItem.getWorkOrder().getUser() == null ? null : workOrderItem.getWorkOrder().getUser().getUserName());
//				UserModel user = workOrderItem.getUser();
//				String userName = null;
//				String roleName = null;
//				if(user != null){
//					List<String> roleNameList = myRoleGroupTaskListDao.getRoleNameByUserId(user.getId());
//					if(roleNameList != null && !roleNameList.isEmpty()){
//						roleName = StringUtils.collectionToDelimitedString(roleNameList, ",");
//					}
//					userName = user.getUserName();
//				}
				workAssignmentRecordOutputDTO.setAssignmentToRoles(workOrderItem.getRole() == null ? null : workOrderItem.getRole().getRoleName());
				workAssignmentRecordOutputDTO.setAllottee(workOrderItem.getUser() == null ? null : workOrderItem.getUser().getUserName());
				workAssignmentRecordOutputDTOList.add(workAssignmentRecordOutputDTO);		
			}		
			return workAssignmentRecordOutputDTOList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<String> getWorkOrderLeaderListByFacilityId(Integer facilityId) {
		Session session = sessionFactory.openSession();
		String hql = "select distinct user.userName from WorkOrder where facility.id = "+facilityId;
		try{
			Query query = session.createQuery(hql);
			return query.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}

}
