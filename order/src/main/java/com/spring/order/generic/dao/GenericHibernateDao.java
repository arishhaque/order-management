package com.spring.order.generic.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericHibernateDao <T, PK extends Serializable> {
    
	public PK create(T o);

	public List<PK> createBatch(List<T> oList);
	
	public T read(PK id);

	public void update(T o);

	public void updateBatch(List<T> transientObjectList);

	public void delete(T o);

	List<Object> convertToObjectList(List<Object[]> objArrayList, Object object, List<String> excludedFieldNames,
			List<String> includedFieldNames)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException;
    



}
