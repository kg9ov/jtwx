package org.jtdev.jtwx.output;

import java.util.Observable;

import org.jtdev.jtwx.WeatherReadingObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogger implements WeatherReadingObserver {

	private final Logger logger = LoggerFactory.getLogger(Slf4jLogger.class);
	
	@Override
	public void update(Observable o, Object arg) {
		logger.info(arg.toString());
	}

	@Override
	public void setParameter(String param, String value) throws Exception {
		
	}

	@Override
	public void initialize() throws Exception {
		
	}

}
