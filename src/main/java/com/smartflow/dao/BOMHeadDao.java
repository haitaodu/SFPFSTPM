package com.smartflow.dao;

import java.util.List;
import java.util.Map;

import com.smartflow.model.BOMHeadModel;

public interface BOMHeadDao {

	//根据Id号查找到相应的BOMHeadItem表的数据并对MaterialNumber+(MaterialName)
	public List<Map<String, Object>> getDataByIdInItemAddBracket(int i);
	//根据MaterialId递归找出他相应的BOMHeadItem子节点
	public void getChildData(Integer materialId);
	//初始化BOMItem的数组以及数组的排列序号n
	public void init();
	//根据物料号和版本号模糊查询出BOM的数据
	public List<BOMHeadModel> getMapList(String materialNumber,Integer Version);
	//根据物料号查询物料Id
	public  Integer getMaterialIdByMaterialNumber(String materialNumber);
	
}
