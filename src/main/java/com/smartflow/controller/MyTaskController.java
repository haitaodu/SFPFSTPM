package com.smartflow.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.smartflow.util.ArrayUtils;
import org.apache.commons.io.FileUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.smartflow.dto.AddMaintenanceRecordInitDTO;
import com.smartflow.dto.AddMaintenanceRecordInputDTO;
import com.smartflow.dto.AssignmentTaskInitOutputDTO;
import com.smartflow.dto.AssignmentTaskInitOutputDTO.AssignmentTaskInitOutputRowDTO;
import com.smartflow.dto.AssignmentTaskInputDTO;
import com.smartflow.dto.RoleGroupTaskListInputDTO;
import com.smartflow.dto.RoleGroupTaskListInputDTO.TaskIdList;
import com.smartflow.dto.RoleGroupTaskListOutputRowDTO;
import com.smartflow.dto.StateAndDeviceInitDTO;
import com.smartflow.dto.StateAndRoleGroupInitDTO;
import com.smartflow.dto.TaskDetailOutputDTO;
import com.smartflow.dto.TaskListInputDTO;
import com.smartflow.dto.TaskListOutputDTO;
import com.smartflow.model.MeasurementDataRecord;
import com.smartflow.model.PMActionRecord;
import com.smartflow.model.UserModel;
import com.smartflow.model.WorkOrderItem;
import com.smartflow.service.MyRoleGroupTaskListSerivce;
import com.smartflow.service.MyTaskService;
import com.smartflow.util.ExportCsvUtil;
import com.smartflow.util.PropertyUtil;
import com.smartflow.util.StringUtil;
/**
 * 我的任务
 * @author smartflow
 *
 */
@RestController
@RequestMapping("/api/MyTask")
public class MyTaskController extends BaseController{

	private static final Logger logger = Logger.getLogger(MyTaskController.class);
	private final
	MyRoleGroupTaskListSerivce myRoleGroupTaskListSerivce;
	private final
	MyTaskService myTaskService;

	@Autowired
	public MyTaskController(MyRoleGroupTaskListSerivce myRoleGroupTaskListSerivce, MyTaskService myTaskService) {
		this.myRoleGroupTaskListSerivce = myRoleGroupTaskListSerivce;
		this.myTaskService = myTaskService;
	}

