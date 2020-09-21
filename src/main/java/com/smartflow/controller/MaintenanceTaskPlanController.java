package com.smartflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.smartflow.dto.maintenancetaskplan.*;
import com.smartflow.dto.StateAndPeriodicTypeInitDTO;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.WorkPlan;
import com.smartflow.service.BOMHeadService;
import com.smartflow.service.FacilityService;
import com.smartflow.service.MaintenanceTaskPlanService;
import com.smartflow.service.StationService;
import com.smartflow.util.KeyLabelToLabel;
import com.smartflow.util.KeyLabelToLabelImpl;
import com.smartflow.util.ReadDataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

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
    private static org.apache.log4j.Logger logger = Logger.getLogger(MaintenanceTaskPlanController.class);

    @Autowired
    public MaintenanceTaskPlanController(FacilityService facilityService, BOMHeadService bomHeadService, MaintenanceTaskPlanService maintenanceTaskPlanService, StationService stationService, ThreadPoolTaskExecutor taskExecutor) {
        this.facilityService = facilityService;
        this.bomHeadService = bomHeadService;
        this.maintenanceTaskPlanService = maintenanceTaskPlanService;
        this.stationService = stationService;
        this.taskExecutor = taskExecutor;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(value = "/GetTByCondition")
    public @ResponseBody
    Object getPages(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = ReadDataUtil.paramData(request);
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        Integer pageSize=jsonObject.getIntValue("PageSize");
		Integer pageIndex=jsonObject.getIntValue("PageIndex");
		String maintenanceTaskPlanName = jsonObject.getString("MaintenanceTaskPlanName")==null?null:jsonObject.getString("MaintenanceTaskPlanName");
		String facilityName = jsonObject.getString("FacilityName")==null?null:jsonObject.getString("FacilityName");
        new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        CountDownLatch latch=new CountDownLatch(2);

        try {
            taskExecutor.execute(() -> {
                map.put("Tdto", maintenanceTaskPlanService.pageDTO(pageSize,
                        pageIndex, maintenanceTaskPlanName, facilityName));
                latch.countDown();
            });
            taskExecutor.execute(() -> {
                map.put("RowCount", maintenanceTaskPlanService.getCount(maintenanceTaskPlanName, facilityName));
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
    
    @CrossOrigin(origins="*",maxAge=3600)
	@GetMapping(value="/GetAssignmentsListInit")
	public Map<String,Object> GetAssignmentsListInit(){
		Map<String,Object> json = new HashMap<>();	
		StateAndPeriodicTypeInitDTO stateAndPeriodicTypeInitDTO = new StateAndPeriodicTypeInitDTO();
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
     
        map.put("Tdto", maintenanceTaskPlanService.getStepByWorkPlanId(Id));
        WorkPlan workPlan=maintenanceTaskPlanService.getWorkPlanById(Id);
        TaskPlanHeadDto taskPlanHeadDto=maintenanceTaskPlanService.geTaskPlanHeadDto(workPlan);
      
        
        map.put("TargetFacilityList", facilityService.getFacilityList());
        map.put("RoleList",maintenanceTaskPlanService.getRoleList());
        map.put("PlanName", taskPlanHeadDto.getPlanName());
        map.put("targetFacility", taskPlanHeadDto.getTargetFacility());
        map.put("State", taskPlanHeadDto.getState());
        map.put("PlanType", taskPlanHeadDto.getPlanType());
        map.put("MainRole", taskPlanHeadDto.getMainRole());
        map.put("Id", Id);
        
        //周期类型
        map.put("PeriodicType", true);
        List<PeriodicTypeDTO> periodcTypeList = new ArrayList<>();

        periodcTypeList=maintenanceTaskPlanService.getReminderList(Id);
        map.put("PeriodcTypeList", periodcTypeList);
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
        map.put("TargetFacilityList",stationService.getStationGroup());
        map.put("RoleList",maintenanceTaskPlanService.getRoleList());
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
    Object addMaintenanceTask(@RequestBody TaskPlanSaveOutputDTO maintenanceTaskPlanSaveDTO) throws Exception {
       
        Map<String, Object> json = new HashMap<String, Object>();
        if (!maintenanceTaskPlanService.isReplication(maintenanceTaskPlanSaveDTO.getPlanName())) {
        	json=this.setJson(202, "维保计划名重复", -1);
			return json;
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
