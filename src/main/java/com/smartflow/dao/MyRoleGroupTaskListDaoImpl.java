package com.smartflow.dao;

import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.smartflow.util.PropertyUtil;
import com.smartflow.util.StringUtil;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.smartflow.dto.AssignmentTaskInitOutputDTO.AssignmentTaskInitOutputRowDTO;
import com.smartflow.dto.RoleGroupTaskListInputDTO;
import com.smartflow.dto.RoleGroupTaskListOutputRowDTO;
import com.smartflow.model.Role;
import com.smartflow.model.Roles_Users;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
@Repository
public class MyRoleGroupTaskListDaoImpl implements MyRoleGroupTaskListDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplate;

	@Autowired
	StationDao stationDao;

	@Override
	public List<String> getVisitButtonListByUserId(Integer userId) {
		Session session = sessionFactory.openSession();
		String hql = "select visitBtn from Role where id in(select role.id from Roles_Users where user.id = "+userId+")";
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

	@Override
	public List<Map<String, Object>> getRoleGroupListByUserId(Integer userId) {
		Session session = sessionFactory.openSession();
		String sql = "select Id [key], RoleName label from core.Role where State = 1 and Id in(select RoleId from core.Roles_Users where UserId = "+userId+")";
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
	public List<Map<String, Object>> getRoleGroupList() {
		Session session = sessionFactory.openSession();
		String sql = "select Id [key], RoleName label from core.Role where State = 1";
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
	public Integer getTotalCountRoleGroupTaskListByCondition(RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO) {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from WorkOrderItem where 1=1 ";
		try{
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getStartDateTime())){
				hql += "and EstimatedStartTime >= :EstimatedStartTime";
			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getEndDateTime())){
				hql += " and EstimatedEndTime <= :EstimatedEndTime";
			}
			if(roleGroupTaskListConditionDTO.getState() != null && !roleGroupTaskListConditionDTO.getState().isEmpty()){
				hql += " and Status in (:Status) ";
			}
			if(CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getRoleGroupId())){
//			if(roleGroupTaskListConditionDTO.getRoleGroupId() != 0){
//				hql += " and role = "+roleGroupTaskListConditionDTO.getRoleGroupId();
				hql += " and role in (:roleIdList)";
			}
			Query query = session.createQuery(hql);			
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getStartDateTime())){
				query.setParameter("EstimatedStartTime", StringUtil.formatDate(roleGroupTaskListConditionDTO.getStartDateTime()));
			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getEndDateTime())){
				String endDateTime = StringUtil.parseEndDateToEndDateTime(roleGroupTaskListConditionDTO.getEndDateTime()); 
				query.setParameter("EstimatedEndTime", endDateTime);
//				Date endDateTime = StringUtil.formatDate(roleGroupTaskListConditionDTO.getEndDateTime()); 
//				if(roleGroupTaskListConditionDTO.getStartDateTime().equals(roleGroupTaskListConditionDTO.getEndDateTime())){
//					Calendar calendar = Calendar.getInstance();
//					calendar.setTime(endDateTime);
//					calendar.add(Calendar.DAY_OF_MONTH, 1);
//					query.setParameter("EstimatedEndTime", calendar.getTime());
//				}else{
//					query.setParameter("EstimatedEndTime", endDateTime);
//				}
			}
			if(roleGroupTaskListConditionDTO.getState() != null && !roleGroupTaskListConditionDTO.getState().isEmpty()){
				List<Integer> stateList = new ArrayList<>();
				for (Map<String,Object> state : roleGroupTaskListConditionDTO.getState()) {
					stateList.add(Integer.parseInt(state.get("key").toString()));
				}
				query.setParameterList("Status", stateList);
			}
			if(CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getRoleGroupId())){
				query.setParameterList("roleIdList", roleGroupTaskListConditionDTO.getRoleGroupId());
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
	public List<RoleGroupTaskListOutputRowDTO> getRoleGroupTaskListByCondition(
			RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkOrderItem where 1=1 ";
		List<RoleGroupTaskListOutputRowDTO> taskList = new ArrayList<>();
		try{	
//			String roleName = null;
//			if(roleGroupTaskListConditionDTO.getRoleGroupId() == 0){
//				List<String> roleNameList = this.getRoleNameByUserId(roleGroupTaskListConditionDTO.getUserId());
//				roleName = StringUtils.collectionToDelimitedString(roleNameList, ",");
//			}else{
//				roleName = this.getRoleNameByRoleId(roleGroupTaskListConditionDTO.getRoleGroupId());
//			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getStartDateTime())){
				hql += "and EstimatedStartTime >= :EstimatedStartTime";
			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getEndDateTime())){
				hql += " and EstimatedEndTime <= :EstimatedEndTime";
			}
			if(roleGroupTaskListConditionDTO.getState() != null && !roleGroupTaskListConditionDTO.getState().isEmpty()){
				hql += " and Status in (:Status) ";
			}
			if(CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getRoleGroupId())){
//			if(roleGroupTaskListConditionDTO.getRoleGroupId() != 0){
				hql += " and role in (:roleIdList)";
			}
			Query query = session.createQuery(hql);			
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getStartDateTime())){
				query.setParameter("EstimatedStartTime", StringUtil.formatDate(roleGroupTaskListConditionDTO.getStartDateTime()));
			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getEndDateTime())){
				String endDateTime = StringUtil.parseEndDateToEndDateTime(roleGroupTaskListConditionDTO.getEndDateTime()); 
				query.setParameter("EstimatedEndTime", endDateTime);
//				if(roleGroupTaskListConditionDTO.getStartDateTime().equals(roleGroupTaskListConditionDTO.getEndDateTime())){
//					Calendar calendar = Calendar.getInstance();
//					calendar.setTime(endDateTime);
//					calendar.add(Calendar.DAY_OF_MONTH, 1);
//					query.setParameter("EstimatedEndTime", calendar.getTime());
//				}else{
//					query.setParameter("EstimatedEndTime", endDateTime);
//				}
				
			}
			if(roleGroupTaskListConditionDTO.getState() != null && !roleGroupTaskListConditionDTO.getState().isEmpty()){
				List<Integer> stateList = new ArrayList<>();
				for (Map<String,Object> state : roleGroupTaskListConditionDTO.getState()) {
					stateList.add(Integer.parseInt(state.get("key").toString()));
				}
				query.setParameterList("Status", stateList);
			}
			if(CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getRoleGroupId())){
				query.setParameterList("roleIdList", roleGroupTaskListConditionDTO.getRoleGroupId());
			}
			query.setFirstResult((roleGroupTaskListConditionDTO.getPageIndex()-1)*roleGroupTaskListConditionDTO.getPageSize());
			query.setMaxResults(roleGroupTaskListConditionDTO.getPageSize());
			List<WorkOrderItem> itemList = query.list();
			if(itemList != null && !itemList.isEmpty()){
				for (WorkOrderItem workOrderItem : itemList) {
					RoleGroupTaskListOutputRowDTO taskDTO = new RoleGroupTaskListOutputRowDTO();
					taskDTO.setTaskId(workOrderItem.getId());
					taskDTO.setState(PropertyUtil.getStatusName(workOrderItem.getStatus()));
					WorkOrder workOrder = workOrderItem.getWorkOrder();
					if(workOrder != null){						
						taskDTO.setAssignmentTaskSheet(workOrder.getName());
						taskDTO.setTargetFacility(workOrder.getFacility() == null ? null : workOrder.getFacility().getName());
					}			
					taskDTO.setItemName(workOrderItem.getWorkItemName());
//					taskDTO.setPosition(workOrderItem.getDesignator());
					taskDTO.setRoleInCharge(workOrderItem.getRole() == null ? null : workOrderItem.getRole().getRoleName());
					taskDTO.setStaff(workOrderItem.getUser() == null ? null : workOrderItem.getUser().getUserName());
//					taskDTO.setPredictTaskLength(workOrderItem.getLabourTimeSec() == null ? null : StringUtil.decimalFormatMinuteToHour(workOrderItem.getLabourTimeSec()/60/60.0));
					//taskDTO.setPredictTaskLength(StringUtil.getHourApart(workOrderItem.getEstimatedStartTime(), workOrderItem.getEstimatedEndTime()));	
					taskList.add(taskDTO);
				}
			}
			return taskList;
		}catch(Exception e){
			e.printStackTrace();
			return taskList;
		}finally {
			session.close();
		}
		
	}

	@Override
	public Integer getTotalCountWorkOrderListByCondition(RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO) {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from WorkOrder where 1=1 ";
		try{
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getStartDateTime())){
				hql += "and CreationDateTime >= :StartTime";
			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getEndDateTime())){
				hql += " and CreationDateTime <= :EndTime";
			}
			if(roleGroupTaskListConditionDTO.getState() != null && !roleGroupTaskListConditionDTO.getState().isEmpty()){
				hql += " and Status in (:Status) ";
			}
			if(CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getRoleGroupId())){
//			if(roleGroupTaskListConditionDTO.getRoleGroupId() != 0){
//				hql += " and role = "+roleGroupTaskListConditionDTO.getRoleGroupId();
				hql += " and role in (:roleIdList) ";
			}
			if(!StringUtils.isEmpty(roleGroupTaskListConditionDTO.getAssignmentTaskSheet())){
				hql += " and Name = :AssignmentTaskSheet";
			}
			if(!CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getFacilityIdList())){
				hql += " and FacilityId = :facilityId ";
			}
			Query query = session.createQuery(hql);
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getStartDateTime())){
				query.setParameter("StartTime", StringUtil.formatDate(roleGroupTaskListConditionDTO.getStartDateTime()));
			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getEndDateTime())){
				String endDateTime = StringUtil.parseEndDateToEndDateTime(roleGroupTaskListConditionDTO.getEndDateTime());
				query.setParameter("EndTime", endDateTime);
//				Date endDateTime = StringUtil.formatDate(roleGroupTaskListConditionDTO.getEndDateTime());
//				if(roleGroupTaskListConditionDTO.getStartDateTime().equals(roleGroupTaskListConditionDTO.getEndDateTime())){
//					Calendar calendar = Calendar.getInstance();
//					calendar.setTime(endDateTime);
//					calendar.add(Calendar.DAY_OF_MONTH, 1);
//					query.setParameter("EstimatedEndTime", calendar.getTime());
//				}else{
//					query.setParameter("EstimatedEndTime", endDateTime);
//				}
			}
			if(roleGroupTaskListConditionDTO.getState() != null && !roleGroupTaskListConditionDTO.getState().isEmpty()){
				List<Integer> stateList = new ArrayList<>();
				for (Map<String,Object> state : roleGroupTaskListConditionDTO.getState()) {
					stateList.add(Integer.parseInt(state.get("key").toString()));
				}
				query.setParameterList("Status", stateList);
			}
			if(CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getRoleGroupId())){
				query.setParameterList("roleIdList", roleGroupTaskListConditionDTO.getRoleGroupId());
			}
			if(!StringUtils.isEmpty(roleGroupTaskListConditionDTO.getAssignmentTaskSheet())){
				query.setParameter("AssignmentTaskSheet", roleGroupTaskListConditionDTO.getAssignmentTaskSheet());
			}
			if(!CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getFacilityIdList())){
				query.setParameter("facilityId", CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getFacilityIdList()) ? null : StringUtils.collectionToDelimitedString(roleGroupTaskListConditionDTO.getFacilityIdList(), ","));
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
	public List<RoleGroupTaskListOutputRowDTO> getWorkOrderListByCondition(RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkOrder where 1=1 ";
		List<RoleGroupTaskListOutputRowDTO> taskList = new ArrayList<>();
		try{
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getStartDateTime())){
				hql += "and CreationDateTime >= :StartTime";
			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getEndDateTime())){
				hql += " and CreationDateTime <= :EndTime";
			}
			if(roleGroupTaskListConditionDTO.getState() != null && !roleGroupTaskListConditionDTO.getState().isEmpty()){
				hql += " and Status in (:Status) ";
			}
			if(CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getRoleGroupId())){
				hql += " and role in (:roleIdList)";
			}
			if(!StringUtils.isEmpty(roleGroupTaskListConditionDTO.getAssignmentTaskSheet())){
				hql += " and Name = :AssignmentTaskSheet";
			}
			if(!CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getFacilityIdList())){
				hql += " and FacilityId = :facilityId ";
			}
			Query query = session.createQuery(hql);
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getStartDateTime())){
				query.setParameter("StartTime", StringUtil.formatDate(roleGroupTaskListConditionDTO.getStartDateTime()));
			}
			if(StringUtil.IsNotBlank(roleGroupTaskListConditionDTO.getEndDateTime())){
				String endDateTime = StringUtil.parseEndDateToEndDateTime(roleGroupTaskListConditionDTO.getEndDateTime());
				query.setParameter("EndTime", endDateTime);

			}
			if(roleGroupTaskListConditionDTO.getState() != null && !roleGroupTaskListConditionDTO.getState().isEmpty()){
				List<Integer> stateList = new ArrayList<>();
				for (Map<String,Object> state : roleGroupTaskListConditionDTO.getState()) {
					stateList.add(Integer.parseInt(state.get("key").toString()));
				}
				query.setParameterList("Status", stateList);
			}
			if(CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getRoleGroupId())){
				query.setParameterList("roleIdList", roleGroupTaskListConditionDTO.getRoleGroupId());
			}
			if(!StringUtils.isEmpty(roleGroupTaskListConditionDTO.getAssignmentTaskSheet())){
				query.setParameter("AssignmentTaskSheet", roleGroupTaskListConditionDTO.getAssignmentTaskSheet());
			}
			if(!CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getFacilityIdList())){
				query.setParameter("facilityId", CollectionUtils.isEmpty(roleGroupTaskListConditionDTO.getFacilityIdList()) ? null : StringUtils.collectionToDelimitedString(roleGroupTaskListConditionDTO.getFacilityIdList(), ","));
			}
			query.setFirstResult((roleGroupTaskListConditionDTO.getPageIndex()-1)*roleGroupTaskListConditionDTO.getPageSize());
			query.setMaxResults(roleGroupTaskListConditionDTO.getPageSize());
			List<WorkOrder> workOrderList = query.list();
			if(workOrderList != null && !workOrderList.isEmpty()){
				for (WorkOrder workOrder : workOrderList) {
					RoleGroupTaskListOutputRowDTO taskDTO = new RoleGroupTaskListOutputRowDTO();
					taskDTO.setTaskId(workOrder.getId());
					taskDTO.setState(PropertyUtil.getStatusName(workOrder.getStatus()));
					taskDTO.setAssignmentTaskSheet(workOrder.getName());
//					taskDTO.setTargetFacility(workOrder.getFacility() == null ? null : workOrder.getFacility().getName());
					List<Integer> facilityIdList = Arrays.stream(workOrder.getFacilityId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
					taskDTO.setTargetFacility(stationDao.getFacilityNameByFacilityIdList(facilityIdList));
					taskDTO.setItemName(workOrder.getItemName());
					taskDTO.setRoleInCharge(workOrder.getRole() == null ? null : workOrder.getRole().getRoleName());
					taskDTO.setStaff(workOrder.getUser() == null ? null : workOrder.getUser().getUserName());
					taskList.add(taskDTO);
				}
			}
			return taskList;
		}catch(Exception e){
			e.printStackTrace();
			return taskList;
		}finally {
			session.close();
		}
	}

	@Override
	public Integer getStatusByWorkOrderItemId(Integer workOrderItemId) {
		Session session = sessionFactory.openSession();
		String hql = "select status from WorkOrderItem where Id = "+workOrderItemId;
		try{
			Query query = session.createQuery(hql);
			return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}

	@Override
	public Integer getStatusByWorkOrderId(Integer workOrderId) {
		Session session = sessionFactory.openSession();
		try{
			String hql = "select status from WorkOrder where Id = "+workOrderId;
			Query query = session.createQuery(hql);
			return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}

	@Override
	public void updateStatusAndUserIdByWorkOrderItemId(Integer workOrderItemId, Integer userId) {
		Session session = sessionFactory.openSession();
		String hql = "update WorkOrderItem set Status = 2";
		try{
			if(userId != 0){
				hql += ", user = "+userId;
			}
			hql += " where Id = "+workOrderItemId;
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	@Override
	public void updateStatusAndUserIdByWorkOrderId(Integer workOrderId, Integer userId) {
		Session session = sessionFactory.openSession();
		try{
			String hql = "update WorkOrder set Status = 2";
			if(userId != 0){
				hql += ", user = "+userId;
			}
			hql += " where Id = "+workOrderId;
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	@Override
	public void updateStatusByWorkOrderId(Integer workOrderId) {
		Session session = sessionFactory.openSession();
		try{
			String hql = "update WorkOrder set Status = 3 where Id = "+workOrderId;//Status = 3 已完成
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	@Override
	public void updateStatusAndUserIdAndRoleIdByWorkOrderItemId(Integer workOrderItemId,Integer userId,Integer roleId) {
		Session session = sessionFactory.openSession();
		String hql = "update WorkOrderItem set Status = 2";
		try{
			if(userId != 0){
				hql += ", user = "+userId;
			}else{
				hql += ", user = NULL";
			}
			if(roleId != 0) {
				hql += ", role = "+roleId;
			}else{
				hql += ", role = NULL";
			}
			hql += " where Id = "+workOrderItemId;
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
	}


	@Override
	public void updateStatusAndUserIdAndRoleIdByWorkOrderId(Integer workOrderId, Integer userId, Integer roleId) {
		Session session = sessionFactory.openSession();
		try{
			String hql = "update WorkOrder set Status = 2";
			if(userId != 0){
				hql += ", user = "+userId;
			}else{
				hql += ", user = NULL";
			}
			if(roleId != 0) {
				hql += ", role = "+roleId;
			}else{
				hql += ", role = NULL";
			}
			hql += " where Id = "+workOrderId;
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	@Override
	public List<String> getRoleNameByUserId(Integer userId) {
		Session session = sessionFactory.openSession();
		String hql = "select roleName from Role where id in(select role from Roles_Users where user = "+userId+")";
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
	
	@Override
	public String getRoleNameByRoleId(Integer roleId) {
		Session session = sessionFactory.openSession();
		String hql = "select RoleName from Role where Id = "+roleId;
		try{
			Query query = session.createQuery(hql);
			return query.uniqueResult().toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	
	@Override
	public List<AssignmentTaskInitOutputRowDTO> getAssignmentTaskInitDTOByWorkOrderItemId(List<Integer> workOrderItemIdList) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkOrderItem where Id in (:workOrderItemIdList)";
		try{
			Query query = session.createQuery(hql);
			query.setParameterList("workOrderItemIdList", workOrderItemIdList);
			List<AssignmentTaskInitOutputRowDTO> assignmentTaskInitOutputRowDTOList = new ArrayList<>();
			List<WorkOrderItem> workOrderItemList = query.list();
			for (WorkOrderItem workOrderItem : workOrderItemList) {
				AssignmentTaskInitOutputRowDTO assignmentTaskInitOutputRowDTO = new AssignmentTaskInitOutputRowDTO();
				assignmentTaskInitOutputRowDTO.setTaskId(workOrderItem.getId());
				assignmentTaskInitOutputRowDTO.setState(PropertyUtil.getStatusName(workOrderItem.getStatus()));
				WorkOrder workOrder = workOrderItem.getWorkOrder();
				if(workOrder != null){
					assignmentTaskInitOutputRowDTO.setAssignmentTaskSheet(workOrder.getName());
					assignmentTaskInitOutputRowDTO.setTargetFacility(workOrder.getFacility() == null ? null : workOrder.getFacility().getName());
				}
				assignmentTaskInitOutputRowDTO.setItemName(workOrderItem.getWorkItemName());			
				assignmentTaskInitOutputRowDTO.setPosition(workOrderItem.getDesignator());
//				Set<Roles_Users> role_UserSet = workOrderItem.getUser().getRole_Users();
//				List<String> roleNameList = new ArrayList<>();
//				for (Roles_Users roles_Users : role_UserSet) {
//					String roleName = roles_Users.getRole().getRoleName();
//					roleNameList.add(roleName);
//				}
//				if(roleNameList != null && !roleNameList.isEmpty()){
//					assignmentTaskInitOutputRowDTO.setRoleInCharge(StringUtils.collectionToDelimitedString(roleNameList, ","));
//				}
				assignmentTaskInitOutputRowDTO.setRoleInCharge(workOrderItem.getRole() == null ? null : workOrderItem.getRole().getRoleName());
				assignmentTaskInitOutputRowDTO.setPredictTaskLength(StringUtil.getHourApart(workOrderItem.getEstimatedStartTime(), workOrderItem.getEstimatedEndTime()));
				assignmentTaskInitOutputRowDTOList.add(assignmentTaskInitOutputRowDTO);
			}
			return assignmentTaskInitOutputRowDTOList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}

	@Override
	public List<AssignmentTaskInitOutputRowDTO> getAssignmentTaskInitDTOByWorkOrderId(List<Integer> workOrderIdList) {
		Session session = sessionFactory.openSession();
		try{
			String hql = "from WorkOrder where Id in (:workOrderIdList)";
			Query query = session.createQuery(hql);
			query.setParameterList("workOrderIdList", workOrderIdList);
			List<AssignmentTaskInitOutputRowDTO> assignmentTaskInitOutputRowDTOList = new ArrayList<>();
			List<WorkOrder> workOrderList = query.list();
			for (WorkOrder workOrder : workOrderList) {
				AssignmentTaskInitOutputRowDTO assignmentTaskInitOutputRowDTO = new AssignmentTaskInitOutputRowDTO();
				assignmentTaskInitOutputRowDTO.setTaskId(workOrder.getId());
				assignmentTaskInitOutputRowDTO.setState(PropertyUtil.getStatusName(workOrder.getStatus()));
				assignmentTaskInitOutputRowDTO.setAssignmentTaskSheet(workOrder.getName());
				assignmentTaskInitOutputRowDTO.setTargetFacility(workOrder.getFacility() == null ? null : workOrder.getFacility().getName());
				assignmentTaskInitOutputRowDTO.setItemName(workOrder.getItemName());
				assignmentTaskInitOutputRowDTO.setRoleInCharge(workOrder.getRole() == null ? null : workOrder.getRole().getRoleName());
				assignmentTaskInitOutputRowDTOList.add(assignmentTaskInitOutputRowDTO);
			}
			return assignmentTaskInitOutputRowDTOList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}

	@Override
	public List<Map<String,Object>> getUserList(Integer roleId) {
		Session session = sessionFactory.openSession();
		String sql = "select Id value,UserName label from core.[User] where State = 1 ";
		try{
			if(roleId != 0){//查询某一角色下的用户
				sql += " and Id in (select UserId from core.Roles_Users where RoleId = "+roleId+")";
			}
			Query query = session.createSQLQuery(sql);
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
		
	}
	
}
