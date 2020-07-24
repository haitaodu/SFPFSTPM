package com.smartflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemAddDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemDetailDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemEditeDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemPageDTO;
import com.smartflow.service.MaintenanceItemService;
import com.smartflow.util.ReadDataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/MaintenanceItem")
public class MaintenanceItemController extends  BaseController{
	@Autowired
	MaintenanceItemService maintenanceItemService;
    private static org.apache.log4j.Logger logger = Logger.getLogger(FacilityController.class);

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/GetTByCondition", method = RequestMethod.POST)
    public @ResponseBody Object getPages(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        JSONObject jsonObject = ReadDataUtil.paramData(request);
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
   	 try {
        Integer pageIndex = jsonObject.get("PageIndex") == null ? null : Integer.parseInt(jsonObject.get("PageIndex").toString());
        Integer pageSize = jsonObject.get("PageSize") == null ? null : Integer.parseInt(jsonObject.get("PageSize").toString());
        String maintenanceItemName = jsonObject.getString("MaintenanceItemName") == null ? null : jsonObject.getString("MaintenanceItemName");
       List<MaintenanceItemPageDTO>  maintenanceItemPageDTOS=maintenanceItemService.pageDTO(pageSize, pageIndex, maintenanceItemName);
        
        map.put("RowCount", maintenanceItemService.countAll(pageSize, pageIndex, maintenanceItemName)
        		);
        map.put("Tdto", maintenanceItemPageDTOS);
       
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;
    }
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/GetTById/{Id}",method= RequestMethod.GET)
    public @ResponseBody Object readDataById(@PathVariable Integer Id) throws Exception {

        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        map.put("Tdto",maintenanceItemService.getDTOById(Id));
       
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
        }
        return json;
    }

    //Until now,we don't need to access db to get categorylist
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/GetCategoryList",method= RequestMethod.GET)
    public @ResponseBody Object readDataById() throws Exception {

        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapList=new ArrayList<>();
        Map<String, Object> map01=new HashMap<>();
        Map<String, Object> map02=new HashMap<>();
        Map<String, Object> map03=new HashMap<>();
        Map<String, Object> map04=new HashMap<>();
        Map<String, Object> map05=new HashMap<>();
        Map<String, Object> map06=new HashMap<>();
        map01.put("key", 0);
        map01.put("label", "清洁清扫");
        map02.put("key", 1);
        map02.put("label", "点检");
        map03.put("key", 2);
        map03.put("label", "紧固");
        map04.put("key", 3);
        map04.put("label", "校准");
        map05.put("key", 4);
        map05.put("label", "跟换");
        map06.put("key", 5);
        map06.put("label", "维修");
        mapList.add(map01);
        mapList.add(map02);
        mapList.add(map03);
        mapList.add(map04);
        mapList.add(map05);
        mapList.add(map06);
        
        map.put("CategoryList",mapList);
        try {
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;
    }
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/Post",method=RequestMethod.POST)
    public @ResponseBody Object addDataForMaintenanceItem(@RequestBody MaintenanceItemAddDTO maintenanceItemAddDTO) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<>();
   boolean isSave=   maintenanceItemService.saveAddData(maintenanceItemAddDTO);
      if (isSave==false) {
    	  json= this.setJson(200, "保存数据失败,数据可能已存在", -1);
    	   return json;
	}
        try {
            json= this.setJson(200, "Success", 0);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/Put",method=RequestMethod.PUT)
    public @ResponseBody Object editeDataForMaintenanceItem(@RequestBody MaintenanceItemEditeDTO maintenanceItemEditeDTO) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<>();
        maintenanceItemService.saveEditeData(maintenanceItemEditeDTO);
        try {
            json= this.setJson(200, "Success", 0);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;
    }
    
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/EditeMaintenanceItem/{Id}",method= RequestMethod.GET)
    public @ResponseBody Object editeMaintenanceItem(@PathVariable Integer Id) throws Exception {

        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
          
            
           map.put("Tdto",maintenanceItemService.getMaintenanceItemEditeDTO(Id));
           List<Map<String, Object>> mapList=new ArrayList<>();
           Map<String, Object> map01=new HashMap<>();
           Map<String, Object> map02=new HashMap<>();
           Map<String, Object> map03=new HashMap<>();
           Map<String, Object> map04=new HashMap<>();
           Map<String, Object> map05=new HashMap<>();
           Map<String, Object> map06=new HashMap<>();
           map01.put("key", 0);
           map01.put("label", "清洁清扫");
           map02.put("key", 1);
           map02.put("label", "点检");
           map03.put("key", 2);
           map03.put("label", "维护保养");
           map04.put("key", 3);
           map04.put("label", "校准");
           map05.put("key", 4);
           map05.put("label", "更换");
           map06.put("key", 5);
           map06.put("label", "维修");
           mapList.add(map01);
           mapList.add(map02);
           mapList.add(map03);
           mapList.add(map04);
           mapList.add(map05);
           mapList.add(map06);
           
           map.put("CategoryList",mapList);
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;
    }
}
