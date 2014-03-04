package org.jtdev.jtwx.web.controller;

import org.jtdev.jtwx.web.domain.CombinedSample;
import org.jtdev.jtwx.web.domain.IndoorSample;
import org.jtdev.jtwx.web.domain.OutdoorSample;
import org.jtdev.jtwx.web.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class SampleController {

	private static final String BASE_URI = "/samples";

	private static final String JSON_BASE_URI = "/api/v1" + BASE_URI;
	
	@Autowired
	private SampleService sampleService;
	
	@ResponseBody
	@RequestMapping(value = BASE_URI + "/indoor/{id}", method = RequestMethod.GET)
	public String findIndoorSampleById(@PathVariable Long id) {
		return this.findIndoorSampleByIdJson(id).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = BASE_URI + "/indoor/latest", method = RequestMethod.GET)
	public String findLatestIndoorSample() {
		return this.findLatestIndoorSampleJson().toString();
	}

	@ResponseBody
	@RequestMapping(value = BASE_URI + "/outdoor/{id}", method = RequestMethod.GET)
	public String findOutdoorSampleById(@PathVariable Long id) {
		return this.findOutdoorSampleByIdJson(id).toString();
	}

	@ResponseBody
	@RequestMapping(value = BASE_URI + "/outdoor/latest", method = RequestMethod.GET)
	public String findLatestOutdoorSample() {
		return this.findLatestOutdoorSampleJson().toString();
	}

	
	
	@ResponseBody
	@RequestMapping(value = JSON_BASE_URI + "/indoor/{id}", method = RequestMethod.GET)
	public IndoorSample findIndoorSampleByIdJson(@PathVariable Long id) {
		return sampleService.findIndoorSampleById(id);
	}
	
	@ResponseBody
	@RequestMapping(value = JSON_BASE_URI + "/indoor/latest", method = RequestMethod.GET)
	public IndoorSample findLatestIndoorSampleJson() {
		return sampleService.findLatestIndoorSample();
	}

	@ResponseBody
	@RequestMapping(value = JSON_BASE_URI + "/outdoor/{id}", method = RequestMethod.GET)
	public OutdoorSample findOutdoorSampleByIdJson(@PathVariable Long id) {
		return sampleService.findOutdoorSampleById(id);
	}

	@ResponseBody
	@RequestMapping(value = JSON_BASE_URI + "/outdoor/latest", method = RequestMethod.GET)
	public OutdoorSample findLatestOutdoorSampleJson() {
		return sampleService.findLatestOutdoorSample();
	}

	
	
	@ResponseBody
	@RequestMapping(value = JSON_BASE_URI, method = RequestMethod.POST)
	public void addCombinedSample(@RequestBody CombinedSample combinedSample) {
		sampleService.persistCombinedSample(combinedSample);
//		return combinedSample;
	}
	
}
