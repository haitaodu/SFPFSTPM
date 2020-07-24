package com.smartflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyLabelToLabelImpl implements KeyLabelToLabel{

	@Override
	public List<Map<String, Object>> changeDataToLabel(List<Map<String, Object>> KeyLabelList) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> labelList=new ArrayList<>();
		for (Map<String, Object> mapForKeyLabel : KeyLabelList) {
			Map<String, Object> mapForLabel=new HashMap<>();
			mapForLabel.put("label", mapForKeyLabel.get("label"));
			mapForLabel.put("key", mapForKeyLabel.get("label"));
			labelList.add(mapForLabel);
		}
		return labelList;
	}

}
