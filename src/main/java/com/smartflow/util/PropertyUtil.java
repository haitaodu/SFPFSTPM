package com.smartflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyUtil {
	/**
	 * 状态下拉框
	 * @return
	 */
	public static List<Map<String,Object>> getStautsList(){
		List<Map<String,Object>> stateList = new ArrayList<Map<String,Object>>();
		Map<String,Object> stateMap1 = new HashMap<String, Object>();
		Map<String,Object> stateMap2 = new HashMap<String, Object>();
		Map<String,Object> stateMap3 = new HashMap<String, Object>();
		Map<String,Object> stateMap4 = new HashMap<String, Object>();
		Map<String,Object> stateMap5 = new HashMap<String, Object>();
		Map<String,Object> stateMap6 = new HashMap<String, Object>();
		Map<String,Object> stateMap7 = new HashMap<String, Object>();
		Map<String,Object> stateMap8 = new HashMap<String, Object>();
		Map<String,Object> stateMap9 = new HashMap<String, Object>();
		stateMap1.put("key", 1);
		stateMap1.put("label", "新");
		stateMap2.put("key", 2);
		stateMap2.put("label", "已分配");
		stateMap3.put("key", 3);
		stateMap3.put("label", "已完成");
		stateMap4.put("key", 4);
		stateMap4.put("label", "已审核");
		stateMap5.put("key", 5);
		stateMap5.put("label", "已审核完成");
		stateMap6.put("key", -1);
		stateMap6.put("label", "已取消");
		stateMap7.put("key", -2);
		stateMap7.put("label", "已拒绝");
		stateMap8.put("key", -3);
		stateMap8.put("label", "已重新安排");
		stateMap9.put("key", 0);
		stateMap9.put("label", "已关闭");
		stateList.add(stateMap1);
		stateList.add(stateMap2);
		stateList.add(stateMap3);
		stateList.add(stateMap4);
		stateList.add(stateMap5);
		stateList.add(stateMap6);
		stateList.add(stateMap7);
		stateList.add(stateMap8);
		stateList.add(stateMap9);				
		return stateList;
	}
	/**
	 * 通过状态id获取状态名
	 * @param statusId
	 * @return
	 */
	public static String getStatusName(Integer statusId){
		String status = "";
		if(statusId == 1){
			status = "新";
		}else if(statusId ==3){
			status =  "已完成";
		}else if(statusId ==4){
			status =  "已审核";
		}else if(statusId ==5){
			status =  "已审核完成";
		}else if(statusId ==-1){
			status =  "已取消";
		}else if(statusId ==-2){
			status =  "已拒绝";
		}else if(statusId ==-3){
			status =  "已重新安排";
		}else if(statusId ==0){
			status =  "已关闭";
		}else if(statusId == 2){
			status =  "已分配";	
		}
		return status;
	}
	/**
	 * 周期类型下拉框
	 * @return
	 */
	public static List<Map<String,Object>> getPeriodicTypeList(){
		List<Map<String, Object>> periodicTypeList = new ArrayList<>();
		Map<String, Object> periodicTypeMap1 = new HashMap<>();
		periodicTypeMap1.put("key", 1);
		periodicTypeMap1.put("label", "临时的");
		Map<String, Object> periodicTypeMap2 = new HashMap<>();
		periodicTypeMap2.put("key", 2);
		periodicTypeMap2.put("label", "周期性的");
		periodicTypeList.add(periodicTypeMap1);
		periodicTypeList.add(periodicTypeMap2);
		return periodicTypeList;
	}
	/**
	 * 根据周期类型id查询周期类型
	 * @param periodicTypeId
	 * @return
	 */
	public static String getPeriodicTypeNameByTypeId(Integer periodicTypeId){
		String periodicTypeName = "";
		if(periodicTypeId == 1){
			periodicTypeName = "临时的";
		}else if(periodicTypeId == 1){
			periodicTypeName = "周期性的";
		}
		return periodicTypeName;
	}
	/**
	 * 获取测量
	 * @return
	 */
	public static List<Map<String,Object>> getMeasurementCategoryList(){
		List<Map<String, Object>> measurementCategoryList = new ArrayList<>();
		Map<String, Object> measurementCategoryMap1 = new HashMap<>();
		measurementCategoryMap1.put("key", 1);
		measurementCategoryMap1.put("label", "单一值");
		Map<String, Object> measurementCategoryMap2 = new HashMap<>();
		measurementCategoryMap2.put("key", 2);
		measurementCategoryMap2.put("label", "曲线");
		measurementCategoryList.add(measurementCategoryMap1);
		measurementCategoryList.add(measurementCategoryMap2);
		return measurementCategoryList;
	}

	/**
	 * 根据测量类别id查询测量类别
	 * @param measurementCategoryId
	 * @return
	 */
	public static String getMeasurementCategory(Integer measurementCategoryId){
		String measurementCategory = "";
		if(measurementCategoryId == 1){
			measurementCategory = "单一值";
		}else if(measurementCategoryId == 2){
			measurementCategory = "曲线";
		}
		return measurementCategory;
	}
	
	public static Integer getMeasurementCategoryId(String measurementCategoryName) {
		Integer measumentCategoryId = null;
		if(measurementCategoryName.equals("单一值")) {
			measumentCategoryId = 1;
		}else if (measurementCategoryName.equals("曲线")) {
			measumentCategoryId = 2;
		}
		return measumentCategoryId;
	}
}
