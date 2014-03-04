package org.jtdev.jtwx.web.dao;

import org.jtdev.jtwx.web.domain.IndoorSample;
import org.springframework.stereotype.Repository;

@Repository
public class IndoorSampleDaoImpl extends AbstractHibernateDaoImpl<IndoorSample, Long> implements
		IndoorSampleDao {

	public IndoorSampleDaoImpl() {
		super(IndoorSample.class);
	}

	@Override
	public IndoorSample findLatest() {
		return (IndoorSample) getCurrentSession()
				.createQuery("from IndoorSample as sample where sample.date = (select max(sample.date) from IndoorSample as sample)")
				.uniqueResult();
	}

}
