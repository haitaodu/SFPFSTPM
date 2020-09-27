package com.smartflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.smartflow.common.enumpack.PeriodicType;
import com.smartflow.cron.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.smartflow.dao.MaintenanceTaskPlanDao;
import com.smartflow.dao.ReminderDao;
import com.smartflow.dto.maintenancetaskplan.MaintenancePreviewDTO;
import com.smartflow.dto.maintenancetaskplan.MaintenanceTaskPlanPageDTO;
import com.smartflow.dto.maintenancetaskplan.MaintenanceTaskPlanStepDTO;
import com.smartflow.dto.maintenancetaskplan.PeriodicTypeDTO;
import com.smartflow.dto.maintenancetaskplan.TaskPlanEditeOutputDTO;
import com.smartflow.dto.maintenancetaskplan.TaskPlanHeadDto;
import com.smartflow.dto.maintenancetaskplan.TaskPlanSaveOutputDTO;
import com.smartflow.model.Reminder;
import com.smartflow.model.TPMWorkPlan_Reminder;
import com.smartflow.model.WorkPlan;
import com.smartflow.util.DTOToModelUtil;
import org.springframework.util.StringUtils;

@Service
public class MaintenanceTaskPlanServiceImpl implements MaintenanceTaskPlanService{
@Autowired
MaintenanceTaskPlanDao maintenanceTaskPlanDao;
@Autowired
ReminderDao reminderDao;
@Autowired
HibernateTemplate hibernate;
	@Override
	public List<MaintenanceTaskPlanPageDTO> pageDTO(Integer pageSize, Integer pageIndex, String maintenanceTaskPlanName,
			String facilityName) {
		
		return maintenanceTaskPlanDao.pageDTO(pageSize, pageIndex, maintenanceTaskPlanName, facilityName);
	}

	@Override
	public List<MaintenancePreviewDTO> geMaintenancePreviewDTO(Integer maintenanceId) {
		
		return maintenanceTaskPlanDao.geMaintenancePreviewDTO(maintenanceId);
	}

	@Override
	public WorkPlan getTaskForCopy(String copyName) {
		
		return maintenanceTaskPlanDao.getTaskForCopy(copyName);
	}

	@Override
	public List<Map<String, Object>> getMaintenanceItemList() {
		
		return maintenanceTaskPlanDao.getMaintenanceItemList();
	}

	@Override
	public List<Map<String, Object>> getRoleList() {
		
		
		return maintenanceTaskPlanDao.getRoleList();
	}

	@Override
	public List<Map<String, Object>> getTypeList() {
		
		return maintenanceTaskPlanDao.getTypeList();
	}

	@Override
	public List<MaintenanceTaskPlanStepDTO> getStepByWorkPlanId(Integer id) {
		
		return maintenanceTaskPlanDao.getStepByWorkPlanId(id);
	}

	@Override
	public TaskPlanHeadDto geTaskPlanHeadDto(WorkPlan workPlan) {
		
		String planName=null;
		String targetFacility=workPlan.getFacilityId().toString();
		String state=null;
		String planType=null;
		String mainRole=null;
		if (workPlan.getState()==1) {
			state="1";
		}
		else if (workPlan.getState()==0) {
			state="0";
		}
		else {
			state="-1";
		}
		
		if (workPlan.getType()==1) {
			planType="周期性";
		}
		else if (workPlan.getType()==0) {
			planType="非周期性";
		}
		else {
			
		}
		if (workPlan.getRole()!=null) {
			mainRole=workPlan.getRole().getId().toString();
		}
		TaskPlanHeadDto taskPlanHeadDto=new TaskPlanHeadDto(workPlan.getName(), targetFacility, state, planType, mainRole);
		return taskPlanHeadDto;
	}

	@Override
	public WorkPlan getWorkPlanById(Integer id) {
		
		return maintenanceTaskPlanDao.getWorkPlanById(id);
	}

	@Override
	public List<Map<String, Object>> getStepList() {
		
		return maintenanceTaskPlanDao.getMaintenanceItemList();
	}

