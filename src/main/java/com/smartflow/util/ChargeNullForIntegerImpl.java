package com.smartflow.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargeNullForIntegerImpl implements ChargeNullForInteger{

	
	private int i=0;
	@Override
	public Boolean isNull(List<Integer> list) {
		// TODO Auto-generated method stub
		for (Integer integer : list) {
		
			if (integer==null) {
				return true;
			}
			i=i+1;
		}
		return false;
	}

	@Override
	public String getErrorString(List<String> list){
		// TODO Auto-generated method stub
		return list.get(i);
	}

	@Override
	public Map<String, Object> getMap() {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<>();
		map.put("Version", "版本号未填写？");
		map.put("SupplierId", "供应商未选择？");
		map.put("UserId", "未登录用户？");
		map.put("StationId", "工站未选择？");
		map.put("State", "状态未选择？");
		
		return map;
	}

}
