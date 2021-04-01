package com.spring.order.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.spring.order.dao.ItemDao;
import com.spring.order.generic.dao.impl.GenericHibernateDaoImpl;
import com.spring.order.model.Item;

@Repository("itemDao")
public class ItemDaoImpl extends GenericHibernateDaoImpl<Item, BigInteger> implements ItemDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> findAllItems() {
		
		Session session = getSession();
		
		TypedQuery<Item> query = session.createQuery("from com.spring.order.model.Item i where i.active=true");
		session.flush();
			
		return query.getResultList();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Item getItemsById(Map<String, Object> paramsKayAndValues) {
		
		Session session = getSession();
		TypedQuery<Item> query = session.createQuery("from com.spring.order.model.Item i where i.active=true");
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		session.flush();
		
		return query.getResultList().get(0);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getItemsByIds(Map<String, Object> paramsKayAndValues) {
		
		Session session = getSession();
		TypedQuery<Item> query = session.createQuery("from com.spring.order.model.Item i where i.active=true and i.id in (:idList)");
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		session.flush();
		
		return query.getResultList();
	}

}
