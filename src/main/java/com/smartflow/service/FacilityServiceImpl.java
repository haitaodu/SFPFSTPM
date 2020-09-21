package com.smartflow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartflow.dto.facility.*;
import com.smartflow.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.smartflow.dao.FacilityDao;
import com.smartflow.model.FacilityModel;
/**
 * @author haita
 */
@Service
public class FacilityServiceImpl implements FacilityService{
private final
FacilityDao facilityDao;
private final
HibernateTemplate hibernateTemplate;

	@Autowired
	public FacilityServiceImpl(FacilityDao facilityDao, HibernateTemplate hibernateTemplate) {
		this.facilityDao = facilityDao;
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public List<FacilityPageDTO> getPagesByConditions(FacilityPageSearchDTO facilityPageSearchDTO) {

		List<FacilityPageDTO> facilityPageDtos =new ArrayList<>();

		for (FacilityModel facilityModel : facilityDao.getPagesByConditions(facilityPageSearchDTO)) {
			//判空操作针对BOMHead,Station
			String stationNumber=null;
			String materialNumber01=null;
			String suppliercode=null;
			if (facilityModel.getStation()!=null) {
				stationNumber=facilityModel.getStation().getStationNumber();
			}
			if(facilityModel.getBOMHead()!=null)
			{	materialNumber01=hibernateTemplate.get(Material.class, facilityModel.getBOMHead().getMaterialId()).getMaterialNumber();
			}
			if (facilityModel.getSupplier()!=null) {
				suppliercode=facilityModel.getSupplier().getCode();
			}
			facilityPageDtos .add(new FacilityPageDTO(facilityModel.getId(),facilityModel.getFacilityCode(),facilityModel.getName(),stationNumber,
					materialNumber01,
					facilityModel.getBrand(),suppliercode,facilityModel.getModel()));
		}

		return facilityPageDtos;

	}

	@Override
	public FacilityDetailDTO getdetailById(Integer id) {
		
		return facilityDao.getdetailById(id);
	}

	@Override
	public FacilityEditeDTO gEditeDTOById(Integer id) {
		
		return facilityDao.gEditeDTOById(id);
	}

	@Override
	public void updateFacility(FacilityAddDTO facilityAddDTO) {
		
		facilityDao.updateFacility(facilityAddDTO);
		
	}

	@Override
	public void saveFacility(FacilityAddDTO facilityAddDTO) {
		
		facilityDao.saveFacility(facilityAddDTO);
		
		
	}

	@Override
	public FacilityModel getFacilityModleByCode(String facilityCode) {
		
		return facilityDao.getFacilityModleByCode(facilityCode);
	}

	@Override
	public List<Map<String, Object>> getFacilityList() {
		
		return facilityDao.getFacilityList();
	}

	@Override
	public FacilityModel getFacilityModelById(Integer id) {
		
		return facilityDao.getFacilityModelById(id);
	}

	@Override
	public int getCountAll(FacilityPageSearchDTO facilityPageSearchDTO) {
		
		return facilityDao.getCountAll(facilityPageSearchDTO);
	}

	@Override
	public Boolean isReplication(String facilityCode) {

		return facilityDao.getFacilityByString(facilityCode).isEmpty();
	}

	
}
