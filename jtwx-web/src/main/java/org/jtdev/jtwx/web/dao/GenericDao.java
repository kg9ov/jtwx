package org.jtdev.jtwx.web.dao;

import java.io.Serializable;

public interface GenericDao<T, K extends Serializable> {

	/** Persist a new instance of an object */
	public K create(T obj);
	
	/** Read an existing instance of an object from storage by id */
	public T read(K id);
	
	/** Update and existing instance of an object in storage */
	public void update(T obj);
	
	/** Delete an instance of an object from storage */
	public void delete(T obj);
	
}
