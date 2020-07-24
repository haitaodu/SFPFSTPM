package com.smartflow.dao;

import java.util.List;

import com.smartflow.dto.MaintenanceItem.MaintenanceItemAddDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemDetailDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemEditeDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemPageDTO;
import com.smartflow.model.MaintenanceItem;

public interface MaintenanceItemDao {
	//With the  Paramaters Of PageSize , PageIndex, MaintenanceTaskPlanName,FacilityName。
	//result PagesDTO
List<MaintenanceItemPageDTO> pageDTO(Integer pageSize,Integer pageIndex,String maintenanceItemName);
//Get MaintenanceItemData By The ID
MaintenanceItemDetailDTO getDTOById(Integer Id);
//Edite data for MaintenanceItem
boolean saveEditeData (MaintenanceItemEditeDTO maintenanceItemEditeDTO);
//Save data for MaintenanceItem
boolean saveAddData(MaintenanceItemAddDTO maintenanceItemAddDTO);
//get  MaintenanceItem by id
MaintenanceItem getMaintenanceById(Integer id);
//get count for the data
Integer countAll(Integer pageSize,Integer pageIndex,String maintenanceItemName);

	/**
	 * 根据相关Id做加假删除处理
	 * @param id Id号
	 */
	void del(int id);
}
