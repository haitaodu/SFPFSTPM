package com.smartflow.service;

import java.util.List;
import java.util.Map;

import com.smartflow.dto.facility.*;
import com.smartflow.model.FacilityModel;

/**
 * @author haita
 */
public interface FacilityService {

	/**
	 * 分页查询
	 * @param facilityPageSearchDTO 分页查询参数
 	 * @return 返回分页
	 */
		List<FacilityPageDTO> getPagesByConditions(FacilityPageSearchDTO facilityPageSearchDTO);
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
	  //The list of facility,Map<String,object>
		List<Map<String, Object>> getFacilityList();
		//Find the FacilityModel By iD
		FacilityModel getFacilityModelById(Integer id);

	/**
	 *分页查询的总的条目数
	 * @param facilityPageSearchDTO  查询参数
	 * @return 总的数据量
	 */
		int getCountAll(FacilityPageSearchDTO facilityPageSearchDTO);
		//根据设备编号判断是否是重复的设备信息
		Boolean isReplication(String facilityCode);
		
		
		
}
