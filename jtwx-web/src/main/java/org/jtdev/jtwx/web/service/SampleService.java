package org.jtdev.jtwx.web.service;

import org.jtdev.jtwx.web.domain.CombinedSample;
import org.jtdev.jtwx.web.domain.IndoorSample;
import org.jtdev.jtwx.web.domain.OutdoorSample;

public interface SampleService {

	public void persistCombinedSample(CombinedSample combinedSample);
	
	public IndoorSample findIndoorSampleById(Long id);
	
	public OutdoorSample findOutdoorSampleById(Long id);
	
	public IndoorSample findLatestIndoorSample();

	public OutdoorSample findLatestOutdoorSample();
	
}
