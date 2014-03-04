package org.jtdev.jtwx.web.dao;

import org.jtdev.jtwx.web.domain.OutdoorSample;
import org.springframework.stereotype.Repository;

@Repository
public class OutdoorSampleDaoImpl extends AbstractHibernateDaoImpl<OutdoorSample, Long> implements
		OutdoorSampleDao {

	public OutdoorSampleDaoImpl() {
		super(OutdoorSample.class);
	}

	@Override
	public OutdoorSample findLatest() {
		return (OutdoorSample) getCurrentSession()
				.createQuery("from OutdoorSample as sample where sample.date = (select max(sample.date) from OutdoorSample as sample)")
				.uniqueResult();
	}

}
