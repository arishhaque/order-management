package com.spring.order.dao;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.spring.order.generic.dao.GenericHibernateDao;

@Transactional(value = "transactionManager")
public abstract class GenericHibernateDaoImpl<T, PK extends Serializable> implements GenericHibernateDao<T, PK> {

	private final Class<T> entity;
	
	@SuppressWarnings("unchecked")
	public GenericHibernateDaoImpl(){
		this.entity =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public GenericHibernateDaoImpl(Class<T> entity) {

		this.entity = entity;
	}
	
    @Autowired
    private SessionFactory sessionFactory;
    
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
		
    	this.sessionFactory = sessionFactory;
		
	}
    
    @SuppressWarnings("unchecked")
	public PK create(T o) {

		Session session = sessionFactory.getCurrentSession();
		PK pk = (PK) session.save(o);
		session.flush();
		return pk;
	}


	@SuppressWarnings({ "unchecked"})
	public List<PK> createBatch(List<T> oList) 
	{		
		Session session = sessionFactory.getCurrentSession();
		
		List<PK> pkList=new ArrayList<PK>();
		for(T o:oList) {
			pkList.add( (PK)session.save(o) );
			session.evict(o);
		}		
		session.flush();
		return pkList;
	}

	public T read(PK id) {

		Session session = sessionFactory.getCurrentSession();
		T t = (T) session.get(entity, id);
		session.flush();
		return t;
	}

	public void update(T o) {

		Session session = sessionFactory.getCurrentSession();
		session.update(o);
		session.flush();
	}

	public void updateBatch(List<T> transientObjectList) {
		
		Session session = sessionFactory.getCurrentSession();
		for(T o:transientObjectList)
			session.merge(o);
		session.flush();
	}
	

	public void delete(T o) {

		Session session = sessionFactory.getCurrentSession();
		session.delete(o);
		session.flush();
	}

	
	@Override
	public List<Object> convertToObjectList(List<Object[]> objArrayList, Object object, List<String> excludedFields, List<String> includedFields)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		List<Object> ObjectList = new ArrayList<>();

		for (Object[] obj : objArrayList) {
			Class<?> cls = object.getClass();
			object = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();

			int i = 0;
			for (Field field : fields)
				
				if (includedFields != null) {
					if (includedFields.contains(field.getName())) {
						field.setAccessible(true);
						
						if(obj[i] != null && field.getType().equals(Boolean.class) &&  !field.getType().equals(obj[i].getClass()))
						{
						
							if(obj[i].equals((byte) 1))
											field.set(object, true);
							else
								field.set(object, false);
						}
							else 
							field.set(object, obj[i]);
							
						i++;
					}
				}
				else {
					if ("serialVersionUID".equalsIgnoreCase(field.getName()) || excludedFields != null && excludedFields.contains(field.getName()))
						continue;
					field.setAccessible(true);
					
					if(obj[i] != null && field.getType().equals(Boolean.class) &&  !field.getType().equals(obj[i].getClass()))
					{
					
						if(obj[i].equals((byte) 1))
										field.set(object, true);
						else
							field.set(object, false);
					}
						else 
						field.set(object, obj[i]);
						
					i++;
				}

			ObjectList.add(object);
		}

		return ObjectList;
	}

    

}
