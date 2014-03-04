package org.jtdev.jtwx.web.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.jtdev.jtwx.web.domain.OutdoorSample;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration("/spring-context.xml")
public class OutdoorSampleDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private OutdoorSampleDao outdoorSampleDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private OutdoorSample os;
	
	@Before
	public void resetOutdoorSample() {
		os = new OutdoorSample();
		os.setDate(new Date());
		os.setBarometer(29.123f);
		os.setDewPoint(24.56f);
		os.setHumidity(45.6f);
		os.setRain60Min(1.2345f);
		os.setRainDay(23.432f);
		os.setRainMonth(56.1f);
		os.setRainYear(75.21f);
		os.setTemperature(102.32f);
		os.setWindAvgSpeed10Min(43.3f);
		os.setWindAvgSpeed2Min(33.21f);
		os.setWindDirection(123.3f);
		os.setWindGust10Min(87.33f);
		os.setWindGust10MinDirection(321.2f);
		os.setWindSpeed(3.44f);
	}
	
	@Test
	public void testCreate() {
		Long id = outdoorSampleDao.create(os);
		sessionFactory.getCurrentSession().flush();
		assertEquals(id, os.getId());
	}
	
	@Test
	public void testRead() {
		outdoorSampleDao.create(os);
		sessionFactory.getCurrentSession().flush();

		assertEquals(os, outdoorSampleDao.read(os.getId()));
	}
	
	@Test
	public void testUpdate() {
		outdoorSampleDao.create(os);
		os.setTemperature(44.44f);
		outdoorSampleDao.update(os);
		sessionFactory.getCurrentSession().flush();

		assertEquals(os, outdoorSampleDao.read(os.getId()));
	}
	
	@Test
	public void testDelete() {
		outdoorSampleDao.create(os);
		sessionFactory.getCurrentSession().flush();

		outdoorSampleDao.delete(os);
		sessionFactory.getCurrentSession().flush();
		
		assertNull(outdoorSampleDao.read(os.getId()));
	}

}
