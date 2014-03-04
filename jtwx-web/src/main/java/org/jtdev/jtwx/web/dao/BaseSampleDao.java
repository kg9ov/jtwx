package org.jtdev.jtwx.web.dao;

import java.io.Serializable;

public interface BaseSampleDao<T, K extends Serializable> extends GenericDao<T, K> {

	public T findLatest();
	
}
