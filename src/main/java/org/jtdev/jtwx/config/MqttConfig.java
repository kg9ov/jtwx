package org.jtdev.jtwx.config;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ConditionalOnProperty(prefix = "jtwx.output.mqtt", name = "hostname")
public class MqttConfig {

	@Value("${jtwx.output.mqtt.hostname}")
	private String hostname;
	
	@Value("${jtwx.output.mqtt.port:1883}")
	private int port;
	
	@Value("${jtwx.output.mqtt.topic:jtwx/observation}")
	private String topic;
	
	@Value("${jtwx.output.mqtt.qos:0}")
	private int qos;
	
	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] {"tcp://" + hostname + ":" + port});
		factory.setConnectionOptions(options);
		return factory;
		
	}
	
	@Bean
	public MessageHandler mqttMessageHandler(MqttPahoClientFactory mqttClientFactory) {
		String clientId = "jtwx-" + UUID.randomUUID().toString();
		MqttPahoMessageHandler handler = new MqttPahoMessageHandler(clientId, mqttClientFactory);
		handler.setAsync(true);
		return handler;
	}
	
	@Bean
	public IntegrationFlow mqttFlow(MessageChannel observation, MessageHandler mqttMessageHandler, ObjectMapper objectMapper) {
		return IntegrationFlows
					.from(observation)
					.transform(new ObjectToJsonTransformer(new Jackson2JsonObjectMapper(objectMapper)))
					.enrichHeaders(h -> h.header(MqttHeaders.TOPIC, topic))
					.enrichHeaders(h -> h.header(MqttHeaders.QOS, qos))
					.log()
					.handle(mqttMessageHandler)
					.get();
	}

}
