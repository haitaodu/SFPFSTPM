package com.smartflow.util;

import java.util.List;

import com.smartflow.dto.maintenancetaskplan.TaskPlanStepOutPutDTO;
import com.smartflow.model.WorkPlan;

public interface WorkPlanUtil {
	//To save the workItems related wirh the workplan
	//para workplan, List<TaskPlanStepOutPutDTO>
	Boolean saveWorkItemRelatedWorkPlan(List<TaskPlanStepOutPutDTO> maintenanceTaskPlanStepDTOs,WorkPlan workPlan );

}
