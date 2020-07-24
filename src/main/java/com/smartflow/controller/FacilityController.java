package com.smartflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.smartflow.cron.pojo.CronPosition;
import com.smartflow.dto.Facility.FacilityAddDTO;
import com.smartflow.dto.Facility.FacilityPageDTO;
import com.smartflow.model.BOMHeadModel;
import com.smartflow.service.BOMHeadService;
import com.smartflow.service.FacilityService;
import com.smartflow.service.StationService;
import com.smartflow.service.SupplierService;
import com.smartflow.util.ChargeNullForInteger;
import com.smartflow.util.ChargeNullForIntegerImpl;
import com.smartflow.util.ImgUtil;
import com.smartflow.util.ReadDataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/Facility")
public class FacilityController extends  BaseController {
	@Autowired
	FacilityService facilityService;
	@Autowired
	BOMHeadService bomHeadService;
	@Autowired
	StationService stationService;
	@Autowired
	SupplierService supplierService;
    private static Logger logger = Logger.getLogger(FacilityController.class);
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/GetTByCondition",method= RequestMethod.POST)
    public @ResponseBody Object getPages(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject= ReadDataUtil.paramData(request);
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        Integer PageIndex =(Integer) jsonObject.get("PageIndex");
        Integer PageSize=(Integer) jsonObject.get("PageSize");
		Integer.parseInt(jsonObject.get("PageSize").toString());
        String facilityCode = jsonObject.getString("FacilityCode") == null ? null : jsonObject.getString("FacilityCode");
        String stationNumber= jsonObject.getString("StationNumber") == null ? null : jsonObject.getString("StationNumber");
        String name = jsonObject.getString("Name") == null ? null : jsonObject.getString("Name");
        String materialNumber = jsonObject.getString("MaterialNumber") == null ? null : jsonObject.getString("MaterialNumber");
        String supplierCode = jsonObject.getString("SupplierCode") == null ? null : jsonObject.getString("SupplierCode");
        String brand = jsonObject.getString("Brand") == null ? null : jsonObject.getString("Brand");
        String model = jsonObject.getString("Model") == null ? null : jsonObject.getString("Model");
        List<FacilityPageDTO> facilityPageDTOS=new ArrayList<>();
        facilityPageDTOS=facilityService.getPagesByConditions(facilityCode, 
        		stationNumber, name, materialNumber, brand,
        		supplierCode, model,PageSize,PageIndex);
        


        int max=CronPosition.SECOND.getMax();
        map.put("RowCount", facilityService.getCountAll(facilityCode, 
        		stationNumber, name, materialNumber, brand, supplierCode, model));
        map.put("Tdto", facilityPageDTOS);
        try {
            json= this.setJson(200, "Success", map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
    }
        return json;

    }
    @RequestMapping(value="/GetTById/{Id}",method= RequestMethod.GET)
    public @ResponseBody Object readDataById(@PathVariable Integer Id) throws Exception {

        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        map.put("Tdto",facilityService.getdetailById(Id) );
   
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
    @RequestMapping(value="/GetAddInitialize",method=RequestMethod.GET)
    public @ResponseBody Object getAddInitialize(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        Map<String, Object> json=new HashMap<String,Object>();
        Map<String, Object> map=new HashMap<>();

  
        map.put("StationList",stationService.getStationGroup());
        map.put("SupplierList", supplierService.getSupplierList());
        try {
            json= this.setJson(200, "Success",map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;

    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/GetEditInitialize/{Id}",method=RequestMethod.GET)
    public @ResponseBody Object getEddInitialize(@PathVariable Integer Id) throws Exception
    {
        Map<String, Object> json=new HashMap<String,Object>();
        Map<String, Object> map=new HashMap<>();

    
        
        
        map.put("StationList",stationService.getStationGroup());
        map.put("SupplierList", supplierService.getSupplierList());
        map.put("FacilityEditeDTO",facilityService.gEditeDTOById(Id));
        try {
            json= this.setJson(200, "Success",map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;

    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/Put",method=RequestMethod.PUT)
    public @ResponseBody Object updateDataForFacility(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	JSONObject jsonObject= ReadDataUtil.paramData(request);
        Map<String, Object> json=new HashMap<String,Object>();
        String facilityCode = jsonObject.getString("FacilityCode") == null ? null : jsonObject.getString("FacilityCode");
        String Name = jsonObject.getString("Name") == null ? null : jsonObject.getString("Name");
        String Brand = jsonObject.getString("Brand") == null ? null : jsonObject.getString("Brand");
        String Model = jsonObject.getString("Model") == null ? null : jsonObject.getString("Model");
        String ManufacturingDate = jsonObject.getString("ManufacturingDate") == null ? null : jsonObject.getString("ManufacturingDate");
        String ServiceExpirationDate = jsonObject.getString("ServiceExpirationDate") == null ? null : jsonObject.getString("ServiceExpirationDate");
        String InstalledDate = jsonObject.getString("InstalledDate") == null ? null : jsonObject.getString("InstalledDate");
        Integer Version=(Integer)jsonObject.get("Version");
        Integer SupplierId=(Integer)jsonObject.get("SupplierId");
        Integer StationId=(Integer)jsonObject.get("StationId");
        Integer State=(Integer)jsonObject.get("State");
        Integer UserId=(Integer)jsonObject.get("UserId");
        String MaterialNumber = jsonObject.getString("MaterialNumber") == null ? null : jsonObject.getString("MaterialNumber");

        Integer Id=Integer.parseInt(jsonObject.get("Id")==null?"":String.valueOf(jsonObject.get("Id")));
		SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date installedDate=utcFormat.parse(InstalledDate);
		Date serviceExpirationDate=utcFormat.parse(ServiceExpirationDate);
		Date manufacturingDate=utcFormat.parse(ManufacturingDate);
	    List<Integer> integers=new ArrayList<>();
	    integers.add(Version);
	    integers.add(SupplierId);
	    integers.add(StationId);
	    integers.add(State);
	    integers.add(UserId);
	    List<String> stringsForCharge=new ArrayList<>();
	    stringsForCharge.add("Version");
	    stringsForCharge.add("SupplierId");
	    stringsForCharge.add("StationId");
	    stringsForCharge.add("State");
	    stringsForCharge.add("UserId");
	    
	    ChargeNullForInteger chargeNullForInteger=new ChargeNullForIntegerImpl();
	    if (chargeNullForInteger.isNull(integers)) {
			String errorString=chargeNullForInteger.getErrorString(stringsForCharge);
			json=this.setJson(202, chargeNullForInteger.getMap().get(errorString).toString(), -1);
			return json;
		}
	
		List<BOMHeadModel> bomHeadModels=bomHeadService.getBOMList(MaterialNumber, Version);
	
        if (bomHeadModels.size()<1) {
			json=this.setJson(202, "物料号或版本号输入错误", -1);
			return json;
		}
		 FacilityAddDTO facilityAddDTO=new FacilityAddDTO(facilityCode, Name, StationId, bomHeadModels.get(0).getId(),
	        		Brand, SupplierId, Model, State, installedDate, manufacturingDate, serviceExpirationDate, UserId);
		 facilityAddDTO.setId(Id);
         facilityService.updateFacility(facilityAddDTO);
        try {
            json= this.setJson(200, "Success",0);
        } catch (Exception e) {
            json = this.setJson(-1, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;

    }
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/Post",method=RequestMethod.POST)
    public @ResponseBody Object addDataForFacility(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	  JSONObject jsonObject= ReadDataUtil.paramData(request);
        Map<String, Object> json=new HashMap<String,Object>();
        String facilityCode = jsonObject.getString("FacilityCode") == null ? null : jsonObject.getString("FacilityCode");
        String Name = jsonObject.getString("Name") == null ? null : jsonObject.getString("Name");
        String Brand = jsonObject.getString("Brand") == null ? null : jsonObject.getString("Brand");
        String Model = jsonObject.getString("Model") == null ? null : jsonObject.getString("Model");
        String ManufacturingDate = jsonObject.getString("ManufacturingDate") == null ? null : jsonObject.getString("ManufacturingDate");
        String ServiceExpirationDate = jsonObject.getString("ServiceExpirationDate") == null ? null : jsonObject.getString("ServiceExpirationDate");
        String InstalledDate = jsonObject.getString("InstalledDate") == null ? null : jsonObject.getString("InstalledDate");
        String MaterialNumber = jsonObject.getString("MaterialNumber") == null ? null : jsonObject.getString("MaterialNumber");
        Integer Version=Integer.parseInt(jsonObject.get("Version")==null?"":String.valueOf(jsonObject.get("Version")));
        Integer SupplierId=Integer.parseInt(jsonObject.get("SupplierId")==null?"":String.valueOf(jsonObject.get("SupplierId")));
        Integer StationId=Integer.parseInt(jsonObject.get("StationId")==null?"":String.valueOf(jsonObject.get("StationId")));
        Integer State=Integer.parseInt(jsonObject.get("State")==null?"":String.valueOf(jsonObject.get("State")));
        Integer UserId=Integer.parseInt(jsonObject.get("UserId")==null?"":String.valueOf(jsonObject.get("UserId")));
    	
		SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date installedDate=utcFormat.parse(InstalledDate);
		Date serviceExpirationDate=utcFormat.parse(ServiceExpirationDate);
		Date manufacturingDate=utcFormat.parse(ManufacturingDate);
		 List<BOMHeadModel> bomHeadModels=bomHeadService.getBOMList(MaterialNumber, Version);
	        if (bomHeadModels.size()<1) {
				json=this.setJson(202, "物料号或版本号输入错误", -1);
				return json;
			}
	        if (!facilityService.isReplication(facilityCode)) {
	        	json=this.setJson(202, "设备编号已存，请勿重复插入", -1);
				return json;
			}
        FacilityAddDTO facilityAddDTO=new FacilityAddDTO(facilityCode, Name, StationId, bomHeadModels.get(0).getId(),
        		Brand, SupplierId, Model, State, installedDate, manufacturingDate, serviceExpirationDate, UserId);
        facilityService.saveFacility(facilityAddDTO);

        try {
            json= this.setJson(200, "Success",0);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;

    }
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/GetBOM",method=RequestMethod.POST)
    public @ResponseBody Object getBOM(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        Map<String, Object> json=new HashMap<String,Object>();
        Map<String, Object> map=new HashMap<>();
        JSONObject jsonObject=ReadDataUtil.paramData(request);
        String materialNumber = jsonObject.getString("MaterialNumber")==null?null:jsonObject.getString("MaterialNumber");
        Integer version = jsonObject.getInteger("Version")==null?null:jsonObject.getInteger("Version");
        List<BOMHeadModel> bomHeadModels=bomHeadService.getBOMList(materialNumber, version);
        if (bomHeadModels.size()<1) {
			json=this.setJson(202, "物料号或版本号输入错误", 0);
			return json;
		}
        Map<String,Object> bom=new HashMap<>();
        bom.put("key", bomHeadModels.get(0).getId());
        bom.put("label",materialNumber+ '|'+bomHeadModels.get(0).getVersion());
        List<Map<String, Object>> boms=new ArrayList<>();
        boms.add(bom);
        map.put("BOM",boms);
        
        try {
            json= this.setJson(200, "Success",map);
        } catch (Exception e) {
            json = this.setJson(0, e.getMessage(),1);
            logger.error(e);
            e.printStackTrace();
        }
        return json;

    }
    
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/File",method=RequestMethod.POST)

	public @ResponseBody  Object  upload( HttpServletRequest request, 

			@RequestParam("upload") MultipartFile[] uploads) throws Exception{

		String result = "error";
		
        request.setCharacterEncoding("utf-8");
		Map<String, Object> json=new HashMap<String,Object>();
		try {
		for (MultipartFile multipartFile : uploads) {
		if(!multipartFile.isEmpty()){
			//获取绝对路径
			String realpath = "/uploads/file/";
			//文件名
			String filename = multipartFile.getOriginalFilename();

			
 
		
			//上传文件

			multipartFile.transferTo(new File(realpath, filename));

			}
		}
		}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Maybe your filenname is error");
			}


			result = "success";//页面
			json=this.setJson(200, "Successful", 0);
			return json;
		}

	

		//上传结果


    
    
    /**
     * @param req
     * @param resp
     * @return
     * @throws Exception
     *@para:
     *@date:2019年8月2日下午5:29:40
     *@return:
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/GetFile",method=RequestMethod.POST)

	public @ResponseBody  OutputStream   downLoad( HttpServletRequest req,HttpServletResponse resp) throws Exception{

        return   ImgUtil.getFileImg(req, resp);
		//上传结果
	}
}
