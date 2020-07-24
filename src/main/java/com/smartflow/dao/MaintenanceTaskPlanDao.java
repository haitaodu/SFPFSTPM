package com.smartflow.dao;

import java.util.List;
import java.util.Map;

import com.smartflow.dto.MaintenanceItem.MaintenanceItemPageDTO;
import com.smartflow.dto.MaintenanceTaskPlan.MaintenancePreviewDTO;
import com.smartflow.dto.MaintenanceTaskPlan.MaintenanceTaskPlanEditeDTO;
import com.smartflow.dto.MaintenanceTaskPlan.MaintenanceTaskPlanPageDTO;
import com.smartflow.dto.MaintenanceTaskPlan.MaintenanceTaskPlanStepDTO;
import com.smartflow.dto.MaintenanceTaskPlan.TaskPlanEditeOutputDTO;
import com.smartflow.dto.MaintenanceTaskPlan.TaskPlanSaveOutputDTO;
import com.smartflow.dto.MaintenanceTaskPlan.TaskPlanStepOutPutDTO;
import com.smartflow.model.Reminder;
import com.smartflow.model.WorkPlan;

public interface MaintenanceTaskPlanDao {
//With the  Paramaters Of PageSize , PageIndex, MaintenanceTaskPlanName,FacilityName。
//result PagesDTO
List<MaintenanceTaskPlanPageDTO> pageDTO(Integer pageSize,Integer pageIndex,String maintenanceTaskPlanName,String facilityName);
//With the Maintenacne Id
//result Maintenance Preview
List<MaintenancePreviewDTO> geMaintenancePreviewDTO(Integer maintenanceId);
//with the Task of copyName
//result WorkPlan
WorkPlan getTaskForCopy(String copyName);
//According  WorkPlanId Find the list of  WorkItems
List<MaintenanceTaskPlanStepDTO> getStepByWorkPlanId(Integer id);
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
//According Id get the workplan
WorkPlan getWorkPlanById(Integer id);
//Genenral Initialize the MaintenanceItem
List<Map<String, Object>> getStepList();
//Generally save the data for workplan and workitems
Boolean saveData(TaskPlanSaveOutputDTO taskPlanSaveOutPutDTO);
//Del the data for the workitem  by workplanid
Boolean delWorkItemByWorkPlanId(int workPlanId);	
//Del the data for workplan by workplanId,really del it
Boolean delWorkPlanById(int workPlanId);

//get Reminder of list without  para
List<Reminder> getReminderList();
//Del the workplan by id,just take state  exchange 1 or 0 to -1
Boolean delWorkPlanForChangeState(int id);
//get count for the workplan in condition
int getCount(String maintenanceTaskPlanName,String facilityName);
//save workplan by taskPlanSaveOutPutDTO
Boolean saveWorkPlanData(WorkPlan workPlan);
//del TPMWorkPlan_Reminder And WorkPlanExcutionState by workplanId
Boolean delTPMWP_RemAndWPExcutionStateByWPId(Integer workPlanId);
//add WorkItem By taskPlanEditeOutPutDTO
Boolean addWorkItemByTaskPlanDTO(TaskPlanEditeOutputDTO   taskPlanEditeOutPutDTO,WorkPlan workPlan);
//add WorkItem By taskPlanSaveOutPutDTO
Boolean addWorkItemByTaskPlanDTO(TaskPlanSaveOutputDTO   taskPlanEditeOutPutDTO,WorkPlan workPlan);
//find the workplans,according string for workplan name
List<WorkPlan> getWorkPlansByString(String workplanforname);
}
