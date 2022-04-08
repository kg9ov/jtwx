package org.jtdev.jtwx;

/**
 * Thrown by implementors of the <code>WeatherStation</code> interface.
 */
public class WeatherStationException extends Exception {

	private static final long serialVersionUID = -2238035283181148790L;

	public WeatherStationException() {
		super();
	}

	public WeatherStationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WeatherStationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeatherStationException(String message) {
		super(message);
	}

	public WeatherStationException(Throwable cause) {
		super(cause);
	}

}
