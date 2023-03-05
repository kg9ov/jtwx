package org.jtdev.jtwx;

public interface WeatherReadingObserver {

	public void initialize() throws Exception;

	public void update(WeatherReading wr);

}
