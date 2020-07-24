package com.smartflow.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.smartflow.dto.MaintenanceItem.MaintenanceItemAddDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemDetailDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemEditeDTO;
import com.smartflow.dto.MaintenanceItem.MaintenanceItemPageDTO;
import com.smartflow.model.MaintenanceItem;
@Repository
public class MaintenanceItemDaoImp implements MaintenanceItemDao{
	final
	HibernateTemplate hibernateTemplate;

	@Autowired
	public MaintenanceItemDaoImp(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public List<MaintenanceItemPageDTO> pageDTO(Integer pageSize, Integer pageIndex, String maintenanceItemName) {
		
        
			
		
		String hql="FROM MaintenanceItem WHERE State=1";
		if (maintenanceItemName!=null && !"".equals(maintenanceItemName)) {
			hql += "and Name like '%"+maintenanceItemName+"%' ";
		}

		@SuppressWarnings("unused")
		List<MaintenanceItemPageDTO> maintenanceItemPageDTOs=new ArrayList<>();
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		query.setFirstResult((pageIndex-1)*pageSize);
		query.setMaxResults(pageSize);
		try {
		@SuppressWarnings("unchecked")
		List<MaintenanceItem> maintenanceItems=query.list();
		for (MaintenanceItem maintenanceItem : maintenanceItems) {
			MaintenanceItemPageDTO maintenanceItemPageDTO2=new MaintenanceItemPageDTO();
			maintenanceItemPageDTO2.setId(maintenanceItem.getId());
			maintenanceItemPageDTO2.setName(maintenanceItem.getName());
			String category=null;
			if (maintenanceItem.getCategory()==0) {
				category="清洁清扫";
			}
			else if (maintenanceItem.getCategory()==1) {
				category="点检";
			}
			else if (maintenanceItem.getCategory()==2) {
				category="紧固";
			}
			else if (maintenanceItem.getCategory()==3) {
				category="校准";
			}
			else if (maintenanceItem.getCategory()==4) {
				category="跟换";
			}
			else if (maintenanceItem.getCategory()==5) {
				category="维修";
			}
			maintenanceItemPageDTO2.setType(category);
			maintenanceItemPageDTOs.add(maintenanceItemPageDTO2);
			
		}
		return maintenanceItemPageDTOs;
		
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	return null;
		}
        finally{
        session.close();
		}
	
	}
	@Override
	public MaintenanceItemDetailDTO getDTOById(Integer Id) {
		
		MaintenanceItem maintenanceItem=hibernateTemplate.get(MaintenanceItem.class, Id);
		String category=null;
		if (maintenanceItem.getCategory()==0) {
			category="清洁清扫";
		}
		else if (maintenanceItem.getCategory()==1) {
			category="点检";
		}
		else if (maintenanceItem.getCategory()==2) {
			category="紧固";
		}
		else if (maintenanceItem.getCategory()==3) {
			category="校准";
		}
		else if (maintenanceItem.getCategory()==4) {
			category="跟换";
		}
		else if (maintenanceItem.getCategory()==5) {
			category="维修";
		}
		MaintenanceItemDetailDTO maintenanceItemDetailDTO=new MaintenanceItemDetailDTO();
		
		maintenanceItemDetailDTO.setName(maintenanceItem.getName());
		maintenanceItemDetailDTO.setType(category);
		return maintenanceItemDetailDTO;
	}
	@Override
	@Transactional
	public boolean saveEditeData(MaintenanceItemEditeDTO maintenanceItemEditeDTO) {
		
		try {
			
	    MaintenanceItem maintenanceItem=new MaintenanceItem();
		maintenanceItem.setCategory(maintenanceItemEditeDTO.getTypeId());
		maintenanceItem.setCreationDateTime(new Date());
		maintenanceItem.setCreatorId(10);
		maintenanceItem.setEditDateTime(new Date());
		maintenanceItem.setEditorId(10);
		maintenanceItem.setName(maintenanceItemEditeDTO.getName());
		maintenanceItem.setState(maintenanceItemEditeDTO.getStateId());
		maintenanceItem.setId(maintenanceItemEditeDTO.getId());
		hibernateTemplate.update(maintenanceItem);
		return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	
	
	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public boolean saveAddData(MaintenanceItemAddDTO maintenanceItemAddDTO) {
		try {
			if ((hibernateTemplate.findByNamedParam
					("FROM MaintenanceItem " +
									"Where Name=:name",
							"name",
							maintenanceItemAddDTO.getName()).size()>0)) {
				return false;
			}
		    MaintenanceItem maintenanceItem=new MaintenanceItem();
			maintenanceItem.setCategory(maintenanceItemAddDTO.getTypeId());
			maintenanceItem.setCreationDateTime(new Date());
			maintenanceItem.setCreatorId(10);
			maintenanceItem.setEditDateTime(new Date());
			maintenanceItem.setEditorId(10);
			maintenanceItem.setName(maintenanceItemAddDTO.getName());
			maintenanceItem.setState(maintenanceItemAddDTO.getStateId());
			hibernateTemplate.save(maintenanceItem);
			return true;
			} catch (Exception e) {
				return false;
			}
	}
	@Override
	public MaintenanceItem getMaintenanceById(Integer id) {
		
		return hibernateTemplate.get(MaintenanceItem.class, id);
	}
	@Override
	public Integer countAll(Integer pageSize, Integer pageIndex, String maintenanceItemName) {
		
		String hql="select count(*) FROM MaintenanceItem WHERE State=1";
		if (maintenanceItemName!=null && !"".equals(maintenanceItemName)) {
			hql += "and Name like '%"+maintenanceItemName+"%' ";
		}
		   @SuppressWarnings("unchecked")
		List<Long> list=(List<Long>) hibernateTemplate.find(hql);
	        if(list!=null&&list.size()>0){
	            return list.get(0).intValue();
	        }
		return 0;
	}

	@Transactional
	@Override
	public void del(int id) {

	}

}
