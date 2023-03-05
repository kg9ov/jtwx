package org.jtdev.jtwx.output;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.jtdev.jtwx.WeatherReading;
import org.jtdev.jtwx.WeatherReadingObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

@Component
@ConditionalOnProperty(prefix = "jtwx.output.jtwxJsonUpload", name = "jtwxServiceUrl")
public class JtwxJsonUpload implements WeatherReadingObserver {

	private final Logger logger = LoggerFactory.getLogger(JtwxJsonUpload.class);
	
	@Value("${jtwx.output.jtwxJsonUpload.jtwxServiceUrl}")
	private String jtwxServiceUrl;
	
	private HttpClient client;
	private ObjectMapper objectMapper;

	@Override
	public void update(WeatherReading wr) {
		String json = null;
		CombinedSample sample = new CombinedSample(wr);
		
		try {
			json = objectMapper.writeValueAsString(sample);
			logger.debug("JSON generated: {}", json);
		} catch (IOException e) {
			logger.error("Marshal to JSON failed", e);
			return;
		}
		
		Request req = client.newRequest(jtwxServiceUrl);
    	req.timeout(3000, TimeUnit.MILLISECONDS);
    	req.agent("jtwx/0.1");
    	
    	req.method("POST");
    	req.content(new StringContentProvider(json), "application/json");
    	
    	try {
			ContentResponse r = req.send();
			if (r.getStatus() == 200) {
				logger.info("Upload success!");
			} else {
				logger.error("Upload failed! status={} msg={}", r.getStatus(), r.getReason());
				logger.debug(r.getContentAsString());
			}
		} catch (InterruptedException | TimeoutException | ExecutionException e) {
			logger.error("Upload failed", e);
		}
	}

	@Override
	@PostConstruct
	public void initialize() throws Exception {
		if (jtwxServiceUrl == null) {
			throw new Exception("jtwx.json.service.url must be set");
		}
		
		client = new HttpClient();
		client.start();
		
		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new StdDateFormat());
	}

	private static class CombinedSample {
		private WeatherReading wr;
		
		public CombinedSample(WeatherReading wr) {
			this.wr = wr;
		}

		@SuppressWarnings("unused")
		public float getWindAvgSpeed2Min() {
			return wr.getAvgWindSpeed2Min();
		}

		@SuppressWarnings("unused")
		public int getWindGust10Min() {
			return wr.getWindGust10Min();
		}

		@SuppressWarnings("unused")
		public int getWindGust10MinDirection() {
			return wr.getWindGust10MinDirection();
		}

		@SuppressWarnings("unused")
		public float getRain60Min() {
			return wr.getRainLast60Min();
		}

		@SuppressWarnings("unused")
		public int getDewPoint() {
			return wr.getDewPoint();
		}

		@SuppressWarnings("unused")
		public Date getDate() {
			return wr.getDate();
		}

		@SuppressWarnings("unused")
		public float getOutdoorTemperature() {
			return wr.getOutsideTemp();
		}

		@SuppressWarnings("unused")
		public float getIndoorTemperature() {
			return wr.getInsideTemp();
		}

		@SuppressWarnings("unused")
		public int getWindSpeed() {
			return wr.getWindSpeed();
		}

		@SuppressWarnings("unused")
		public int getWindDirection() {
			return wr.getWindDirection();
		}

		@SuppressWarnings("unused")
		public float getWindAvgSpeed10Min() {
			return wr.getAvgWindSpeed10Min();
		}

		@SuppressWarnings("unused")
		public float getBarometer() {
			return wr.getBarometer();
		}

		@SuppressWarnings("unused")
		public int getOutdoorHumidity() {
			return wr.getOutsideHumidity();
		}

		@SuppressWarnings("unused")
		public int getIndoorHumidity() {
			return wr.getInsideHumidity();
		}

		@SuppressWarnings("unused")
		public float getRainDay() {
			return wr.getDayRainTotal();
		}

		@SuppressWarnings("unused")
		public float getRainMonth() {
			return wr.getMonthRainTotal();
		}

		@SuppressWarnings("unused")
		public float getRainYear() {
			return wr.getYearRainTotal();
		}
		
	}
}
