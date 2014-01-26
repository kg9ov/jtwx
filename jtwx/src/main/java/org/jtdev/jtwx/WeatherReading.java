package org.jtdev.jtwx;

import java.util.Date;

public final class WeatherReading {
	
	private final Date date;

	private final float outsideTemp;
	
	private final float insideTemp;
	
	private final int windSpeed;
	
	private final int windDirection;
	
	private final float avgWindSpeed2Min;
	
	private final float avgWindSpeed10Min;
	
	private final int windGust10Min;
	
	private final int windGust10MinDirection;
	
	private final float barometer;
	
	private final int outsideHumidity;
	
	private final int insideHumidity;
	
	private final float dayRainTotal;
	
	private final float monthRainTotal;
	
	private final float yearRainTotal;
	
	private final float rainLast60Min;
	
	private final int dewPoint;

	public WeatherReading(Date date, float outsideTemp, float insideTemp,
			int windSpeed, int windDirection, float avgWindSpeed2Min,
			float avgWindSpeed10Min, int windGust10Min,
			int windGust10MinDirection, float barometer, int outsideHumidity,
			int insideHumidity, float dayRainTotal, float monthRainTotal,
			float yearRainTotal, float rainLast60Min, int dewPoint) {
		super();
		this.date = new Date(date.getTime());
		this.outsideTemp = outsideTemp;
		this.insideTemp = insideTemp;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.avgWindSpeed2Min = avgWindSpeed2Min;
		this.avgWindSpeed10Min = avgWindSpeed10Min;
		this.windGust10Min = windGust10Min;
		this.windGust10MinDirection = windGust10MinDirection;
		this.barometer = barometer;
		this.outsideHumidity = outsideHumidity;
		this.insideHumidity = insideHumidity;
		this.dayRainTotal = dayRainTotal;
		this.monthRainTotal = monthRainTotal;
		this.yearRainTotal = yearRainTotal;
		this.rainLast60Min = rainLast60Min;
		this.dewPoint = dewPoint;
	}

	public float getAvgWindSpeed2Min() {
		return avgWindSpeed2Min;
	}

	public int getWindGust10Min() {
		return windGust10Min;
	}

	public int getWindGust10MinDirection() {
		return windGust10MinDirection;
	}

	public float getRainLast60Min() {
		return rainLast60Min;
	}

	public int getDewPoint() {
		return dewPoint;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public float getOutsideTemp() {
		return outsideTemp;
	}

	public float getInsideTemp() {
		return insideTemp;
	}

	public int getWindSpeed() {
		return windSpeed;
	}

	public int getWindDirection() {
		return windDirection;
	}

	public float getAvgWindSpeed10Min() {
		return avgWindSpeed10Min;
	}

	public float getBarometer() {
		return barometer;
	}

	public int getOutsideHumidity() {
		return outsideHumidity;
	}

	public int getInsideHumidity() {
		return insideHumidity;
	}

	public float getDayRainTotal() {
		return dayRainTotal;
	}

	public float getMonthRainTotal() {
		return monthRainTotal;
	}

	public float getYearRainTotal() {
		return yearRainTotal;
	}

	@Override
	public String toString() {
		return "WeatherReading [date=" + date + ", outsideTemp=" + outsideTemp
				+ ", insideTemp=" + insideTemp + ", windSpeed=" + windSpeed
				+ ", windDirection=" + windDirection + ", avgWindSpeed2Min="
				+ avgWindSpeed2Min + ", avgWindSpeed10Min=" + avgWindSpeed10Min
				+ ", windGust10Min=" + windGust10Min
				+ ", windGust10MinDirection=" + windGust10MinDirection
				+ ", barometer=" + barometer + ", outsideHumidity="
				+ outsideHumidity + ", insideHumidity=" + insideHumidity
				+ ", dayRainTotal=" + dayRainTotal + ", monthRainTotal="
				+ monthRainTotal + ", yearRainTotal=" + yearRainTotal
				+ ", rainLast60Min=" + rainLast60Min + ", dewPoint=" + dewPoint
				+ "]";
	}

}
