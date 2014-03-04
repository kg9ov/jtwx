package org.jtdev.jtwx.web.domain;

import java.util.Date;

public class OutdoorSample {

	private Long id;
	
	private Date date;
	
	private float temperature;
	
	private float humidity;
	
	private float windSpeed;
	
	private float windDirection;
	
	private float rainDay;
	
	private float rainMonth;
	
	private float rainYear;
	
	private float rain60Min;
	
	private float windGust10Min;
	
	private float windGust10MinDirection;
	
	private float windAvgSpeed2Min;
	
	private float windAvgSpeed10Min;
	
	private float barometer;
	
	private float dewPoint;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public float getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(float windDirection) {
		this.windDirection = windDirection;
	}

	public float getRainDay() {
		return rainDay;
	}

	public void setRainDay(float rainDay) {
		this.rainDay = rainDay;
	}

	public float getRainMonth() {
		return rainMonth;
	}

	public void setRainMonth(float rainMonth) {
		this.rainMonth = rainMonth;
	}

	public float getRainYear() {
		return rainYear;
	}

	public void setRainYear(float rainYear) {
		this.rainYear = rainYear;
	}

	public float getRain60Min() {
		return rain60Min;
	}

	public void setRain60Min(float rain60Min) {
		this.rain60Min = rain60Min;
	}

	public float getWindGust10Min() {
		return windGust10Min;
	}

	public void setWindGust10Min(float windGust10Min) {
		this.windGust10Min = windGust10Min;
	}

	public float getWindGust10MinDirection() {
		return windGust10MinDirection;
	}

	public void setWindGust10MinDirection(float windGust10MinDirection) {
		this.windGust10MinDirection = windGust10MinDirection;
	}

	public float getWindAvgSpeed2Min() {
		return windAvgSpeed2Min;
	}

	public void setWindAvgSpeed2Min(float windAvgSpeed2Min) {
		this.windAvgSpeed2Min = windAvgSpeed2Min;
	}

	public float getWindAvgSpeed10Min() {
		return windAvgSpeed10Min;
	}

	public void setWindAvgSpeed10Min(float windAvgSpeed10Min) {
		this.windAvgSpeed10Min = windAvgSpeed10Min;
	}

	public float getBarometer() {
		return barometer;
	}

	public void setBarometer(float barometer) {
		this.barometer = barometer;
	}

	public float getDewPoint() {
		return dewPoint;
	}

	public void setDewPoint(float dewPoint) {
		this.dewPoint = dewPoint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(barometer);
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + Float.floatToIntBits(dewPoint);
		result = prime * result + Float.floatToIntBits(humidity);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(rain60Min);
		result = prime * result + Float.floatToIntBits(rainDay);
		result = prime * result + Float.floatToIntBits(rainMonth);
		result = prime * result + Float.floatToIntBits(rainYear);
		result = prime * result + Float.floatToIntBits(temperature);
		result = prime * result + Float.floatToIntBits(windAvgSpeed10Min);
		result = prime * result + Float.floatToIntBits(windAvgSpeed2Min);
		result = prime * result + Float.floatToIntBits(windDirection);
		result = prime * result + Float.floatToIntBits(windGust10Min);
		result = prime * result + Float.floatToIntBits(windGust10MinDirection);
		result = prime * result + Float.floatToIntBits(windSpeed);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutdoorSample other = (OutdoorSample) obj;
		if (Float.floatToIntBits(barometer) != Float
				.floatToIntBits(other.barometer))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Float.floatToIntBits(dewPoint) != Float
				.floatToIntBits(other.dewPoint))
			return false;
		if (Float.floatToIntBits(humidity) != Float
				.floatToIntBits(other.humidity))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Float.floatToIntBits(rain60Min) != Float
				.floatToIntBits(other.rain60Min))
			return false;
		if (Float.floatToIntBits(rainDay) != Float
				.floatToIntBits(other.rainDay))
			return false;
		if (Float.floatToIntBits(rainMonth) != Float
				.floatToIntBits(other.rainMonth))
			return false;
		if (Float.floatToIntBits(rainYear) != Float
				.floatToIntBits(other.rainYear))
			return false;
		if (Float.floatToIntBits(temperature) != Float
				.floatToIntBits(other.temperature))
			return false;
		if (Float.floatToIntBits(windAvgSpeed10Min) != Float
				.floatToIntBits(other.windAvgSpeed10Min))
			return false;
		if (Float.floatToIntBits(windAvgSpeed2Min) != Float
				.floatToIntBits(other.windAvgSpeed2Min))
			return false;
		if (Float.floatToIntBits(windDirection) != Float
				.floatToIntBits(other.windDirection))
			return false;
		if (Float.floatToIntBits(windGust10Min) != Float
				.floatToIntBits(other.windGust10Min))
			return false;
		if (Float.floatToIntBits(windGust10MinDirection) != Float
				.floatToIntBits(other.windGust10MinDirection))
			return false;
		if (Float.floatToIntBits(windSpeed) != Float
				.floatToIntBits(other.windSpeed))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OutdoorSample [id=" + id + ", date=" + date + ", temperature="
				+ temperature + ", humidity=" + humidity + ", windSpeed="
				+ windSpeed + ", windDirection=" + windDirection + ", rainDay="
				+ rainDay + ", rainMonth=" + rainMonth + ", rainYear="
				+ rainYear + ", rain60Min=" + rain60Min + ", windGust10Min="
				+ windGust10Min + ", windGust10MinDirection="
				+ windGust10MinDirection + ", windAvgSpeed2Min="
				+ windAvgSpeed2Min + ", windAvgSpeed10Min=" + windAvgSpeed10Min
				+ ", barometer=" + barometer + ", dewPoint=" + dewPoint + "]";
	}
	
}
