package com.smartflow.dao;

import java.util.List;
import java.util.Map;

import com.smartflow.dto.facility.*;
import com.smartflow.model.FacilityModel;

/**
 * @author haita
 */
public interface FacilityDao {
	/**
	 * 查询分页数据
	 * @param facilityPageSearchDTO 查询参数
	 * @return 返回分页数据
	 */
	List<FacilityModel> getPagesByConditions(FacilityPageSearchDTO facilityPageSearchDTO);
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
	 *
	 * @param facilityPageSearchDTO
	 * @return
	 */
	int getCountAll(FacilityPageSearchDTO facilityPageSearchDTO);
	List<FacilityModel> getFacilityByString(String facilityCode);
}