	/**
	 * 初始化状态下拉框和角色组下拉框
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetStateAndRoleGroupInit/{userId}",method=RequestMethod.GET)
	public Map<String,Object> getStateAndRoleGroupInit(@PathVariable Integer userId) {
		Map<String, Object> json = new HashMap<>();
		StateAndRoleGroupInitDTO stateAndRoleGroupInitDTO = new StateAndRoleGroupInitDTO();
		List<Map<String,Object>> stateList = PropertyUtil.getStautsList();
		stateAndRoleGroupInitDTO.setStateList(stateList);
		try{
			List<Map<String,Object>> roleGroupList = myRoleGroupTaskListSerivce.getRoleGroupListByUserId(2);
			Map<String,Object> roleGroupMap1 = new HashMap<String,Object>();
			roleGroupMap1.put("key", 0);
			roleGroupMap1.put("label", "所有");
			roleGroupList.add(roleGroupMap1);
			stateAndRoleGroupInitDTO.setRoleGroupList(roleGroupList);
			json = this.setJson(200, "初始化成功", stateAndRoleGroupInitDTO);
		}catch(Exception e){
			logger.error(e);
			json = this.setJson(0, "初始化失败:"+e.getMessage(), -1);
		}
		return json;
	}

	/**
	 * 通过开始时间、结束时间、状态、角色组查询我的角色组任务清单列表
	 * @param roleGroupTaskListConditionDTO
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@PostMapping (value="/GetMyRoleGroupTaskListByConditon")
	public Map<String,Object> getMyRoleGroupTaskListByConditon(@RequestBody RoleGroupTaskListInputDTO roleGroupTaskListConditionDTO){
		Map<String,Object> json = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<>();
		try{
			Integer RowCount = myRoleGroupTaskListSerivce.getTotalCountRoleGroupTaskListByCondition(roleGroupTaskListConditionDTO);
			List<RoleGroupTaskListOutputRowDTO> roleGroupTaskListOutputDTOList = myRoleGroupTaskListSerivce.getRoleGroupTaskListByCondition(roleGroupTaskListConditionDTO);
			map.put("RowCount", RowCount);
			map.put("Tdto", roleGroupTaskListOutputDTOList);
			json = this.setJson(200, "查询成功！", map);
		}catch(Exception e){
			logger.error(e);
			json = this.setJson(0, "查询失败:"+e.getMessage(), e.getMessage());
		}
		return json;
	}

	/**
	 * 领取任务
	 * @param taskIdList
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@PostMapping (value="/ReceiveTask")
	public Map<String,Object> ReceiveTask(@RequestBody TaskIdList taskIdList){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			for (Integer workOrderItemId : taskIdList.getTaskIdList()) {
				Integer status = myRoleGroupTaskListSerivce.getStatusByWorkOrderItemId(workOrderItemId);
				if(status != 1){					
					json = this.setJson(0, "只可以领取状态为新的任务！", -1);			
					return json;
				}
			}
			for (Integer workOrderItemId : taskIdList.getTaskIdList()) {
				myRoleGroupTaskListSerivce.updateStatusAndUserIdByWorkOrderItemId(workOrderItemId, taskIdList.getUserId());
			}			
			json = this.setJson(200, "领取任务成功！", null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "领取任务失败："+e.getMessage(), -1);
		}
		return json;
	}
	/**
	 * 分配任务初始化
	 * @param taskIdList
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/AssignmentTaskInit",method=RequestMethod.POST)
	public Map<String,Object> AssignmentTaskInit(@RequestBody TaskIdList taskIdList){
		Map<String,Object> json = new HashMap<String,Object>();
		AssignmentTaskInitOutputDTO assignmentTaskInitOutputDTO = new AssignmentTaskInitOutputDTO();
		try{
			if(taskIdList.getTaskIdList() != null){
				List<AssignmentTaskInitOutputRowDTO> taskList = myRoleGroupTaskListSerivce.getAssignmentTaskInitDTOByWorkOrderItemId(taskIdList.getTaskIdList());
				assignmentTaskInitOutputDTO.setTaskList(taskList);
			}
			List<Map<String, Object>> roleGroupList = myRoleGroupTaskListSerivce.getRoleGroupList();
			List<Map<String,Object>> allUserList = myRoleGroupTaskListSerivce.getUserList(0);//查询所有人员
			List<Map<String, Object>> roleList = new ArrayList<>();
			if(roleGroupList != null && !roleGroupList.isEmpty()){
				Iterator<Map<String,Object>> it = roleGroupList.iterator();					
				Map<String, Object> staffMap = new HashMap<>();
				staffMap.put("value", 0);
				staffMap.put("label", "所有");
				while(it.hasNext()){
					Map<String,Object> roleGroupMap = it.next();
					roleGroupMap.put("value", roleGroupMap.get("key"));
					roleGroupMap.remove("key");
					roleGroupMap.put("label", roleGroupMap.get("label"));
					List<Map<String,Object>> userList = myRoleGroupTaskListSerivce.getUserList(Integer.parseInt(roleGroupMap.get("value").toString()));
					userList.add(staffMap);
					roleGroupMap.put("children", userList);
					roleList.add(roleGroupMap);
				}
				Map<String,Object> roleGroupMap1 = new HashMap<String,Object>();
				roleGroupMap1.put("value", 0);
				roleGroupMap1.put("label", "所有");
				roleGroupMap1.put("children", allUserList);
				roleList.add(roleGroupMap1);
			}
			assignmentTaskInitOutputDTO.setRoleGroupList(roleList);
			json = this.setJson(200, "分配任务初始化成功！", assignmentTaskInitOutputDTO);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "分配任务初始化失败:"+e.getMessage(), -1);	
		}		
		return json;
	}


	/**
	 * 分配任务
	 * @param assignmentTaskInputDTO
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@PostMapping (value="/AssignmentTask")
	public Map<String,Object> AssignmentTask(@RequestBody AssignmentTaskInputDTO assignmentTaskInputDTO){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			for (Integer workOrderItemId : assignmentTaskInputDTO.getTaskIdList()) {
				Integer status = myRoleGroupTaskListSerivce.getStatusByWorkOrderItemId(workOrderItemId);
				if(status != 1 && status != 2){
					json = this.setJson(0, "只可以分配状态为新或者已分配的任务！", -1);
					return json;
				}
			}
			for (Integer workOrderItemId : assignmentTaskInputDTO.getTaskIdList()) {
				myRoleGroupTaskListSerivce.updateStatusAndUserIdAndRoleIdByWorkOrderItemId(workOrderItemId, assignmentTaskInputDTO.getStaffId(), assignmentTaskInputDTO.getRoleGroupId());	
			}
			json = this.setJson(200, "分配任务成功！", 0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "分配任务失败:"+e.getMessage(), -1);
		}
		return json;
	}
	/**********************************我的任务*******************************************/

