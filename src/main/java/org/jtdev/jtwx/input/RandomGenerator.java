package org.jtdev.jtwx.input;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jtdev.jtwx.WeatherReading;
import org.jtdev.jtwx.WeatherReadingBuilder;
import org.jtdev.jtwx.WeatherStation;
import org.jtdev.jtwx.WeatherStationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * This class generates a new random response each time it is queried
 * and should only be used for testing.  The data retrieved from this
 * class should *NEVER* be uploaded to any public weather site.
 */
@Component
@ConditionalOnProperty(prefix = "jtwx", name = "input", havingValue = "random")
public class RandomGenerator implements WeatherStation {

	private final Logger logger = LoggerFactory.getLogger(RandomGenerator.class);
	
	@Override
	@PostConstruct
	public void connect() throws WeatherStationException {
		logger.debug("connect()");
	}

	@Override
	@PreDestroy
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
