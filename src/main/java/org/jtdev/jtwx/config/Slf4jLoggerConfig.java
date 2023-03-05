package org.jtdev.jtwx.config;

import org.jtdev.jtwx.output.Slf4jLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.MethodInvokingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@ConditionalOnProperty(prefix = "jtwx.output", name = "slf4jLogger")
public class Slf4jLoggerConfig {

	@Bean
	public MessageHandler slf4jLoggerMessageHandler(Slf4jLogger slf4jLogger) {
		return new MethodInvokingMessageHandler(slf4jLogger, "update");
	}
	
	@Bean
	public IntegrationFlow slf4jLoggerFlow(MessageChannel observation, MessageHandler slf4jLoggerMessageHandler) {
		return IntegrationFlows
				.from(observation)
				.handle(slf4jLoggerMessageHandler)
				.get();
	}

}
