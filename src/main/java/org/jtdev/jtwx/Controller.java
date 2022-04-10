package org.jtdev.jtwx;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Controller extends Observable implements Runnable {
	
	private final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	private Config config;
	private volatile boolean shutdown = false;
	
	public Controller(Config config) {
		this.config = config;
	}

	@PostConstruct
	public void postConstruct() {
		logger.info("Starting Controller thread");
		new Thread(this, this.getClass().getSimpleName()).start();
	}
	
	@PreDestroy
	public void preDestroy() {
		logger.info("Stopping Controller thread");
		shutdown = true;
	}
	
	@Override
	public void run() {
		logger.info("init weather station");
		WeatherStation ws = initInput(config);
		if (ws == null) {
			logger.error("init weather station failed");
			return;
		}

		logger.info("connecting to weather station");
		try {
			ws.connect();
		} catch (IllegalArgumentException | WeatherStationException e) {
			logger.error("weather station connect failed", e);
			return;
		}
		
		logger.info("init outputs");
		List<WeatherReadingObserver> wroList = initOutputs(config);
		if (wroList == null) {
			logger.error("init outputs failed");
			return;
		}
		
		logger.info("adding observers");
		for (WeatherReadingObserver wro : wroList) {
			this.addObserver(wro);
		}
		
		logger.info("startup complete: hold on tight, here we go...");
		while (!shutdown) {
			long start = System.currentTimeMillis();
			try {
				logger.debug("getting weather reading");
				WeatherReading wr = ws.getWeatherReading();
				logger.debug("notify observers");
				setChanged();
				notifyObservers(wr);
			} catch (Exception e) {
				logger.info("failed to get weather reading", e);
			}
			logger.debug("Elapsed time (ms): {}", (System.currentTimeMillis() - start));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		logger.info("disconnect weather station");
		try {
			ws.disconnect();
		} catch (WeatherStationException e) {
			logger.error("disconnect weather station failed", e);
		}
		logger.info("controller thread exiting");
	}

	private WeatherStation initInput(Config config) {
		String className = config.getInput().getClassName();
		WeatherStation ws = null;
		
		try {
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor();
			ws = (WeatherStation) constructor.newInstance();
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | ClassCastException e) {

			logger.error("Failed to load input class: {}", className, e);
			return null;
		}
		
		for (Config.Parameter p : config.getInput().getParameters()) {
			try {
				ws.setParameter(p.getParam(), p.getValue());
			} catch (IllegalArgumentException | WeatherStationException e) {
				logger.error("Input set parameter failed for: {}", p.getParam(), e);
				return null;
			}
		}
		
		return ws;
	}
	
	private List<WeatherReadingObserver> initOutputs(Config config) {
		List<WeatherReadingObserver> wroList = new ArrayList<WeatherReadingObserver>();
		
		for (Config.Output output : config.getOutputs()) {
			WeatherReadingObserver wro;
			try {
				Class<?> clazz = Class.forName(output.getClassName());
				Constructor<?> constructor = clazz.getConstructor();
				wro = (WeatherReadingObserver) constructor.newInstance();
			} catch (ClassNotFoundException | NoSuchMethodException
					| SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				logger.error("Failed to load output class: {}", output.getClassName(), e);
				return null;
			}

			if (output.getParameters() != null) {
				for (Config.Parameter p : output.getParameters()) {
					try {
						wro.setParameter(p.getParam(), p.getValue());
					} catch (Exception e) {
						logger.error("output {} set parameter failed for: {}", output.getClassName(), p.getParam(), e);
						return null;
					}
				}
			}
			
			try {
				wro.initialize();
			} catch (Exception e) {
				logger.error("initialize() failed for {}", output.getClassName(), e);
				return null;
			}
			
			wroList.add(wro);
		}
		
		return wroList;
	}
	
}
