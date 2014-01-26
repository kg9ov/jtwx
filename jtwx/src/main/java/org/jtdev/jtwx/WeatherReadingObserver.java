package org.jtdev.jtwx;

import java.util.Observer;

public interface WeatherReadingObserver extends Observer {

	public void setParameter(String param, String value) throws Exception;
	
	public void initialize() throws Exception;
	
}
