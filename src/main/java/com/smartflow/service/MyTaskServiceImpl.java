package com.smartflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartflow.dao.MyTaskDao;
import com.smartflow.dto.AddMaintenanceRecordInputDTO;
import com.smartflow.dto.AddMeasurementRecordDTO;
import com.smartflow.dto.RoleGroupTaskListInputDTO.TaskIdList;
import com.smartflow.dto.TaskDetailOutputDTO;
import com.smartflow.dto.TaskListInputDTO;
import com.smartflow.dto.TaskListOutputDTO;
import com.smartflow.model.MeasurementDataRecord;
import com.smartflow.model.PMActionRecord;
@Service
public class MyTaskServiceImpl implements MyTaskService {

	@Autowired
	MyTaskDao myTaskDao;
	@Override
	public List<Map<String, Object>> getFacilityList() {
		return myTaskDao.getFacilityList();
	}
	@Override
	public Integer getTotalCountRoleGroupTaskListByCondition(TaskListInputDTO taskListInputDTO) {
		return myTaskDao.getTotalCountRoleGroupTaskListByCondition(taskListInputDTO);
	}
	@Override
	public List<TaskListOutputDTO> getRoleGroupTaskListByCondition(TaskListInputDTO taskListInputDTO) {
		return myTaskDao.getRoleGroupTaskListByCondition(taskListInputDTO);
	}

	@Override
	public Integer getTotalCountWorkOrderListByCondition(TaskListInputDTO taskListInputDTO) {
		return myTaskDao.getTotalCountWorkOrderListByCondition(taskListInputDTO);
	}

	@Override
	public List<TaskListOutputDTO> getWorkOrderListByCondition(TaskListInputDTO taskListInputDTO) {
		return myTaskDao.getWorkOrderListByCondition(taskListInputDTO);
	}

	@Override
	public List<TaskListOutputDTO> getRoleGroupTaskListByTaskIdList(TaskIdList taskIdList) {		
		return myTaskDao.getRoleGroupTaskListByTaskIdList(taskIdList);
	}

	@Override
	public List<TaskListOutputDTO> getRoleGroupTaskListByWorkOrderIdList(TaskIdList workOrderIdList) {
		return myTaskDao.getRoleGroupTaskListByWorkOrderIdList(workOrderIdList);
	}

	@Override
	public TaskDetailOutputDTO getTaskDetailByTaskId(Integer taskId) {
		return myTaskDao.getTaskDetailByTaskId(taskId);
	}
	@Override
	public List<Map<String, Object>> getUserList() {
		return myTaskDao.getUserList();
	}
	@Transactional
	@Override
	public void addMaintenanceRecord(AddMaintenanceRecordInputDTO addMaintenanceRecordInputDTO,
			AddMeasurementRecordDTO addMeasurementRecordDTO) {
		myTaskDao.addMaintenanceRecord(addMaintenanceRecordInputDTO, addMeasurementRecordDTO);
	}
	@Override
	public Integer getRoleIdByUserId(Integer userId) {
		return myTaskDao.getRoleIdByUserId(userId);
	}
	@Transactional
	@Override
	public void addMaintenanceRecord(PMActionRecord pmActionRecord, MeasurementDataRecord measurementDataRecord) {
		myTaskDao.addMaintenanceRecord(pmActionRecord, measurementDataRecord);
	}
	
}
