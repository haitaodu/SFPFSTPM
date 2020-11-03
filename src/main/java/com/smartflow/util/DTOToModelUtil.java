package com.smartflow.util;

import com.smartflow.cron.util.DateUtil;
import com.smartflow.dto.maintenancetaskplan.PeriodicTypeDTO;
import com.smartflow.dto.maintenancetaskplan.TaskPlanEditeOutputDTO;
import com.smartflow.dto.maintenancetaskplan.TaskPlanSaveOutputDTO;
import com.smartflow.model.*;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author haita
 */
public class DTOToModelUtil{
	public static WorkPlan TaskPlanSaveOutputDTOModel(TaskPlanSaveOutputDTO taskPlanSaveOutPutDTO,String facilityId) {
		WorkPlan workPlan=new WorkPlan();
		workPlan.setCreationDateTime(new Date());
		workPlan.setCreatorId(10);
		workPlan.setEditDateTime(new Date());
		workPlan.setEditorId(10);
//		workPlan.setTargetFacilityId(taskPlanSaveOutPutDTO.getTargetFacilityId());
		workPlan.setName(taskPlanSaveOutPutDTO.getPlanName());
		Role role=new Role();
		role.setId(taskPlanSaveOutPutDTO.getMainRoleId());
		workPlan.setRole(role);
		workPlan.setState(taskPlanSaveOutPutDTO.getState());
//		workPlan.setType(taskPlanSaveOutPutDTO.getPeriodicType()==true ? 2:1);
		workPlan.setType(taskPlanSaveOutPutDTO.getPeriodicTypeList().get(0));
		workPlan.setVersion(1);
		workPlan.setMaintenanceItemId(taskPlanSaveOutPutDTO.getMaintenanceItemId());
		workPlan.setFacilityId(facilityId);
		return workPlan;
	}


	public static WorkPlan TaskPlanEditeOutputDTOModel(TaskPlanEditeOutputDTO taskPlanEditeOutputDTO) {
		
		WorkPlan workPlan=new WorkPlan();

		workPlan.setCreationDateTime(new Date());
		workPlan.setCreatorId(10);
		workPlan.setEditDateTime(new Date());
		workPlan.setEditorId(10);
		workPlan.setTargetFacilityId(taskPlanEditeOutputDTO.getTargetFacilityId());
		workPlan.setName(taskPlanEditeOutputDTO.getPlanName());
		Role role=new Role();
		role.setId(taskPlanEditeOutputDTO.getMainRoleId());
		workPlan.setRole(role);
		workPlan.setState(taskPlanEditeOutputDTO.getState());

//		workPlan.setType(taskPlanEditeOutputDTO.isPeriodicType() == true ? 2 : 1);
		workPlan.setType(taskPlanEditeOutputDTO.getPeriodicTypeList().get(0));
		workPlan.setVersion(1);
		workPlan.setId(taskPlanEditeOutputDTO.getId());
		return workPlan;
	}

	public static PeriodicTypeDTO periodicTypeDTOToPeriodicType(List<Integer> periodicTypeDTOs, String temporaryDateStr){
		Integer periodicType = periodicTypeDTOs.get(0);
		String cronExpression = "";
		if (periodicType == 1) {//临时的
			Date temporaryDate = DateUtil.toDate(temporaryDateStr, "yyyy-MM-dd");
			int year = DateUtil.year(temporaryDate);
			int month = DateUtil.month(temporaryDate);
			int day = DateUtil.day(temporaryDate);
			int hour = DateUtil.hour(temporaryDate);
			int minute = DateUtil.minute(temporaryDate);
			int second = DateUtil.second(temporaryDate);
			cronExpression = second + " " + minute + " " + hour + " " + day + " " + month + " " + "? " + year + "-" + year;
		} else if (periodicType == 2) {//周期性的
			Integer monthWeek = periodicTypeDTOs.get(1);
			Integer number = periodicTypeDTOs.get(2);
			if (monthWeek == 1) {//每周
				if (number == 7) {//周日
					cronExpression = "0 0 0 ? * " + 1;// + " *"
				} else {
					cronExpression = "0 0 0 ? * " + (monthWeek + 1);
				}
			} else if (monthWeek == 2) {//每月
				cronExpression = "0 0 0 " + number + " * ?";
			}
		}
		PeriodicTypeDTO periodicTypeDTO = new PeriodicTypeDTO();
		periodicTypeDTO.setPeriodicName(StringUtils.collectionToDelimitedString(periodicTypeDTOs, ","));
		periodicTypeDTO.setCRONExpression(cronExpression);
		return periodicTypeDTO;
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
