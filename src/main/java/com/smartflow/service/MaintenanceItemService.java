package com.smartflow.service;

import java.util.List;

import com.smartflow.dto.MaintenanceItem.MaintenanceItemAddDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemDetailDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemEditeDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemPageDTO;
import com.smartflow.dto.MaintenanceTaskPlan.TaskPlanSaveOutputDTO;

public interface MaintenanceItemService {
	//With the  Paramaters Of PageSize , PageIndex, MaintenanceTaskPlanName,FacilityName。
	//result PagesDTO
List<MaintenanceItemPageDTO> pageDTO(Integer pageSize,Integer pageIndex,String maintenanceItemName);
//Get MaintenanceItemData By The ID
MaintenanceItemDetailDTO getDTOById(Integer Id);
//Edite data for MaintenanceItem
boolean saveEditeData (MaintenanceItemEditeDTO maintenanceItemEditeDTO);
//Save data for MaintenanceItem
boolean saveAddData(MaintenanceItemAddDTO maintenanceItemAddDTO);
//get edite data for instialize
MaintenanceItemEditeDTO getMaintenanceItemEditeDTO(Integer Id);

	/**
	 *
	 * @param pageSize
	 * @param pageIndex
	 * @param maintenanceItemName
	 * @return
	 */
		Integer countAll(Integer pageSize,Integer pageIndex,String maintenanceItemName);

}
