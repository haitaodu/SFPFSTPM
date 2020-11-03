package com.smartflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.smartflow.common.enumpack.PeriodicType;
import com.smartflow.dto.MaintenanceTaskPlanConditionInputDTO;
import com.smartflow.dto.maintenancetaskplan.*;
import com.smartflow.dto.StateAndPeriodicTypeInitDTO;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.WorkPlan;
import com.smartflow.service.*;
import com.smartflow.util.KeyLabelToLabel;
import com.smartflow.util.KeyLabelToLabelImpl;
import com.smartflow.util.PropertyUtil;
import com.smartflow.util.ReadDataUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @author haita
 */
@RestController
@RequestMapping("/api/MaintenanceTaskPlan")
public class MaintenanceTaskPlanController extends  BaseController {
    private final
    ThreadPoolTaskExecutor taskExecutor;
    private final
    FacilityService facilityService;
	private final
    BOMHeadService bomHeadService;
	private final
    MaintenanceTaskPlanService maintenanceTaskPlanService;
	private final
    StationService stationService;
	private final
    RemindsAndAssignmentsCalendarService remindsAndAssignmentsCalendarService;
    private static org.apache.log4j.Logger logger = Logger.getLogger(MaintenanceTaskPlanController.class);

    @Autowired
    public MaintenanceTaskPlanController(FacilityService facilityService, BOMHeadService bomHeadService, MaintenanceTaskPlanService maintenanceTaskPlanService, StationService stationService,RemindsAndAssignmentsCalendarService remindsAndAssignmentsCalendarService, ThreadPoolTaskExecutor taskExecutor) {
        this.facilityService = facilityService;
        this.bomHeadService = bomHeadService;
        this.maintenanceTaskPlanService = maintenanceTaskPlanService;
        this.stationService = stationService;
        this.taskExecutor = taskExecutor;
        this.remindsAndAssignmentsCalendarService = remindsAndAssignmentsCalendarService;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(value = "/GetTByCondition")
    public @ResponseBody
    Object getPages(@RequestBody MaintenanceTaskPlanConditionInputDTO maintenanceTaskPlanConditionInputDTO,HttpServletRequest request, HttpServletResponse response) throws Exception {
//        JSONObject jsonObject = ReadDataUtil.paramData(request);
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
//        Integer pageSize=jsonObject.getIntValue("PageSize");
//		Integer pageIndex=jsonObject.getIntValue("PageIndex");
//		String maintenanceTaskPlanName = jsonObject.getString("MaintenanceTaskPlanName")==null?null:jsonObject.getString("MaintenanceTaskPlanName");
//		String facilityName = jsonObject.getString("FacilityName")==null?null:jsonObject.getString("FacilityName");
//        new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        CountDownLatch latch=new CountDownLatch(2);

        try {
            taskExecutor.execute(() -> {
                map.put("Tdto", maintenanceTaskPlanService.pageDTO(maintenanceTaskPlanConditionInputDTO.getPageSize(),
                        maintenanceTaskPlanConditionInputDTO.getPageIndex(), maintenanceTaskPlanConditionInputDTO.getMaintenanceTaskPlanName(), maintenanceTaskPlanConditionInputDTO.getFacilityIdList(), maintenanceTaskPlanConditionInputDTO.getState()));
                latch.countDown();
            });
            taskExecutor.execute(() -> {
                map.put("RowCount", maintenanceTaskPlanService.getCount(maintenanceTaskPlanConditionInputDTO.getMaintenanceTaskPlanName(), maintenanceTaskPlanConditionInputDTO.getFacilityIdList(), maintenanceTaskPlanConditionInputDTO.getState()));
                latch.countDown();
            });
            latch.await();
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
        }
        return json;
    }

    /**
     * 根据Id修改状态
     * @param Id
     * @return
     */
    @CrossOrigin(origins="*",maxAge=3600)
    @GetMapping(value="/UpdateStateById/{Id}")
    public Map<String,Object> updateStateById(@PathVariable Integer Id){
        Map<String,Object> json = new HashMap<>();
        WorkPlan workPlan = maintenanceTaskPlanService.getWorkPlanById(Id);
        if(workPlan.getState() == 1){//1:生效；0：失效
            workPlan.setState(0);
            maintenanceTaskPlanService.updateWorkPlanByWorkPlanId(workPlan);
            json = this.setJson(200, "状态修改为失效成功！", 0);
        }else{
            workPlan.setState(1);
            maintenanceTaskPlanService.updateWorkPlanByWorkPlanId(workPlan);
            json = this.setJson(200, "状态修改为生效成功！", 0);
        }
        return json;
    }

    /**
     * 查询列表初始化查询条件
     * @return
     */
    @CrossOrigin(origins="*",maxAge=3600)
	@GetMapping(value="/GetAssignmentsListInit")
	public Map<String,Object> GetAssignmentsListInit(){
		Map<String,Object> json = new HashMap<>();	
		StateAndPeriodicTypeInitDTO stateAndPeriodicTypeInitDTO = new StateAndPeriodicTypeInitDTO();
		List<Map<String,Object>> stateList = new ArrayList<Map<String,Object>>();
		Map<String,Object> stateMap1 = new HashMap<String, Object>();
		Map<String,Object> stateMap2 = new HashMap<String, Object>();
//		Map<String,Object> stateMap3 = new HashMap<String, Object>();
//		Map<String,Object> stateMap4 = new HashMap<String, Object>();
//		Map<String,Object> stateMap5 = new HashMap<String, Object>();
//		Map<String,Object> stateMap6 = new HashMap<String, Object>();
//		Map<String,Object> stateMap7 = new HashMap<String, Object>();
//		Map<String,Object> stateMap8 = new HashMap<String, Object>();
//		Map<String,Object> stateMap9 = new HashMap<String, Object>();
//		stateMap1.put("key", 1);
//		stateMap1.put("label", "新");
//		stateMap2.put("key", 2);
//		stateMap2.put("label", "已分配");
//		stateMap3.put("key", 3);
//		stateMap3.put("label", "已完成");
//		stateMap4.put("key", 4);
//		stateMap4.put("label", "已审核");
//		stateMap5.put("key", 5);
//		stateMap5.put("label", "已审核完成");
//		stateMap6.put("key", -1);
//		stateMap6.put("label", "已取消");
//		stateMap7.put("key", -2);
//		stateMap7.put("label", "已拒绝");
//		stateMap8.put("key", -3);
//		stateMap8.put("label", "已重新安排");
//		stateMap9.put("key", 0);
//		stateMap9.put("label", "已关闭");

        stateMap2.put("key", 1);
        stateMap2.put("label", "生效");
        stateMap1.put("key", 0);
		stateMap1.put("label", "失效");
		stateList.add(stateMap1);
		stateList.add(stateMap2);
//		stateList.add(stateMap3);
//		stateList.add(stateMap4);
//		stateList.add(stateMap5);
//		stateList.add(stateMap6);
//		stateList.add(stateMap7);
//		stateList.add(stateMap8);
//		stateList.add(stateMap9);
		stateAndPeriodicTypeInitDTO.setStateList(stateList);
//		List<Map<String, Object>> periodicTypeList = new ArrayList<>();
//		Map<String, Object> periodicTypeMap1 = new HashMap<>();
//		periodicTypeMap1.put("key", 1);
//		periodicTypeMap1.put("label", "临时的");
//		Map<String, Object> periodicTypeMap2 = new HashMap<>();
//		periodicTypeMap2.put("key", 2);
//		periodicTypeMap2.put("label", "周期性的");
//		periodicTypeList.add(periodicTypeMap1);
//		periodicTypeList.add(periodicTypeMap2);
//        stateAndPeriodicTypeInitDTO.setPeriodicTypeList(periodicTypeList);
//        map.put("TargetFacilityList",stationService.getFacilityList());
        stateAndPeriodicTypeInitDTO.setTargetFacilityList(stationService.getFacilityList());
		json = this.setJson(200, "初始化成功！", stateAndPeriodicTypeInitDTO);
		return json;
	}
	
	
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/GetMaintenancePreview/{Id}", method = RequestMethod.GET)
    public @ResponseBody
    Object getMaintenancePreview(@PathVariable Integer Id) throws Exception {
        
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<MaintenancePreviewDTO> maintenancePreviewDTOs=maintenanceTaskPlanService.geMaintenancePreviewDTO(Id);
     
        map.put("Tdto",maintenancePreviewDTOs);
        try {
            json = this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(), 1);
            logger.error(e);
        }
        return json;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/DeleteTaskById/{Id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteTaskById(@PathVariable Integer Id) throws Exception {
       
        Map<String, Object> json = new HashMap<String, Object>();
        new HashMap<String, Object>();

        try {
        	Boolean isDel=maintenanceTaskPlanService.delWorkPlanById(Id);
            json = this.setJson(200, "Success", 0);
        } catch (Exception e) {
            json = this.setJson(202, e.getMessage(), 1);
            logger.error(e);
            e.printStackTrace();
            return json;
        }
        return json;
    }
    
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/GetTaskForCopy", method = RequestMethod.POST)
    public @ResponseBody
    Object getTaskForCopy(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = ReadDataUtil.paramData(request);
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        String taskName = jsonObject.getString("TaskName")==null?null:jsonObject.getString("TaskName");
      
      
        WorkPlan workPlan=maintenanceTaskPlanService.getTaskForCopy(taskName);
        map.put("Tdto", maintenanceTaskPlanService.getStepByWorkPlanId(workPlan.getId()));
        TaskPlanHeadDto taskPlanHeadDto=maintenanceTaskPlanService.geTaskPlanHeadDto(workPlan);
        map.put("TargetFacilityList",facilityService.getFacilityList());
        map.put("RoleList",maintenanceTaskPlanService.getRoleList());
        map.put("PlanName", taskPlanHeadDto.getPlanName());
        map.put("targetFacility", taskPlanHeadDto.getTargetFacility());
        map.put("State", taskPlanHeadDto.getState());
        map.put("PlanType", taskPlanHeadDto.getPlanType());
        map.put("MainRole", taskPlanHeadDto.getMainRole());
        try {
            json = this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(), 1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/TaskInitialize/{Id}", method = RequestMethod.GET)
    public @ResponseBody
    Object editeMaintenanceStep(@PathVariable Integer Id) throws Exception {
        
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
     
//        map.put("Tdto", maintenanceTaskPlanService.getStepByWorkPlanId(Id));
        WorkPlan workPlan=maintenanceTaskPlanService.getWorkPlanById(Id);
        TaskPlanHeadDto taskPlanHeadDto=maintenanceTaskPlanService.geTaskPlanHeadDto(workPlan);

        //map.put("TargetFacilityList", facilityService.getFacilityList());
        map.put("TargetFacilityList",stationService.getFacilityList());
        map.put("RoleList",maintenanceTaskPlanService.getRoleList());
        map.put("PlanName", taskPlanHeadDto.getPlanName());
        map.put("targetFacility", taskPlanHeadDto.getTargetFacility());
        map.put("State", taskPlanHeadDto.getState());
        map.put("PlanType", taskPlanHeadDto.getPlanType());
        map.put("MainRole", taskPlanHeadDto.getMainRole());
        map.put("Id", Id);
        

        List<PeriodicTypeDTO> periodicTypeList = maintenanceTaskPlanService.getReminderList(Id);
        List<Integer> PeriodicType = null;
        if(CollectionUtils.isNotEmpty(periodicTypeList)){
            String periodicType = periodicTypeList.get(0).getPeriodicName();
//            String[] periodicTypeArray = periodicType.split(",");
//            PeriodicType = Arrays.asList(periodicTypeArray);
            PeriodicType = Arrays.asList(periodicType.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        }
        //周期类型
        map.put("PeriodicType", PeriodicType);
        map.put("PeriodicTypeList", PropertyUtil.getPeriodicTypeList());
        try {
            json = this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(), 1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;
    }
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/GetTaskInitialize", method = RequestMethod.GET)
    public @ResponseBody
    Object getTaskInitialize(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReadDataUtil.paramData(request);
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        KeyLabelToLabelImpl keyLabelToLabel = new KeyLabelToLabelImpl();
        List<Map<String, Object>>  stepList = keyLabelToLabel.changeDataToLabel(maintenanceTaskPlanService.getStepList());
        map.put("TargetFacilityList",stationService.getFacilityList());
        map.put("RoleList",maintenanceTaskPlanService.getRoleList());
        map.put("PeriodicList", PropertyUtil.getPeriodicTypeList());
        map.put("MaintenanceItemList", keyLabelToLabel.changeDataToLabel(maintenanceTaskPlanService.getStepList()));
//        map.put("MaintenanceItemList", PropertyUtil.getCategoryMapList());
        try {
            json = this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(), 1);
            logger.error(e);
        }
        return json;
    }
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping (value = "/GetMaintenanceStepInitialize")
    public @ResponseBody
    Object getMaintenanceStep(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = ReadDataUtil.paramData(request);
       Integer facilityId=Integer.parseInt(jsonObject.get("TargetFacility")==null?"":String.valueOf(jsonObject.get("TargetFacility")));
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapsForBOM =new ArrayList<>();
        FacilityModel facilityModel=facilityService.getFacilityModelById(facilityId);
        if (facilityModel.getBOMHead()!=null) {
        	mapsForBOM  =bomHeadService.getDataByIdInItemAddBracket(facilityModel.getBOMHead().getId());
		}
      
        KeyLabelToLabel keyLabelToLabel=new KeyLabelToLabelImpl();
        
        List<Map<String, Object>> checkTypeList=  keyLabelToLabel.changeDataToLabel(maintenanceTaskPlanService.getTypeList());
        List<Map<String, Object>>  roleList=keyLabelToLabel.changeDataToLabel(maintenanceTaskPlanService.getRoleList());
        List<Map<String, Object>>  stepList=keyLabelToLabel.changeDataToLabel(maintenanceTaskPlanService.getStepList());
        map.put("StepList",stepList);
        map.put("RoleList",roleList);
        map.put("CheckTypeList",checkTypeList);
        map.put("BOMList",mapsForBOM);
        try {
            json = this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(), 1);
            logger.error(e);
        }
        return json;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(value = "/AddMaintenanceTask")
    public @ResponseBody
    Object addMaintenanceTask(@Valid @RequestBody TaskPlanSaveOutputDTO maintenanceTaskPlanSaveDTO) throws Exception {
       
        Map<String, Object> json = new HashMap<String, Object>();
        if (!maintenanceTaskPlanService.isReplication(maintenanceTaskPlanSaveDTO.getPlanName())) {
        	json=this.setJson(202, "维保计划名重复", -1);
			return json;
		}

		if(maintenanceTaskPlanSaveDTO.getPeriodicTypeList().get(0) == 1){
            if(StringUtils.isEmpty(maintenanceTaskPlanSaveDTO.getTemporaryDate())){
                json=this.setJson(202, "临时性的日期不能为空", -1);
                return json;
            }
        }
        try {
        	if (maintenanceTaskPlanService.saveData(maintenanceTaskPlanSaveDTO)==false) {
        		json=this.setJson(202, "保存信息失败，有可能是Cron表达式错误", -1);
    			return json;
			}
            json = this.setJson(200, "Success", 0);
        } catch (Exception e) {
            json = this.setJson(202, e.getMessage(), -1);
            logger.error(e);
        }
        return json;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PutMapping (value = "/EditeMaintenanceTask")
    public @ResponseBody
    Object editeMaintenanceTask(@RequestBody TaskPlanEditeOutputDTO maintenanceTaskPlanEditeDTO) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        maintenanceTaskPlanService.updateWorkPlanByOutPutDTO(maintenanceTaskPlanEditeDTO);
        try {
            json = this.setJson(200, "Success", 0);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(), 1);
            logger.error(e);
        }
        return json;
    }
    }
