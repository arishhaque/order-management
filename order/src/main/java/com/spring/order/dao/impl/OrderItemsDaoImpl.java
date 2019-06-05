package com.spring.order.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.spring.order.dao.OrderItemsDao;
import com.spring.order.generic.dao.impl.GenericHibernateDaoImpl;
import com.spring.order.model.OrderItems;

@Repository("orderItemsDao")
public class OrderItemsDaoImpl extends GenericHibernateDaoImpl<OrderItems, BigInteger> implements OrderItemsDao {

	
	@SuppressWarnings("unchecked")
	public List<OrderItems> findAllOrderItems() {
		
		Session session = getSession();
		TypedQuery<OrderItems> query = session.createQuery("from com.spring.order.model.OrderItems oi where oi.active=true");
		session.flush();
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public OrderItems getOrderItemsById(Map<String, Object> paramsKayAndValues) {
		
		Session session = getSession();
		TypedQuery<OrderItems> query = session.createQuery("from com.spring.order.model.OrderItems oi where oi.active=true");
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		session.flush();
		
		return query.getResultList().get(0);
	}
}
