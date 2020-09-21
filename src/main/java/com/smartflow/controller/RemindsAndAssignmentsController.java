package com.smartflow.controller;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartflow.dto.AssignmentsListOutputDTO;
import com.smartflow.dto.CalendarDataDTO;
import com.smartflow.dto.ConfirmUnifiedArrangementsDTO;
import com.smartflow.dto.EditAssignListInitDTO;
import com.smartflow.dto.EditAssignListInitDTO.EditAssignListRowDTO;
import com.smartflow.dto.EditAssignListInputDTO;
import com.smartflow.dto.EditMaintenanceStepsInitOutputDTO;
import com.smartflow.dto.EditMaintenanceStepsInputDTO;
import com.smartflow.dto.GetAssignmentsListConditionDTO;
import com.smartflow.dto.MaintenanceAssignmentCalendarDTO.CalendarData;
import com.smartflow.dto.MaintenanceContentOutputDTO;
import com.smartflow.dto.StateAndPeriodicTypeInitDTO;
import com.smartflow.dto.WorkAssignmentRecordOutputDTO;
import com.smartflow.dto.WorkItemContentPreviewDTO;
import com.smartflow.model.BOMHeadModel;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.MaintenanceItem;
import com.smartflow.model.Material;
import com.smartflow.model.Reminder;
import com.smartflow.model.Role;
import com.smartflow.model.TPMWorkPlan_Reminder;
import com.smartflow.model.UserModel;
import com.smartflow.model.WorkItem;
import com.smartflow.model.WorkOrder;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.model.WorkPlan;
import com.smartflow.model.WorkPlanExcutionState;
import com.smartflow.service.AddAssignmentsListService;
import com.smartflow.service.AssignmentsListService;
import com.smartflow.service.BOMHeadService;
import com.smartflow.service.EditAssignmentsListService;
import com.smartflow.service.MyRoleGroupTaskListSerivce;
import com.smartflow.service.MyTaskService;
import com.smartflow.service.RemindsAndAssignmentsCalendarService;
import com.smartflow.util.GetDateByCRONExpressionUtil;
import com.smartflow.util.KeyLabelToLabel;
import com.smartflow.util.KeyLabelToLabelImpl;
import com.smartflow.util.PropertyUtil;
import com.smartflow.util.StringUtil;
import com.smartflow.dao.BOMHeadDao;
import com.smartflow.dao.FacilityDao;
import com.smartflow.dto.AddAssignListInitDTO;
import com.smartflow.dto.AddAssignListInputDTO;
import com.smartflow.dto.AddMaintenanceStepsInitOutputDTO;
/**
 * 提醒及派工
 * @author smartflow
 *
 */
