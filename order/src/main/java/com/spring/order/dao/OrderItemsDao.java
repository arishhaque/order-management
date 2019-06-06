package com.spring.order.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.spring.order.generic.dao.GenericHibernateDao;
import com.spring.order.model.OrderItems;

public interface OrderItemsDao extends GenericHibernateDao<OrderItems, BigInteger> {

	public List<OrderItems> findAllOrderItems();

	public OrderItems getOrderItemsById(Map<String, Object> paramsKayAndValues);
	
	public OrderItems getOrderItemsByOrderIdandItemId(Map<String, Object> paramsKayAndValues);
	
	public List<OrderItems> listOrderItemsByOrderIdandItemIds(Map<String, Object> paramsKayAndValues);
	
	public List<Object[]> getOrderDetailsById( Map<String, Object> paramsKayAndValues);

}
