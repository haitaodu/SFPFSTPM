package com.smartflow.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.smartflow.dto.maintenancetaskplan.TaskPlanStepOutPutDTO;
import com.smartflow.model.MaintenanceItem;
import com.smartflow.model.Material;
import com.smartflow.model.Role;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkPlan;
@Repository
public class WorkPlanUtilImpl implements WorkPlanUtil{
@Autowired
HibernateTemplate hibernateTemplate;
	@Override
	public Boolean saveWorkItemRelatedWorkPlan(List<TaskPlanStepOutPutDTO> maintenanceTaskPlanStepDTOs,
			WorkPlan workPlan) 
	{
		// TODO Auto-generated method stub
	
		for (TaskPlanStepOutPutDTO maintenanceTaskPlanStepDTO : maintenanceTaskPlanStepDTOs) {
			WorkItem workItem=new WorkItem();
			workItem.setDesignator(maintenanceTaskPlanStepDTO.getPosition());
			workItem.setGuideDocURI(maintenanceTaskPlanStepDTO.getGuideDocURI());
			workItem.setLSL(maintenanceTaskPlanStepDTO.getMeasurementStandardLowerLimit());
			
			@SuppressWarnings("unchecked")
			List<MaintenanceItem> maintenanceItems=(List<MaintenanceItem>) hibernateTemplate.
					findByNamedParam("FROM MaintenanceItem WHERE Name=:name", "name",maintenanceTaskPlanStepDTO.getMaintenanceItemNameId() );
			if (maintenanceItems!=null) {
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
			if (maintenanceTaskPlanStepDTO.getMeasurementTypeId().equals("单值")) {
				MeasureType=1;
			}
			else if (maintenanceTaskPlanStepDTO.getMeasurementTypeId().equals("曲线")) {
				MeasureType=0;
			}
			workItem.setMeasurementType(MeasureType);
			Integer roleId=null;
		    @SuppressWarnings("unchecked")
			List<Role> roles=(List<Role>) hibernateTemplate.findByNamedParam("From Role where RoleName=:roleName ", "roleName", maintenanceTaskPlanStepDTO.getRoleId());
            if (roles!=null) {
				roleId=roles.get(0).getId();
			}
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
		
	}

}
