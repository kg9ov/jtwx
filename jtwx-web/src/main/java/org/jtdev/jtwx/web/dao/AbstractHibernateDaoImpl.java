package org.jtdev.jtwx.web.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractHibernateDaoImpl<T, K extends Serializable> implements GenericDao<T, K> {

	private Class<T> type;
	
	private SessionFactory sessionFactory;
	
	protected AbstractHibernateDaoImpl(Class<T> type) {
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public K create(T obj) {
		return (K) getCurrentSession().save(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T read(K id) {
		return (T) getCurrentSession().get(type, id);
	}

	@Override
	public void update(T obj) {
		getCurrentSession().update(obj);
	}

	@Override
	public void delete(T obj) {
		getCurrentSession().delete(obj);
	}

	@Autowired
	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
