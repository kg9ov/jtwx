package org.jtdev.jtwx.config;

import org.jtdev.jtwx.WeatherStation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.messaging.MessageChannel;

@Configuration
public class WeatherStationConfig {

	@Value("${jtwx.input.pollingInterval}")
	private int pollingInterval;
	
	@Bean
	public MessageSource<?> weatherReadingMessageSource(WeatherStation weatherStation) {
		
		MethodInvokingMessageSource source = new MethodInvokingMessageSource();
		source.setObject(weatherStation);
		source.setMethodName("getWeatherReading");
		
		return source;
		
	}
	
	@Bean
	public IntegrationFlow weatherStationFlow(MessageSource<?> weatherReadingMessageSource, MessageChannel observation) {
		return IntegrationFlows
					.from(weatherReadingMessageSource, s -> s.poller(Pollers.fixedDelay(pollingInterval)))
					.channel(observation)
					.get();
	}

}
