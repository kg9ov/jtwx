package org.jtdev.jtwx.config;

import org.jtdev.jtwx.output.Stdout;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.MethodInvokingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@ConditionalOnProperty(prefix = "jtwx.output", name = "stdout")
public class StdoutConfig {

	@Bean
	public MessageHandler stdoutMessageHandler(Stdout stdout) {
		return new MethodInvokingMessageHandler(stdout, "update");
	}
	
	@Bean
	public IntegrationFlow stdoutFlow(MessageChannel observation, MessageHandler stdoutMessageHandler) {
		return IntegrationFlows
				.from(observation)
				.handle(stdoutMessageHandler)
				.get();
	}

}
