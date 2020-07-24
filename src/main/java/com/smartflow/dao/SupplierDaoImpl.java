package com.smartflow.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
/**
 * @author haita
 */
@Repository
public class SupplierDaoImpl implements SupplierDao{
private final
HibernateTemplate hibernateTemplate;

	@Autowired
	public SupplierDaoImpl(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSupplierList() {
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "select Id [key],concat(SupplierCode,'('+Name+')') label from core.Supplier where State = 1 Order By SupplierCode";
		try{
			Query query = session.createSQLQuery(sql);
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			return new ArrayList<>();
		}finally{
			session.close();
		}
	}

}
