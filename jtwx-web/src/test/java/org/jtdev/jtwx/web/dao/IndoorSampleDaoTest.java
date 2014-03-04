package org.jtdev.jtwx.web.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.jtdev.jtwx.web.domain.IndoorSample;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration("/spring-context.xml")
public class IndoorSampleDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IndoorSampleDao indoorSampleDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private IndoorSample is;
	
	@Before
	public void resetIndoorSample() {
		is = new IndoorSample();
		is.setDate(new Date());
		is.setTemperature(55.55f);
		is.setHumidity(66.66f);
	}
	
	@Test
	public void testCreate() {
		Long id = indoorSampleDao.create(is);
		sessionFactory.getCurrentSession().flush();
		assertEquals(id, is.getId());
	}
	
	@Test
	public void testRead() {
		indoorSampleDao.create(is);
		sessionFactory.getCurrentSession().flush();

		assertEquals(is, indoorSampleDao.read(is.getId()));
	}
	
	@Test
	public void testUpdate() {
		indoorSampleDao.create(is);
		is.setTemperature(44.44f);
		indoorSampleDao.update(is);
		sessionFactory.getCurrentSession().flush();

		assertEquals(is, indoorSampleDao.read(is.getId()));
	}
	
	@Test
	public void testDelete() {
		indoorSampleDao.create(is);
		sessionFactory.getCurrentSession().flush();

		indoorSampleDao.delete(is);
		sessionFactory.getCurrentSession().flush();
		
		assertNull(indoorSampleDao.read(is.getId()));
	}

}
