package com.smartflow.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.smartflow.dto.Facility.FacilityAddDTO;
import com.smartflow.dto.Facility.FacilityDetailDTO;
import com.smartflow.dto.Facility.FacilityEditeDTO;
import com.smartflow.dto.Facility.FacilityPageDTO;
import com.smartflow.model.BOMHeadModel;
import com.smartflow.model.FacilityCurrentStatus;
import com.smartflow.model.FacilityModel;
import com.smartflow.model.Material;
import com.smartflow.model.StationModel;
import com.smartflow.model.SupplierModel;
import com.smartflow.model.UserModel;
@Repository
public class FacilityDaoImpl implements FacilityDao{
@Autowired 
HibernateTemplate hibernateTemplate;
@Autowired
BOMHeadDao  bomHeadDao;
@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	@Override
	public List<FacilityPageDTO> getPagesByConditions(String facilityCode, String stationCode, String name,
			String materialNumber, String brand, String supplier, String model,Integer pageSize,Integer pageIndex) {
		// TODO Auto-generated method stub
		 Session session=hibernateTemplate.getSessionFactory().openSession();
		try {
			
	   
		String hql="FROM FacilityModel WHERE State!=-1";
		if (facilityCode!=null && !"".equals(facilityCode)) {
			hql += "and FacilityCode like '%"+facilityCode+"%' ";
		}
		if (stationCode!=null && !"".equals(stationCode)) {
			hql += "and  StationId in (select S.id FROM StationModel AS S where StationNumber like '%"+stationCode+"%' )";
		}
		if (name!=null && !"".equals(name)) {
			hql += "and Name like '%"+name+"%' ";
		}
		if (materialNumber!=null && !"".equals(materialNumber)) {
			hql += "and BOMHeadId in (select B.Id from BOMHeadModel as B where MaterialId in (select "
					+ "M.id from Material as M where MaterialNumber like '%"+materialNumber+"%'))" ;
		}
		if (brand!=null && !"".equals(brand)) {
			hql += "and Brand like '%"+brand+"%' ";
		}
		if (supplier!=null && !"".equals(supplier)) {
			hql += "and  SupplierId in (select S.id FROM SupplierModel as S where Code like '%"+supplier+"%' )";
		}
		if (model!=null && !"".equals(model)) {
			hql += "and Model like '%"+model+"%' ";
		}
		Query query=session.createQuery(hql);
		query.setFirstResult((pageIndex-1)*pageSize);
		query.setMaxResults(pageSize);
	    @SuppressWarnings("unused")
	    List<FacilityModel> facilityModels=query.list();
	     List<FacilityPageDTO> facilityPageDTOs=new ArrayList<>();
	
	for (FacilityModel facilityModel : facilityModels) {
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
		facilityPageDTOs.add(new FacilityPageDTO(facilityModel.getId(),facilityModel.getFacilityCode(),facilityModel.getName(),stationNumber,
				materialNumber01,
				facilityModel.getBrand(),suppliercode,facilityModel.getModel()));
	}
	return facilityPageDTOs;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}
	

	@Override
	public FacilityDetailDTO getdetailById(Integer id) {
		// TODO Auto-generated method stub
		FacilityModel facilityModel=hibernateTemplate.get(FacilityModel.class, id);
		@SuppressWarnings("unchecked")
		List<FacilityCurrentStatus> facilityCurrentStatusList=(List<FacilityCurrentStatus>)hibernateTemplate.findByNamedParam("From "
				+ "FacilityCurrentStatus WHERE FacilityId=:facilityId", "facilityId", id);
		if (facilityModel==null) {
			return null;
		}
		FacilityDetailDTO facilityDetailDTO=new FacilityDetailDTO();
	
		facilityDetailDTO.setBOMItemList(bomHeadDao.getDataByIdInItemAddBracket(facilityModel.getBOMHead().getId()));
		facilityDetailDTO.setBrand(facilityModel.getBrand());
		facilityDetailDTO.setFacilityCode(facilityModel.getFacilityCode());
		if (facilityModel.getStation()!=null) {
			facilityDetailDTO.setStationNumber(facilityModel.getStation().getStationNumber());
		}
		if (facilityModel.getBOMHead()!=null) {
			facilityDetailDTO.setMaterialNumber(hibernateTemplate.get(Material.class, 
					facilityModel.getBOMHead().getMaterialId()).getMaterialNumber());
		}
		if (facilityModel.getSupplier()!=null) {
			facilityDetailDTO.setSupplierNumber(facilityModel.getSupplier().getCode());
		}
		
		facilityDetailDTO.setModel(facilityModel.getModel());
		facilityDetailDTO.setName(facilityModel.getName());
		String state=null;
		if (facilityModel.getState()==1) {
			state="已激活";
		}
		else if(facilityModel.getState()==0){
			state="未激活";
		}
		else {
			state="已删除";
		}
		facilityDetailDTO.setState(state);

		String BOM=null;
		if (facilityModel.getBOMHead()!=null) {
			Material material=hibernateTemplate.get(Material.class, facilityModel.getBOMHead().getMaterialId());
			BOM=material.getMaterialNumber()+'|'+material.getVersion().toString();
		}
		
		facilityDetailDTO.setBOM(BOM);
		facilityDetailDTO.setManufacturingDate(facilityModel.getManufacturingDate());
		facilityDetailDTO.setInstalledDate(facilityModel.getInstalledDate());
		if (facilityCurrentStatusList.size()>=1) {
			FacilityCurrentStatus facilityCurrentStatus=facilityCurrentStatusList.get(0);
			facilityDetailDTO.setRunningState(facilityCurrentStatus.getRunningStatus().toString());
			facilityDetailDTO.setCurrentJobCode(facilityCurrentStatus.getCurrentJobCode());
			facilityDetailDTO.setCurrentJobName(facilityCurrentStatus.getCurrentJobName());
			facilityDetailDTO.setCurrentPartCode(facilityCurrentStatus.getCurrentPartCode());
			facilityDetailDTO.setCurrentPartSerialNumber(facilityCurrentStatus.getCurrentPartSerialNumber());
			facilityDetailDTO.setCurrentPartInputDateTime(facilityCurrentStatus.getCurrentPartInputDateTime());
			facilityDetailDTO.setCurrentPartType(facilityCurrentStatus.getCurrentPartType());
			facilityDetailDTO.setWorkingOperatorCode(facilityCurrentStatus.getWorkingOperatorCode());
			facilityDetailDTO.setIPV4Address(facilityCurrentStatus.getIPV4Address());
			facilityDetailDTO.setRoutingCode(facilityCurrentStatus.getRoutingCode());
			facilityDetailDTO.setUpdateDateTime(facilityCurrentStatus.getUpdateDateTime());
			facilityDetailDTO.setLastPartSerialNumber(facilityCurrentStatus.getLastPartSerialNumber());
			facilityDetailDTO.setLastPartOutputDateTime(facilityCurrentStatus.getLastPartOutputDateTime());
			
			facilityDetailDTO.setSerialNumber(facilityCurrentStatus.getCurrentPartCode());
			facilityDetailDTO.setLastPartCycleTimeSec(facilityCurrentStatus.getLastPartCycleTimeSec());
		}
		
	
		return facilityDetailDTO;
	
	}

	@Override
	public FacilityEditeDTO gEditeDTOById(Integer id) {
		// TODO Auto-generated method stub
		FacilityEditeDTO facilityEditeDTO=new FacilityEditeDTO();
		FacilityModel facilityModel=hibernateTemplate.get(FacilityModel.class, id);
		if (facilityModel==null) {
			return null;
		}
		BOMHeadModel bomHeadModel=facilityModel.getBOMHead();
		
		if (bomHeadModel!=null) {
			Material material=hibernateTemplate.get(Material.class, bomHeadModel.getMaterialId());
			facilityEditeDTO.setMaterialNumber(material.getMaterialNumber());
			facilityEditeDTO.setVersion(bomHeadModel.getVersion());
		}
		
	
		facilityEditeDTO.setBrand(facilityModel.getBrand());
		facilityEditeDTO.setFacilityCode(facilityModel.getFacilityCode());
		facilityEditeDTO.setInstalledDate(facilityModel.getInstalledDate());
		facilityEditeDTO.setManufacturingDate(facilityModel.getManufacturingDate());
		facilityEditeDTO.setModel(facilityModel.getModel());
		facilityEditeDTO.setName(facilityModel.getName());
		facilityEditeDTO.setId(id);
		facilityEditeDTO.setServiceExpirationDate(facilityModel.getServiceExpirationDate());
		facilityEditeDTO.setState(facilityModel.getState().toString());
		if (facilityModel.getStation()!=null) {
			facilityEditeDTO.setStationNumber(facilityModel.getStation().getId().toString());
		}
        if (facilityModel.getSupplier()!=null) {
			facilityEditeDTO.setSupplierNumber(String.valueOf(facilityModel.getSupplier().getId()));
		}
       
		return facilityEditeDTO;
	}
	@Transactional
	@Override
	public void updateFacility(FacilityAddDTO facilityAddDTO) {
		// TODO Auto-generated method stub
		FacilityModel facilityModel=new FacilityModel();
		Date date=new Date();
		//BOM列表的格式有待开发，先写死掉为1
		facilityModel.setBOMHead(hibernateTemplate.get(BOMHeadModel.class, facilityAddDTO.getBOMHeadId()));
		facilityModel.setBrand(facilityAddDTO.getBrand());
		facilityModel.setCreateDateTime(date);
		facilityModel.setCreator(hibernateTemplate.get(UserModel.class, facilityAddDTO.getUserId()));
		facilityModel.setEditDateTime(date);
		
		facilityModel.setEditor(hibernateTemplate.get(UserModel.class, facilityAddDTO.getUserId()));
		facilityModel.setFacilityCode(facilityAddDTO.getFacilityCode());
		facilityModel.setInstalledDate(new Date());
		facilityModel.setManufacturingDate(facilityAddDTO.getManufacturingDate());
		facilityModel.setModel(facilityAddDTO.getModel());
		facilityModel.setName(facilityAddDTO.getName());
		facilityModel.setServiceExpirationDate(facilityAddDTO.getServiceExpirationDate());
		facilityModel.setState(facilityAddDTO.getState());
		facilityModel.setStation(hibernateTemplate.get(StationModel.class, facilityAddDTO.getStationId()));
		facilityModel.setSupplier(hibernateTemplate.get(SupplierModel.class, facilityAddDTO.getSupplierId()));
		facilityModel.setId(facilityAddDTO.getId());
		hibernateTemplate.update(facilityModel);
		
	}
@Transactional
	@Override
	public void saveFacility(FacilityAddDTO facilityAddDTO) {
		// TODO Auto-generated method stub
		FacilityModel facilityModel=new FacilityModel();
		Date date=new Date();
		//BOM列表的格式有待开发，先写死掉为1
		facilityModel.setBOMHead(hibernateTemplate.get(BOMHeadModel.class, facilityAddDTO.getBOMHeadId()));
		facilityModel.setBrand(facilityAddDTO.getBrand());
		facilityModel.setCreateDateTime(date);
		facilityModel.setCreator(hibernateTemplate.get(UserModel.class, facilityAddDTO.getUserId()));
		facilityModel.setEditDateTime(date);
		facilityModel.setEditor(hibernateTemplate.get(UserModel.class, facilityAddDTO.getUserId()));
		facilityModel.setFacilityCode(facilityAddDTO.getFacilityCode());
		facilityModel.setInstalledDate(facilityAddDTO.getInstalledDate());
		facilityModel.setManufacturingDate(facilityAddDTO.getManufacturingDate());
		facilityModel.setModel(facilityAddDTO.getModel());
		facilityModel.setName(facilityAddDTO.getName());
		facilityModel.setServiceExpirationDate(facilityAddDTO.getServiceExpirationDate());
		facilityModel.setState(facilityAddDTO.getState());
		facilityModel.setStation(hibernateTemplate.get(StationModel.class, facilityAddDTO.getStationId()));
		facilityModel.setSupplier(hibernateTemplate.get(SupplierModel.class, facilityAddDTO.getSupplierId()));
		
		hibernateTemplate.save(facilityModel);
		
	}
	@Override
	public FacilityModel getFacilityModleByCode(String facilityCode) {
		// TODO Auto-generated method stub
		List<FacilityModel> facilityModels=(List<FacilityModel>)hibernateTemplate.findByNamedParam("From FacilityModel Where FacilityCode=:facilityCode", "facilityCode", facilityCode);
		if (facilityModels.size()==0) {
			return null;
		}
		return facilityModels.get(0);
	}
	
	@Override
	public List<Map<String, Object>> getFacilityList() {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],FacilityCode label from core.Facility where State = 1 Order By FacilityCode";
		try{
			Query query = session.createSQLQuery(sql);//.addScalar("key", StandardBasicTypes.INTEGER).addScalar("label", StandardBasicTypes.STRING);
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	@Override
	public FacilityModel getFacilityModelById(Integer id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(FacilityModel.class, id);
	}
	@Override
	public int getCountAll(String facilityCode, String stationCode, String name, String materialNumber, String brand,
			String supplier, String model) {
		// TODO Auto-generated method stub
		try {
			String hql="select count(*) FROM FacilityModel WHERE State!=-1";
			if (facilityCode!=null && !"".equals(facilityCode)) {
				hql += "and FacilityCode like '%"+facilityCode+"%' ";
			}
			if (stationCode!=null && !"".equals(stationCode)) {
				hql += "and  StationId in (select S.id FROM StationModel AS S where StationNumber like '%"+stationCode+"%' )";
			}
			if (name!=null && !"".equals(name)) {
				hql += "and Name like '%"+name+"%' ";
			}
			if (materialNumber!=null && !"".equals(materialNumber)) {
				hql += "and BOMHeadId in (select B.Id from BOMHeadModel as B where MaterialId in (select "
						+ "M.id from Material as M where MaterialNumber like '%"+materialNumber+"%'))" ;
			}
			if (brand!=null && !"".equals(brand)) {
				hql += "and Brand like '%"+brand+"%' ";
			}
			if (supplier!=null && !"".equals(supplier)) {
				hql += "and  SupplierId in (select S.id FROM SupplierModel as S where Code like '%"+supplier+"%' )";
			}
			if (model!=null && !"".equals(model)) {
				hql += "and Model like '%"+model+"%' ";
			}
			
			List<Long> list=(List<Long>) hibernateTemplate.find(hql);
			if(list!=null&&list.size()>0){
	            return list.get(0).intValue();
	        }

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		finally {
			
		}
		return 0;
	
	}
	@Override
	public List<FacilityModel> getFacilityByString(String facilityCode) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<FacilityModel> facilityModels=(List<FacilityModel>) hibernateTemplate.
				findByNamedParam("from FacilityModel where FacilityCode=:facilityCode", "facilityCode", facilityCode);
		return facilityModels;
	}

}
