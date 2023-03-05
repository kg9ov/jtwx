package org.jtdev.jtwx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(30);
		executor.setQueueCapacity(100);
		return executor;
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new StdDateFormat());
		return objectMapper;
	}

	@Bean
	public MessageChannel observation(TaskExecutor taskExecutor) {
		return new PublishSubscribeChannel(taskExecutor);
	}

}
