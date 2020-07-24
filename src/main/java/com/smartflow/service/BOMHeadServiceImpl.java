package com.smartflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartflow.dao.BOMHeadDao;
import com.smartflow.model.BOMHeadModel;
@Service
public class BOMHeadServiceImpl implements BOMHeadService{
@Autowired
BOMHeadDao bomHeadDao;
	@Override
	public List<Map<String, Object>> getDataByIdInItemAddBracket(int i) {
		// TODO Auto-generated method stub
		return bomHeadDao.getDataByIdInItemAddBracket(i);
	}

	@Override
	public void getChildData(Integer materialId) {
		// TODO Auto-generated method stub
		bomHeadDao.getChildData(materialId);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		bomHeadDao.init();
	}

	@Override
	public List<BOMHeadModel> getBOMList(String materialNumber, Integer Version) {
		// TODO Auto-generated method stub
		return bomHeadDao.getMapList(materialNumber, Version);
	}

}
