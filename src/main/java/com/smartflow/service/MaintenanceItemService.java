package com.smartflow.service;

import com.smartflow.dto.maintenanceitem.*;

import java.util.List;

/**
 * @author haita
 */
public interface MaintenanceItemService {

	/**
	 * 分页查询
	 * @param maintenanceItemSearchDTO 分页查询参数
	 * @return 分页查询数据List
	 */
	List<MaintenanceItemPageDTO> pageDTO(MaintenanceItemSearchDTO maintenanceItemSearchDTO);

	/**
	 * 根据id查找设备详情
	 * @param id id
	 * @return 返回设备
	 */
	MaintenanceItemDetailDTO getDtoById(Integer id);

	/**
	 * 保存编辑项
	 * @param maintenanceItemEditeDTO 编辑项
	 * @return 是否编辑成功
	 */
	boolean saveEditeData (MaintenanceItemEditeDTO maintenanceItemEditeDTO);

	/**
	 * 保存
	 * @param maintenanceItemAddDTO 保存dto
	 * @return 返回是否保存成功
	 */
	boolean saveAddData(MaintenanceItemAddDTO maintenanceItemAddDTO);

	/**
	 * 根据id查找到编辑dto
	 * @param id id
	 * @return 返回编辑dto
	 */
	MaintenanceItemEditeDTO getMaintenanceItemEditeDTO(Integer id);

	/**
	 * 查找总的条目数用于分页查询
	 * @param maintenanceItemName 编辑名
	 * @return 返回总的条目数
	 */
	Integer countAll(String maintenanceItemName);

	/**
	 * 删除，根据id列表
	 * @param list id列表
	 * @return 是否删除成功
	 */
	boolean delByList(List<Integer> list );

}
