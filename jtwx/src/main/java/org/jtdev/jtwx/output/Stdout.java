package org.jtdev.jtwx.output;

import java.util.Observable;

import org.jtdev.jtwx.WeatherReadingObserver;

public class Stdout implements WeatherReadingObserver {

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(this.getClass().getName() + " update fired");
		System.out.println(arg.toString());
	}

	@Override
	public void setParameter(String param, String value) throws Exception {
		
	}

	@Override
	public void initialize() throws Exception {
		
	}

}
