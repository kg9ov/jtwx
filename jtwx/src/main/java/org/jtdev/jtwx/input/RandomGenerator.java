package org.jtdev.jtwx.input;

import java.util.Date;

import org.jtdev.jtwx.WeatherReading;
import org.jtdev.jtwx.WeatherReadingBuilder;
import org.jtdev.jtwx.WeatherStation;
import org.jtdev.jtwx.WeatherStationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class generates a new random response each time it is queried
 * and should only be used for testing.  The data retrieved from this
 * class should *NEVER* be uploaded to any public weather site.
 */
public class RandomGenerator implements WeatherStation {

	private final Logger logger = LoggerFactory.getLogger(RandomGenerator.class);
	
	@Override
	public void setParameter(String param, String value)
			throws IllegalArgumentException, WeatherStationException {
		logger.debug("setParameter(): param={} value={}", param, value);
	}

	@Override
	public void connect() throws WeatherStationException {
		logger.debug("connect()");
	}

	@Override
	public void disconnect() throws WeatherStationException {
		logger.debug("disconnect()");
	}

	@Override
	public WeatherReading getWeatherReading() throws WeatherStationException {
		logger.debug("getWeatherReading()");
		
		WeatherReadingBuilder b = new WeatherReadingBuilder();
		
		b.outsideTemp(69f)
			.outsideHumidity(100)
			.windSpeed(55)
			.windDirection(123)
			.date(new Date());
		
		return b.build();
	}

}
