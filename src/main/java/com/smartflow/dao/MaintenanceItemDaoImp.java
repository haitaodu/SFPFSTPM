package com.smartflow.dao;

import com.smartflow.common.util.CategoryUtil;
import com.smartflow.dto.maintenanceitem.MaintenanceItemAddDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemDetailDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemEditeDTO;
import com.smartflow.dto.maintenanceitem.MaintenanceItemPageDTO;
import com.smartflow.model.MaintenanceItem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author haita
 */
@Repository
public class MaintenanceItemDaoImp implements MaintenanceItemDao{
	final
	HibernateTemplate hibernateTemplate;

	@Autowired
	public MaintenanceItemDaoImp(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public List<MaintenanceItemPageDTO> pageDTO(Integer pageSize,
												Integer pageIndex,
												String maintenanceItemName) {
		String hql="FROM MaintenanceItem WHERE State=1";
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query=session.createQuery(parseToString(hql,maintenanceItemName));
		query.setFirstResult((pageIndex-1)*pageSize);
		query.setMaxResults(pageSize);
		try {
		@SuppressWarnings("unchecked")
		List<MaintenanceItem> maintenanceItems=query.list();

		return getPageDtoList(maintenanceItems);
		
        } catch (Exception e) {
        	return new ArrayList<>();
		}
        finally{
        session.close();
		}
	
	}
	@Override
	public MaintenanceItemDetailDTO getDtoById(Integer id) {
		
		MaintenanceItem maintenanceItem=hibernateTemplate.get(MaintenanceItem.class, id);

		MaintenanceItemDetailDTO maintenanceItemDetailDTO=new MaintenanceItemDetailDTO();
		
		maintenanceItemDetailDTO.setName(maintenanceItem.getName());
		maintenanceItemDetailDTO.setType
				(CategoryUtil.getValueByKey(maintenanceItem.getCategory()));
		return maintenanceItemDetailDTO;
	}
	@Override
	@Transactional(rollbackOn=Exception.class)
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
						return false;
		}
	
	
	}
	@Transactional(rollbackOn=Exception.class)
	@Override
	public boolean saveAddData(MaintenanceItemAddDTO maintenanceItemAddDTO) {
		try {

		    MaintenanceItem maintenanceItem=new MaintenanceItem();
		    parseToMaintenance(maintenanceItem,
					maintenanceItemAddDTO.getTypeId(),
					maintenanceItemAddDTO.getName(),
					maintenanceItemAddDTO.getStateId());
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
	public Integer countAll( String maintenanceItemName) {
		
		String hql="select count(*) FROM MaintenanceItem WHERE state=1";
		if (maintenanceItemName!=null && !"".equals(maintenanceItemName)) {
			hql += "and Name like '%"+maintenanceItemName+"%' ";
		}
		   @SuppressWarnings("unchecked")
		List<Long> list=(List<Long>) hibernateTemplate.find(hql);
	        if(!list.isEmpty()){
	            return list.get(0).intValue();
	        }
		return 0;
	}


	@Transactional(rollbackOn=Exception.class)
	@Override
	public void del(int id) {
		MaintenanceItem maintenanceItem
				=hibernateTemplate.get(MaintenanceItem.class,id);
        maintenanceItem.setState(-1);
        hibernateTemplate.update(maintenanceItem);
	}

	@Override
	public boolean isRegister(String name) {
		return (!hibernateTemplate.findByNamedParam
				("FROM MaintenanceItem " +
								"Where name=:name",
						"name",
						name).isEmpty());
	}

	/**
	 * 分页查询查询转换
	 * @param hql 查询语句
	 * @param maintenanceItemName 维保项名称
	 * @return 返回sql查询语句
	 */
	private String parseToString(String hql,String maintenanceItemName)
	{
		if (maintenanceItemName!=null && !"".equals(maintenanceItemName)) {
			hql += "and Name like '%"+maintenanceItemName+"%' ";
		}
		return hql;
	}


	/**
	 * 把维保项的列表转化为维保项分页
	 * @return 维保项分页列表
	 */
	private List<MaintenanceItemPageDTO> getPageDtoList(List<MaintenanceItem> maintenanceItems)
	{
		List<MaintenanceItemPageDTO> itemPageDtos =new ArrayList<>();
		for (MaintenanceItem maintenanceItem : maintenanceItems) {

			itemPageDtos .add(parseToPageDto(maintenanceItem));

		}
		return itemPageDtos ;
	}


	/**
	 * 将数据库维保项实体转化为分页数据
	 * @param maintenanceItem 数据库维保项
	 * @return 分页数据
	 */
	private  MaintenanceItemPageDTO parseToPageDto(MaintenanceItem maintenanceItem)
	{
		MaintenanceItemPageDTO maintenanceItemPageDTO=new MaintenanceItemPageDTO();
	    maintenanceItemPageDTO.setType
				(CategoryUtil.getValueByKey(maintenanceItem.getCategory()));
		maintenanceItemPageDTO.setId(maintenanceItem.getId());
		maintenanceItemPageDTO.setName(maintenanceItem.getName());
		return maintenanceItemPageDTO;
	}

	/**
	 * 将维保项修饰为可保存的维保项
	 * @param maintenanceItem 维保项
	 * @param categoryType 类型
	 * @param name 名字
	 * @param state 状态
	 */
	private void parseToMaintenance(MaintenanceItem maintenanceItem,int categoryType,String name,int state)
	{

		maintenanceItem.setName(name);
		maintenanceItem.setState(state);
		maintenanceItem.setCreationDateTime(new Date());
		maintenanceItem.setCreatorId(10);
		maintenanceItem.setEditDateTime(new Date());
		maintenanceItem.setEditorId(10);
		maintenanceItem.setCategory(categoryType);
	}
}
