package org.jtdev.jtwx.web.service;

import javax.transaction.Transactional;

import org.jtdev.jtwx.web.dao.IndoorSampleDao;
import org.jtdev.jtwx.web.dao.OutdoorSampleDao;
import org.jtdev.jtwx.web.domain.CombinedSample;
import org.jtdev.jtwx.web.domain.IndoorSample;
import org.jtdev.jtwx.web.domain.OutdoorSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SampleServiceImpl implements SampleService {

	@Autowired
	private IndoorSampleDao indoorSampleDao;
	
	@Autowired
	private OutdoorSampleDao outdoorSampleDao;
	
	@Override
	public void persistCombinedSample(CombinedSample combinedSample) {
		indoorSampleDao.create(combinedSample.getIndoorSample());
		outdoorSampleDao.create(combinedSample.getOutdoorSample());
	}

	@Override
	public IndoorSample findIndoorSampleById(Long id) {
		return indoorSampleDao.read(id);
	}

	@Override
	public OutdoorSample findOutdoorSampleById(Long id) {
		return outdoorSampleDao.read(id);
	}

	@Override
	public IndoorSample findLatestIndoorSample() {
		return indoorSampleDao.findLatest();
	}

	@Override
	public OutdoorSample findLatestOutdoorSample() {
		return outdoorSampleDao.findLatest();
	}

}
