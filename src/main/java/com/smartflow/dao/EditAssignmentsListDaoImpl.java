package com.smartflow.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.smartflow.model.FacilityModel;
import com.smartflow.model.Role;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
@Repository
public class EditAssignmentsListDaoImpl implements EditAssignmentsListDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public WorkOrder getWorkOrderById(Integer workOrderId) {
		return hibernateTemplate.get(WorkOrder.class, workOrderId);
	}

//	@Override
//	public WorkItem getWorkItemByWorkItemId(Integer workItemId) {
//		return hibernateTemplate.get(WorkItem.class, workItemId);
//	}

	@Override
	public FacilityModel getFacilityByFacilityId(Integer facilityId) {
		return hibernateTemplate.get(FacilityModel.class, facilityId);
	}

	@Override
	public List<WorkItem> getWorkItemByWorkPlanId(List<Integer> workPlanIdList) {
		Session session = sessionFactory.openSession();
		String hql = "from WorkItem where state != -1 and workPlan.id in :workPlanIdList";
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
	public void updateWorkOrder(WorkOrder workOrder) {
		hibernateTemplate.update(workOrder);
	}
	
//	@Override
//	public void addWorkItem(WorkItem workItem) {
//		hibernateTemplate.save(workItem);
//	}

//	@Override
//	public void DeleteWorkItem(WorkItem workItem) {
//		workItem.setState(-1);//已删除状态
//		hibernateTemplate.update(workItem);
//	}

	@Override
	public Integer getMaterialIdByMaterialNumber(String materialNumber) {
		String hql = "select id from Material where MaterialNumber = '"+materialNumber+"'";
		Session session = sessionFactory.openSession();
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
	public Role getRoleByRoleName(String roleName) {
		//String hql = "select r.id as id,r.roleName as roleName,r.state as state from Role r where r.roleName = '"+roleName+"'";
		String sql = "select * from core.Role where roleName = '"+roleName+"'";
		Session session = sessionFactory.openSession();
		try{
			//Query query = session.createQuery(hql);
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(Role.class));
			return (Role) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
		
	}

//	@Override
//	public maintenanceitem getMaintenanceItemByMaintenanceName(String maintenanceItemName) {
//		//String hql = "select m.id as id,m.name as name from maintenanceitem m where m.name = '"+maintenanceItemName+"'";
//		String sql = "select * from tpm.maintenanceitem where Name = '"+maintenanceItemName+"'";
//		Session session = sessionFactory.openSession();
//		try{
//			//Query query = session.createQuery(hql);
//			Query query = session.createSQLQuery(sql);
//			query.setResultTransformer(Transformers.aliasToBean(maintenanceitem.class));
//			return query.uniqueResult() == null ? null : (maintenanceitem) query.uniqueResult();
//			//return (maintenanceitem) query.setResultTransformer(Transformers.aliasToBean(maintenanceitem.class)).uniqueResult();
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}finally {
//			session.close();
//		}
//	}

	@Override
	public List<WorkOrderItem> getWorkOrderItemListByWorkOrderId(Integer workOrderId) {
		String hql = "from WorkOrderItem where workOrder.id = "+workOrderId+" and status != -1";
		try{
			List<WorkOrderItem> workOrderItemList = (List<WorkOrderItem>) hibernateTemplate.find(hql);
			return workOrderItemList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void addWorkOrderItem(WorkOrderItem workOrderItem) {
		hibernateTemplate.save(workOrderItem);
	}

}