	@Transactional
	@Override
	public Boolean saveData(TaskPlanSaveOutputDTO taskPlanSaveOutPutDTO) {
		
		try {
		WorkPlan workPlan=DTOToModelUtil.TaskPlanSaveOutputDTOModel(taskPlanSaveOutPutDTO);
		//从前台dto中取出Reminder数组
//		List<PeriodicTypeDTO> periodicTypeDTOs=taskPlanSaveOutPutDTO.getPeriodicTypeList();
		//保存reminder(内存有cron表达式)
		List<Integer> periodicTypeDTOs = taskPlanSaveOutPutDTO.getPeriodicTypeList();
		hibernate.save(workPlan);
		if(CollectionUtils.isNotEmpty(periodicTypeDTOs)) {
//		for (PeriodicTypeDTO periodicTypeDTO : periodicTypeDTOs) {
//			Reminder reminder=DTOToModelUtil.periodicTypeDTOToReminder(periodicTypeDTO);
//			if (!CronExpression.isValidExpression(reminder.getCRONExpression())) {
//				return false;
//			}
//			hibernate.save(reminder);
//			TPMWorkPlan_Reminder tpmWorkPlan_Reminder=DTOToModelUtil.ReminderAndWorkPlanToTPMWorkPlan_Reminder(reminder, workPlan);
//			//这个和reminder关联的表也要进行数据的插入
//			hibernate.save(tpmWorkPlan_Reminder);
//
//		}
			PeriodicTypeDTO periodicTypeDTO = DTOToModelUtil.periodicTypeDTOToPeriodicType(periodicTypeDTOs, taskPlanSaveOutPutDTO.getTemporaryDate());
			Reminder reminder=DTOToModelUtil.periodicTypeDTOToReminder(periodicTypeDTO);
			if (!CronExpression.isValidExpression(reminder.getCRONExpression())) {
				return false;
			}
			hibernate.save(reminder);
			TPMWorkPlan_Reminder tpmWorkPlan_Reminder=DTOToModelUtil.ReminderAndWorkPlanToTPMWorkPlan_Reminder(reminder, workPlan);
			//这个和reminder关联的表也要进行数据的插入
			hibernate.save(tpmWorkPlan_Reminder);
			//保存workplan数据
		}
		//最后插入workplan的子表workItem
//		maintenanceTaskPlanDao.addWorkItemByTaskPlanDTO(taskPlanSaveOutPutDTO, workPlan);
		return true;
	
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Transactional
	@Override
	public Boolean updateWorkPlanByOutPutDTO(TaskPlanEditeOutputDTO taskPlanEditeOutputDTO) {
		

try {
	//先删除最底层的WorkItem表中的数据
//	maintenanceTaskPlanDao.delWorkItemByWorkPlanId(taskPlanEditeOutputDTO.getId());
	//根据前台传来的数据，取出WorkPlan类型数据
	WorkPlan workPlan=DTOToModelUtil.TaskPlanEditeOutputDTOModel(taskPlanEditeOutputDTO);
	//删除与workplanId相关的二个表workPlanExcutionState,tpmWorkPlan_Reminder
	maintenanceTaskPlanDao.delTPMWP_RemAndWPExcutionStateByWPId(workPlan.getId());
	//从前台dto中取出Reminder数组
//	List<PeriodicTypeDTO> periodicTypeDTOs=taskPlanEditeOutputDTO.getPeriodicTypeList();
	List<Integer> periodicTypeDTOs = taskPlanEditeOutputDTO.getPeriodicTypeList();
	//保存reminder(内存有cron表达式)
//	for (PeriodicTypeDTO periodicTypeDTO : periodicTypeDTOs) {
//		Reminder reminder=DTOToModelUtil.periodicTypeDTOToReminder(periodicTypeDTO);
//		hibernate.save(reminder);
//		TPMWorkPlan_Reminder tpmWorkPlan_Reminder=DTOToModelUtil.ReminderAndWorkPlanToTPMWorkPlan_Reminder(reminder, workPlan);
//		//这个和reminder关联的表也要进行数据的插入
//		hibernate.save(tpmWorkPlan_Reminder);
//	}
	if(CollectionUtils.isNotEmpty(periodicTypeDTOs)) {
		PeriodicTypeDTO periodicTypeDTO = DTOToModelUtil.periodicTypeDTOToPeriodicType(periodicTypeDTOs, taskPlanEditeOutputDTO.getTemporaryDate());
		Reminder reminder = DTOToModelUtil.periodicTypeDTOToReminder(periodicTypeDTO);
		if (!CronExpression.isValidExpression(reminder.getCRONExpression())) {
			return false;
		}
		hibernate.save(reminder);
		TPMWorkPlan_Reminder tpmWorkPlan_Reminder = DTOToModelUtil.ReminderAndWorkPlanToTPMWorkPlan_Reminder(reminder, workPlan);
		//这个和reminder关联的表也要进行数据的插入
		hibernate.save(tpmWorkPlan_Reminder);
	}

	//保存workplan数据
	hibernate.merge(workPlan);
	//最后插入workplan的子表workItem
//	maintenanceTaskPlanDao.addWorkItemByTaskPlanDTO(taskPlanEditeOutputDTO, workPlan);
	return true;
} catch (Exception e) {
	return false;
	// TODO: handle exception
}
	}

	@Override
	public List<PeriodicTypeDTO> getReminderList(Integer workPlanId) {
		
	List<PeriodicTypeDTO> reminderDTOs=new ArrayList<>();
	List<Reminder>reminders=reminderDao.getReminderListByWorkPlanId(workPlanId);
	for (Reminder reminder : reminders) {
		PeriodicTypeDTO periodicTypeDTO=new PeriodicTypeDTO();
		periodicTypeDTO.setCRONExpression(reminder.getCRONExpression());
		periodicTypeDTO.setPeriodicName(reminder.getName());
		periodicTypeDTO.setId(reminder.getId());
		periodicTypeDTO.setKey(reminder.getId().toString());
		reminderDTOs.add(periodicTypeDTO);
	}
		return reminderDTOs;
	}

	@Override
	public Boolean delWorkPlanById(Integer workPlanId) {
		
		try {
			maintenanceTaskPlanDao.delWorkPlanForChangeState(workPlanId);
			 maintenanceTaskPlanDao.delWorkItemByWorkPlanId(workPlanId);
			 maintenanceTaskPlanDao.delWorkPlanForChangeState(workPlanId);
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
		
	}

	@Override
	public int getCount(String maintenanceTaskPlanName, String facilityName) {
		
		return maintenanceTaskPlanDao.getCount(maintenanceTaskPlanName, facilityName)
				;
	}

	@Override
	public Boolean isReplication(String workplan) {
		
		if (maintenanceTaskPlanDao.getWorkPlansByString(workplan).size()>=1) {
			return false;
		}
		return true;
	}

}
