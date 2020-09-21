package com.smartflow.dao;

import com.smartflow.dto.maintenancetaskplan.*;
import com.smartflow.model.*;
import com.smartflow.util.DTOToModelUtil;
import com.smartflow.util.WorkPlanUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author haita
 */
@Repository
public class MaintenanceTaskPlanDaoImpl implements MaintenanceTaskPlanDao,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4676070302914047978L;
	private final
	HibernateTemplate hibernateTemplate;
	private final
	ReminderDao reminderDao;
    private final
	StationDao stationDao;

	private final
	WorkPlanUtil workPlanUtil;
   Logger logger= LoggerFactory.getLogger(this.getClass());
	@Autowired
	public MaintenanceTaskPlanDaoImpl(HibernateTemplate hibernateTemplate,
									  ReminderDao reminderDao,
									  WorkPlanUtil workPlanUtil,
									  StationDao stationDao) {
		this.hibernateTemplate = hibernateTemplate;
		this.reminderDao = reminderDao;
		this.workPlanUtil = workPlanUtil;
		this.stationDao=stationDao;
	}

	@Override
	public List<MaintenanceTaskPlanPageDTO> pageDTO(Integer pageSize, Integer pageIndex, String maintenanceTaskPlanName,
			String facilityName) {
		String hql="FROM WorkPlan WHERE State=1";
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query=session.createQuery(parseSearchString
				(maintenanceTaskPlanName,facilityName,hql));
		query.setFirstResult((pageIndex-1)*pageSize);
		query.setMaxResults(pageSize);
		try {
			@SuppressWarnings("unchecked")
			List<WorkPlan> workPlans=query.list();
			List<MaintenanceTaskPlanPageDTO> taskPlanPageDTOS =new ArrayList<>();
			for (WorkPlan workPlan : workPlans) {
				String targetFacilityName=null;
				String roleName=null;
				String state=null;
				String periodicType=null;
				Station station=stationDao.getStationById(workPlan.getFacilityId());
				if (station!=null) {
					targetFacilityName=station.getName();
				}
				Role roleModel=new Role();
				if ((roleModel=workPlan.getRole())!=null) {
					roleName=roleModel.getRoleName();
				}
				if (workPlan.getState()==1) {
					state="生效";
				}
				else {
					state="失效";
				}
				if (workPlan.getType()==1) {
					periodicType="周期性的";
				}
				else {
					periodicType="临时的";
				}
				taskPlanPageDTOS .add(new MaintenanceTaskPlanPageDTO(workPlan.getId(), workPlan.getName(), workPlan.getVersion(),
						periodicType, state, targetFacilityName,roleName,workPlan.getEditDateTime()));
			}
			return taskPlanPageDTOS ;
		}
		catch (Exception e) {
			return new ArrayList<>();

		}
		finally {
			session.close(); 
		}

	}

	@Override
	public List<MaintenancePreviewDTO> geMaintenancePreviewDTO(Integer maintenanceId) {
		List<MaintenancePreviewDTO> maintenancePreviewDTOs =new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems=
		(List<WorkItem>)hibernateTemplate.findByNamedParam("From WorkItem Where WorkPlanId=:workPlanId",
				"workPlanId", maintenanceId);
		if (workItems.isEmpty()) {
			return new ArrayList<>();
		}

		for (WorkItem workItem : workItems) {
			Material material=new Material();
			String materialNumber=null;
			Role role=new Role();
			String roleName=null;
			String type=null;
			if ((material=workItem.getMaterial())!=null) {
				materialNumber=material.getMaterialNumber();
			}
			if ((role=workItem.getRole())!=null) {
				roleName=role.getRoleName();
			}
			if (workItem.getMeasurementType()!=null) {
				
			
			if (workItem.getMeasurementType()==1) {
				type="单一值";
			}
			else if (workItem.getMeasurementType()==0) {
				type="曲线";
			}
			}
			maintenancePreviewDTOs .add(new MaintenancePreviewDTO(workItem.getId(), workItem.getName(),
					workItem.getDesignator(), materialNumber, workItem.getName(), workItem.getWorkDurationSec(), 
					roleName, type,workItem.getUSL(), workItem.getLSL(), workItem.getNormalValue(), workItem.getGuideDocURI()));
		}
		return maintenancePreviewDTOs ;
	}

	@Override
	public WorkPlan getTaskForCopy(String copyName) {
		@SuppressWarnings("unchecked")
		List<WorkPlan> workPlans=(List<WorkPlan>)hibernateTemplate.findByNamedParam("FROM WorkPlan where Name=:copyName", "copyName", copyName); 
		if (workPlans.isEmpty()) {
			return null;
		}
		return workPlans.get(0);
	}

	@Override
	public List<Map<String, Object>> getMaintenanceItemList() {
	
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],Name label from tpm.maintenanceitem where State = 1 Order By Name";
		try{
			Query query = session.createSQLQuery(sql);
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			return new ArrayList<>();
		}finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getRoleList() {
	
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],RoleName label from core.Role where State = 1 Order By RoleName";
		try{
			Query query = session.createSQLQuery(sql);//.addScalar("key", StandardBasicTypes.INTEGER).addScalar("label", StandardBasicTypes.STRING);
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			return new ArrayList<>();
		}finally{
			session.close();
		}
	}

	@Override
	public List<Map<String, Object>> getTypeList() {
	
		List<Map<String, Object>> maps=new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("key", 0);
		map.put("label", "单值");
		Map<String, Object> map2=new HashMap<>();
		map2.put("key", 1);
		map2.put("label", "曲线值");
		maps.add(map);
		maps.add(map2);
		return maps;
	}

	@Override
	public List<MaintenanceTaskPlanStepDTO> getStepByWorkPlanId(Integer id) {
	
		@SuppressWarnings("unused")
		List<WorkItem> workItems=(List<WorkItem>)hibernateTemplate.findByNamedParam("FROM WorkItem where WorkPlanId=:id ", "id", id);
		List<MaintenanceTaskPlanStepDTO> maintenanceTaskPlanStepDTOs=new ArrayList<>();
		for (WorkItem workItem : workItems) {
			String rolename=null;
			String maintenanceItemName=null;
			String materialNumber=null;
			String measurementType=null;
			if (workItem.getRole()!=null) {
				rolename=workItem.getRole().getRoleName();
			}
			if (workItem.getMaintenanceItem()!=null) {
				maintenanceItemName=workItem.getMaintenanceItem().getName();
			}
			if (workItem.getMaterial()!=null) {
				materialNumber=workItem.getMaterial().getMaterialNumber();
			}
			if (workItem.getMeasurementType()!=null) {
				
			
			if (workItem.getMeasurementType().equals(1)) {
				measurementType="单值的";
			}
			else
			{
				measurementType="曲线的";
			}
			}
			maintenanceTaskPlanStepDTOs.add(new MaintenanceTaskPlanStepDTO(rolename, maintenanceItemName,
					workItem.getDesignator(), materialNumber, 
					workItem.getName(), workItem.getWorkDurationSec(),
					measurementType, workItem.getUSL(), workItem.getLSL(), workItem.getNormalValue(),
					workItem.getGuideDocURI(),workItem.getId()));
		}
		return maintenanceTaskPlanStepDTOs;
	}

	@Override
	public WorkPlan getWorkPlanById(Integer id) {
	
		return hibernateTemplate.get(WorkPlan.class, id);
	}

	@Override
	public List<Map<String, Object>> getStepList() {
	
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],Name label from  tpm.maintenanceitem where State = 1 Order By Name";
		try{
			Query query = session.createSQLQuery(sql);
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			return new ArrayList<>();
		}finally{
			session.close();
		}
	}
	@Transactional
	@Override
	public Boolean saveData(TaskPlanSaveOutputDTO taskPlanSaveOutPutDTO) {
	
		try {


			WorkPlan workPlan=	DTOToModelUtil.TaskPlanSaveOutputDTOModel(taskPlanSaveOutPutDTO);
			hibernateTemplate.save(workPlan);

			workPlanUtil.saveWorkItemRelatedWorkPlan(taskPlanSaveOutPutDTO.getTaskPlanStepOutPutDTOs(), workPlan);

		} catch (Exception e) {
			return false;
		}
		return true;
	}
	@Transactional
	@Override
	public Boolean delWorkItemByWorkPlanId(int workPlanId) {
	
		Session session=hibernateTemplate.getSessionFactory().openSession();
		String sql="FROM WorkItem WHERE WorkPlanId=:Id";		
		Query query=session.createQuery(sql);
		query.setParameter("Id", workPlanId);

		try {
			@SuppressWarnings({ "unchecked", "unused" })
			List<WorkItem> workItems=query.list();

			for (WorkItem workItem : workItems) {
				hibernateTemplate.delete(workItem);
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
		finally {
			session.close();
		}

	}
	@Transactional
	@Override
	public Boolean delWorkPlanById(int workPlanId) {
	
		try {
			WorkPlan workPlan=hibernateTemplate.get(WorkPlan.class, workPlanId);
			hibernateTemplate.delete(workPlan);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	

	@Override
	public List<Reminder> getReminderList() {
	
		return reminderDao.getReminderList();
	}

	@Transactional
	@Override
	public Boolean delWorkPlanForChangeState(int id) {
	
		try
		{
			WorkPlan workPlan=hibernateTemplate.get(WorkPlan.class, id);
			workPlan.setState(-1);
			workPlan.setName("Del@"+workPlan.getName());
			hibernateTemplate.update(workPlan);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public int getCount(String maintenanceTaskPlanName, String facilityName) {
		String hql="select count(*) FROM WorkPlan WHERE State=1";
		@SuppressWarnings("unchecked")
		List<Long> list=(List<Long>)
				hibernateTemplate.find(parseSearchString
						(maintenanceTaskPlanName,facilityName,hql));
		if(list!=null&&!list.isEmpty()){
			return list.get(0).intValue();
		}
		return 0;
	}

	@Transactional
	@Override
	public Boolean saveWorkPlanData(WorkPlan workPlan) {
		try {
			hibernateTemplate.save(workPlan);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Transactional
	@Override
	public Boolean delTPMWP_RemAndWPExcutionStateByWPId(Integer workPlanId) {
		try {
			@SuppressWarnings("unused")
			List<TPMWorkPlan_Reminder> tpmWorkPlanReminders =
					(List<TPMWorkPlan_Reminder>)hibernateTemplate.findByNamedParam
			("FROM TPMWorkPlan_Reminder WHERE TPMWorkPlanId=:Id", "Id",workPlanId);
			@SuppressWarnings("unused")
			List<Reminder> reminders=reminderDao.getReminderListByWorkPlanId(workPlanId);
			for (TPMWorkPlan_Reminder tpmWorkPlan_Reminder : tpmWorkPlanReminders ) {
				@SuppressWarnings("unused")
				List<WorkPlanExcutionState> workPlanExcutionStates=(List<WorkPlanExcutionState>)
				hibernateTemplate.findByNamedParam
				("From WorkPlanExcutionState WHERE TPMWorkPlan_ReminderId=:TPMWorkPlan_ReminderId", "TPMWorkPlan_ReminderId",tpmWorkPlan_Reminder.getId() );
				for (WorkPlanExcutionState workPlanExcutionState : workPlanExcutionStates) {
					hibernateTemplate.delete(workPlanExcutionState);
				}
				hibernateTemplate.delete(tpmWorkPlan_Reminder);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}



	@Override
	public Boolean addWorkItemByTaskPlanDTO(TaskPlanEditeOutputDTO taskPlanEditeOutPutDTO,WorkPlan workPlan) {
	
		try {
			List<TaskPlanStepOutPutDTO> maintenanceTaskPlanStepDTOs
			=taskPlanEditeOutPutDTO.getTaskPlanStepOutPutDTOs();
			for (TaskPlanStepOutPutDTO maintenanceTaskPlanStepDTO : maintenanceTaskPlanStepDTOs) {
				WorkItem workItem=new WorkItem();
				workItem.setDesignator(maintenanceTaskPlanStepDTO.getPosition());
				workItem.setGuideDocURI(maintenanceTaskPlanStepDTO.getGuideDocURI());
				workItem.setLSL(maintenanceTaskPlanStepDTO.getMeasurementStandardLowerLimit());
				@SuppressWarnings("unchecked")
				List<MaintenanceItem> maintenanceItems=(List<MaintenanceItem>) hibernateTemplate.
				findByNamedParam("FROM MaintenanceItem WHERE Name=:name", "name",maintenanceTaskPlanStepDTO.getMaintenanceItemNameId() );
				if (!maintenanceItems.isEmpty()) {
					workItem.setMaintenanceItem(maintenanceItems.get(0));	
				}
				@SuppressWarnings("unchecked")
				List<Material> materials=(List<Material>)hibernateTemplate.findByNamedParam(
						"FROM Material WHERE MaterialNumber=:materialNumber", "materialNumber",
						maintenanceTaskPlanStepDTO.getMaterialNumber());
				if (materials.isEmpty()) {
					workItem.setMaterial(null);
				}else {
					workItem.setMaterial(materials.get(0));
				} 
				Integer MeasureType=null;
				if (maintenanceTaskPlanStepDTO.getMeasurementTypeId()!=null) {
				if (maintenanceTaskPlanStepDTO.getMeasurementTypeId().equals("单值")) {
					MeasureType=1;
				}
				else if (maintenanceTaskPlanStepDTO.getMeasurementTypeId().equals("曲线")) {
					MeasureType=0;
				}
				}
				workItem.setMeasurementType(MeasureType);
				Integer roleId=null;
				@SuppressWarnings("unchecked")
				List<Role> roles=(List<Role>) hibernateTemplate.findByNamedParam("From Role where RoleName=:roleName ", "roleName", maintenanceTaskPlanStepDTO.getRoleId());
				if (roles!=null) {
					roleId=roles.get(0).getId();
				}
				workItem.setMeasurementType(MeasureType);
				workItem.setName(maintenanceTaskPlanStepDTO.getItemName());
				workItem.setNormalValue(maintenanceTaskPlanStepDTO.getMeasurementStandardMiddleValue());
				Role role2=new Role();
				role2.setId(roleId);
				workItem.setRole(role2);
				workItem.setState(1);
				workItem.setUSL(maintenanceTaskPlanStepDTO.getMeasurementStandardUpperLimit());
				workItem.setWorkDurationSec(maintenanceTaskPlanStepDTO.getWorkLength());
				workItem.setWorkPlan(workPlan);
				hibernateTemplate.save(workItem);
			} 
			return true;
		}catch (Exception e) {
			return false;
		}

	}

	@Override
	public Boolean addWorkItemByTaskPlanDTO(TaskPlanSaveOutputDTO taskPlanEditeOutPutDTO, WorkPlan workPlan) {
		try {
			List<TaskPlanStepOutPutDTO> maintenanceTaskPlanStepDTOs
			=taskPlanEditeOutPutDTO.getTaskPlanStepOutPutDTOs();
			for (TaskPlanStepOutPutDTO maintenanceTaskPlanStepDTO : maintenanceTaskPlanStepDTOs) {
				WorkItem workItem=new WorkItem();
				workItem.setDesignator(maintenanceTaskPlanStepDTO.getPosition());
				workItem.setGuideDocURI(maintenanceTaskPlanStepDTO.getGuideDocURI());
				workItem.setLSL(maintenanceTaskPlanStepDTO.getMeasurementStandardLowerLimit());
				@SuppressWarnings("unchecked")
				List<MaintenanceItem> maintenanceItems=(List<MaintenanceItem>) hibernateTemplate.
				findByNamedParam("FROM MaintenanceItem WHERE Name=:name", "name",maintenanceTaskPlanStepDTO.getMaintenanceItemNameId() );
				if (!maintenanceItems.isEmpty()) {
					workItem.setMaintenanceItem(maintenanceItems.get(0));	
				}
				@SuppressWarnings("unchecked")
				List<Material> materials=(List<Material>)hibernateTemplate.findByNamedParam(
						"FROM Material WHERE MaterialNumber=:materialNumber", "materialNumber",
						maintenanceTaskPlanStepDTO.getMaterialNumber());
				if (materials.size()==0) {
					workItem.setMaterial(null);
				}else {
					workItem.setMaterial(materials.get(0));
				} 
				Integer MeasureType=null;
				if (maintenanceTaskPlanStepDTO.getMeasurementTypeId()!=null) {
				if (maintenanceTaskPlanStepDTO.getMeasurementTypeId().equals("单值")) {
					MeasureType=1;
				}
				else if (maintenanceTaskPlanStepDTO.getMeasurementTypeId().equals("曲线")) {
					MeasureType=0;
				}
				}
				workItem.setMeasurementType(MeasureType);
				Integer roleId=null;
				@SuppressWarnings("unchecked")
				List<Role> roles=(List<Role>) hibernateTemplate.findByNamedParam("From Role where RoleName=:roleName ", "roleName", maintenanceTaskPlanStepDTO.getRoleId());
				if (roles!=null) {
					roleId=roles.get(0).getId();
				}
				workItem.setMeasurementType(MeasureType);
				workItem.setName(maintenanceTaskPlanStepDTO.getItemName());
				workItem.setNormalValue(maintenanceTaskPlanStepDTO.getMeasurementStandardMiddleValue());
				Role role2=new Role();
				role2.setId(roleId);
				workItem.setRole(role2);
				workItem.setState(1);
				workItem.setUSL(maintenanceTaskPlanStepDTO.getMeasurementStandardUpperLimit());
				workItem.setWorkDurationSec(maintenanceTaskPlanStepDTO.getWorkLength());
				workItem.setWorkPlan(workPlan);
				hibernateTemplate.save(workItem);
			} 
			return true;
		}catch (Exception e) {
			return false;
		}
	}
@SuppressWarnings("unchecked")
	@Override
	public List<WorkPlan> getWorkPlansByString(String workplanforname) {
		return (List<WorkPlan>) hibernateTemplate.findByNamedParam("from WorkPlan where Name=:name",
				"name", workplanforname);
	}

	/**
	 * 根据维保计划名，设备名查找维保计划
	 * @param maintenanceTaskPlanName 维保计划名
	 * @param facilityName 设备名
	 * @return 返回修饰好的查询语句
	 */
	private String parseSearchString(String maintenanceTaskPlanName,
									 String facilityName,
									 String hql)
	{
		if (maintenanceTaskPlanName!=null && !"".equals(maintenanceTaskPlanName)) {
			hql += "and Name like '%"+maintenanceTaskPlanName+"%' ";
		}
		if (facilityName!=null && !"".equals(facilityName)) {
			hql += "and TargetFacilityId in (select F.id from StationModel as F where Name like '%"+facilityName+"%'))" ;
		}
		return hql;
	}

}
