package com.smartflow.util;

import com.smartflow.dto.maintenancetaskplan.PeriodicTypeDTO;
import com.smartflow.dto.maintenancetaskplan.TaskPlanEditeOutputDTO;
import com.smartflow.dto.maintenancetaskplan.TaskPlanSaveOutputDTO;
import com.smartflow.model.*;

import java.util.Calendar;
import java.util.Date;

/**
 * @author haita
 */
public class DTOToModelUtil{
	public static WorkPlan TaskPlanSaveOutputDTOModel(TaskPlanSaveOutputDTO taskPlanSaveOutPutDTO) {
		WorkPlan workPlan=new WorkPlan();
		workPlan.setCreationDateTime(new Date());
		workPlan.setCreatorId(10);
		workPlan.setEditDateTime(new Date());
		workPlan.setEditorId(10);
		workPlan.setFacilityId(taskPlanSaveOutPutDTO.getTargetFacilityId());
		workPlan.setName(taskPlanSaveOutPutDTO.getPlanName());
		Role role=new Role();
		role.setId(taskPlanSaveOutPutDTO.getMainRoleId());
		workPlan.setRole(role);
		workPlan.setState(taskPlanSaveOutPutDTO.getState());
		workPlan.setType(taskPlanSaveOutPutDTO.getPeriodicType()==true ? 2:1);
		workPlan.setVersion(1);
		return workPlan;
	}


	public static WorkPlan TaskPlanEditeOutputDTOModel(TaskPlanEditeOutputDTO taskPlanEditeOutputDTO) {
		
		WorkPlan workPlan=new WorkPlan();

		workPlan.setCreationDateTime(new Date());
		workPlan.setCreatorId(10);
		workPlan.setEditDateTime(new Date());
		workPlan.setEditorId(10);
		workPlan.setFacilityId(taskPlanEditeOutputDTO.getTargetFacilityId());
		workPlan.setName(taskPlanEditeOutputDTO.getPlanName());
		Role role=new Role();
		role.setId(taskPlanEditeOutputDTO.getMainRoleId());
		workPlan.setRole(role);
		workPlan.setState(taskPlanEditeOutputDTO.getState());

		workPlan.setType(taskPlanEditeOutputDTO.isPeriodicType() == true ? 2 : 1);
		workPlan.setVersion(1);
		workPlan.setId(taskPlanEditeOutputDTO.getId());
		return workPlan;
	}

	public static Reminder periodicTypeDTOToReminder(PeriodicTypeDTO periodicTypeDTO)
	{
		Reminder reminder=new Reminder();
		reminder.setCRONExpression(periodicTypeDTO.getCRONExpression());
		reminder.setName(periodicTypeDTO.getPeriodicName());
		Date dateForStart=new Date();
		reminder.setStartDateTime(dateForStart);
		//Although we haven't decided the endtime,that just exchange for starttime temperially
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateForStart);
		calendar.add(Calendar.YEAR, 1);
		Date dateForEnd=calendar.getTime();

		reminder.setEndDateTime(dateForEnd);
		reminder.setState(1);
		return reminder;
	}
	
	public static TPMWorkPlan_Reminder ReminderAndWorkPlanToTPMWorkPlan_Reminder(Reminder reminder,WorkPlan workPlan)
	{
		 TPMWorkPlan_Reminder tpmWorkPlan_ReminderForAdd=new TPMWorkPlan_Reminder();
		tpmWorkPlan_ReminderForAdd.setAssignedDateTime(new Date());
		   tpmWorkPlan_ReminderForAdd.setCreatorId(10);
		   tpmWorkPlan_ReminderForAdd.setReminder(reminder);
	       tpmWorkPlan_ReminderForAdd.setWorkPlan(workPlan);
	       return tpmWorkPlan_ReminderForAdd;
	}
	
	
}
