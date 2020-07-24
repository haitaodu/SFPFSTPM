package com.smartflow.service;

import java.util.List;
import java.util.Map;

import com.smartflow.dto.AddAssignListInputDTO;
import com.smartflow.model.Material;
import com.smartflow.model.Role;
import com.smartflow.model.UserModel;
/**
 * 新增派工单
 * @author smartflow
 *
 */
public interface AddAssignmentsListService {

	
	/**
	 * 获取维保项下拉框
	 * @return
	 */
	public List<Map<String,Object>> getMaintenanceItemList();
	/**
	 * 根据设备id查询工作计划id
	 * @param facilityId
	 * @return
	 */
//	public List<Integer> getWorkPlanIdByFacilityId(Integer  facilityId);
	/**
	 * 根据设备id查询BOMHeadId
	 * @param facilityId
	 * @return
	 */
	public Integer getBOMHeadIdByFacilityId(Integer facilityId);
	/**
	 * 根据物料号查询物料id
	 * @param materialNumber
	 * @return
	 */
	public Integer getMaterialIdByMaterialNumber(String materialNumber);
	/**
	 * 根据物料号查询物料
	 * @param materialNumber
	 * @return
	 */
//	public Material getMaterialByMaterialNumber(String materialNumber);
	/**
	 * 新增派工单和维保步骤
	 * @param addAssignListInputDTO
	 */
	public void addWorkOrderAndMaintenanceSteps(AddAssignListInputDTO addAssignListInputDTO);
	/**
	 * 根据维保项名称查询维保项id
	 * @param maintenanceItemName 维保项名称
	 * @return 维保项id
	 */
	public Integer getMaintenanceItemIdByMaintenanceItem(String maintenanceItemName);
	
	/**
	 * 根据角色名查询角色id
	 * @param roleName 角色名
	 * @return 角色
	 */
	public Role getRoleIdByRoleName(String roleName);
	/**
	 * 根据角色id查询角色
	 * @param roleId
	 * @return
	 */
	public Role getRoleByRoleId(Integer roleId);
	/**
	 * 根据用户id查询用户
	 * @param userId
	 * @return
	 */
	public UserModel getUserByUserId(Integer userId);
	
	/**
	 * 根据用户名查用户
	 * @param userName
	 * @return
	 */
	public UserModel getUserByUserName(String userName);
}