	/**
	 * 状态下拉框、设备下拉框初始化
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetStateAndDeviceInit",method=RequestMethod.GET)
	public Map<String,Object> getStateAndDeviceInit(){
		Map<String,Object> json = new HashMap<String,Object>();
		StateAndDeviceInitDTO stateAndDeviceInitDTO = new StateAndDeviceInitDTO();
		try{
			List<Map<String,Object>> stateList = PropertyUtil.getStautsList();
			stateAndDeviceInitDTO.setStateList(stateList);
			List<Map<String, Object>> deviceList = myTaskService.getFacilityList();
			Map<String,Object> deviceMap = new HashMap<>();
			deviceMap.put("key", 0);
			deviceMap.put("label", "所有");
			deviceList.add(deviceMap);
			stateAndDeviceInitDTO.setDeviceList(deviceList);
			json = this.setJson(200, "初始化状态和设备成功！", stateAndDeviceInitDTO);
		}catch(Exception e){
			logger.error(e);
			json = this.setJson(0, "初始化状态和设备失败！", -1);
		}
		return json;
	}

	/**
	 * 根据开始日期、结束日期、状态、设备查询任务列表
	 * @param taskListInputDTO
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetTaskListByCondition",method=RequestMethod.POST)
	public Map<String,Object> getTaskListByCondition(@RequestBody TaskListInputDTO taskListInputDTO){
		Map<String,Object> json = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<>();
		try {
			List<TaskListOutputDTO> taskListOutputDTOList = myTaskService.getRoleGroupTaskListByCondition(taskListInputDTO);
			Integer RowCount = myTaskService.getTotalCountRoleGroupTaskListByCondition(taskListInputDTO);
			map.put("RowCount", RowCount);
			map.put("Tdto", taskListOutputDTOList);
			json = this.setJson(200, "查询成功！", map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "查询失败:"+e.getMessage(), -1);
		}		
		return json;
	}
	/**
	 * 任务列表打印预览
	 * @param taskIdList
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/TaskListPrintPreview",method=RequestMethod.POST)
	public Map<String,Object> taskListPrintPreview(@RequestBody TaskIdList taskIdList, HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			List<TaskListOutputDTO> taskListOutputDTOList = myTaskService.getRoleGroupTaskListByTaskIdList(taskIdList);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String fileName = "MyTaskList_";
			String webappPath = request.getSession().getServletContext().getRealPath("/temp");
			String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/temp/";
			System.out.println(filePath);
			String file_Name = ExportCsvUtil.exportCsv(request, response, taskListOutputDTOList, webappPath, fileName);
			if(file_Name != null){
				json = this.setJson(200, "打印预览成功！", filePath+file_Name);
			}else{
				json = this.setJson(0, "打印预览失败", -1);
			}
			return json;
		} catch (Exception e) {
			json = this.setJson(0, "打印预览失败", -1);
		}
		return json;
	}

	/**
	 * 查询任务详情
	 * @param TaskId
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/GetTaskDetail/{TaskId}",method=RequestMethod.GET)
	public Map<String,Object> getTaskDetail(@PathVariable Integer TaskId){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			TaskDetailOutputDTO taskDetailOutputDTO = myTaskService.getTaskDetailByTaskId(TaskId);
			json = this.setJson(200, "查询任务详情成功！", taskDetailOutputDTO);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.setJson(200, "查询任务详情失败！", -1);
		}	
		return json;
	}

	/**
	 * 新增维保记录初始化人员下拉框、维保结果下拉框
	 * @return
	 */
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/AddMaintenanceRecordInit",method=RequestMethod.GET)
	public Map<String,Object> addMaintenanceRecordInit(){
		Map<String,Object> json = new HashMap<String,Object>();
		AddMaintenanceRecordInitDTO addMaintenanceRecordInitDTO = new AddMaintenanceRecordInitDTO();		
		try{
			List<Map<String, Object>> staffList = myTaskService.getUserList();
			addMaintenanceRecordInitDTO.setStaffList(staffList);
			List<Map<String,Object>> maintenanceResultList = new ArrayList<>();
			Map<String, Object> maintenanceResultMap1 = new HashMap<>();
			maintenanceResultMap1.put("key", 0);
			maintenanceResultMap1.put("label", "通过");
			Map<String, Object> maintenanceResultMap2 = new HashMap<>();
			maintenanceResultMap2.put("key", 1);
			maintenanceResultMap2.put("label", "失败");
			maintenanceResultList.add(maintenanceResultMap1);
			maintenanceResultList.add(maintenanceResultMap2);
			addMaintenanceRecordInitDTO.setMaintenanceResultList(maintenanceResultList);
			json = this.setJson(200, "初始化成功！", addMaintenanceRecordInitDTO);	
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "初始化失败！", -1);
		}
		return json;
	}
	/*******************@Multipart的作用是文件上传时定义multipart的格式
	@Multipart(value="assetfile",type="text/plain")
	type="text/plain"表示上传文件是txt类型
	@RequestPart注解与@Multipart作用类似，但是使用它容易报null错误，不建议使用
	 ********/

	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/AddMaintenanceRecord",method=RequestMethod.POST)
	public Map<String,Object> addMaintenanceRecord(@RequestBody AddMaintenanceRecordInputDTO addMaintenanceRecordInputDTO){
		Map<String,Object> json = new HashMap<>();
		try{	
			PMActionRecord pmActionRecord = new PMActionRecord();
			WorkOrderItem workOrderItem = new WorkOrderItem();
			workOrderItem.setId(addMaintenanceRecordInputDTO.getTaskId());
			pmActionRecord.setWorkOrderItem(workOrderItem);
			UserModel user = new UserModel();
			user.setId(addMaintenanceRecordInputDTO.getStaffId());
			pmActionRecord.setWorker(user);
			Date startDateTime = StringUtil.formatDateTime(addMaintenanceRecordInputDTO.getStartDateTime());
			pmActionRecord.setActionStartTime(startDateTime);
			long time = startDateTime.getTime() + addMaintenanceRecordInputDTO.getActualUseTime().multiply(new BigDecimal(3600000)).longValue();
			Date actionEndTime = new Date(time);
			pmActionRecord.setActionEndTime(actionEndTime);
			pmActionRecord.setActionResult(addMaintenanceRecordInputDTO.getMaintenanceResultId());
			pmActionRecord.setReviewer(null);
			pmActionRecord.setReviewDateTime(null);
			pmActionRecord.setReviewResult(null);
			MeasurementDataRecord measurementDataRecord = new MeasurementDataRecord();
			measurementDataRecord.setValue(addMaintenanceRecordInputDTO.getMeasurementValue());
			measurementDataRecord.setAVGValue(addMaintenanceRecordInputDTO.getAvgValue());
			measurementDataRecord.setMEANValue(addMaintenanceRecordInputDTO.getMidValue());
			measurementDataRecord.setMAXValue(addMaintenanceRecordInputDTO.getMaxValue());
			measurementDataRecord.setMINValue(addMaintenanceRecordInputDTO.getMinValue());
			measurementDataRecord.setProfileValues(addMaintenanceRecordInputDTO.getProfileValue() == null ? null : addMaintenanceRecordInputDTO.getProfileValue().toString());
			measurementDataRecord.setPlainText(addMaintenanceRecordInputDTO.getTextContext());
			myTaskService.addMaintenanceRecord(pmActionRecord, measurementDataRecord);
		
			json = this.setJson(200, "添加成功！", 0);		
			return json;
		}catch(Exception e){
			logger.error(e);
			json = this.setJson(0, "添加失败！", -1);
			return json;
		}
		
	}

	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
	public Map<String,Object> uploadFile(@RequestParam(name="upfile",required=false)CommonsMultipartFile upfile,
			@RequestParam(name="picture",required=false)CommonsMultipartFile[] picture
			,HttpServletRequest request){
		Map<String,Object> json = new HashMap<>();
		System.out.println(upfile);
		String fileName = upfile.getOriginalFilename();
		String imgName = upfile.getOriginalFilename();
		System.out.println(fileName+imgName);
		try{
			String filePath = saveFile(upfile, request);
			System.out.println(filePath);
			List<String> imgPathList = new ArrayList<>();
			if(picture != null&& picture.length>0){
				for(int i = 0;i<picture.length;i++){
					MultipartFile pic = picture[i];
					String imgPath = savePicture(pic, request);
					imgPathList.add(imgPath);
				}
			}
			for (String imgPath : imgPathList) {
				System.out.println(imgPath);
			}
			json = this.setJson(200, "添加成功！", 0);		
			return json;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			json = this.setJson(0, "添加失败！", -1);
			return json;
		}
	}
	
	/**
	 * 保存文件
	 * @param upfile 要上传的文件
	 * @param request 请求对象
	 * @return 文件路径
	 */
	private static String saveFile(MultipartFile upfile,HttpServletRequest request){
		if(upfile != null){
			String fileName = upfile.getOriginalFilename();
			String documentPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/SFPFSTPM_upload/"+fileName;
			String webappPath = request.getSession().getServletContext().getRealPath("");
			String path = webappPath.substring(0, webappPath.lastIndexOf("\\"))+"\\SFPFSTPM_upload";
			File file = new File(path, fileName);
			File fileDirectory = new File(path);
			if(!fileDirectory.exists()){
				fileDirectory.mkdir();
				try{
					FileUtils.copyInputStreamToFile(upfile.getInputStream(), file);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return documentPath;
		}else{
			return null;
		}
	}
	
	/**
	 * 保存图片
	 * @param picture 图片
	 * @param request 请求对象
	 * @return 图片路径
	 */
	private static String savePicture(MultipartFile picture,HttpServletRequest request) {
		if (picture != null){
			String imgName = picture.getOriginalFilename();
			String imgPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/SFPFSTPM_upload/images"+imgName;
			String webappPath = request.getSession().getServletContext().getRealPath("");//+"\\images\\"+picture.getOriginalFilename();
			System.out.println(webappPath.substring(0));
			System.out.println(webappPath.substring(0, webappPath.lastIndexOf("\\")));
			String path = webappPath.substring(0, webappPath.lastIndexOf("\\"))+"\\SFPFSTPM_upload\\images";
			System.out.println(path);
			File file = new File(path, imgName);
			File filesDirectory = new File(path);
			if (!filesDirectory.exists()) {//如果文件不存在
				filesDirectory.mkdirs();//则创建文件目录
				try {	
					FileUtils.copyInputStreamToFile(picture.getInputStream(), file);
				} catch (IOException e) {

				}
			}
			return imgPath;
		}else{
			return null;
		}
	}
}