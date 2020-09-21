package com.smartflow.dao;

import com.smartflow.dto.facility.FacilityAddDTO;
import com.smartflow.dto.facility.FacilityDetailDTO;
import com.smartflow.dto.facility.FacilityEditeDTO;
import com.smartflow.dto.facility.FacilityPageSearchDTO;
import com.smartflow.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author haita
 */
@Repository
public class FacilityDaoImpl implements FacilityDao{
final
HibernateTemplate hibernateTemplate;
private final
BOMHeadDao  bomHeadDao;

	@Autowired
	public FacilityDaoImpl(HibernateTemplate hibernateTemplate, BOMHeadDao bomHeadDao) {
		this.hibernateTemplate = hibernateTemplate;
		this.bomHeadDao = bomHeadDao;
	}

	@Override
	public String toString() {
		
		return super.toString();
	}
	@Override
	public List<FacilityModel> getPagesByConditions(FacilityPageSearchDTO facilityPageSearchDTO) {
		 Session session=hibernateTemplate.getSessionFactory().openSession();
		try {
		String hql="FROM FacilityModel WHERE State!=-1";
		Query query=session.createQuery(parseToSearch(hql,facilityPageSearchDTO));
		query.setFirstResult((facilityPageSearchDTO.getPageIndex()-1)*facilityPageSearchDTO.getPageSize());
		query.setMaxResults(facilityPageSearchDTO.getPageSize());
	    @SuppressWarnings("unchecked")
	    List<FacilityModel> facilityModels=query.list();
	    return facilityModels;
		} catch (Exception e) {

			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}
	

	@Override
	public FacilityDetailDTO getdetailById(Integer id) {
		
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
		
		List<FacilityModel> facilityModels=(List<FacilityModel>)hibernateTemplate.findByNamedParam("From FacilityModel Where FacilityCode=:facilityCode", "facilityCode", facilityCode);
		if (facilityModels.size()==0) {
			return null;
		}
		return facilityModels.get(0);
	}
	
	@Override
	public List<Map<String, Object>> getFacilityList() {
		
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],FacilityCode label from core.facility where State = 1 Order By FacilityCode";
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
		
		return hibernateTemplate.get(FacilityModel.class, id);
	}
	@Override
	public int getCountAll(FacilityPageSearchDTO facilityPageSearchDTO) {
		
		try {
			String hql="select count(*) FROM FacilityModel WHERE State!=-1";


			@SuppressWarnings("unchecked")
			List<Long> list=(List<Long>) hibernateTemplate.find(parseToSearch(hql,facilityPageSearchDTO));
			if(!list.isEmpty()){
	            return list.get(0).intValue();
	        }

		} catch (Exception e) {
			
			e.printStackTrace();
			return 0;
		}
		finally {
			
		}
		return 0;
	
	}
	@Override
	public List<FacilityModel> getFacilityByString(String facilityCode) {
		
		
		@SuppressWarnings("unchecked")
		List<FacilityModel> facilityModels=(List<FacilityModel>) hibernateTemplate.
				findByNamedParam("from FacilityModel where FacilityCode=:facilityCode", "facilityCode", facilityCode);
		return facilityModels;
	}


	/**
	 * 填充分页请求参数
	 * @param hql 分页请求数据
	 * @param facilityPageSearchDTO 分页请求参数dto
	 * @return 分页查询语句
	 */
	private  String parseToSearch(String hql, FacilityPageSearchDTO facilityPageSearchDTO
								  )
	{
		if (facilityPageSearchDTO.getFacilityCode()!=null && !"".equals(facilityPageSearchDTO.getFacilityCode())) {
			hql += "and FacilityCode like '%"+facilityPageSearchDTO.getFacilityCode()+"%' ";
		}
		if (facilityPageSearchDTO.getStationNumber()!=null && !"".equals(facilityPageSearchDTO.getStationNumber())) {
			hql += "and  StationId in (select S.id FROM StationModel AS S where StationNumber like '%"+facilityPageSearchDTO.getStationNumber()+"%' )";
		}
		if (facilityPageSearchDTO.getName()!=null && !"".equals(facilityPageSearchDTO.getName())) {
			hql += "and Name like '%"+facilityPageSearchDTO.getName()+"%' ";
		}
		if (facilityPageSearchDTO.getMaterialNumber()!=null && !"".equals(facilityPageSearchDTO.getMaterialNumber())) {
			hql += "and BOMHeadId in (select B.Id from BOMHeadModel as B where MaterialId in (select "
					+ "M.id from Material as M where MaterialNumber like '%"+facilityPageSearchDTO.getMaterialNumber()+"%'))" ;
		}
		if (facilityPageSearchDTO.getBrand()!=null && !"".equals(facilityPageSearchDTO.getBrand())) {
			hql += "and Brand like '%"+facilityPageSearchDTO.getBrand()+"%' ";
		}
		if (facilityPageSearchDTO.getSupplierNumber()!=null && !"".equals(facilityPageSearchDTO.getSupplierNumber())) {
			hql += "and  SupplierId in (select S.id FROM SupplierModel as S where Code like '%"+facilityPageSearchDTO.getSupplierNumber()+"%' )";
		}
		if (facilityPageSearchDTO.getModel()!=null && !"".equals(facilityPageSearchDTO.getModel())) {
			hql += "and Model like '%"+facilityPageSearchDTO.getModel()+"%' ";
		}
		return hql;
	}
}
