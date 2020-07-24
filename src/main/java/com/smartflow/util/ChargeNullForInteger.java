package com.smartflow.util;

import java.util.List;
import java.util.Map;

public interface ChargeNullForInteger {

	Boolean isNull(List<Integer> list);
	
	String getErrorString(List<String> list);
	
	Map<String, Object> getMap();
}
