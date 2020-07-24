package com.smartflow.service;

import java.util.List;
import java.util.Map;

import com.smartflow.model.BOMHeadModel;
import com.smartflow.model.FacilityModel;

public interface BOMHeadService {


	//根据Id号查找到相应的BOMHeadItem表的数据并对MaterialNumber+(MaterialName)
	public List<Map<String, Object>> getDataByIdInItemAddBracket(int i);
	//根据MaterialId递归找出他相应的BOMHeadItem子节点
	public void getChildData(Integer materialId);
	//初始化BOMItem的数组以及数组的排列序号n
	public void init();
	//根据物料号和版本号模糊查询出BOM的数据
	public List<BOMHeadModel> getBOMList(String materialNumber,Integer Version);

}
