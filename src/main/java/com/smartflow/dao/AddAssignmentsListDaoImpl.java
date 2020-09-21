package com.smartflow.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.smartflow.dto.AddAssignListInputDTO;
import com.smartflow.dto.AddMaintenanceStepsInputDTO;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.MaintenanceItem;
import com.smartflow.model.Material;
import com.smartflow.model.Role;
import com.smartflow.model.UserModel;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.model.WorkPlan;
import com.smartflow.util.PropertyUtil;
import com.smartflow.util.StringUtil;
@Repository
public class AddAssignmentsListDaoImpl implements AddAssignmentsListDao {

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplate;
	@Override
	public List<Map<String, Object>> getMaintenanceItemList() {
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],Name label from tpm.maintenanceitem where State = 1";
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
	public Integer getBOMHeadIdByFacilityId(Integer facilityId) {
		Session session = sessionFactory.openSession();
		String hql = "select BOMHead.id from FacilityModel where id = "+facilityId;
		try{
			Query query = session.createQuery(hql);
			return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
//	@Override
//	public List<Integer> getWorkPlanIdByFacilityId(Integer facilityId) {
//		Session session = sessionFactory.openSession();
//		String hql = "select id from WorkPlan where facility.id = "+facilityId;
//		try{
//			Query query = session.createQuery(hql);
//			return query.list();
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}finally{
//			session.close();
//		}
//	}

	@Override
	public Integer getMaterialIdByMaterialNumber(String materialNumber) {
		Session session = sessionFactory.openSession();
		String hql = "select id from Material where materialNumber = '"+materialNumber+"'";
		try{
			Query query = session.createQuery(hql);
			return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}

//	@Override
//	public Material getMaterialByMaterialNumber(String materialNumber) {
//		Session session = sessionFactory.openSession();
//		String hql = "from Material where materialNumber = '"+materialNumber+"'";
//		try{
//			Query query = session.createQuery(hql);
//			query.setResultTransformer(Transformers.aliasToBean(Material.class));
//			return query.uniqueResult() == null ? null : (Material)query.uniqueResult();
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}finally{
//			session.close();
//		}
//	}

	@Override
	public Integer getMaintenanceItemIdByMaintenanceItem(String maintenanceItemName) {
		String hql = "select id from maintenanceitem where Name = '"+maintenanceItemName+"'";
		Session session = sessionFactory.openSession();
		try {
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
	public Role getRoleIdByRoleName(String roleName) {
		String hql = "from Role where RoleName = '"+roleName+"'";
		Session session = sessionFactory.openSession();
		try{
			Query query = session.createQuery(hql);
			return query.uniqueResult() == null ? null : (Role)query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	@Override
	public Role getRoleByRoleId(Integer roleId) {
		return hibernateTemplate.get(Role.class, roleId);
	}
	@Override
	public UserModel getUserByUserId(Integer userId) {
		return hibernateTemplate.get(UserModel.class, userId);
	}
	@SuppressWarnings("null")
	@Override
	public UserModel getUserByUserName(String userName) {
		List<UserModel> userModel = (List<UserModel>) hibernateTemplate.find("from UserModel where userName = ?", userName);
		if(userModel == null || userModel.isEmpty()){
			return null;
		}else{
			return userModel.get(0);
		}
	}


}
