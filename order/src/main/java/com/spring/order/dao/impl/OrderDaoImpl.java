package com.spring.order.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.spring.order.dao.GenericHibernateDaoImpl;
import com.spring.order.dao.OrderDao;
import com.spring.order.model.Order;

@Repository("orderDao")
public class OrderDaoImpl extends GenericHibernateDaoImpl<Order, BigInteger> implements OrderDao {

	@SuppressWarnings("unchecked")
	public List<Order> findAllOrders() {
		
		Session session = getSession();
		TypedQuery<Order> query = session.createQuery("from com.spring.order.model.Order t where t.active=true");
		session.flush();
		
		return query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public Order getOrderById(Map<String, Object> paramsKayAndValues) {
		
		Session session = getSession();
		TypedQuery<Order> query = session.createQuery("from com.spring.order.model.Order o where o.active=true");
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		session.flush();
		
		return query.getResultList().get(0);
	}
}
