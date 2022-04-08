package org.jtdev.jtwx;

import java.util.Date;

public class WeatherReadingBuilder {

	private Date date;

	private float outsideTemp;
	
	private float insideTemp;
	
	private int windSpeed;
	
	private int windDirection;
	
	private float avgWindSpeed2Min;
	
	private float avgWindSpeed10Min;
	
	private int windGust10Min;
	
	private int windGust10MinDirection;
	
	private float barometer;
	
	private int outsideHumidity;
	
	private int insideHumidity;
	
	private float dayRainTotal;
	
	private float monthRainTotal;
	
	private float yearRainTotal;

	private float rainLast60Min;
	
	private int dewPoint;

	public WeatherReadingBuilder date(Date date) {
		this.date = date;
		return this;
	}

	public WeatherReadingBuilder outsideTemp(float outsideTemp) {
		this.outsideTemp = outsideTemp;
		return this;
	}

	public WeatherReadingBuilder insideTemp(float insideTemp) {
		this.insideTemp = insideTemp;
		return this;
	}

	public WeatherReadingBuilder windSpeed(int windSpeed) {
		this.windSpeed = windSpeed;
		return this;
	}

	public WeatherReadingBuilder windDirection(int windDirection) {
		this.windDirection = windDirection;
		return this;
	}

	public WeatherReadingBuilder avgWindSpeed2Min(float avgWindSpeed2Min) {
		this.avgWindSpeed2Min = avgWindSpeed2Min;
		return this;
	}
	
	public WeatherReadingBuilder avgWindSpeed10Min(float avgWindSpeed10Min) {
		this.avgWindSpeed10Min = avgWindSpeed10Min;
		return this;
	}

	public WeatherReadingBuilder windGust10Min(int windGust10Min) {
		this.windGust10Min = windGust10Min;
		return this;
	}
	
	public WeatherReadingBuilder windGust10MinDirection(int windGust10MinDirection) {
		this.windGust10MinDirection = windGust10MinDirection;
		return this;
	}
	
	public WeatherReadingBuilder barometer(float barometer) {
		this.barometer = barometer;
		return this;
	}

	public WeatherReadingBuilder outsideHumidity(int outsideHumidity) {
		this.outsideHumidity = outsideHumidity;
		return this;
	}

	public WeatherReadingBuilder insideHumidity(int insideHumidity) {
		this.insideHumidity = insideHumidity;
		return this;
	}

	public WeatherReadingBuilder dayRainTotal(float dayRainTotal) {
		this.dayRainTotal = dayRainTotal;
		return this;
	}

	public WeatherReadingBuilder monthRainTotal(float monthRainTotal) {
		this.monthRainTotal = monthRainTotal;
		return this;
	}

	public WeatherReadingBuilder yearRainTotal(float yearRainTotal) {
		this.yearRainTotal = yearRainTotal;
		return this;
	}

	public WeatherReadingBuilder rainLast60Min(float rainLast60Min) {
		this.rainLast60Min = rainLast60Min;
		return this;
	}
	
	public WeatherReadingBuilder dewPoint(int dewPoint) {
		this.dewPoint = dewPoint;
		return this;
	}
	
	public WeatherReading build() {
		return new WeatherReading(date, outsideTemp, insideTemp, windSpeed,
				windDirection, avgWindSpeed2Min, avgWindSpeed10Min,
				windGust10Min, windGust10MinDirection, barometer,
				outsideHumidity, insideHumidity, dayRainTotal, monthRainTotal,
				yearRainTotal, rainLast60Min, dewPoint);
	}
}
