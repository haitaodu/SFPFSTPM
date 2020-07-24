package com.smartflow.dao;

import java.util.List;

import com.smartflow.dto.AssignmentsListOutputDTO;
import com.smartflow.dto.GetAssignmentsListConditionDTO;
import com.smartflow.dto.MaintenanceContentOutputDTO;
import com.smartflow.dto.WorkAssignmentRecordOutputDTO;
import com.smartflow.model.WorkOrder;
/**
 * 派工单列表
 * @author smartflow
 *
 */
public interface AssignmentsListDao {
	/**
	 * 根据派工单号、开始日期、结束日期、目标设备名、负责人、是否显示已完成项等条件查询派工清单总条数
	 * @param getAssignmentsListConditionDTO
	 * @return
	 */
	public Integer getTotalCountWorkOrderListByCondition(GetAssignmentsListConditionDTO getAssignmentsListConditionDTO);

	/**
	 * 根据派工单号、开始日期、结束日期、目标设备名、负责人、是否显示已完成项等条件查询派工清单
	 * @param getAssignmentsListConditionDTO
	 * @return
	 */
	public List<AssignmentsListOutputDTO> getWorkOrderListByCondition(GetAssignmentsListConditionDTO getAssignmentsListConditionDTO);

	/**
	 * 根据workOrderId删除WorkOrderItem
	 * @param workOrderId
	 */
	public void updateWorkOrderItemByWorkOrderId(Integer workOrderId);
	
	/**
	 * 根据workOrderId删除workOrder
	 * @param workOrderId
	 */
	public void updateWorkOrderByWorkOrderId(Integer workOrderId);
	/**
	 * 根据workOrderId查询WorkOrderItem
	 * @param workOrderId
	 * @return
	 */
	public List<MaintenanceContentOutputDTO> getWorkOrderItemByWorkOrderId(Integer workOrderId);
	/**
	 * 根据workItemId查询工作分配记录
	 * @param workItemId
	 * @return
	 */
	public List<WorkAssignmentRecordOutputDTO> getWorkAssignmentRecordByWorkItemId(Integer workItemId);
	
	/**
	 * 根据设备id查询工单分配任务者
	 * @param facilityId
	 * @return
	 */
	public List<String> getWorkOrderLeaderListByFacilityId(Integer facilityId);
}
