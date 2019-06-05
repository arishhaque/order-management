package com.spring.order.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


import com.spring.order.dao.OrderItemsDao;
import com.spring.order.generic.dao.impl.GenericHibernateDaoImpl;
import com.spring.order.model.OrderItems;

@Repository("orderItemsDao")
public class OrderItemsDaoImpl extends GenericHibernateDaoImpl<OrderItems, BigInteger> implements OrderItemsDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItems> findAllOrderItems() {
		
		Session session = getSession();
		TypedQuery<OrderItems> query = session.createQuery("from com.spring.order.model.OrderItems oi where oi.active=true");
		session.flush();
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getOrderDetailsById( Map<String, Object> paramsKayAndValues) {
		
		Session session = getSession();
		String sqlQuery = "select distinct oi.order_id as orderId, \n"+
				" o.email_id as emailId, \n"+
				" oi.order_status as orderStatus, \n"+
				" oi.price as price, \n"+
				" i.id itemId \n"+
				" from order_items oi \n"+
				" left join item i on i.id=oi.item_id and i.active = true \n"+
				" left join orders o on o.id=oi.order_id and o.active = true \n"+
				" where oi.id=:orderId";
		Query query = session.createSQLQuery(sqlQuery);
		
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
		}
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.list();
		session.flush();
		
		return result;
	}
	
	
	
}
