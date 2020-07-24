package com.smartflow.dao;

import java.util.List;
import java.util.Map;

import com.smartflow.dto.AssignmentTaskInitOutputDTO.AssignmentTaskInitOutputRowDTO;
import com.smartflow.dto.RoleGroupTaskListInputDTO;
import com.smartflow.dto.RoleGroupTaskListOutputRowDTO;
import com.smartflow.model.Role;
/**
 * 我的角色组任务
 * @author smartflow
 *
 */
public interface MyRoleGroupTaskListDao {
	/**
	 * 根据用户id查询按钮
	 * @param userId
	 * @return
	 */
	public List<String> getVisitButtonListByUserId(Integer userId);

	/**
	 * 根据当前登陆用户获取角色组下拉框
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getRoleGroupListByUserId(Integer userId);	
	/**
	 * 获取角色组下拉框
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getRoleGroupList();	
	
	/**
	 * 通过开始时间、结束时间、状态、角色组查询我的角色组任务清单列表总条数
	 * @param roleGroupTaskListConditionDTO
	 * @return
	 */
	public Integer getTotalCountRoleGroupTaskListByCondition(RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO);
	/**
	 * 通过开始时间、结束时间、状态、角色组查询我的角色组任务清单列表
	 * @param roleGroupTaskListConditionDTO
	 * @return
	 */
	public List<RoleGroupTaskListOutputRowDTO> getRoleGroupTaskListByCondition(RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO);
	/**
	 * 根据workOrderItemId查询当前任务状态
	 * @param workOrderItemId
	 * @return
	 */
	public Integer getStatusByWorkOrderItemId(Integer workOrderItemId);
	/**
	 * 根据workOrderItemId修改当前任务状态和领取任务人
	 * @param workOrderItemId
	 * @param userId
	 */
	public void updateStatusAndUserIdByWorkOrderItemId(Integer workOrderItemId,Integer userId);
	/**
	 * 根据workOrderItemId修改当前任务状态和领取任务人和负责角色id
	 * @param workOrderItemId
	 * @param userId
	 * @param roleId
	 */
	public void updateStatusAndUserIdAndRoleIdByWorkOrderItemId(Integer workOrderItemId,Integer userId, Integer roleId);
	/**
	 * 根据用户id查询用户对应的角色名
	 * @param userId
	 * @return
	 */
	public List<String> getRoleNameByUserId(Integer userId);
	/**
	 * 根据角色id查询角色名
	 * @param roleId
	 * @return
	 */
	public String getRoleNameByRoleId(Integer roleId);
	/**
	 * 根据workOrderItemId查询任务详情
	 * @param workOrderItemIdList
	 * @return
	 */
	public List<AssignmentTaskInitOutputRowDTO> getAssignmentTaskInitDTOByWorkOrderItemId(List<Integer> workOrderItemIdList);
	/**
	 *  (根据角色id)获取人员下拉框
	 * @param roleId
	 * @return
	 */
	public List<Map<String,Object>> getUserList(Integer roleId);

}
