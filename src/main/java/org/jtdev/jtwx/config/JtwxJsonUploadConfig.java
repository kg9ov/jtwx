package org.jtdev.jtwx.config;

import org.jtdev.jtwx.output.JtwxJsonUpload;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.MethodInvokingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@ConditionalOnProperty(prefix = "jtwx.output.jtwxJsonUpload", name = "jtwxServiceUrl")
public class JtwxJsonUploadConfig {

	@Bean
	public MessageHandler jtwxJsonUploadMessageHandler(JtwxJsonUpload jtwxJsonUpload) {
		return new MethodInvokingMessageHandler(jtwxJsonUpload, "update");
	}
	
	@Bean
	public IntegrationFlow jtwxJsonUploadFlow(MessageChannel observation, MessageHandler jtwxJsonUploadMessageHandler) {
		return IntegrationFlows
				.from(observation)
				.handle(jtwxJsonUploadMessageHandler)
				.get();
	}

}
