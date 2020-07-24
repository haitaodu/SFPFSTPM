package com.smartflow.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.smartflow.dto.CalendarDataDTO;
import com.smartflow.dto.MaintenanceAssignmentCalendarDTO.CalendarData;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.TPMWorkPlan_Reminder;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.model.WorkPlan;
import com.smartflow.model.WorkPlanExcutionState;
@Repository
public class RemindsAndAssignmentsCalendarDaoImpl implements RemindsAndAssignmentsCalendarDao {

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplate;
	@Override
	public List<CalendarDataDTO> getAssignmentsCalendar() {
		String sql = "select a.StartDateTime,a.EndDateTime,a.CRONExpression,a.WorkPlan_ReminderId,b.* from "
				+ "(select r.StartDateTime,r.EndDateTime,r.CRONExpression,tr.Id WorkPlan_ReminderId,tr.TPMWorkPlanId,tr.ReminderId from core.Reminder r,tpm.TPMWorkPlan_Reminder tr where r.State = 1 and r.Id = tr.ReminderId) a,"
				+ "(select wp.Id WorkPlanId,wp.Name WorkPlanName,f.Id FacilityId,f.Name FacilityName  from tpm.WorkPlan wp,core.Facility f where wp.TargetFacilityId = f.Id and wp.State = 1 and f.State = 1) b "
				+ "where a.TPMWorkPlanId = b.WorkPlanId";
		Session session = sessionFactory.openSession();
		List<CalendarDataDTO> calendarDataList = new ArrayList<>();
		try{
			Query query = session.createSQLQuery(sql);
			calendarDataList = query.setResultTransformer(Transformers.aliasToBean(CalendarDataDTO.class)).list();
			return calendarDataList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
//		CalendarData calendarData = new CalendarData();
//		calendarData.setWorkPlanId(workPlan.getId());
//		calendarData.setWorkPlanName(workPlan.getName());
//		FacilityModel facility = workPlan.getFacility();
//		calendarData.setFacilityId(facility.getId());
//		calendarData.setTargetFacilityName(facility.getName());
//		calendarData.setWorkPlanExcutionState("");		
	}
	
	@Override
	public String getCalendarStateByTPMWorkPlanReminderId(Integer tpmWorkPlanReminderId) {
		Session session = sessionFactory.openSession();
		//select count(*) from tpm.WorkPlanExcutionState where State = 1 and TPMWorkPlan_ReminderId = 2
		String sql = "select count(*) from tpm.WorkPlanExcutionState where TPMWorkPlan_ReminderId = "+tpmWorkPlanReminderId;
		try{
			Query query = session.createSQLQuery(sql);
			Integer count = Integer.parseInt(query.uniqueResult().toString());
			String state = null;
			if(count == 0){//没有记录，还未安排
				state = "Processing";
			}else{//已安排
				state = "Default";
			}
			return state;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public Integer getRoleGroupIdByWorkPlanId(Integer workPlanId) {
		Session session = sessionFactory.openSession();
		String hql = "select role.id from WorkPlan where Id = "+workPlanId;
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
	public void addWorkOrderItemByWorkItem(WorkOrderItem workOrderItem) {
		hibernateTemplate.save(workOrderItem);
	}
	@Override
	public void addWorkPlanExcutionState(WorkPlanExcutionState workPlanExcutionState) {
		hibernateTemplate.save(workPlanExcutionState);
	}
	@Override
	public List<Integer> getWorkOrderIdFromTPMWorkPlan_ReminderIdByWorkItemId(List<Integer> workItemId) {
		Session session = sessionFactory.openSession();
		String sql = "select TPMWorkOrderId from tpm.WorkPlanExcutionState where TPMWorkPlan_ReminderId in (select Id from tpm.TPMWorkPlan_Reminder where TPMWorkPlanId in (select WorkPlanId from tpm.WorkItem where Id in (:workItemId)))";
		try {
			Query query = session.createSQLQuery(sql);
			query.setParameterList("workItemId", workItemId);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	
	@Override
	public void addWorkOrder(WorkOrder workOrder) {
		hibernateTemplate.save(workOrder);
	}

	
	@Override
	public Integer getFacilityIdByWorkItemId(List<Integer> workItemId) {
		Session session = sessionFactory.openSession();
		String sql = "select distinct TargetFacilityId from tpm.WorkPlan where Id in (select distinct WorkPlanId from tpm.WorkItem where Id in (:workItemId))";
		try {
			Query query = session.createSQLQuery(sql);
			query.setParameterList("workItemId", workItemId);
			return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public List<WorkItem> getWorkItemByWorkItemIdList(List<Integer> workItemIdList) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkItem where Id in(:workItemIdList)";
		try {
			Query query = session.createQuery(hql);
			query.setParameterList("workItemIdList", workItemIdList);
			return query.list();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	@Override
	public void addTPMWorkPlan_Reminder(TPMWorkPlan_Reminder tpmWorkPlan_Reminder) {
		hibernateTemplate.save(tpmWorkPlan_Reminder);
	}
	@Override
	public List<Integer> getWorkPlanIdByWorkItemIdList(List<Integer> workItemIdList) {
		Session session = sessionFactory.openSession();
		String hql = "select distinct workPlan.id from WorkItem where id in (:workItemIdList)";
		try{
			Query query = session.createQuery(hql);
			query.setParameterList("workItemIdList", workItemIdList);
			return query.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public List<Integer> getReminderIdByWorkPlanId(List<Integer> workPlanIdList) {
		Session session = sessionFactory.openSession();
		String hql = "select distinct reminder.id from TPMWorkPlan_Reminder where WorkPlan.id in (:workPlanIdList)";
		try{
			Query query = session.createQuery(hql);
			query.setParameterList("workPlanIdList", workPlanIdList);
			return query.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public List<TPMWorkPlan_Reminder> getTPMWorkPlan_ReminderByWorkPlanId(List<Integer> workPlanIdList) {
		Session session = sessionFactory.openSession();
		String hql = "from TPMWorkPlan_Reminder where workPlan.id in (:workPlanIdList)";
		try{
			Query query = session.createQuery(hql);
			query.setParameterList("workPlanIdList", workPlanIdList);
			return query.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
}
