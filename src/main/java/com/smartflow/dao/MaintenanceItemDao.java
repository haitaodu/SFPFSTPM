package com.smartflow.dao;

import java.util.List;

import com.smartflow.dto.maintenanceitem.MaintenanceItemAddDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemDetailDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemEditeDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemPageDTO;
import com.smartflow.model.MaintenanceItem;

/**
 * @author haita
 */
public interface MaintenanceItemDao {
	/**
	 * 分页查询
	 * @param pageSize 分页大小
	 * @param pageIndex 分页页码
	 * @param maintenanceItemName 维保项名字
	 * @return 返回维保项分页
	 */
	List<MaintenanceItemPageDTO> pageDTO(Integer pageSize,
										 Integer pageIndex,
										 String maintenanceItemName);

	/**
	 * 根据id查找
	 * @param id 查找详情的id
	 * @return 返回详情
	 */
	MaintenanceItemDetailDTO getDtoById(Integer id);

	/**
	 *更新数据
	 * @param maintenanceItemEditeDTO 更新数据
	 * @return 是否成功
	 */
    boolean saveEditeData (MaintenanceItemEditeDTO maintenanceItemEditeDTO);

	/**
	 *保存数据
	 * @param maintenanceItemAddDTO 保存数据dto
	 * @return 返回是否保存
	 */
	boolean saveAddData(MaintenanceItemAddDTO maintenanceItemAddDTO);

	/**
	 *根据id查找数据库实体
	 * @param id id
	 * @return 返回数据库实体
	 */
	MaintenanceItem getMaintenanceById(Integer id);

	/**
	 *
	 * @param maintenanceItemName 维保项名称
	 * @return 返回总的条目数
	 */
	Integer countAll(String maintenanceItemName);

	/**
	 * 根据相关Id做加假删除处理
	 * @param id Id号
	 */
	void del(int id);

	/**
	 * 是否注册
	 * @return 查看是否被注册过
	 */
	boolean isRegister(String name);
}
