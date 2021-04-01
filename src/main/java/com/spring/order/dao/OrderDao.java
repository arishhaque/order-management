package com.spring.order.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.spring.order.generic.dao.GenericHibernateDao;
import com.spring.order.model.Order;

public interface OrderDao extends GenericHibernateDao<Order, BigInteger> {


	public List<Order> findAllOrders();
	
	public Order getOrderById(Map<String, Object> paramsKayAndValues);
	

}
