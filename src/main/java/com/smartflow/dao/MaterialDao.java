package com.smartflow.dao;

import java.util.List;
import java.util.Map;

import com.smartflow.model.Material;
import com.smartflow.util.MaterialDataForSearch;

public interface MaterialDao {


	
	/**
	 * 根据公司id获取公司名称
	 * @param companyId
	 * @return
	 */
	public String getCompanyNumberByCompanyId(Integer companyId);
	
	/**
	 * 根据工站组id获取工站组名称
	 * @param stationGroupId
	 * @return
	 */
	public String getStationGroupNumberByStationGroupId(Integer stationGroupId);
	
	/**
	 * 根据请求地点id查询请求地点名称
	 * @param locationId
	 * @return
	 */
	public String getLocationNumberByLocationId(Integer locationId);
	
	/**
	 * 根据物料类型id查询物料类型名称
	 * @param materialGroupTypeId
	 * @return
	 */
	public String getMaterialGroupTypeNameByMaterialGroupTypeId(Integer materialGroupTypeId);
	
	/**
	 * 根据MSLId查询MSL名称
	 * @param mslId
	 * @return
	 */
	public String getMSLNameByMSLId(Integer mslId);

	/**
	 * 根据采购类型id查询采购类型名称
	 * @param procurementTypeId
	 * @return
	 */
	public String getProcurementTypeNameByProcurementTypeId(Integer procurementTypeId);
	
	/**
	 * 根据unitId查询UnitName
	 * @param unitId
	 * @return
	 */
	public String getUnitNameByUnitId(Integer unitId);
	
	/**
	 * 根据物料id查询物料详情
	 * @param material
	 * @return
	 */
	public Material getMaterialById(Integer materialId);

	/**
	 * 查询物料类型
	 * @return
	 */
	public List<Map<String, Object>> getMaterialTypeNameAndId();
	
	/**
	 * 查询单位名称和id
	 * @return
	 */
	public List<Map<String, Object>> getUnitNameAndId();
	
	/**
	 * 查询采购类型id和名称
	 * @return
	 */
	public List<Map<String, Object>> getProcurementTypeNameAndId();
	
	/**
	 * 查询请求地点id和名称
	 * @return
	 */
	public List<Map<String, Object>> getLocationNumberAndId();
	
	/**
	 * 查询MSL id和名称
	 * @return
	 */
	public List<Map<String, Object>> getMSLNameAndId();
	
	/**
	 * 查询工站组id和名称
	 * @return
	 */
	public List<Map<String, Object>> getStationGroupNumberAndId();
	
	/**
	 * 查询公司名称和id
	 * @return
	 */
	public List<Map<String, Object>> getCompanyNameAndId();
	
	/**
	 * 查询materialNumber存在的次数，判断materialNumber是否重复
	 * @param materialNumber
	 * @return
	 */
	public Integer getCountByMaterialNumber(String materialNumber);
	
	/**
	 * 添加物料
	 * @param material
	 */
	public void addMaterial(Material material);
	
	/**
	 * 修改物料
	 * @param material
	 */
	public void updateMaterial(Material material);
	
	/**
	 * 删除物料
	 * @param material
	 */
	public void deleteMaterial(Material material);
	
	//根据Id号找到相应的数据记录
	public Material getDataById(int id);
	//根据前端给出的物料号提示模糊查询物料信息
	public List<MaterialDataForSearch> getDataForSearch(String  materialNumber);
	//查找全部物料信息
	public List<String> getMaterials();
	//根据物料号精准查找物料信息
	public  Material getMaterialByNumber(String materialNumber);
}
