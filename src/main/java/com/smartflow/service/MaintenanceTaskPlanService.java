package com.smartflow.service;

import java.util.List;
import java.util.Map;

import com.smartflow.dto.MaintenanceTaskPlan.MaintenancePreviewDTO;
import com.smartflow.dto.MaintenanceTaskPlan.MaintenanceTaskPlanPageDTO;
import com.smartflow.dto.MaintenanceTaskPlan.MaintenanceTaskPlanStepDTO;
import com.smartflow.dto.MaintenanceTaskPlan.PeriodicTypeDTO;
import com.smartflow.dto.MaintenanceTaskPlan.TaskPlanEditeOutputDTO;
import com.smartflow.dto.MaintenanceTaskPlan.TaskPlanHeadDto;
import com.smartflow.dto.MaintenanceTaskPlan.TaskPlanSaveOutputDTO;
import com.smartflow.model.WorkPlan;

public interface MaintenanceTaskPlanService {
	//With the  Paramaters Of PageSize , PageIndex, MaintenanceTaskPlanName,FacilityName。
	//result PagesDTO
	List<MaintenanceTaskPlanPageDTO> pageDTO(Integer pageSize,Integer pageIndex,String maintenanceTaskPlanName,String facilityName);
	//With the Maintenacne Id
	//result Maintenance Preview
	List<MaintenancePreviewDTO> geMaintenancePreviewDTO(Integer maintenanceId);
	//with the Task of copeName
	//result MaintenanceTaskPlanEditeDTO
	WorkPlan getTaskForCopy(String copyName);
	//General Initialize The EditMaintenanceStep List Data
	//@para none
	//@result MaintenanceTaskPlanEditeDTO
	List<Map<String, Object>> getMaintenanceItemList();
	//Genenral Initialize the RoleList
	//@para none
	//result RoleList
	List<Map<String, Object>> getRoleList();
	//Genenral Initialize the TypeList
	//@para none
	//result RoleList
	List<Map<String, Object>> getTypeList();
	//According  WorkPlanId Find the list of  WorkItems
	List<MaintenanceTaskPlanStepDTO> getStepByWorkPlanId(Integer id);
	//According to exchange TaskHeadDTO
	TaskPlanHeadDto geTaskPlanHeadDto(WorkPlan workPlan);
	//According Id get the workplan
	WorkPlan getWorkPlanById(Integer id);
	//Genenral Initialize the MaintenanceItem
	List<Map<String, Object>> getStepList();
	//Generally save the data for workplan and workitems
	Boolean saveData(TaskPlanSaveOutputDTO taskPlanSaveOutPutDTO);
	//update the workplan and workitem by the data for taskPlanSaveOutPutDTO
	Boolean updateWorkPlanByOutPutDTO(TaskPlanEditeOutputDTO taskPlanSaveOutPutDTO);
	//get Reminder of list by workplanId
	List<PeriodicTypeDTO> getReminderList(Integer workPlanId);
	//del the workplan for change state
	Boolean delWorkPlanById(Integer workPlanId);
	//get count for the workplan in condition
	int getCount(String maintenanceTaskPlanName,String facilityName);
	//judge is has a replication in the database for workplan,according workplan name
	Boolean isReplication(String workplan);

}
