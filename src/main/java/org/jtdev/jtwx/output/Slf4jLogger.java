package org.jtdev.jtwx.output;

import javax.annotation.PostConstruct;

import org.jtdev.jtwx.WeatherReading;
import org.jtdev.jtwx.WeatherReadingObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "jtwx.output", name = "slf4jLogger")
public class Slf4jLogger implements WeatherReadingObserver {

	private final Logger logger = LoggerFactory.getLogger(Slf4jLogger.class);
	
	@Override
	public void update(WeatherReading wr) {
		logger.info(wr.toString());
	}

	@Override
	@PostConstruct
	public void initialize() throws Exception {
		
	}

}
