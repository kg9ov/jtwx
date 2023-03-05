package org.jtdev.jtwx.output;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.jtdev.jtwx.WeatherReading;
import org.jtdev.jtwx.WeatherReadingObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "jtwx.output.weatherUndergroundRapidFire", name = "stationId")
public class WeatherUndergroundRapidFire implements WeatherReadingObserver {

	private final Logger logger = LoggerFactory
			.getLogger(WeatherUndergroundRapidFire.class);
	
	@Value("${jtwx.output.weatherUndergroundRapidFire.stationId}")
	private String stationId;
	@Value("${jtwx.output.weatherUndergroundRapidFire.password}")
	private String password;
	
	private HttpClient client;
	private SimpleDateFormat simpleDateFormat;
	
	private static final String MYSQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public void update(WeatherReading wr) {
		
        try {
        	Request req = client.newRequest("http://rtupdate.wunderground.com/weatherstation/updateweatherstation.php");
        	req.timeout(3000, TimeUnit.MILLISECONDS);
        	req.agent("jtwx/0.1");
        	
        	req.param("action", "updateraw")
        		.param("ID", stationId)
        		.param("PASSWORD", password)
        		.param("dateutc", simpleDateFormat.format(wr.getDate()))
        		.param("tempf", String.valueOf(wr.getOutsideTemp()))
        		.param("humidity", String.valueOf(wr.getOutsideHumidity()))
        		.param("baromin", String.valueOf(wr.getBarometer()))
        		.param("windspeedmph", String.valueOf(wr.getWindSpeed()))
        		.param("winddir", String.valueOf(wr.getWindDirection()))
        		.param("windspdmph_avg2m", String.valueOf(wr.getAvgWindSpeed2Min()))
        		.param("windgustmph_10m", String.valueOf(wr.getWindGust10Min()))
        		.param("windgustdir_10m", String.valueOf(wr.getWindGust10MinDirection()))
        		.param("windgustmph", String.valueOf(wr.getWindGust10Min()))
        		.param("windgustdir", String.valueOf(wr.getWindGust10MinDirection()))
        		.param("dewptf", String.valueOf(wr.getDewPoint()))
        		.param("rainin", String.valueOf(wr.getRainLast60Min()))
        		.param("dailyrainin", String.valueOf(wr.getDayRainTotal()))
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
	@PostConstruct
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
