package com.smartflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.smartflow.common.enumpack.Category;
import com.smartflow.dto.maintenanceitem.MaintenanceItemAddDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemEditeDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemPageDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemSearchDTO;
import com.smartflow.service.MaintenanceItemService;
import com.smartflow.util.ReadDataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haita
 */
@Controller
@RequestMapping("/api/MaintenanceItem")
public class MaintenanceItemController extends  BaseController{
	private final
    MaintenanceItemService maintenanceItemService;
    private static org.apache.log4j.Logger logger = Logger.getLogger(MaintenanceItemController.class);

    @Autowired
    public MaintenanceItemController(MaintenanceItemService maintenanceItemService) {
        this.maintenanceItemService = maintenanceItemService;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping (value = "/GetTByCondition")
    public @ResponseBody Object getPages(@Valid @RequestBody MaintenanceItemSearchDTO maintenanceItemSearchDTO) throws Exception {
    
        Map<String, Object> json ;
        Map<String, Object> map = new HashMap<>();
   	 try {
          List<MaintenanceItemPageDTO> maintenanceItemPageDto =
                  maintenanceItemService.pageDTO(maintenanceItemSearchDTO);
        map.put("RowCount", maintenanceItemService.countAll(maintenanceItemSearchDTO.getMaintenanceItem())
        		);
        map.put("Tdto", maintenanceItemPageDto );
       
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
        }
        return json;
    }
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping(value="/GetTById/{Id}")
    public @ResponseBody Object readDataById(@PathVariable Integer Id) throws Exception {

        Map<String, Object> json;
        Map<String, Object> map = new HashMap<>();
        try {
        map.put("Tdto",maintenanceItemService.getDtoById(Id));
       
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
        }
        return json;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping(value="/GetCategoryList")
    public @ResponseBody Object readDataById() throws Exception {

        Map<String, Object> json ;
        Map<String, Object> map = new HashMap<>();
        map.put("CategoryList",getCategoryMapList());
        try {
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
        }
        return json;
    }
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping (value="/Post")
    public @ResponseBody Object addDataForMaintenanceItem(@RequestBody MaintenanceItemAddDTO maintenanceItemAddDTO) throws Exception {
        Map<String, Object> json ;
        boolean isSave=   maintenanceItemService.saveAddData(maintenanceItemAddDTO);
      if (!isSave) {
    	  json= this.setJson(200, "保存数据失败,数据可能已存在", -1);
    	   return json;
	}
        try {
            json= this.setJson(200, "Success", 0);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
        }
        return json;
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @PutMapping (value="/Put")
    public @ResponseBody Object editeDataForMaintenanceItem(@RequestBody MaintenanceItemEditeDTO maintenanceItemEditeDTO) throws Exception {
        Map<String, Object> json;
        maintenanceItemService.saveEditeData(maintenanceItemEditeDTO);
        try {
            json= this.setJson(200, "Success", 0);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
        }
        return json;
    }
    
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping(value="/EditeMaintenanceItem/{Id}")
    public @ResponseBody Object editeMaintenanceItem(@PathVariable Integer Id) throws Exception {

        Map<String, Object> json;
        Map<String, Object> map = new HashMap<>();
        try {
           map.put("Tdto",maintenanceItemService.getMaintenanceItemEditeDTO(Id));
           map.put("CategoryList",getCategoryMapList());
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
        }
        return json;
    }


    /**
     * 删除，批量删除功能
     * @param request 请求参数
     * @return 返回数据
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping (value = "/Delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody Map<String, Object> del(HttpServletRequest request) {
        Map<String,Object> json;
        try {
            JSONObject jsonObject = ReadDataUtil.paramData(request);
            List<Integer> list=(List<Integer>)  jsonObject.get("List");
            maintenanceItemService.delByList(list);
            json = this.setJson(200, "删除成功!", 0);

        } catch (Exception e) {
            logger.error(e.getMessage());
            json = this.setJson(0, "删除失败", null);
        }
        return json;
    }

    /**
     * 获取维保项列表以map的形式展示
     * @return 返回维保项列表
     */
    private List<Map<String,Object>> getCategoryMapList()
    {
        List<Map<String,Object>> mapList=new ArrayList<>();
        for (Category category:Category.values())
        {
            Map<String,Object> map=new HashMap<>();
            map.put("key",category.getKey());
            map.put("label",category.getValue());
            mapList.add(map);
        }
        return mapList;
    }
}
