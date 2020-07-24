package com.smartflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.smartflow.dao.MaintenanceItemDao;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemAddDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemDetailDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemEditeDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemPageDTO;
import com.smartflow.dto.MaintenanceTaskPlan.TaskPlanSaveOutputDTO;
import com.smartflow.model.MaintenanceItem;
@Service
public class MaintenanceItemServiceImpl implements MaintenanceItemService{
@Autowired
MaintenanceItemDao maintenanceItemDao;
	@Override
	public List<MaintenanceItemPageDTO> pageDTO(Integer pageSize, Integer pageIndex, String maintenanceItemName) {
		// TODO Auto-generated method stub
		return maintenanceItemDao.pageDTO(pageSize, pageIndex, maintenanceItemName);
	}
	@Override
	public MaintenanceItemDetailDTO getDTOById(Integer Id) {
		// TODO Auto-generated method stub
		return maintenanceItemDao.getDTOById(Id);
	}
	@Override
	public boolean saveEditeData(MaintenanceItemEditeDTO maintenanceItemEditeDTO) {
		// TODO Auto-generated method stub
		return maintenanceItemDao.saveEditeData(maintenanceItemEditeDTO);
	}
	@Override
	public boolean saveAddData(MaintenanceItemAddDTO maintenanceItemAddDTO) {
		// TODO Auto-generated method stub
		return maintenanceItemDao.saveAddData(maintenanceItemAddDTO);
	}
	@Override
	public MaintenanceItemEditeDTO getMaintenanceItemEditeDTO(Integer Id) {
		// TODO Auto-generated method stub
		MaintenanceItemEditeDTO maintenanceItemEditeDTO=new MaintenanceItemEditeDTO();
		MaintenanceItem maintenanceItem=maintenanceItemDao.getMaintenanceById(Id);
		if (maintenanceItem==null) {
			return null;
		}
		maintenanceItemEditeDTO.setId(maintenanceItem.getId());
		maintenanceItemEditeDTO.setName(maintenanceItem.getName());
		maintenanceItemEditeDTO.setStateId(maintenanceItem.getState());
		maintenanceItemEditeDTO.setTypeId(maintenanceItem.getCategory());;
		return maintenanceItemEditeDTO;
	}
	@Override
	public Integer countAll(Integer pageSize, Integer pageIndex, String maintenanceItemName) {
		// TODO Auto-generated method stub
		return maintenanceItemDao.countAll(pageSize, pageIndex, maintenanceItemName)
				;
	}



}
