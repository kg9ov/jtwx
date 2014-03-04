package org.jtdev.jtwx.web.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.jtdev.jtwx.web.domain.CombinedSample;
import org.jtdev.jtwx.web.service.SampleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration("/spring-context.xml")
public class SampleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SampleService sampleService;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void testPersistCombinedSample() {
		CombinedSample cs = new CombinedSample();
		cs.setDate(new Date());
		cs.setIndoorTemperature(68f);
		cs.setOutdoorTemperature(43f);
		
		long beforeCount = this.countRowsInTable("indoor_samples")
							+ this.countRowsInTable("outdoor_samples");
		
		sampleService.persistCombinedSample(cs);
		sessionFactory.getCurrentSession().flush();
		
		long afterCount = this.countRowsInTable("indoor_samples")
							+ this.countRowsInTable("outdoor_samples");
		
		assertTrue(afterCount == beforeCount + 2);
	}

}
