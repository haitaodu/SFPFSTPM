package com.smartflow.service;

import java.util.List;
import java.util.Map;

import com.smartflow.dto.Facility.FacilityAddDTO;
import com.smartflow.dto.Facility.FacilityDetailDTO;
import com.smartflow.dto.Facility.FacilityEditeDTO;
import com.smartflow.dto.Facility.FacilityPageDTO;
import com.smartflow.model.FacilityModel;

public interface FacilityService {
	//根据前端给出的条件，分页查询
		List<FacilityPageDTO> getPagesByConditions(String facilityCode,String stationCode,String name
				,String materialNumber,String brand,String supplier,
				String model,Integer pageSize,Integer pageIndex);
		//根据Id找出相应的设备信息
		FacilityDetailDTO getdetailById(Integer id);
		//根据Id查找到相应的设备信息用于编辑化模块的使用
		FacilityEditeDTO gEditeDTOById(Integer id);
		//保存更新设备信息
		void updateFacility(FacilityAddDTO facilityAddDTO);
	   //保存设备信息
		void saveFacility(FacilityAddDTO facilityAddDTO );
		//根据设备号查出相应的设备信息
	    FacilityModel getFacilityModleByCode(String facilityCode);
	  //The list of Facility,Map<String,object>
		List<Map<String, Object>> getFacilityList();
		//Find the FacilityModel By iD
		FacilityModel getFacilityModelById(Integer id);
		//找出总的记录条数
		int getCountAll(String facilityCode,String stationCode,String name
				,String materialNumber,String brand,String supplier,
				String model);
		//根据设备编号判断是否是重复的设备信息
		Boolean isReplication(String facilityCode);
		
		
		
}
