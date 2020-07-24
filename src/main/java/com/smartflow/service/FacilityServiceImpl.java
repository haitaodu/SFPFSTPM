package com.smartflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartflow.dao.FacilityDao;
import com.smartflow.dto.Facility.FacilityAddDTO;
import com.smartflow.dto.Facility.FacilityDetailDTO;
import com.smartflow.dto.Facility.FacilityEditeDTO;
import com.smartflow.dto.Facility.FacilityPageDTO;
import com.smartflow.model.FacilityModel;
@Service
public class FacilityServiceImpl implements FacilityService{
@Autowired
FacilityDao facilityDao;
	@Override
	public List<FacilityPageDTO> getPagesByConditions(String facilityCode, String stationCode, String name,
			String materialNumber, String brand, String supplier, String model,Integer pageSize,Integer pageIndex) {
		// TODO Auto-generated method stub
		return facilityDao.getPagesByConditions(facilityCode, stationCode, name,
				materialNumber, brand,
				supplier, model,pageSize,pageIndex)
				;
	}

	@Override
	public FacilityDetailDTO getdetailById(Integer id) {
		// TODO Auto-generated method stub
		return facilityDao.getdetailById(id);
	}

	@Override
	public FacilityEditeDTO gEditeDTOById(Integer id) {
		// TODO Auto-generated method stub
		return facilityDao.gEditeDTOById(id);
	}

	@Override
	public void updateFacility(FacilityAddDTO facilityAddDTO) {
		// TODO Auto-generated method stub
		facilityDao.updateFacility(facilityAddDTO);
		
	}

	@Override
	public void saveFacility(FacilityAddDTO facilityAddDTO) {
		// TODO Auto-generated method stub
		facilityDao.saveFacility(facilityAddDTO);
		
		
	}

	@Override
	public FacilityModel getFacilityModleByCode(String facilityCode) {
		// TODO Auto-generated method stub
		return facilityDao.getFacilityModleByCode(facilityCode);
	}

	@Override
	public List<Map<String, Object>> getFacilityList() {
		// TODO Auto-generated method stub
		return facilityDao.getFacilityList();
	}

	@Override
	public FacilityModel getFacilityModelById(Integer id) {
		// TODO Auto-generated method stub
		return facilityDao.getFacilityModelById(id);
	}

	@Override
	public int getCountAll(String facilityCode, String stationCode, String name, String materialNumber, String brand,
			String supplier, String model) {
		// TODO Auto-generated method stub
		return facilityDao.getCountAll(facilityCode, stationCode, name, materialNumber, brand, supplier, model);
	}

	@Override
	public Boolean isReplication(String facilityCode) {
		// TODO Auto-generated method stub
		if (facilityDao.getFacilityByString(facilityCode).size()>=1) {
			return false;
		}
		return true;
	}

	
}