@RestController
@RequestMapping("/api/RemindsAndAssignments")
public class RemindsAndAssignmentsController extends BaseController {
	private static final Logger logger = Logger.getLogger(RemindsAndAssignmentsController.class);
	@Autowired
	AssignmentsListService assignmentsListService;
	@Autowired
	MyRoleGroupTaskListSerivce myRoleGroupTaskListSerivce;
	@Autowired
	MyTaskService myTaskService;
	@Autowired
	AddAssignmentsListService addAssignmentsListService;
	@Autowired
	BOMHeadService bomHeadService;
	@Autowired
	EditAssignmentsListService editAssignmentsListService;
	@Autowired
	BOMHeadService bomHeadServcie;
	@Autowired
	RemindsAndAssignmentsCalendarService remindsAndAssignmentsCalendarService;
	@Autowired
	BOMHeadDao bomHeadDao;
	/**
	 * 获取维保及派工日历表数据
	 * @param flag 月/周/天
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetMaintenanceAssignmentCalendar",method=RequestMethod.GET)
	public Map<String,Object> GetMaintenanceAssignmentCalendar(){
		Map<String,Object> json = new HashMap<>();
		/**	TestData	
		Map<String,Object> calendarMap = new HashMap<>();
		List<CalendarData> calendarDataList1 = new ArrayList<>();
		CalendarData calendarData1 = new CalendarData("设备1", "工作计划1", "gray", 4, 4);
		CalendarData calendarData2 = new CalendarData("设备2", "工作计划2", "blue", 5, 4);
		CalendarData calendarData3 = new CalendarData("设备3", "工作计划3", "gray", 6, 6);
		CalendarData calendarData4 = new CalendarData("设备4", "工作计划4", "blue", 7, 7);
		calendarDataList1.add(calendarData1);
		calendarDataList1.add(calendarData2);
		calendarDataList1.add(calendarData3);
		List<CalendarData> calendarDataList2 = new ArrayList<>();
		calendarDataList2.add(calendarData3);
		List<CalendarData> calendarDataList3 = new ArrayList<>();
		calendarDataList3.add(calendarData4);
		calendarMap.put("2019-08-15", calendarDataList1);
		calendarMap.put("2019-08-22", calendarDataList2);
		calendarMap.put("2019-08-26", calendarDataList3);
		*/
		try{
			Map<String,List<CalendarData>> calendarMap = new HashMap<>();
			List<CalendarDataDTO> calendarDataDTOList = remindsAndAssignmentsCalendarService.getAssignmentsCalendar();
			for (CalendarDataDTO calendarDataDTO : calendarDataDTOList) {
				GetDateByCRONExpressionUtil.init();//静态变量在jvm虚拟机 初始化类的时候建立内存  即使GC回收对象,它也不会回收。静态变量一直增加会内存溢出
				String state = remindsAndAssignmentsCalendarService.getCalendarStateByTPMWorkPlanReminderId(calendarDataDTO.getWorkPlan_ReminderId());
				CalendarData calendarData = new CalendarData(calendarDataDTO.getFacilityName(), calendarDataDTO.getWorkPlanName(), state, calendarDataDTO.getWorkPlanId(), calendarDataDTO.getFacilityId());
				List<Date> dateList = GetDateByCRONExpressionUtil.getDateFromCRONExpression(calendarDataDTO.getStartDateTime(), calendarDataDTO.getEndDateTime(), calendarDataDTO.getCRONExpression());
				List<String> strDateList = GetDateByCRONExpressionUtil.parseDatetoString(dateList);
				for (String strDate : strDateList) {
					List<CalendarData> calendarDataList = null;
					if(!calendarMap.containsKey(strDate)){//不包含重复的key
						calendarDataList = new ArrayList<>();
					}else{//包含重复的key
						calendarDataList = calendarMap.get(strDate);
					}
					calendarDataList.add(calendarData);
					calendarMap.put(strDate, calendarDataList);
				}
			}
			json = this.setJson(200, "日历查询成功！", calendarMap);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "日历查询失败:"+e.getMessage(), -1);
		}
		return json;
	}
	
	/**
	 * 判断所选任务计划，与第一个选择的任务计划是否属于同一设备
	 * @param workPlanId 任务计划id
	 * @return
	
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/JudgeTaskPlanFacilityIsSame",method=RequestMethod.GET)
	public Map<String,Object> judgeTaskPlanFacilityIsSame(@RequestParam List<Integer> WorkPlanIdList){
		Map<String,Object> json = new HashMap<>();
		json = this.setJson(200, "任务计划所属设备相同", true);
		return json;
	}
	 */
	/**
	 * 角色组初始化
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetRoleGroupInit/{WorkPlanId}",method=RequestMethod.GET)
	public Map<String,Object> getRoleGroupInit(@PathVariable("WorkPlanId") Integer WorkPlanId){
		Map<String,Object> json = new HashMap<>();
		Map<String,Object> map = new HashMap<>();
		/**
		Integer AllocatedRoleGroup = 2;
		List<Map<String, Object>> roleGroupList = new ArrayList<>();
		Map<String,Object> roleGroupMap1 = new HashMap<String,Object>();
		Map<String,Object> roleGroupMap2 = new HashMap<String,Object>();
		Map<String,Object> roleGroupMap3 = new HashMap<String,Object>();
		roleGroupMap1.put("key", 1);
		roleGroupMap1.put("label", "维护小组1");
		roleGroupMap2.put("key", 2);
		roleGroupMap2.put("label", "维护小组2");
		roleGroupMap3.put("key", 3);
		roleGroupMap3.put("label", "维护小组3");
		roleGroupList.add(roleGroupMap1);
		roleGroupList.add(roleGroupMap2);
		roleGroupList.add(roleGroupMap3);
		map.put("RoleGroupList", roleGroupList);
		map.put("AllocatedRoleGroup", AllocatedRoleGroup);
		*/
		try{
			List<Map<String, Object>> roleGroupList = myRoleGroupTaskListSerivce.getRoleGroupList();
			Integer AllocatedRoleGroup = remindsAndAssignmentsCalendarService.getRoleGroupIdByWorkPlanId(WorkPlanId);
			map.put("RoleGroupList", roleGroupList);
			map.put("AllocatedRoleGroup", AllocatedRoleGroup);
			json = this.setJson(200, "角色组初始化成功！", map);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "角色组初始化失败:"+e.getMessage(), -1);
		}		
		return json;
	}
	
	/**
	 * 点击统一安排，可以看到工作项内容预览
	 * @param WorkPlanId 工作计划id
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/UnifiedArrangementsPreview",method=RequestMethod.POST)
	public Map<String,Object> unifiedArrangementsPreview(@RequestBody List<Integer> WorkPlanId){
		Map<String,Object> json = new HashMap<>();
		/**
		List<WorkItemContentPreviewDTO> workItemContentDTOList = new ArrayList<>();
		WorkItemContentPreviewDTO workItemContentPreviewDTO1 = new WorkItemContentPreviewDTO(1, "检查氧分析仪", "设备底部", "MPN0001", "校准", new BigDecimal(2.5), "保养小组1", "单一值", new BigDecimal(40), new BigDecimal(20), new BigDecimal(10), "文档名超链接");
		WorkItemContentPreviewDTO workItemContentPreviewDTO2 = new WorkItemContentPreviewDTO(1, "检查炉温1区曲线", "设备中部", "", "校准", new BigDecimal(2.5), "保养小组1", "曲线", new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "文档名超链接");
		WorkItemContentPreviewDTO workItemContentPreviewDTO3 = new WorkItemContentPreviewDTO(1, "检查炉温2区曲线", "设备中部", "", "校准", new BigDecimal(2.5), "保养小组1", "曲线", new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "文档名超链接");
		workItemContentDTOList.add(workItemContentPreviewDTO1);
		workItemContentDTOList.add(workItemContentPreviewDTO2);
		workItemContentDTOList.add(workItemContentPreviewDTO3);
		*/
		try{
			if(WorkPlanId == null || WorkPlanId.isEmpty()){
				return this.setJson(0, "WorkPlanId不允许为空!", -1);
			}
			List<WorkItemContentPreviewDTO> workItemContentDTOList = new ArrayList<>();
			List<WorkItem> workItemList = editAssignmentsListService.getWorkItemByWorkPlanId(WorkPlanId);
			if(workItemList != null && !workItemList.isEmpty()){
				for (WorkItem workItem : workItemList) {
					WorkItemContentPreviewDTO workItemContentPreviewDTO = new WorkItemContentPreviewDTO();
					workItemContentPreviewDTO.setWorkItemId(workItem.getId());
					workItemContentPreviewDTO.setItemName(workItem.getName());
					workItemContentPreviewDTO.setPosition(workItem.getDesignator());
					workItemContentPreviewDTO.setMaterialNumber(workItem.getMaterial() == null ? null : workItem.getMaterial().getMaterialNumber());
					workItemContentPreviewDTO.setMaintenanceItemName(workItem.getMaintenanceItem() == null ? null : workItem.getMaintenanceItem().getName());
					workItemContentPreviewDTO.setWorkLength(new BigDecimal(workItem.getWorkDurationSec()/60));
					workItemContentPreviewDTO.setRoleInCharge(workItem.getRole() == null ? null : workItem.getRole().getRoleName());
					workItemContentPreviewDTO.setMeasurementCategory(workItem.getMeasurementType() == null ? null : PropertyUtil.getMeasurementCategory(workItem.getMeasurementType()));
					workItemContentPreviewDTO.setMeasurementStandardUpperLimit(workItem.getUSL());
					workItemContentPreviewDTO.setMeasurementStandardMiddleValue(workItem.getNormalValue());
					workItemContentPreviewDTO.setMeasurementStandardLowerLimit(workItem.getLSL());
					workItemContentPreviewDTO.setDescriptionDocument(workItem.getGuideDocURI());
					workItemContentDTOList.add(workItemContentPreviewDTO);
				}
			}
			json = this.setJson(200, "查询成功！", workItemContentDTOList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "查询失败:"+e.getMessage(), -1);
		}
		return json;			
	}

	/**
	 * 确认统一安排，添加tpm.WorkOrderItem表和tpm.WorkPlanExcutionState表
	 * @param confirmUnifiedArrangementsDTO 表单数据
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/ConfirmUnifiedArrangements",method=RequestMethod.POST)
	public Map<String,Object> confirmUnifiedArrangements(@RequestBody ConfirmUnifiedArrangementsDTO confirmUnifiedArrangementsDTO){
		Map<String,Object> json = new HashMap<>();
		try{
			remindsAndAssignmentsCalendarService.addWorkOrderAndWorkOrderItemAndWorkPlanExcutionState(confirmUnifiedArrangementsDTO);			
			json = this.setJson(200, "添加成功！", null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "添加失败:"+e.getMessage(), -1);
		}
		return json;			
	}
	/*************************************派工清单******************************************/
	
	/**
	 * 派工列表初始化状态下拉框、周期类型下拉框
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetAssignmentsListInit",method=RequestMethod.GET)
	public Map<String,Object> GetAssignmentsListInit(){
		Map<String,Object> json = new HashMap<>();	
		StateAndPeriodicTypeInitDTO stateAndPeriodicTypeInitDTO = new StateAndPeriodicTypeInitDTO();
		/**		 
		List<Map<String,Object>> stateList = new ArrayList<Map<String,Object>>();
		Map<String,Object> stateMap1 = new HashMap<String, Object>();
		Map<String,Object> stateMap2 = new HashMap<String, Object>();
		Map<String,Object> stateMap3 = new HashMap<String, Object>();
		Map<String,Object> stateMap4 = new HashMap<String, Object>();
		Map<String,Object> stateMap5 = new HashMap<String, Object>();
		Map<String,Object> stateMap6 = new HashMap<String, Object>();
		Map<String,Object> stateMap7 = new HashMap<String, Object>();
		Map<String,Object> stateMap8 = new HashMap<String, Object>();
		Map<String,Object> stateMap9 = new HashMap<String, Object>();
		stateMap1.put("key", 1);
		stateMap1.put("label", "新");
		stateMap2.put("key", 2);
		stateMap2.put("label", "已分配");
		stateMap3.put("key", 3);
		stateMap3.put("label", "已完成");
		stateMap4.put("key", 4);
		stateMap4.put("label", "已审核");
		stateMap5.put("key", 5);
		stateMap5.put("label", "已审核完成");
		stateMap6.put("key", -1);
		stateMap6.put("label", "已取消");
		stateMap7.put("key", -2);
		stateMap7.put("label", "已拒绝");
		stateMap8.put("key", -3);
		stateMap8.put("label", "已重新安排");
		stateMap9.put("key", 0);
		stateMap9.put("label", "已关闭");
		stateList.add(stateMap1);
		stateList.add(stateMap2);
		stateList.add(stateMap3);
		stateList.add(stateMap4);
		stateList.add(stateMap5);
		stateList.add(stateMap6);
		stateList.add(stateMap7);
		stateList.add(stateMap8);
		stateList.add(stateMap9);
		stateAndPeriodicTypeInitDTO.setStateList(stateList);
		List<Map<String, Object>> periodicTypeList = new ArrayList<>();
		Map<String, Object> periodicTypeMap1 = new HashMap<>();
		periodicTypeMap1.put("key", 1);
		periodicTypeMap1.put("label", "临时的");
		Map<String, Object> periodicTypeMap2 = new HashMap<>();
		periodicTypeMap2.put("key", 2);
		periodicTypeMap2.put("label", "周期性的");
		periodicTypeList.add(periodicTypeMap1);
		periodicTypeList.add(periodicTypeMap2);
		stateAndPeriodicTypeInitDTO.setPeriodicTypeList(periodicTypeList);
		*/
		try{
			List<Map<String,Object>> stateList = PropertyUtil.getStautsList();
			stateAndPeriodicTypeInitDTO.setStateList(stateList);
			List<Map<String, Object>> periodicTypeList = PropertyUtil.getPeriodicTypeList();
			stateAndPeriodicTypeInitDTO.setPeriodicTypeList(periodicTypeList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "初始化失败:"+e.getMessage(), -1);
		}
		json = this.setJson(200, "初始化成功！", stateAndPeriodicTypeInitDTO);
		return json;
	}
	
	
	/**
	 * 根据派工单号、开始日期、结束日期、目标设备名、负责人、是否显示已完成项等条件查询派工清单
	 * @param confirmUnifiedArrangementsDTO
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetAssignmentsListByCondition",method=RequestMethod.POST)
	public Map<String,Object> getAssignmentsListByCondition(@RequestBody GetAssignmentsListConditionDTO getAssignmentsListConditionDTO){
		Map<String,Object> json = new HashMap<>();		
		Map<String,Object> map = new HashMap<>();
		try{
			/**
			List<AssignmentsListOutputDTO> assignmentsListOutputDTOList = new ArrayList<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			AssignmentsListOutputDTO assignmentsListOutputDTO1 = new AssignmentsListOutputDTO(1, "PMWO001", "小保养", 1, "临时的", "生效", "", "", null);
			AssignmentsListOutputDTO assignmentsListOutputDTO2 = new AssignmentsListOutputDTO(2, "PMWO002", "大保养", 1, "临时的", "生效", "", "", null);
			AssignmentsListOutputDTO assignmentsListOutputDTO3 = new AssignmentsListOutputDTO(3, "PMWO003", "点检", 1, "临时的", "生效", "", "", null);
			AssignmentsListOutputDTO assignmentsListOutputDTO4 = new AssignmentsListOutputDTO(4, "PMWO004", "校正", 1, "已完成", "生效", "", "", null);
			AssignmentsListOutputDTO assignmentsListOutputDTO5 = new AssignmentsListOutputDTO(5, "PMWO005", "回流炉小保养", 1, "周期性的", "生效", "Reflow Oven001", "保养组1", sdf.parse("2016-06-01 00:00:00"));
			assignmentsListOutputDTOList.add(assignmentsListOutputDTO1);
			assignmentsListOutputDTOList.add(assignmentsListOutputDTO2);
			assignmentsListOutputDTOList.add(assignmentsListOutputDTO3);
			assignmentsListOutputDTOList.add(assignmentsListOutputDTO4);
			assignmentsListOutputDTOList.add(assignmentsListOutputDTO5);
			*/
			List<AssignmentsListOutputDTO> assignmentsListOutputDTOList = assignmentsListService.getWorkOrderListByCondition(getAssignmentsListConditionDTO);
			Integer RowCount = assignmentsListService.getTotalCountWorkOrderListByCondition(getAssignmentsListConditionDTO);
			map.put("RowCount", RowCount);
			map.put("Tdto", assignmentsListOutputDTOList);
			json = this.setJson(200, "查询成功！", map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "查询失败:"+e.getMessage(), -1);
		}
		return json;	
	}
	
	/**
	 * 根据WorkOrderId删除派工清单
	 * @param WorkOrderId
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/DeleteAssignmentsListByWorkOrderId/{WorkOrderId}",method=RequestMethod.DELETE)
	public Map<String,Object> deleteAssignmentsListByWorkOrderId(@PathVariable("WorkOrderId") Integer WorkOrderId){
		Map<String,Object> json = new HashMap<>();
		try{
			assignmentsListService.updateWorkOrderItemByWorkOrderId(WorkOrderId);
			assignmentsListService.updateWorkOrderByWorkOrderId(WorkOrderId);
			json = this.setJson(200, "删除成功！", null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "删除失败:"+e.getMessage(), -1);
		}		
		return json;
	}
	/**
	 * 保养内容预览
	 * @param WorkOrderId
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetMaintenanceContentPreviewByWorkOrderId/{WorkOrderId}",method=RequestMethod.GET)
	public Map<String,Object> getMaintenanceContentPreviewByWorkOrderId(@PathVariable("WorkOrderId")Integer WorkOrderId){
		Map<String,Object> json = new HashMap<>();
		List<MaintenanceContentOutputDTO> maintenanceContentOutputDTOList = new ArrayList<>();
		/**
		List<MaintenanceContentOutputDTO> maintenanceContentOutputDTOList = new ArrayList<>();
		MaintenanceContentOutputDTO maintenanceContentOutputDTO1 = new MaintenanceContentOutputDTO(1, "检查氧分析仪", "设备底部", "MPN0001", "校准", new BigDecimal(2.5), "保养小组1", "单一值", new BigDecimal(40), new BigDecimal(20), new BigDecimal(10), "");
		MaintenanceContentOutputDTO maintenanceContentOutputDTO2 = new MaintenanceContentOutputDTO(2, "检查炉温1区曲线", "设备中部", "", "校准", new BigDecimal(2.5), "保养小组1", "曲线", new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "");
		MaintenanceContentOutputDTO maintenanceContentOutputDTO3 = new MaintenanceContentOutputDTO(3, "检查炉温2区曲线", "设备中部", "", "校准", new BigDecimal(2.5), "保养小组1", "曲线", new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "");
		MaintenanceContentOutputDTO maintenanceContentOutputDTO4 = new MaintenanceContentOutputDTO(4, "清洗炉膛", "设备中部", "", "清洁", new BigDecimal(2), "保养小组1", "", null, null, null, "");
		MaintenanceContentOutputDTO maintenanceContentOutputDTO5 = new MaintenanceContentOutputDTO(5, "更换过滤器", "设备后侧", "MPN0002", "更换", new BigDecimal(1), "保养小组1", "", null, null, null, "");
		maintenanceContentOutputDTOList.add(maintenanceContentOutputDTO1);
		maintenanceContentOutputDTOList.add(maintenanceContentOutputDTO2);
		maintenanceContentOutputDTOList.add(maintenanceContentOutputDTO3);
		maintenanceContentOutputDTOList.add(maintenanceContentOutputDTO4);
		maintenanceContentOutputDTOList.add(maintenanceContentOutputDTO5);
		*/
		try{
			maintenanceContentOutputDTOList = assignmentsListService.getWorkOrderItemByWorkOrderId(WorkOrderId);
			json = this.setJson(200, "查询成功！", maintenanceContentOutputDTOList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "查询失败:"+e.getMessage(), -1);
		}
		return json;
	}
	
	/**
	 * 工作分配记录
	 * @param WorkItemId
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetWorkAssignmentRecordByWorkItemId/{WorkItemId}",method=RequestMethod.GET)
	public Map<String,Object> getWorkAssignmentRecordByWorkItemId(@PathVariable("WorkItemId")Integer WorkOrderItemId){
		Map<String,Object> json = new HashMap<>();
		/**
		List<WorkAssignmentRecordOutputDTO> workAssignmentRecordOutputDTOList = new ArrayList<>();
		WorkAssignmentRecordOutputDTO workAssignmentRecordOutputDTO = new WorkAssignmentRecordOutputDTO(1, "PMWO001", "检查氧分析仪", "设备底部", new Date(), "分配人001", "维保小组1", "张三");
		workAssignmentRecordOutputDTOList.add(workAssignmentRecordOutputDTO);
		*/
		try{
			List<WorkAssignmentRecordOutputDTO> workAssignmentRecordOutputDTOList = assignmentsListService.getWorkAssignmentRecordByWorkItemId(WorkOrderItemId);
			json = this.setJson(200, "查询成功！", workAssignmentRecordOutputDTOList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "查询失败:"+e.getMessage(), null);
		}		
		return json;
	}
	
	/*******************************新增派工单*******************************/
	/**
	 * 新增派工单初始化目标设备下拉框、责任角色下拉框
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/AddAssignListInit",method=RequestMethod.GET)
	public Map<String,Object> addAssignListInit(){
		Map<String,Object> json = new HashMap<>();
		/**
		AddAssignListInitDTO addAssignListInit = new AddAssignListInitDTO();
		List<Map<String,Object>> targetFacilityList = new ArrayList<>();
		Map<String,Object> targetFacilityMap1 = new HashMap<>();
		targetFacilityMap1.put("key", 1);
		targetFacilityMap1.put("label", "ReflowOven0001");
		Map<String,Object> targetFacilityMap2 = new HashMap<>();
		targetFacilityMap2.put("key", 2);
		targetFacilityMap2.put("label", "ReflowOven0002");
		targetFacilityList.add(targetFacilityMap1);
		targetFacilityList.add(targetFacilityMap2);
		List<Map<String,Object>> roleInChargeList = new ArrayList<>();
		Map<String,Object> roleInChargeMap1 = new HashMap<>();
		roleInChargeMap1.put("key", 1);
		roleInChargeMap1.put("label", "保养小组1");
		Map<String,Object> roleInChargeMap2 = new HashMap<>();
		roleInChargeMap2.put("key", 2);
		roleInChargeMap2.put("label", "保养小组2");
		roleInChargeList.add(roleInChargeMap1);
		roleInChargeList.add(roleInChargeMap2);
		addAssignListInit.setTargetFacilityList(targetFacilityList);
		addAssignListInit.setRoleInChargeList(roleInChargeList);
		*/
		try{
			AddAssignListInitDTO addAssignListInit = new AddAssignListInitDTO();
			List<Map<String,Object>> targetFacilityList = myTaskService.getFacilityList();
			addAssignListInit.setTargetFacilityList(targetFacilityList);
			List<Map<String,Object>> roleInChargeList = myRoleGroupTaskListSerivce.getRoleGroupList();
			addAssignListInit.setRoleInChargeList(roleInChargeList);
			json = this.setJson(200, "初始化成功！", addAssignListInit);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "初始化失败:"+e.getMessage(), -1);
		}
		return json;
	}
	/**
	 * 根据目标设备、责任角色、时间等条件查询维保记录
	 * @param maintenanceStepsConditionInputDTO
	 * @return
	 
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetMaintenanceStepsByCondition",method=RequestMethod.POST)
	public Map<String,Object> getMaintenanceStepsByCondition(@RequestBody MaintenanceStepsConditionInputDTO maintenanceStepsConditionInputDTO){
		Map<String,Object> json = new HashMap<>();
		List<GetMaintenanceStepsInputDTO> getMaintenanceStepsInputDTOList = new ArrayList<>();
		GetMaintenanceStepsInputDTO getMaintenanceStepsInputDTO1 = new GetMaintenanceStepsInputDTO(1, "检查氧分析仪", "设备底部", "MPN0001", "校准", new BigDecimal(2.5), "保养小组1", "单一值", new BigDecimal(40), new BigDecimal(20), new BigDecimal(10), "");
		GetMaintenanceStepsInputDTO getMaintenanceStepsInputDTO2 = new GetMaintenanceStepsInputDTO(2, "检查炉温1区曲线", "设备中部", "", "校准", new BigDecimal(2.5), "保养小组1", "曲线", new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "");
		GetMaintenanceStepsInputDTO getMaintenanceStepsInputDTO3 = new GetMaintenanceStepsInputDTO(3, "检查炉温2区曲线", "设备中部", "", "校准", new BigDecimal(2.5), "保养小组1", "曲线", new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "");
		GetMaintenanceStepsInputDTO getMaintenanceStepsInputDTO4 = new GetMaintenanceStepsInputDTO(4, "清洗炉膛", "设备中部", "", "清洁", new BigDecimal(2), "保养小组1", "", null, null, null, "");
		GetMaintenanceStepsInputDTO getMaintenanceStepsInputDTO5 = new GetMaintenanceStepsInputDTO(5, "更换过滤器", "设备后侧", "MPN0002", "更换", new BigDecimal(1), "保养小组1", "", null, null, null, "");
		getMaintenanceStepsInputDTOList.add(getMaintenanceStepsInputDTO1);
		getMaintenanceStepsInputDTOList.add(getMaintenanceStepsInputDTO2);
		getMaintenanceStepsInputDTOList.add(getMaintenanceStepsInputDTO3);
		getMaintenanceStepsInputDTOList.add(getMaintenanceStepsInputDTO4);
		getMaintenanceStepsInputDTOList.add(getMaintenanceStepsInputDTO5);
		json = this.setJson(200, "查询成功！", getMaintenanceStepsInputDTOList);
		return json;
	}
	*/
	
	/**
	 * 编辑维保步骤
	 * @return
	 
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/EditMaintenanceSteps",method=RequestMethod.PUT)
	public Map<String,Object> editMaintenanceSteps(){
		Map<String,Object> json = new HashMap<>();
		json = this.setJson(200, "编辑成功！", null);
		return json;
	}
	*/
	
	/**
	 * 删除维保步骤
	 * @param WorkItemId
	 * @return
	 
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/DeleteMaintenanceStepsByWrokItemId/{WorkItemId}",method=RequestMethod.DELETE)
	public Map<String,Object> deleteMaintenanceStepsByWrokItemId(@PathVariable Integer WorkItemId){
		Map<String,Object> json = new HashMap<>();
		json = this.setJson(200, "删除成功！", null);
		return json;
	}
	*/
	/**
	 * 新增维保步骤初始化
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/AddMaintenanceStepsInit/{FacilityId}",method=RequestMethod.GET)
	public Map<String,Object> addMaintenanceStepsInit(@PathVariable("FacilityId") Integer FacilityId){
		Map<String,Object> json = new HashMap<>();		
		/**
		AddMaintenanceStepsInitOutputDTO addMaintenanceStepsInitOutputDTO = new AddMaintenanceStepsInitOutputDTO();
		List<Map<String, Object>> maintenanceItemList = new ArrayList<>();
		Map<String, Object> maintenanceItemMap1 = new HashMap<>();
		maintenanceItemMap1.put("key", 1);
		maintenanceItemMap1.put("label", "清洗");
		Map<String, Object> maintenanceItemMap2 = new HashMap<>();
		maintenanceItemMap2.put("key", 2);
		maintenanceItemMap2.put("label", "校准");
		Map<String, Object> maintenanceItemMap3 = new HashMap<>();
		maintenanceItemMap3.put("key", 3);
		maintenanceItemMap3.put("label", "更换");
		maintenanceItemList.add(maintenanceItemMap1);
		maintenanceItemList.add(maintenanceItemMap2);
		maintenanceItemList.add(maintenanceItemMap3);
		addMaintenanceStepsInitOutputDTO.setMaintenanceItemList(maintenanceItemList);
		List<Map<String, Object>> bomList=new ArrayList<>();
		Map<String, Object> bom1=new HashMap<>();
		Map<String, Object> bom2=new HashMap<>();
		Map<String, Object> bom3=new HashMap<>();
		bom1.put("key", 0);
		bom1.put("label", "BOM1");
		bom2.put("key", 1);
		bom2.put("label", "BOM2");
		bom3.put("key", 2);
		bom3.put("label", "BOM3");
		bomList.add(bom1);
		bomList.add(bom2);
		bomList.add(bom3);
		List<Map<String, Object>> roleGroupList = new ArrayList<>();
		Map<String, Object> roleGroupMap1 = new HashMap<>();
		roleGroupMap1.put("key", 1);
		roleGroupMap1.put("label", "保养小组1");
		Map<String, Object> roleGroupMap2 = new HashMap<>();
		roleGroupMap2.put("key", 2);
		roleGroupMap2.put("label", "保养小组2");
		roleGroupList.add(roleGroupMap1);
		roleGroupList.add(roleGroupMap2);
		addMaintenanceStepsInitOutputDTO.setRoleGroupList(roleGroupList);
		addMaintenanceStepsInitOutputDTO.setBOMList(bomList);
		List<Map<String, Object>> measurementCategoryList = new ArrayList<>();
		Map<String, Object> measurementCategoryMap1 = new HashMap<>();
		measurementCategoryMap1.put("key", 1);
		measurementCategoryMap1.put("label", "单值");
		Map<String, Object> measurementCategoryMap2 = new HashMap<>();
		measurementCategoryMap2.put("key", 2);
		measurementCategoryMap2.put("label", "曲线");
		measurementCategoryList.add(measurementCategoryMap1);
		measurementCategoryList.add(measurementCategoryMap2);
		addMaintenanceStepsInitOutputDTO.setMeasurementCategoryList(measurementCategoryList);
		*/
		try{
			AddMaintenanceStepsInitOutputDTO addMaintenanceStepsInitOutputDTO = new AddMaintenanceStepsInitOutputDTO();
			KeyLabelToLabelImpl keyLabelToLabel = new KeyLabelToLabelImpl();
			List<Map<String, Object>> maintenanceItemList = keyLabelToLabel.changeDataToLabel(addAssignmentsListService.getMaintenanceItemList());
			addMaintenanceStepsInitOutputDTO.setMaintenanceItemList(maintenanceItemList);
			Integer bomHeadId = addAssignmentsListService.getBOMHeadIdByFacilityId(FacilityId);
			List<Map<String, Object>> bomList = new ArrayList<>();
			if(bomHeadId != null){
				bomList = bomHeadService.getDataByIdInItemAddBracket(bomHeadId);
				addMaintenanceStepsInitOutputDTO.setBOMList(bomList);
			}
			//List<Map<String, Object>> roleGroupList = keyLabelToLabel.changeDataToLabel(myRoleGroupTaskListSerivce.getRoleGroupList());
			
			List<Map<String, Object>> roleGroupList = myRoleGroupTaskListSerivce.getRoleGroupList();
			//List<Map<String,Object>> allUserList = myRoleGroupTaskListSerivce.getUserList(0);//查询所有人员
			List<Map<String, Object>> roleList = new ArrayList<>();
			if(roleGroupList != null && !roleGroupList.isEmpty()){
				Iterator<Map<String,Object>> it = roleGroupList.iterator();					
				while(it.hasNext()){
					Map<String,Object> roleGroupMap = it.next();
					roleGroupMap.put("value", roleGroupMap.get("label"));
					roleGroupMap.put("label", roleGroupMap.get("label"));
					List<Map<String,Object>> users = new ArrayList<>();
					List<Map<String,Object>> userList = myRoleGroupTaskListSerivce.getUserList(Integer.parseInt(roleGroupMap.get("key").toString()));
					roleGroupMap.remove("key");
					if(userList != null && !userList.isEmpty()){
						Iterator<Map<String, Object>> iterator = userList.iterator();
						while(iterator.hasNext()){
							Map<String,Object> userMap = iterator.next();
							userMap.put("value", userMap.get("label"));
							userMap.put("label", userMap.get("label"));
							users.add(userMap);
						}
					}
					roleGroupMap.put("children", users);
					roleList.add(roleGroupMap);
				}
//				Map<String,Object> roleGroupMap1 = new HashMap<String,Object>();
//				roleGroupMap1.put("value", 0);
//				roleGroupMap1.put("label", "所有");
//				roleGroupMap1.put("children", allUserList);
				//roleList.add(roleGroupMap1);
			}
			addMaintenanceStepsInitOutputDTO.setRoleGroupList(roleList);
		 
			//addMaintenanceStepsInitOutputDTO.setRoleGroupList(roleGroupList);
			List<Map<String, Object>> measurementCategoryList = keyLabelToLabel.changeDataToLabel(PropertyUtil.getMeasurementCategoryList());
			addMaintenanceStepsInitOutputDTO.setMeasurementCategoryList(measurementCategoryList);
			json = this.setJson(200, "新增初始化成功！", addMaintenanceStepsInitOutputDTO);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "新增初始化失败:"+e.getMessage(), -1);
		}
		return json;
	}
	/**
	 * 从BOM中选取
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/SelectFromBOMByFacilityId/{FacilityId}",method=RequestMethod.GET)
	public Map<String,Object> selectFromBOMByFacilityId(@PathVariable Integer FacilityId){	
		Map<String,Object> json = new HashMap<>();
		/**
		Map<String,List<String>> BOMList = new HashMap<>();
		List<String> bomList = new ArrayList<>();
		String bom1 = "79123123|1|LeftScrew|2";
		String bom2 = "12312310|1|RightEngine|3";
		String bom3 = "3123123123|2|TOPCover|2";
		bomList.add(bom1);
		bomList.add(bom2);
		bomList.add(bom3);
		BOMList.put("608299401|2", bomList);
		json = this.setJson(200, "查询成功！", BOMList);
		*/
		try{
			Integer bomHeadId = addAssignmentsListService.getBOMHeadIdByFacilityId(FacilityId);
			if(bomHeadId != null){
				List<Map<String, Object>> bomList = bomHeadService.getDataByIdInItemAddBracket(bomHeadId);
				json = this.setJson(200, "查询成功！", bomList);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "查询失败:"+e.getMessage(), -1);
		}
		return json;		
	}
	/**
	 * 根据所选BOM获取位置和物料号
	 * @param BOMId
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetPositionAndMateiralByBOMId/{BOMId}",method=RequestMethod.GET)
	public Map<String,Object> GetPositionAndMateiralByBOMId(@PathVariable Integer BOMId){
		Map<String,Object> json = new HashMap<>();
		json = this.setJson(200, "查询成功！", null);
		return json;
	}
	/**
	 * 新建维保步骤
	 * @param addMaintenanceStepsInputDTO
	 * @return
	
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/AddMaintenanceSteps",method=RequestMethod.POST)
	public Map<String,Object> addMaintenanceSteps(@RequestBody AddMaintenanceStepsInputDTO addMaintenanceStepsInputDTO){
		Map<String,Object> json = new HashMap<>();
		json = this.setJson(200, "添加成功！", null);
		return json;		
	}
	 */
	/**
	 * 新增派工单
	 * @param addAssignListInputDTO
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/AddAssignList",method=RequestMethod.POST)
	public Map<String,Object> addAssignList(@RequestBody AddAssignListInputDTO addAssignListInputDTO){
		Map<String,Object> json = new HashMap<>();
		try{
			addAssignmentsListService.addWorkOrderAndMaintenanceSteps(addAssignListInputDTO);
			json = this.setJson(200, "新增成功！", null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(200, "新增失败:"+e.getMessage(), -1);
		}
		return json;		
	}
	
	/****************************************编辑派工单******************************************/
	
	/**
	 * 编辑派工单初始化
	 * @param WorkOrderId 
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/EditAssignListInit/{WorkOrderId}",method=RequestMethod.GET)
	public Map<String,Object> editAssignListInit(@PathVariable(value="WorkOrderId") Integer WorkOrderId){
		Map<String,Object> json = new HashMap<>();
		List<EditAssignListRowDTO> tDto = new ArrayList<>();
		EditAssignListInitDTO editAssignListInit = new EditAssignListInitDTO();
		try{
			WorkOrder workOrder = editAssignmentsListService.getWorkOrderById(WorkOrderId);
			editAssignListInit.setId(workOrder.getId());
			editAssignListInit.setDateTime(workOrder.getCreationDateTime());
			Integer roleId = myTaskService.getRoleIdByUserId(workOrder.getUser() == null ? null : workOrder.getUser().getId());
			if(roleId != null){
				editAssignListInit.setRoleInChargeId(roleId);
			}
			List<Map<String,Object>> roleInChargeList = myRoleGroupTaskListSerivce.getRoleGroupList();
			editAssignListInit.setRoleInChargeList(roleInChargeList);
			editAssignListInit.setTargetFacilityId(workOrder.getFacility().getId());
//			Integer facilityId = editAssignListInit.getTargetFacilityId();
			List<Map<String,Object>> facilityList = myTaskService.getFacilityList();
			editAssignListInit.setTargetFacilityList(facilityList);
//			List<Integer> workPlanIdList = addAssignmentsListService.getWorkPlanIdByFacilityId(facilityId);
//			if(workPlanIdList != null && !workPlanIdList.isEmpty()){
				List<WorkOrderItem> workOrderItemList = editAssignmentsListService.getWorkOrderItemListByWorkOrderId(WorkOrderId);
				if(workOrderItemList != null && !workOrderItemList.isEmpty()){
					for (WorkOrderItem workOrderItem : workOrderItemList) {
						EditAssignListRowDTO editAssignListRowDTO = new EditAssignListRowDTO();
						editAssignListRowDTO.setWorkItemId(workOrderItem.getId());
						editAssignListRowDTO.setItemName(workOrderItem.getWorkItemName());
						editAssignListRowDTO.setBOMId(bomHeadDao.getMaterialIdByMaterialNumber(workOrderItem.getMaterialNumber()));
						editAssignListRowDTO.setPosition(workOrderItem.getDesignator());
						editAssignListRowDTO.setMaterialNumber(workOrderItem.getMaterialNumber());
						editAssignListRowDTO.setMaintenanceItemName(workOrderItem.getMaintenanceItemName());
						editAssignListRowDTO.setEstimatedStartTime(workOrderItem.getEstimatedStartTime());
						editAssignListRowDTO.setWorkLength(workOrderItem.getLabourTimeSec() == null ? null : StringUtil.decimalFormatMinuteToHour(workOrderItem.getLabourTimeSec()/60/60.0));//得到小数
						editAssignListRowDTO.setRoleInCharge(workOrderItem.getRole()== null ? null : workOrderItem.getRole().getRoleName());
						editAssignListRowDTO.setStaff(workOrderItem.getUser() == null ? null : workOrderItem.getUser().getUserName());
						editAssignListRowDTO.setIsRequireMeasure(workOrderItem.getMeasurementType() == null ? false : true);
						editAssignListRowDTO.setMeasurementCategory(workOrderItem.getMeasurementType() == null ? null : PropertyUtil.getMeasurementCategory(workOrderItem.getMeasurementType()));
						editAssignListRowDTO.setMeasurementStandardMiddleValue(workOrderItem.getNormalValue());
						editAssignListRowDTO.setMeasurementStandardUpperLimit(workOrderItem.getUSL());
						editAssignListRowDTO.setMeasurementStandardLowerLimit(workOrderItem.getLSL());
						editAssignListRowDTO.setDescriptionDocument(workOrderItem.getGuideDocURI());
						tDto.add(editAssignListRowDTO);
					}
				}
//			}
			editAssignListInit.setTDto(tDto);
			/** TestData
			editAssignListInit.setAssignNumber("PMWO0001");
			List<Map<String,Object>> targetFacilityList = new ArrayList<>();
			Map<String,Object> targetFacilityMap1 = new HashMap<>();
			targetFacilityMap1.put("key", 1);
			targetFacilityMap1.put("label", "ReflowOven0001");
			Map<String,Object> targetFacilityMap2 = new HashMap<>();
			targetFacilityMap2.put("key", 2);
			targetFacilityMap2.put("label", "ReflowOven0002");
			targetFacilityList.add(targetFacilityMap1);
			targetFacilityList.add(targetFacilityMap2);
			List<Map<String,Object>> roleInChargeList = new ArrayList<>();
			Map<String,Object> roleInChargeMap1 = new HashMap<>();
			roleInChargeMap1.put("key", 1);
			roleInChargeMap1.put("label", "保养小组1");
			Map<String,Object> roleInChargeMap2 = new HashMap<>();
			roleInChargeMap2.put("key", 2);
			roleInChargeMap2.put("label", "保养小组2");
			roleInChargeList.add(roleInChargeMap1);
			roleInChargeList.add(roleInChargeMap2);
			editAssignListInit.setTargetFacilityList(targetFacilityList);
			editAssignListInit.setRoleInChargeList(roleInChargeList);
			editAssignListInit.setTargetFacilityId(1);
			editAssignListInit.setRoleGroupId(1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			editAssignListInit.setDateTime(sdf.parse("2019-06-01 10:00:00"));
			List<EditAssignListRowDTO> editAssignListRowDTOList = new ArrayList<>();
			EditAssignListRowDTO TDto1 = new EditAssignListRowDTO(1, "检查氧分析仪", 1, "BOM001", "设备底部", "MPN0001", 2, "校准", new BigDecimal(2.5), 1, "保养小组1", true, 1, "单一值", new BigDecimal(40), new BigDecimal(20), new BigDecimal(10), "");
			EditAssignListRowDTO TDto2 = new EditAssignListRowDTO(2, "检查炉温1区曲线", 1, "BOM002", "设备中部", "", 2, "校准", new BigDecimal(2.5), 1, "保养小组1", true, 2, "曲线", new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "");
			EditAssignListRowDTO TDto3 = new EditAssignListRowDTO(3, "检查炉温2区曲线", 1, "BOM003", "设备中部", "", 2, "校准", new BigDecimal(2.5), 1, "保养小组1", false, 2, "曲线", new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "");
			EditAssignListRowDTO TDto4 = new EditAssignListRowDTO(4, "清洗炉膛", 1, "BOM004", "设备中部", "", 1, "清洁", new BigDecimal(2), 1, "保养小组1", false , 0, "", null, null, null, "");
			EditAssignListRowDTO TDto5 = new EditAssignListRowDTO(5, "更换过滤器", 1, "BOM005", "设备后侧", "MPN0002", 3, "更换", new BigDecimal(1), 1, "保养小组1", false, 0, "", null, null, null, "");
			editAssignListRowDTOList.add(TDto1);
			editAssignListRowDTOList.add(TDto2);
			editAssignListRowDTOList.add(TDto3);
			editAssignListRowDTOList.add(TDto4);
			editAssignListRowDTOList.add(TDto5);
			editAssignListInit.setTDto(editAssignListRowDTOList);
			*/
			json = this.setJson(200, "编辑初始化成功！", editAssignListInit);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "编辑初始化失败:"+e.getMessage(), -1);
		}
		return json;		
	}
	
	/**
	 * 维保步骤编辑初始化（不与后端交互）
	 * @param WorkItemId
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/EditMaintenanceStepsInit/{WorkItemId}",method=RequestMethod.GET)
	public Map<String,Object> editMaintenanceStepsInit(@PathVariable Integer WorkItemId){
		Map<String,Object> json = new HashMap<>();
		EditMaintenanceStepsInitOutputDTO editMaintenanceStepsInitOutputDTO = new EditMaintenanceStepsInitOutputDTO();
		/** TestData
		List<Map<String, Object>> maintenanceItemList = new ArrayList<>();
		Map<String, Object> maintenanceItemMap1 = new HashMap<>();
		maintenanceItemMap1.put("key", 1);
		maintenanceItemMap1.put("label", "清洗");
		Map<String, Object> maintenanceItemMap2 = new HashMap<>();
		maintenanceItemMap2.put("key", 2);
		maintenanceItemMap2.put("label", "校准");
		Map<String, Object> maintenanceItemMap3 = new HashMap<>();
		maintenanceItemMap3.put("key", 3);
		maintenanceItemMap3.put("label", "更换");
		maintenanceItemList.add(maintenanceItemMap1);
		maintenanceItemList.add(maintenanceItemMap2);
		maintenanceItemList.add(maintenanceItemMap3);
		editMaintenanceStepsInitOutputDTO.setMaintenanceItemList(maintenanceItemList);
		
		List<Map<String, Object>> roleGroupList = new ArrayList<>();
		Map<String, Object> roleGroupMap1 = new HashMap<>();
		roleGroupMap1.put("key", 1);
		roleGroupMap1.put("label", "保养小组1");
		Map<String, Object> roleGroupMap2 = new HashMap<>();
		roleGroupMap2.put("key", 2);
		roleGroupMap2.put("label", "保养小组2");
		roleGroupList.add(roleGroupMap1);
		roleGroupList.add(roleGroupMap2);
		editMaintenanceStepsInitOutputDTO.setRoleGroupList(roleGroupList);
		
		List<Map<String, Object>> measurementCategoryList = new ArrayList<>();
		Map<String, Object> measurementCategoryMap1 = new HashMap<>();
		measurementCategoryMap1.put("key", 1);
		measurementCategoryMap1.put("label", "单值");
		Map<String, Object> measurementCategoryMap2 = new HashMap<>();
		measurementCategoryMap2.put("key", 2);
		measurementCategoryMap2.put("label", "曲线");
		measurementCategoryList.add(measurementCategoryMap1);
		measurementCategoryList.add(measurementCategoryMap2);
		editMaintenanceStepsInitOutputDTO.setMeasurementCategoryList(measurementCategoryList);
		EditMaintenanceStepsRowDTO TDto = new EditMaintenanceStepsRowDTO(WorkItemId, "检查炉温1区曲线", "设备中部", "MPN0002", 1, "校准", new BigDecimal(2.5), 1, true, 2, new BigDecimal(330), new BigDecimal(250), new BigDecimal(200), "文档路径");
		editMaintenanceStepsInitOutputDTO.setTDto(TDto);
		*/
		try{
			/*
			List<Map<String, Object>> maintenanceItemList = addAssignmentsListService.getMaintenanceItemList();
			editMaintenanceStepsInitOutputDTO.setMaintenanceItemList(maintenanceItemList);
			List<Map<String, Object>> roleGroupList = myRoleGroupTaskListSerivce.getRoleGroupList();
			editMaintenanceStepsInitOutputDTO.setRoleGroupList(roleGroupList);
			List<Map<String, Object>> measurementCategoryList = PropertyUtil.getMeasurementCategoryList();
			editMaintenanceStepsInitOutputDTO.setMeasurementCategoryList(measurementCategoryList);
			*/
			json = this.setJson(200, "编辑初始化成功！", editMaintenanceStepsInitOutputDTO);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "编辑初始化失败:"+e.getMessage(), -1);
		}
		return json;
	}
	
	/**
	 * 编辑派工单
	 * @param editAssignListInputDTO
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/EditAssignList",method=RequestMethod.PUT)
	public Map<String,Object> editAssignList(@RequestBody EditAssignListInputDTO editAssignListInputDTO){
		Map<String,Object> json = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{	
			Integer workOrderId = editAssignListInputDTO.getWorkOrderId();
			WorkOrder workOrder = editAssignmentsListService.getWorkOrderById(workOrderId);	
			workOrder.setCreationDateTime(sdf.parse(editAssignListInputDTO.getDateTime()));
			if(editAssignListInputDTO.getRoleInChargeId() != null){
				workOrder.setRole(addAssignmentsListService.getRoleByRoleId(editAssignListInputDTO.getRoleInChargeId()));
			}
			if(editAssignListInputDTO.getTargetFacilityId() != null){
				FacilityModel facility = editAssignmentsListService.getFacilityByFacilityId(editAssignListInputDTO.getTargetFacilityId());
				workOrder.setFacility(facility);
			}
			workOrder.setEditorId(editAssignListInputDTO.getUserId());
			workOrder.setEditDateTime(new Date());
			editAssignmentsListService.updateWorkOrder(workOrder);
			
//			List<WorkOrderItem> workOrderItemList = editAssignmentsListService.getWorkOrderItemListByWorkOrderId(workOrderId);
//			if(workOrderItemList != null && !workOrderItemList.isEmpty()){
//				
//			}
			//将workOrderItem改成已删除的状态，先删除后添加
			assignmentsListService.updateWorkOrderItemByWorkOrderId(workOrderId);
//			List<Integer> workPlanIdList = addAssignmentsListService.getWorkPlanIdByFacilityId(facilityId);
//			if(workPlanIdList != null && !workPlanIdList.isEmpty()){
//				List<WorkItem> workItemList = editAssignmentsListService.getWorkItemByWorkPlanId(workPlanIdList);
//				if(workItemList != null && !workItemList.isEmpty()){
//					for (WorkItem workItem : workItemList) {
//						editAssignmentsListService.DeleteWorkItem(workItem);
//					}
//				}
//			}
			
			for (EditMaintenanceStepsInputDTO maintenanceSteps : editAssignListInputDTO.getMaintenanceStepsList()) {
				WorkOrderItem workOrderItem = new WorkOrderItem();
				//workItem.setWorkPlan(workPlan);
				workOrderItem.setWorkOrder(workOrder);
				workOrderItem.setRole(maintenanceSteps.getRoleInCharge() == null ? null : editAssignmentsListService.getRoleByRoleName(maintenanceSteps.getRoleInCharge()));
				workOrderItem.setUser(maintenanceSteps.getStaff() == null ? null : addAssignmentsListService.getUserByUserName(maintenanceSteps.getStaff()));
				Date startDateTime;
				if(StringUtil.IsNotBlank(maintenanceSteps.getEstimatedStartTime())){
					startDateTime = sdf.parse(maintenanceSteps.getEstimatedStartTime());	
				}else{
					startDateTime = sdf.parse(editAssignListInputDTO.getDateTime());
				}
				workOrderItem.setEstimatedStartTime(startDateTime);
				BigDecimal workLength = maintenanceSteps.getWorkLength();
				if(workLength != null){
					long labourTimeSec = workLength.multiply(new BigDecimal(60*60)).longValue();//秒
					workOrderItem.setLabourTimeSec(labourTimeSec);
					long endDateTime = startDateTime.getTime() + labourTimeSec * 1000;
					workOrderItem.setEstimatedEndTime(new Date(endDateTime));
				}	
				workOrderItem.setWorkItemName(maintenanceSteps.getItemName());
				workOrderItem.setDesignator(maintenanceSteps.getPosition());
				workOrderItem.setMaterialNumber(maintenanceSteps.getMaterialNumber());
				workOrderItem.setMaintenanceItemName(maintenanceSteps.getMaintenanceItemName());
//				workOrderItem.setFinalResult(null);
				if(maintenanceSteps.getIsRequireMeasure())	{
					workOrderItem.setMeasurementType(maintenanceSteps.getMeasurementCategory()== null ? null : PropertyUtil.getMeasurementCategoryId(maintenanceSteps.getMeasurementCategory()));
					workOrderItem.setUSL(maintenanceSteps.getMeasurementStandardUpperLimit());
					workOrderItem.setLSL(maintenanceSteps.getMeasurementStandardLowerLimit());
					workOrderItem.setNormalValue(maintenanceSteps.getMeasurementStandardMiddleValue());
				}
				workOrderItem.setStatus(1);
				workOrderItem.setGuideDocURI(maintenanceSteps.getDescriptionDocument());
				editAssignmentsListService.addWorkOrderItem(workOrderItem);
			}
//			for (EditMaintenanceStepsInputDTO maintenanceSteps : editAssignListInputDTO.getMaintenanceStepsList()) {
//				WorkItem workItem = editAssignmentsListService.getWorkItemByWorkItemId(maintenanceSteps.getWorkItemId());//修改
//				/*if(workItem != null){//页面的数据(根据id可以在数据库中查询的到就执行修改操作)
//					workItem.setName(maintenanceSteps.getItemName());
//					workItem.setDesignator(maintenanceSteps.getPosition());
//					Material material = new Material();
//					Integer materialId = addAssignmentsListService.getMaterialIdByMaterialNumber(maintenanceSteps.getMaterialNumber());
//					material.setId(materialId);
//					workItem.setMaterial(material);
//					maintenanceitem maintenanceitem = new maintenanceitem();
//					maintenanceitem.setId(maintenanceSteps.getMaintenanceItemId());
//					workItem.setMaintenanceItem(maintenanceitem);
//					workItem.setWorkDurationSec(maintenanceSteps.getWorkLength().multiply(new BigDecimal(60*60)).longValue());
//					Role role = new Role();
//					role.setId(maintenanceSteps.getRoleInChargeId());
//					workItem.setRole(role);
//					workItem.setMeasurementType(maintenanceSteps.getMeasurementCategoryId());
//					workItem.setUSL(maintenanceSteps.getMeasurementStandardUpperLimit());
//					workItem.setLSL(maintenanceSteps.getMeasurementStandardLowerLimit());
//					workItem.setNromalValue(maintenanceSteps.getMeasurementStandardMiddleValue());
//					workItem.setState(1);
//					//workItem.setGuideDocURI(guideDocURI);
//					editAssignmentsListService.addOrUpdateWorkItem(workItem);
//				}else{//页面的数据（根据id在数据库中找不到数据，就执行新增操作）
//					workItem = new WorkItem();
//					workItem.setName(maintenanceSteps.getItemName());
//					workItem.setDesignator(maintenanceSteps.getPosition());
//					Material material = new Material();
//					Integer materialId = addAssignmentsListService.getMaterialIdByMaterialNumber(maintenanceSteps.getMaterialNumber());
//					material.setId(materialId);
//					workItem.setMaterial(material);
//					maintenanceitem maintenanceitem = new maintenanceitem();
//					maintenanceitem.setId(maintenanceSteps.getMaintenanceItemId());
//					workItem.setMaintenanceItem(maintenanceitem);
//					workItem.setWorkDurationSec(maintenanceSteps.getWorkLength().multiply(new BigDecimal(60*60)).longValue());
//					Role role = new Role();
//					role.setId(maintenanceSteps.getRoleInChargeId());
//					workItem.setRole(role);
//					workItem.setMeasurementType(maintenanceSteps.getMeasurementCategoryId());
//					workItem.setUSL(maintenanceSteps.getMeasurementStandardUpperLimit());
//					workItem.setLSL(maintenanceSteps.getMeasurementStandardLowerLimit());
//					workItem.setNromalValue(maintenanceSteps.getMeasurementStandardMiddleValue());
//					workItem.setState(1);
//					//workItem.setGuideDocURI(guideDocURI);
//					editAssignmentsListService.addOrUpdateWorkItem(workItem);
//				}*/
//				if(workItem == null){
//					workItem = new WorkItem();//新增
//				}	
//				List<Integer> workPlanIdList = addAssignmentsListService.getWorkPlanIdByFacilityId(facilityId);
//				if(workPlanIdList != null){
//					for (Integer workPlanId : workPlanIdList) {
//						WorkPlan workPlan = new WorkPlan();
//						workPlan.setId(workPlanId);
//						workItem.setWorkPlan(workPlan);
//						workItem.setName(maintenanceSteps.getItemName());
//						workItem.setDesignator(maintenanceSteps.getPosition());
//						Material material = new Material();
//						Integer materialId = addAssignmentsListService.getMaterialIdByMaterialNumber(maintenanceSteps.getMaterialNumber());
//						material.setId(materialId);
//						workItem.setMaterial(material);
//						maintenanceitem maintenanceitem = new maintenanceitem();
//						maintenanceitem.setId(maintenanceSteps.getMaintenanceItemId());
//						workItem.setMaintenanceItem(maintenanceitem);
//						workItem.setWorkDurationSec(maintenanceSteps.getWorkLength().multiply(new BigDecimal(60*60)).longValue());
//						Role role = new Role();
//						role.setId(maintenanceSteps.getRoleInChargeId());
//						workItem.setRole(role);
//						workItem.setMeasurementType(maintenanceSteps.getMeasurementCategoryId());
//						workItem.setUSL(maintenanceSteps.getMeasurementStandardUpperLimit());
//						workItem.setLSL(maintenanceSteps.getMeasurementStandardLowerLimit());
//						workItem.setNromalValue(maintenanceSteps.getMeasurementStandardMiddleValue());
//						workItem.setState(1);
//						//workItem.setGuideDocURI(guideDocURI);
//						editAssignmentsListService.addOrUpdateWorkItem(workItem);
//					}
//					
//					List<WorkItem> workItemList = editAssignmentsListService.getWorkItemByWorkPlanId(workPlanIdList);
//					List<Object> contains = new ArrayList<>();
//					for (WorkItem item : workItemList) {
//						if(item.getId() == maintenanceSteps.getWorkItemId()){
//							contains.add(item);
//							contains.add(maintenanceSteps);
//						}
//					}
//					workItemList.removeAll(contains);
//					for (WorkItem item : workItemList) {
//						item.setState(-1);
//						editAssignmentsListService.addOrUpdateWorkItem(item);
//					}
//				}				
//			}
			
			json = this.setJson(200, "编辑成功！", null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "编辑失败:"+e.getMessage(), -1);
		}
		
		return json;
	}
}
