package org.jtdev.jtwx;

/**
 * The <code>WeatherStation</code> interface must be implemented by any
 * class used as an input source for <code>WeatherReading</code>s.
 */
public interface WeatherStation {

	public void setParameter(String param, String value) throws IllegalArgumentException, WeatherStationException;
		
	public void connect() throws WeatherStationException;
	
	public void disconnect() throws WeatherStationException;
	
	public WeatherReading getWeatherReading() throws WeatherStationException;
	
}
