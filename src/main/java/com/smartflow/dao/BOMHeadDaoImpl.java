package com.smartflow.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import com.smartflow.model.BOMHeadModel;
import com.smartflow.model.BOMItemModel;
import com.smartflow.model.Material;
@Repository
public class BOMHeadDaoImpl implements BOMHeadDao {
	List<Map<String, Object>> bomItemDatas=new ArrayList<>();
	List<Integer> materialIds=new ArrayList<>();
	Integer rowCount=0;
	String m="●";
	int n=0;
	@Autowired
	HibernateTemplate hibernate;
	@Autowired
	MaterialDao materialDao;
	



	@Override
	public List<Map<String, Object>> getDataByIdInItemAddBracket(int i) {
		// TODO Auto-generated method stub
		//初始化BOMItem数据组的代码
		bomItemDatas=new ArrayList<>();
		n=0;
		m="●";

		Session session=hibernate.getSessionFactory().openSession();
		String hql="FROM BOMItemModel WHERE BOMHeadId=:i";
		Query query=session.createQuery(hql);
		query.setParameter("i", i);
		List<BOMItemModel> bomItemModels=query.list();
		session.close();

		for (BOMItemModel bomItemModel : bomItemModels) {

			Material material=materialDao.getDataById(bomItemModel.getMaterialId());
			String materialNumber=material.getMaterialNumber();
			String  materialName=material.getDescription();
			/*
			String isNeedSetupCheck=null;
			if (bomItemModel.isIsNeedSetupCheck()==true) {
				isNeedSetupCheck="True";
			}
			else if(bomItemModel.isIsNeedSetupCheck()==false){
				isNeedSetupCheck="False";
			}
			String isAlternative=null;
			if (bomItemModel.isIsAlternative()==true) {
				isAlternative="True";
			}
			else if (bomItemModel.isIsAlternative()==false) {
				isAlternative="False";
			}
			 */
			Map<String, Object> map=new HashMap<>();
			map.put("key", bomItemModel.getMaterialId());
			map.put("label",m+ materialNumber+'('+materialName+')'+'|'+bomItemModel.getVersion()+'|'+bomItemModel.getDesignator()+'|'+materialNumber);
			bomItemDatas.add(map);
			/*
			bomItemDatas.add(new BOMItemData(bomItemModel.getMaterialId(),
					materialName, bomItemModel.getVersion(), m+materialNumber+"("+materialName+")",
					bomItemModel.getDesignator(), bomItemModel.getQuantity(), 
					bomItemModel.getStationGroupId(), stationGroup.getStationGroupById(bomItemModel.getStationGroupId()).getDescription(), 
					bomItemModel.isIsNeedSetupCheck(), bomItemModel.getLayer(), bomItemModel.isIsAlternative(),
					unitDao.getUnitById(bomItemModel.getUnit()).getName(),bomItemModel.getUnit(),n));
					*/
			n=n+1;
			m=m+"●";
			
			getChildData(bomItemModel.getMaterialId());
			m=m.substring(0, m.length()-1);
			
		}
		return bomItemDatas;
	}
	@Override
	public void getChildData(Integer  materialId) {
		// TODO Auto-generated method stub


		@SuppressWarnings("unchecked")
		List<BOMHeadModel> bomHeadModels=(List<BOMHeadModel>) hibernate.findByNamedParam("FROM BOMHeadModel WHERE MaterialId=:materialId", "materialId", materialId);
		if (bomHeadModels.isEmpty()) {

		}
		else {
			//因为materialId为BomHead表的外键，对应关系为1对多关系，但是X说一个 materialId只对应一个BomHead记录，所以在这里我们只取第一个值
			Integer bomHeadId=bomHeadModels.get(0).getId();
			@SuppressWarnings("unchecked")
			List<BOMItemModel> bomItemModels=(List<BOMItemModel>) hibernate.findByNamedParam("FROM BOMItemModel WHERE BOMHeadId=:bomHeadId", "bomHeadId", bomHeadId);
			for (BOMItemModel bomItemModel : bomItemModels) {
				Material material=materialDao.getDataById(bomItemModel.getMaterialId());
				String materialNumber=material.getMaterialNumber();
				String  materialName=material.getDescription();
				/*
			String isNeedSetupCheck=null;
			if (bomItemModel.isIsNeedSetupCheck()==true) {
				isNeedSetupCheck="True";
			}
			else if(bomItemModel.isIsNeedSetupCheck()==false){
				isNeedSetupCheck="False";
			}
			String isAlternative=null;
			if (bomItemModel.isIsAlternative()==true) {
				isAlternative="True";
			}
			else if (bomItemModel.isIsAlternative()==false) {
				isAlternative="False";
			}
				 */
				/*
				bomItemDatas.add(new BOMItemData(bomItemModel.getMaterialId(),
						materialName, bomItemModel.getVersion(), m+materialNumber+"("+materialName+")",
						bomItemModel.getDesignator(), bomItemModel.getQuantity(), 
						bomItemModel.getStationGroupId(), stationGroup.getStationGroupById(bomItemModel.getStationGroupId()).getDescription(), 
						bomItemModel.isIsNeedSetupCheck(), bomItemModel.getLayer(),bomItemModel.isIsAlternative(),
						unitDao.getUnitById(bomItemModel.getUnit()).getName(),bomItemModel.getUnit(),n));
				*/
				Map<String, Object> map=new HashMap<>();
				map.put("key", bomItemModel.getMaterialId());
				map.put("label",m+ materialNumber+'('+materialName+')'+'|'+bomItemModel.getVersion()+'|'+bomItemModel.getDesignator()+'|'+materialNumber);
				bomItemDatas.add(map);
				n=n+1;
				m=m+"●";
				getChildData(bomItemModel.getMaterialId());
				m=m.substring(0, m.length()-1);

			}


		}

	}
	@Override
	public void init() {

		bomItemDatas=new ArrayList<>();

	
		materialIds=new ArrayList<>();
	}
	@Override
	public List<BOMHeadModel> getMapList(String materialNumber, Integer Version) {
		// TODO Auto-generated method stub
		
	String hql="From BOMHeadModel WHERE State=1 AND Version=:Version AND MaterialId IN(Select id From Material Where MaterialNumber =:materialNumber)"; 
	String[] paramNames= new String[]{"Version", "materialNumber"};
	Object[] values= new Object[]{Version, materialNumber};  
	@SuppressWarnings("unchecked")
	List<BOMHeadModel> bomHeadModels=(List<BOMHeadModel>)hibernate.findByNamedParam(hql, paramNames, values);
		return bomHeadModels;
	}
	@Override
	public Integer getMaterialIdByMaterialNumber(String materialNumber) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Material> materials=(List<Material>)hibernate.findByNamedParam(" From Material Where MaterialNumber =:materialNumber", 
				"materialNumber", materialNumber);
		if (materials.size()==0) {
			return null;
		}
		return materials.get(0).getId();
	}
	


}
