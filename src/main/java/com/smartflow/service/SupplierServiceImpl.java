package com.smartflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartflow.dao.SupplierDao;
@Service
public class SupplierServiceImpl implements SupplierService{
@Autowired
SupplierDao supplierDao;
	@Override
	public List<Map<String, Object>> getSupplierList() {
		// TODO Auto-generated method stub
		return  supplierDao.getSupplierList();
	}

}
