package org.jtdev.jtwx.web.domain;

import java.util.Date;

public class CombinedSample {

	private IndoorSample indoorSample;
	
	private OutdoorSample outdoorSample;
	
	public CombinedSample() {
		indoorSample = new IndoorSample();
		outdoorSample = new OutdoorSample();
	}
	
	public CombinedSample(IndoorSample is, OutdoorSample os) {
		if (is == null || os == null) {
			throw new IllegalArgumentException("Arguments must not be null");
		}
		if (!is.getDate().equals(os.getDate())) {
			throw new IllegalArgumentException("Sample dates must be equal");
		}
		
		indoorSample = is;
		outdoorSample = os;
	}

	public IndoorSample getIndoorSample() {
		return indoorSample;
	}

	public OutdoorSample getOutdoorSample() {
		return outdoorSample;
	}

	public Date getDate() {
		return indoorSample.getDate();
	}

	public void setDate(Date date) {
		indoorSample.setDate(date);
		outdoorSample.setDate(date);
	}

	public float getIndoorTemperature() {
		return indoorSample.getTemperature();
	}

	public void setIndoorTemperature(float temperature) {
		indoorSample.setTemperature(temperature);
	}

	public float getIndoorHumidity() {
		return indoorSample.getHumidity();
	}

	public void setIndoorHumidity(float humidity) {
		indoorSample.setHumidity(humidity);
	}

	public float getOutdoorTemperature() {
		return outdoorSample.getTemperature();
	}

	public void setOutdoorTemperature(float temperature) {
		outdoorSample.setTemperature(temperature);
	}

	public float getOutdoorHumidity() {
		return outdoorSample.getHumidity();
	}

	public void setOutdoorHumidity(float humidity) {
		outdoorSample.setHumidity(humidity);
	}

	public float getWindSpeed() {
		return outdoorSample.getWindSpeed();
	}

	public void setWindSpeed(float windSpeed) {
		outdoorSample.setWindSpeed(windSpeed);
	}

	public float getWindDirection() {
		return outdoorSample.getWindDirection();
	}

	public void setWindDirection(float windDirection) {
		outdoorSample.setWindDirection(windDirection);
	}

	public float getRainDay() {
		return outdoorSample.getRainDay();
	}

	public void setRainDay(float rainDay) {
		outdoorSample.setRainDay(rainDay);
	}

	public float getRainMonth() {
		return outdoorSample.getRainMonth();
	}

	public void setRainMonth(float rainMonth) {
		outdoorSample.setRainMonth(rainMonth);
	}

	public float getRainYear() {
		return outdoorSample.getRainYear();
	}

	public void setRainYear(float rainYear) {
		outdoorSample.setRainYear(rainYear);
	}

	public float getRain60Min() {
		return outdoorSample.getRain60Min();
	}

	public void setRain60Min(float rain60Min) {
		outdoorSample.setRain60Min(rain60Min);
	}

	public float getWindGust10Min() {
		return outdoorSample.getWindGust10Min();
	}

	public void setWindGust10Min(float windGust10Min) {
		outdoorSample.setWindGust10Min(windGust10Min);
	}

	public float getWindGust10MinDirection() {
		return outdoorSample.getWindGust10MinDirection();
	}

	public void setWindGust10MinDirection(float windGust10MinDirection) {
		outdoorSample.setWindGust10MinDirection(windGust10MinDirection);
	}

	public float getWindAvgSpeed2Min() {
		return outdoorSample.getWindAvgSpeed2Min();
	}

	public void setWindAvgSpeed2Min(float windAvgSpeed2Min) {
		outdoorSample.setWindAvgSpeed2Min(windAvgSpeed2Min);
	}

	public float getWindAvgSpeed10Min() {
		return outdoorSample.getWindAvgSpeed10Min();
	}

	public void setWindAvgSpeed10Min(float windAvgSpeed10Min) {
		outdoorSample.setWindAvgSpeed10Min(windAvgSpeed10Min);
	}

	public float getBarometer() {
		return outdoorSample.getBarometer();
	}

	public void setBarometer(float barometer) {
		outdoorSample.setBarometer(barometer);
	}

	public float getDewPoint() {
		return outdoorSample.getDewPoint();
	}

	public void setDewPoint(float dewPoint) {
		outdoorSample.setDewPoint(dewPoint);
	}
	
	
}
