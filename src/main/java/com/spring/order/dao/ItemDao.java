package com.spring.order.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.spring.order.generic.dao.GenericHibernateDao;
import com.spring.order.model.Item;

public interface ItemDao extends GenericHibernateDao<Item, BigInteger> {
	
	public List<Item> findAllItems();
	
	public Item getItemsById(Map<String, Object> paramsKayAndValues);
	
	public List<Item> getItemsByIds(Map<String, Object> paramsKayAndValues);
	
}
