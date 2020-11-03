package com.smartflow.service;

import java.util.List;
import java.util.Map;

import com.smartflow.dto.AddMaintenanceRecordInputDTO;
import com.smartflow.dto.AddMeasurementRecordDTO;
import com.smartflow.dto.TaskDetailOutputDTO;
import com.smartflow.dto.TaskListInputDTO;
import com.smartflow.dto.TaskListOutputDTO;
import com.smartflow.dto.RoleGroupTaskListInputDTO.TaskIdList;
import com.smartflow.model.MeasurementDataRecord;
import com.smartflow.model.PMActionRecord;

public interface MyTaskService {
	/**
	 * 获取设备下拉框
	 * @return
	 */
	public List<Map<String,Object>> getFacilityList();
	/**
	 * 通过开始时间、结束时间、状态、设备查询我的任务列表总条数
	 * @param taskListInputDTO
	 * @return
	 */
	public Integer getTotalCountRoleGroupTaskListByCondition(TaskListInputDTO taskListInputDTO);
	/**
	 * 通过开始时间、结束时间、状态、设备查询我的任务列表
	 * @param roleGroupTaskListConditionDTO
	 * @return
	 */
	public List<TaskListOutputDTO> getRoleGroupTaskListByCondition(TaskListInputDTO taskListInputDTO);
	/**
	 * 通过开始时间、结束时间、状态、设备查询我的任务列表总条数
	 * @param taskListInputDTO
	 * @return
	 */
	public Integer getTotalCountWorkOrderListByCondition(TaskListInputDTO taskListInputDTO);
	/**
	 * 通过开始时间、结束时间、状态、设备查询我的任务列表
	 * @param taskListInputDTO
	 * @return
	 */
	public List<TaskListOutputDTO> getWorkOrderListByCondition(TaskListInputDTO taskListInputDTO);
	/**
	 * 根据任务id查询任务，实现打印功能
	 * @param taskIdList
	 * @return
	 */
	public List<TaskListOutputDTO> getRoleGroupTaskListByTaskIdList(TaskIdList taskIdList);
	/**
	 * 根据任务id查询任务，实现打印功能
	 * @param workOrderIdList
	 * @return
	 */
	public List<TaskListOutputDTO> getRoleGroupTaskListByWorkOrderIdList(TaskIdList workOrderIdList);
	/**
	 * 根据任务id查询任务详情
	 * @param taskId
	 * @return
	 */
	public TaskDetailOutputDTO getTaskDetailByTaskId(Integer taskId);
	/**
	 * 获取人员下拉框
	 * @return
	 */
	public List<Map<String,Object>> getUserList();
	/**
	 * 根据用户id查询角色id
	 * @param userId
	 * @return
	 */
	public Integer getRoleIdByUserId(Integer userId);
	/**
	 * 新增维保记录
	 * @param addMaintenanceRecordInputDTO
	 * @param addMeasurementRecordDTO
	 */
	public void addMaintenanceRecord(AddMaintenanceRecordInputDTO addMaintenanceRecordInputDTO, AddMeasurementRecordDTO addMeasurementRecordDTO);
	/**
	 * 新增维保记录
	 * @param pmActionRecord
	 * @param measurementDataRecord
	 */
	public void addMaintenanceRecord(PMActionRecord pmActionRecord, MeasurementDataRecord measurementDataRecord);
}
