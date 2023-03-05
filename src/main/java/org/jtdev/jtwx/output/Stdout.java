package org.jtdev.jtwx.output;

import javax.annotation.PostConstruct;

import org.jtdev.jtwx.WeatherReading;
import org.jtdev.jtwx.WeatherReadingObserver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "jtwx.output", name = "stdout")
public class Stdout implements WeatherReadingObserver {

	@Override
	public void update(WeatherReading wr) {
		System.out.println(this.getClass().getName() + " update fired");
		System.out.println(wr.toString());
	}

	@Override
	@PostConstruct
	public void initialize() throws Exception {
		
	}

}
