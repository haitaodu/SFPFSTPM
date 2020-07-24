package com.smartflow.service;

import java.util.List;
import java.util.Map;

public interface SupplierService {
	//拉取Supplier表的key,label数据
		List<Map<String, Object>> getSupplierList();
}
