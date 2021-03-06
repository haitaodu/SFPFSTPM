package com.smartflow.dao;

import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.smartflow.dto.AddMaintenanceRecordInputDTO;
import com.smartflow.dto.AddMeasurementRecordDTO;
import com.smartflow.dto.RoleGroupTaskListInputDTO.TaskIdList;
import com.smartflow.dto.RoleGroupTaskListOutputRowDTO;
import com.smartflow.dto.TaskDetailOutputDTO;
import com.smartflow.dto.TaskListInputDTO;
import com.smartflow.dto.TaskListOutputDTO;
import com.smartflow.model.MaintenanceItem;
import com.smartflow.model.Material;
import com.smartflow.model.MeasurementDataRecord;
import com.smartflow.model.PMActionRecord;
import com.smartflow.model.Role;
import com.smartflow.model.UserModel;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.model.WorkPlan;
import com.smartflow.util.PropertyUtil;
import com.smartflow.util.StringUtil;
@Repository
public class MyTaskDaoImpl implements MyTaskDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplate;
	@Autowired
	MyRoleGroupTaskListDao myRoleGroupTaskListDao;
	@Autowired
	StationDao stationDao;
	@Override
	public Integer getTotalCountRoleGroupTaskListByCondition(TaskListInputDTO taskListInputDTO) {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from WorkOrderItem item where 1=1 ";
		try{
			if(StringUtil.IsNotBlank(taskListInputDTO.getStartDateTime())){
				hql += "and EstimatedStartTime >= '"+taskListInputDTO.getStartDateTime()+"'";
			}
			if(StringUtil.IsNotBlank(taskListInputDTO.getEndDateTime())){
				hql += " and EstimatedEndTime <= '"+StringUtil.parseEndDateToEndDateTime(taskListInputDTO.getEndDateTime())+"'";
			}
			if(taskListInputDTO.getState() != null && !taskListInputDTO.getState().isEmpty()){				
				hql += " and item.status in (:status) ";
			}
//			if(taskListInputDTO.getFacilityId() != null && taskListInputDTO.getFacilityId() != 0){
//				hql += " and workOrder.facility = "+taskListInputDTO.getFacilityId();
//			}
			if(taskListInputDTO.getUserId() != null) {
				hql += " and user.id = "+taskListInputDTO.getUserId();
			}
			Query query = session.createQuery(hql);
			if(taskListInputDTO.getState() != null && !taskListInputDTO.getState().isEmpty()){
//				List<Integer> stateList = new ArrayList<>();
//				for (Map<String,Object> state : taskListInputDTO.getState()) {
//					stateList.add(Integer.parseInt(state.get("key").toString()));
//				}
				query.setParameterList("status", taskListInputDTO.getState());
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
	public List<TaskListOutputDTO> getRoleGroupTaskListByCondition(TaskListInputDTO taskListInputDTO) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkOrderItem item where 1=1 ";
		try{
//			List<String> roleNameList = myRoleGroupTaskListDao.getRoleNameByUserId(taskListInputDTO.getUserId());
//			String roleName = StringUtils.collectionToDelimitedString(roleNameList, ",");
			if(StringUtil.IsNotBlank(taskListInputDTO.getStartDateTime())){
				hql += "and EstimatedStartTime >= '"+taskListInputDTO.getStartDateTime()+"'";
			}
			if(StringUtil.IsNotBlank(taskListInputDTO.getEndDateTime())){
				hql += " and EstimatedEndTime <= '"+StringUtil.parseEndDateToEndDateTime(taskListInputDTO.getEndDateTime())+"'";
			}
			if(taskListInputDTO.getState() != null && !taskListInputDTO.getState().isEmpty()){				
				hql += " and item.status in (:status) ";
			}
//			if(taskListInputDTO.getFacilityId() != null && taskListInputDTO.getFacilityId() != 0){
//				hql += " and workOrder.facility = "+taskListInputDTO.getFacilityId();
//			}
			if(taskListInputDTO.getUserId() != null) {
				hql += " and user.id = "+taskListInputDTO.getUserId();
			}
			Query query = session.createQuery(hql);
			if(taskListInputDTO.getState() != null && !taskListInputDTO.getState().isEmpty()){
//				List<Integer> stateList = new ArrayList<>();
//				for (Map<String,Object> state : taskListInputDTO.getState()) {
//					stateList.add(Integer.parseInt(state.get("key").toString()));
//				}
				query.setParameterList("status", taskListInputDTO.getState());
			}
			query.setFirstResult((taskListInputDTO.getPageIndex()-1)*taskListInputDTO.getPageSize());
			query.setMaxResults(taskListInputDTO.getPageSize());
			List<WorkOrderItem> itemList = query.list();
			List<TaskListOutputDTO> taskList = new ArrayList<>();
			if(itemList != null && !itemList.isEmpty()){
				for (WorkOrderItem workOrderItem : itemList) {
					TaskListOutputDTO taskDTO = new TaskListOutputDTO();
					taskDTO.setTaskId(workOrderItem.getId());
					taskDTO.setState(PropertyUtil.getStatusName(workOrderItem.getStatus()));
					WorkOrder workOrder = workOrderItem.getWorkOrder();
					if(workOrder != null){	
						taskDTO.setCreateDateTime(workOrder.getCreationDateTime());
						taskDTO.setAssignmentTaskSheet(workOrder.getName());
						List<Integer> facilityIdList = Arrays.stream(workOrder.getFacilityId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
						taskDTO.setTargetFacility(stationDao.getFacilityNameByFacilityIdList(facilityIdList));
					}			
					taskDTO.setItemName(workOrderItem.getWorkItemName());
//					taskDTO.setPosition(workOrderItem.getDesignator());
					taskDTO.setRoleInCharge(workOrderItem.getRole() == null ? null : workOrderItem.getRole().getRoleName());
					taskDTO.setStaff(workOrderItem.getUser().getUserName());
//					taskDTO.setPredictTaskLength(workOrderItem.getLabourTimeSec() == null ? null : StringUtil.decimalFormatMinuteToHour(workOrderItem.getLabourTimeSec()/60/60.0));
					taskList.add(taskDTO);
				}
				return taskList;
			}else {
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
	public List<TaskListOutputDTO> getRoleGroupTaskListByTaskIdList(TaskIdList taskIdList) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkOrderItem where id in (:taskIdList)";
		try{
			Query query = session.createQuery(hql);
			query.setParameterList("taskIdList", taskIdList.getTaskIdList());
			List<WorkOrderItem> WorkOrderItemList = query.list();
			List<TaskListOutputDTO> taskList = new ArrayList<>();
			if(WorkOrderItemList != null && !WorkOrderItemList.isEmpty()){
				for (WorkOrderItem workOrderItem : WorkOrderItemList) {
					TaskListOutputDTO taskDTO = new TaskListOutputDTO();
					taskDTO.setTaskId(workOrderItem.getId());
					taskDTO.setState(PropertyUtil.getStatusName(workOrderItem.getStatus()));
					WorkOrder workOrder = workOrderItem.getWorkOrder();
					if(workOrder != null){	
						taskDTO.setCreateDateTime(workOrder.getCreationDateTime());
						taskDTO.setAssignmentTaskSheet(workOrder.getName());
						List<Integer> facilityIdList = Arrays.stream(workOrder.getFacilityId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
						taskDTO.setTargetFacility(stationDao.getFacilityNameByFacilityIdList(facilityIdList));
					}			
					taskDTO.setItemName(workOrderItem.getWorkItemName());
//					taskDTO.setPosition(workOrderItem.getDesignator());
					UserModel user = workOrderItem.getUser();
					String roleName = null;
					if(user != null){
						List<String> roleNameList = myRoleGroupTaskListDao.getRoleNameByUserId(workOrderItem.getUser().getId());
						roleName = StringUtils.collectionToDelimitedString(roleNameList, ",");
					}
					taskDTO.setRoleInCharge(roleName);
					taskDTO.setStaff(workOrderItem.getUser().getUserName());
//					taskDTO.setPredictTaskLength(StringUtil.getHourApart(workOrderItem.getEstimatedStartTime(), workOrderItem.getEstimatedEndTime()));
					taskList.add(taskDTO);
				}
				return taskList;
			}else {
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
	public List<TaskListOutputDTO> getRoleGroupTaskListByWorkOrderIdList(TaskIdList workOrderIdList) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkOrder where id in (:workOrderIdList)";
		try{
			Query query = session.createQuery(hql);
			query.setParameterList("workOrderIdList", workOrderIdList.getTaskIdList());
			List<WorkOrder> workOrderList = query.list();
			List<TaskListOutputDTO> taskList = new ArrayList<>();
			if(!CollectionUtils.isEmpty(workOrderList)){
				for (WorkOrder workOrder : workOrderList) {
					TaskListOutputDTO taskDTO = new TaskListOutputDTO();
					taskDTO.setTaskId(workOrder.getId());
					taskDTO.setState(PropertyUtil.getStatusName(workOrder.getStatus()));
					taskDTO.setCreateDateTime(workOrder.getCreationDateTime());
					taskDTO.setAssignmentTaskSheet(workOrder.getName());
					List<Integer> facilityIdList = Arrays.stream(workOrder.getFacilityId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
					taskDTO.setTargetFacility(stationDao.getFacilityNameByFacilityIdList(facilityIdList));
					taskDTO.setItemName(workOrder.getItemName());
//					UserModel user = workOrder.getUser();
//					String roleName = null;
//					if(user != null){
//						List<String> roleNameList = myRoleGroupTaskListDao.getRoleNameByUserId(workOrder.getUser().getId());
//						roleName = StringUtils.collectionToDelimitedString(roleNameList, ",");
//					}
//					taskDTO.setRoleInCharge(roleName);
					taskDTO.setRoleInCharge(workOrder.getRole().getRoleName());
					taskDTO.setStaff(workOrder.getUser().getUserName());
//					taskDTO.setPredictTaskLength(StringUtil.getHourApart(workOrderItem.getEstimatedStartTime(), workOrderItem.getEstimatedEndTime()));
					taskList.add(taskDTO);
				}
				return taskList;
			}else {
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
	public Integer getTotalCountWorkOrderListByCondition(TaskListInputDTO taskListInputDTO) {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from WorkOrder where 1=1 ";
		try{
			if(StringUtil.IsNotBlank(taskListInputDTO.getStartDateTime())){
				hql += "and CreationDateTime >= '"+taskListInputDTO.getStartDateTime()+"'";
			}
			if(StringUtil.IsNotBlank(taskListInputDTO.getEndDateTime())){
				hql += " and CreationDateTime <= '"+StringUtil.parseEndDateToEndDateTime(taskListInputDTO.getEndDateTime())+"'";
			}
			if(taskListInputDTO.getState() != null && !taskListInputDTO.getState().isEmpty()){
				hql += " and status in (:status) ";
			}
//			if(taskListInputDTO.getFacilityId() != null && taskListInputDTO.getFacilityId() != 0){
//				hql += " and workOrder.facility = "+taskListInputDTO.getFacilityId();
//			}
			if(taskListInputDTO.getUserId() != null) {
				hql += " and user.id = "+taskListInputDTO.getUserId();
			}
			if(!StringUtils.isEmpty(taskListInputDTO.getAssignmentTaskSheet())){
				hql += " and Name = :AssignmentTaskSheet";
			}
			if(!CollectionUtils.isEmpty(taskListInputDTO.getFacilityIdList())){
				hql += " and FacilityId = :facilityId ";
			}
			Query query = session.createQuery(hql);
			if(taskListInputDTO.getState() != null && !taskListInputDTO.getState().isEmpty()){
//				List<Integer> stateList = new ArrayList<>();
//				for (Map<String,Object> state : taskListInputDTO.getState()) {
//					stateList.add(Integer.parseInt(state.get("key").toString()));
//				}
				query.setParameterList("status", taskListInputDTO.getState());
			}
			if(!StringUtils.isEmpty(taskListInputDTO.getAssignmentTaskSheet())){
				query.setParameter("AssignmentTaskSheet", taskListInputDTO.getAssignmentTaskSheet());
			}
			if(!CollectionUtils.isEmpty(taskListInputDTO.getFacilityIdList())){
				query.setParameter("facilityId", CollectionUtils.isEmpty(taskListInputDTO.getFacilityIdList()) ? null : StringUtils.collectionToDelimitedString(taskListInputDTO.getFacilityIdList(), ","));
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
	public List<TaskListOutputDTO> getWorkOrderListByCondition(TaskListInputDTO taskListInputDTO) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkOrder where 1=1 ";
		try{
			if(StringUtil.IsNotBlank(taskListInputDTO.getStartDateTime())){
				hql += "and CreationDateTime >= '"+taskListInputDTO.getStartDateTime()+"'";
			}
			if(StringUtil.IsNotBlank(taskListInputDTO.getEndDateTime())){
				hql += " and CreationDateTime <= '"+StringUtil.parseEndDateToEndDateTime(taskListInputDTO.getEndDateTime())+"'";
			}
			if(taskListInputDTO.getState() != null && !taskListInputDTO.getState().isEmpty()){
				hql += " and status in (:status) ";
			}
//			if(taskListInputDTO.getFacilityId() != null && taskListInputDTO.getFacilityId() != 0){
//				hql += " and workOrder.facility = "+taskListInputDTO.getFacilityId();
//			}
			if(!StringUtils.isEmpty(taskListInputDTO.getAssignmentTaskSheet())){
				hql += " and Name = :AssignmentTaskSheet";
			}
			if(!CollectionUtils.isEmpty(taskListInputDTO.getFacilityIdList())){
				hql += "";
			}
			if(taskListInputDTO.getUserId() != null) {
				hql += " and user.id = "+taskListInputDTO.getUserId();
			}
			if(!CollectionUtils.isEmpty(taskListInputDTO.getFacilityIdList())){
				hql += " and FacilityId = :facilityId ";
			}
			Query query = session.createQuery(hql);
			if(taskListInputDTO.getState() != null && !taskListInputDTO.getState().isEmpty()){
//				List<Integer> stateList = new ArrayList<>();
//				for (Map<String,Object> state : taskListInputDTO.getState()) {
//					stateList.add(Integer.parseInt(state.get("key").toString()));
//				}
				query.setParameterList("status", taskListInputDTO.getState());
			}
			if(!StringUtils.isEmpty(taskListInputDTO.getAssignmentTaskSheet())){
				query.setParameter("AssignmentTaskSheet", taskListInputDTO.getAssignmentTaskSheet());
			}
			if(!CollectionUtils.isEmpty(taskListInputDTO.getFacilityIdList())){
				query.setParameter("facilityId", CollectionUtils.isEmpty(taskListInputDTO.getFacilityIdList()) ? null : StringUtils.collectionToDelimitedString(taskListInputDTO.getFacilityIdList(), ","));
			}
			query.setFirstResult((taskListInputDTO.getPageIndex()-1)*taskListInputDTO.getPageSize());
			query.setMaxResults(taskListInputDTO.getPageSize());
			List<WorkOrder> workOrderList = query.list();
			List<TaskListOutputDTO> taskList = new ArrayList<>();
			if(!CollectionUtils.isEmpty(workOrderList)){
				for (WorkOrder workOrder : workOrderList) {
					TaskListOutputDTO taskDTO = new TaskListOutputDTO();
					taskDTO.setTaskId(workOrder.getId());
					taskDTO.setState(PropertyUtil.getStatusName(workOrder.getStatus()));
					taskDTO.setCreateDateTime(workOrder.getCreationDateTime());
					taskDTO.setAssignmentTaskSheet(workOrder.getName());
//					taskDTO.setTargetFacility(workOrder.getFacility() == null ? null : workOrder.getFacility().getName());
					List<Integer> facilityIdList = Arrays.stream(workOrder.getFacilityId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
					taskDTO.setTargetFacility(stationDao.getFacilityNameByFacilityIdList(facilityIdList));
					taskDTO.setItemName(workOrder.getItemName());
					taskDTO.setRoleInCharge(workOrder.getRole() == null ? null : workOrder.getRole().getRoleName());
					taskDTO.setStaff(workOrder.getUser().getUserName());
					if(workOrder.getStatus() == 1 || workOrder.getStatus() == 2){//已分配状态的可以点完成
						taskDTO.setCompleteButtonFlag(true);
					}else if(workOrder.getStatus() == 3 || workOrder.getState() == 0){//已完成、已关闭的不可点完成
						taskDTO.setCompleteButtonFlag(false);
					}
					taskList.add(taskDTO);
				}
				return taskList;
			}else {
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
	public TaskDetailOutputDTO getTaskDetailByTaskId(Integer taskId) {
		Session session = sessionFactory.openSession();
		try{
			WorkOrderItem workOrderItem = hibernateTemplate.get(WorkOrderItem.class, taskId);
			TaskDetailOutputDTO taskDetail = new TaskDetailOutputDTO();
			if(workOrderItem != null){
				taskDetail.setTaskId(workOrderItem.getId());
				WorkOrder workOrder = workOrderItem.getWorkOrder();
				if(workOrder != null){
					List<Integer> facilityIdList = Arrays.stream(workOrder.getFacilityId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
					taskDetail.setTargetFacility(stationDao.getFacilityNameByFacilityIdList(facilityIdList));
				}			
				taskDetail.setItemName(workOrderItem.getWorkItemName());
				taskDetail.setPosition(workOrderItem.getDesignator());
				taskDetail.setAssignDateTime(workOrderItem.getWorkOrder().getCreationDateTime());
				taskDetail.setIsRequireMeasure(workOrderItem.getMeasurementType() == null ? false : true);
				taskDetail.setMaintenanceDocumentName(workOrderItem.getGuideDocURI());
				taskDetail.setMaintenanceDocumentPath(workOrderItem.getGuideDocURI());
				taskDetail.setState(PropertyUtil.getStatusName(workOrderItem.getStatus()));		
			}
			return taskDetail;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public List<Map<String, Object>> getFacilityList() {
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],Name label from core.facility where State = 1";
		try{
			Query query = session.createSQLQuery(sql);
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	@Override
	public List<Map<String, Object>> getUserList() {
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],UserName label from core.[User]";
		try{
			Query query = session.createSQLQuery(sql);
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}		
	}
	@Override
	public Integer getMaterialIdByMaterialNumber(String materialNumber) {
		Session session = sessionFactory.openSession();
		String hql = "select id from Material where materialNumber = "+materialNumber;
		try{
			Query query = session.createSQLQuery(hql);
			return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}		
	}	
	@Override
	public Integer getRoleIdByUserId(Integer userId) {
		Session session = sessionFactory.openSession();
		String hql = "select role.id from Roles_Users where user.id = "+userId;
		try{
			Query query = session.createQuery(hql);
			query.setFirstResult(0);
			return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public void addMaintenanceRecord(AddMaintenanceRecordInputDTO addMaintenanceRecordInputDTO,
			AddMeasurementRecordDTO addMeasurementRecordDTO) {
		WorkOrderItem workOrderItem = new WorkOrderItem();
		//添加MaintenanceItem表
		MaintenanceItem maintenanceItem = new MaintenanceItem();
		maintenanceItem.setName(workOrderItem.getMaintenanceItemName());
		maintenanceItem.setCategory(null);
		maintenanceItem.setCreatorId(null);//当前登陆用户id:UserId
		maintenanceItem.setCreationDateTime(new Date());
		maintenanceItem.setEditorId(null);
		maintenanceItem.setEditDateTime(new Date());
		maintenanceItem.setState(1);
		hibernateTemplate.save(maintenanceItem);

		WorkItem workItem = new WorkItem();
		workItem.setWorkPlan(null);
		workItem.setName(workOrderItem.getWorkItemName());
		workItem.setDesignator(workOrderItem.getDesignator());
		Material material = new Material();
		material.setId(this.getMaterialIdByMaterialNumber(workOrderItem.getMaterialNumber()));
		workItem.setMaterial(material);
		workItem.setMaintenanceItem(maintenanceItem);
		workItem.setWorkDurationSec(addMaintenanceRecordInputDTO.getActualUseTime().longValue());
		Role role = new Role();
		role.setId(this.getRoleIdByUserId(addMaintenanceRecordInputDTO.getStaffId()));
		workItem.setRole(role);
		workItem.setMeasurementType(addMeasurementRecordDTO.getProfileValue() == null ? 1:2);
		workItem.setUSL(addMeasurementRecordDTO.getMaxValue());
		workItem.setLSL(addMeasurementRecordDTO.getMinValue());
		workItem.setNormalValue(addMeasurementRecordDTO.getMidValue());
		workItem.setState(1);
		workItem.setGuideDocURI("");
		hibernateTemplate.save(workItem);
	}
	@Override
	public WorkOrderItem getWorkOrderItemById(Integer workOrderItemId) {		
		return hibernateTemplate.get(WorkOrderItem.class, workOrderItemId);
	}
	@Override
	public void addMaintenanceRecord(PMActionRecord pmActionRecord, MeasurementDataRecord measurementDataRecord) {
		//hibernateTemplate.save(pmActionRecord);
		measurementDataRecord.setPMActionRecord(pmActionRecord);
		hibernateTemplate.save(measurementDataRecord);
	}
}
