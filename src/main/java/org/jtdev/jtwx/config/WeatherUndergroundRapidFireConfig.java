package org.jtdev.jtwx.config;

import org.jtdev.jtwx.output.WeatherUndergroundRapidFire;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.MethodInvokingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@ConditionalOnProperty(prefix = "jtwx.output.weatherUndergroundRapidFire", name = "stationId")
public class WeatherUndergroundRapidFireConfig {

	@Bean
	public MessageHandler weatherUndergroundRapidFireMessageHandler(WeatherUndergroundRapidFire weatherUndergroundRapidFire) {
		return new MethodInvokingMessageHandler(weatherUndergroundRapidFire, "update");
	}
	
	@Bean
	public IntegrationFlow weatherUndergroundRapidFireFlow(MessageChannel observation, MessageHandler weatherUndergroundRapidFireMessageHandler) {
		return IntegrationFlows
				.from(observation)
				.handle(weatherUndergroundRapidFireMessageHandler)
				.get();
	}

}
