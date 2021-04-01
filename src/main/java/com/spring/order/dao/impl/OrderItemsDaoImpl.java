package com.spring.order.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
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

	@Override
	public OrderItems getOrderItemsById(Map<String, Object> paramsKayAndValues) {
		
		/*Session session = getSession();
		TypedQuery<OrderItems> query = session.createQuery("from com.spring.order.model.OrderItems oi where oi.active=true");
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		session.flush();
		
		return query.getResultList().get(0);*/
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OrderItems getOrderItemsByOrderIdandItemId(Map<String, Object> paramsKayAndValues) {
		
		String hql = "from com.spring.order.model.OrderItems oi where oi.order.id=:orderId and oi.item.id=:itemId and oi.active=true";
		Session session = getSession();
		TypedQuery<OrderItems> query = session.createQuery(hql);
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		session.flush();
		
		return query.getResultList()!=null && !query.getResultList().isEmpty()? query.getResultList().get(0):null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItems> listOrderItemsByOrderIdandItemIds(Map<String, Object> paramsKayAndValues) {
		
		String hql = "from com.spring.order.model.OrderItems oi where oi.order.id=:orderId and oi.item.id in (:itemIds) and oi.active=true";
		Session session = getSession();
		TypedQuery<OrderItems> query = session.createQuery(hql);
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		session.flush();
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getOrderDetailsById( Map<String, Object> paramsKayAndValues) {
		
		Session session = getSession();
		String sqlQuery = "select distinct oi.order_id as orderId, \n"+
				" o.email_id as emailId, \n"+
				" o.description as description, \n" +
				" i.id itemId, \n"+
				" oi.order_status as orderStatus, \n"+
				" oi.price as price, \n"+
				" oi.quantity quantity \n"+
				" from order_items oi \n"+
				" left join item i on i.id=oi.item_id and i.active = true \n"+
				" left join orders o on o.id=oi.order_id and o.active = true \n"+
				" where oi.active=true and o.id=:orderId";
		
		@SuppressWarnings("deprecation")
		Query<Object[]> query = session.createSQLQuery(sqlQuery);
		
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
		}
		
		List<Object[]> result = query.list();
		session.flush();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getOrderDetailsBySearchKey( Map<String, Object> paramsKayAndValues) {
		
		Session session = getSession();
		String sqlQuery = "select distinct oi.order_id as orderId, \n"+
				" o.email_id as emailId, \n"+
				" o.description as description, \n" +
				" i.id itemId, \n"+
				" oi.order_status as orderStatus, \n"+
				" oi.price as price, \n"+
				" oi.quantity quantity \n"+
				" from order_items oi \n"+
				" left join item i on i.id=oi.item_id and i.active = true \n"+
				" left join orders o on o.id=oi.order_id and o.active = true \n"+
				" where oi.active=true and (o.description is not null and o.description like (:searchKey))";
		
		@SuppressWarnings("deprecation")
		Query<Object[]> query = session.createSQLQuery(sqlQuery);
		
		if(paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for(String key : paramsKayAndValues.keySet())
				query.setParameter(key, paramsKayAndValues.get(key));
		}
		
		List<Object[]> result = query.list();
		session.flush();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getOrderDetailsByEmptySearchKey() {
		
		Session session = getSession();
		String sqlQuery = "select distinct oi.order_id as orderId, \n"+
				" o.email_id as emailId, \n"+
				" o.description as description, \n" +
				" i.id itemId, \n"+
				" oi.order_status as orderStatus, \n"+
				" oi.price as price, \n"+
				" oi.quantity quantity \n"+
				" from order_items oi \n"+
				" left join item i on i.id=oi.item_id and i.active = true \n"+
				" left join orders o on o.id=oi.order_id and o.active = true \n"+
				" where oi.active=true";
		
		@SuppressWarnings("deprecation")
		Query<Object[]> query = session.createSQLQuery(sqlQuery);
		
		List<Object[]> result = query.list();
		session.flush();
		
		return result;
	}
	
}
