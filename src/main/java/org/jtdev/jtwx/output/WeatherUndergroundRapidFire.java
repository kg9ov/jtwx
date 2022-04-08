package org.jtdev.jtwx.output;

import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.jtdev.jtwx.WeatherReading;
import org.jtdev.jtwx.WeatherReadingObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherUndergroundRapidFire implements WeatherReadingObserver {

	private final Logger logger = LoggerFactory
			.getLogger(WeatherUndergroundRapidFire.class);
	
	private String stationId;
	private String password;
	
	private HttpClient client;
	private SimpleDateFormat simpleDateFormat;
	
	private static final String MYSQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public void update(Observable o, Object arg) {
		
		WeatherReading wx = null;
		
		if (arg instanceof WeatherReading) {
			wx = (WeatherReading) arg;
		} else {
			return;
		}
		
        try {
        	Request req = client.newRequest("http://rtupdate.wunderground.com/weatherstation/updateweatherstation.php");
        	req.timeout(3000, TimeUnit.MILLISECONDS);
        	req.agent("jtwx/0.1");
        	
        	req.param("action", "updateraw")
        		.param("ID", stationId)
        		.param("PASSWORD", password)
        		.param("dateutc", simpleDateFormat.format(wx.getDate()))
        		.param("tempf", String.valueOf(wx.getOutsideTemp()))
        		.param("humidity", String.valueOf(wx.getOutsideHumidity()))
        		.param("baromin", String.valueOf(wx.getBarometer()))
        		.param("windspeedmph", String.valueOf(wx.getWindSpeed()))
        		.param("winddir", String.valueOf(wx.getWindDirection()))
        		.param("windspdmph_avg2m", String.valueOf(wx.getAvgWindSpeed2Min()))
        		.param("windgustmph_10m", String.valueOf(wx.getWindGust10Min()))
        		.param("windgustdir_10m", String.valueOf(wx.getWindGust10MinDirection()))
        		.param("windgustmph", String.valueOf(wx.getWindGust10Min()))
        		.param("windgustdir", String.valueOf(wx.getWindGust10MinDirection()))
        		.param("dewptf", String.valueOf(wx.getDewPoint()))
        		.param("rainin", String.valueOf(wx.getRainLast60Min()))
        		.param("dailyrainin", String.valueOf(wx.getDayRainTotal()))
        		.param("softwaretype", "jtwx")
        		.param("realtime", "1")
        		.param("rtfreq", "3");
        	
			ContentResponse r = req.send();
			logger.debug("wunderground resp: " + r.getContentAsString());
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.info("eek!", e);
		}
  
	}

	@Override
	public void setParameter(String param, String value) throws Exception {
		if (param == null || value == null) {
			throw new Exception("nulls not allowed");
		}
		if (param.equals("wurf.id")) {
			this.stationId = value;
		} else if (param.equals("wurf.password")) {
			this.password = value;
		} else {
			throw new Exception("Unknown param: " + param);
		}
	}

	@Override
	public void initialize() throws Exception {

		if (stationId == null || password == null) {
			throw new Exception("wurf.id and wurf.password must be set");
		}
		
		client = new HttpClient();
		client.start();
		
		simpleDateFormat = new SimpleDateFormat(MYSQL_DATE_FORMAT);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
	}

}
