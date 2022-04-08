package org.jtdev.jtwx;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="jtwxConfig")
@XmlAccessorType(XmlAccessType.NONE)
class Config {

	@XmlElement(name="controller", required=true)
	ControllerConfig controllerConfig;
	
	@XmlElement(name="input", required=true)
	Input input;
	
	@XmlElement(name="output", required=true)
	List<Output> outputs;

	@XmlAccessorType(XmlAccessType.NONE)
	static class ControllerConfig {
		@XmlElement(required=true)
		long pollingInterval;
	}
	
	@XmlAccessorType(XmlAccessType.NONE)
	static class Input {
		@XmlElement(name="class", required=true)
		String className;
		
		@XmlElement(name="parameter")
		List<Parameter> parameters;
	}

	@XmlAccessorType(XmlAccessType.NONE)
	static class Output {
		@XmlElement(name="class", required=true)
		String className;
		
		@XmlElement(name="parameter")
		List<Parameter> parameters;
	}
	
	@XmlAccessorType(XmlAccessType.NONE)
	static class Parameter {
		@XmlElement(required=true)
		String param;
		
		@XmlElement(required=true)
		String value;
	}
	
	static Config load() {
		Config conf = null;
		
		InputStream is = Config.class.getClassLoader().getResourceAsStream("jtwx.xml");
		if (is == null) {
			throw new RuntimeException("Unable to load jtwx.xml from classpath");
		}
		
		try {
			JAXBContext jc = JAXBContext.newInstance(Config.class);
			Unmarshaller u = jc.createUnmarshaller();
			conf = (Config) u.unmarshal(is);
		} catch (JAXBException e) {
			throw new RuntimeException("Failed to read jtwx.xml", e);
		}
		
		return conf;
	}
}
