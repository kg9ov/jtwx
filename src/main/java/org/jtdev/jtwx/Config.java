package org.jtdev.jtwx;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("jtwx")
@ConstructorBinding
class Config {

	private final ControllerConfig controller;
	
	private final Input input;
	
	private final List<Output> outputs;

	public Config(ControllerConfig controller, Input input, List<Output> outputs) {
		this.controller = controller;
		this.input = input;
		this.outputs = outputs;
	}

	public ControllerConfig getController() {
		return controller;
	}

	public Input getInput() {
		return input;
	}

	public List<Output> getOutputs() {
		return outputs == null ? null : Collections.unmodifiableList(outputs);
	}

	static class ControllerConfig {
		
		private long pollingInterval;
		
		public ControllerConfig(long pollingInterval) {
			this.pollingInterval = pollingInterval;
		}

		public long getPollingInterval() {
			return pollingInterval;
		}
		
	}
	
	static class Input {

		private final String className;
		
		private final List<Parameter> parameters;
		
		public Input(String className, List<Parameter> parameters) {
			this.className = className;
			this.parameters = parameters;
		}

		public String getClassName() {
			return className;
		}

		public List<Parameter> getParameters() {
			return parameters == null ? null : Collections.unmodifiableList(parameters);
		}
		
	}

	static class Output {

		private final String className;
		
		private final List<Parameter> parameters;
		
		public Output(String className, List<Parameter> parameters) {
			this.className = className;
			this.parameters = parameters;
		}

		public String getClassName() {
			return className;
		}

		public List<Parameter> getParameters() {
			return parameters == null ? null : Collections.unmodifiableList(parameters);
		}
		
	}
	
	static class Parameter {

		private final String param;
		
		private final String value;
		
		public Parameter(String param, String value) {
			this.param = param;
			this.value = value;
		}
		
		public String getParam() {
			return param;
		}
		
		public String getValue() {
			return value;
		}
		
	}
	
}
