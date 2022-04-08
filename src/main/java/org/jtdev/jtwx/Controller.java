package org.jtdev.jtwx;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller extends Observable implements Daemon, Runnable {
	
	private final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	private Thread controllerThread;
	private volatile boolean shutdown = false;
	
	@Override
	public void run() {
		Config config = Config.load();
						
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

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(DaemonContext daemonContext) throws DaemonInitException, Exception {
		controllerThread = new Thread(this, this.getClass().getSimpleName());
	}

	@Override
	public void start() throws Exception {
		controllerThread.start();
	}

	@Override
	public void stop() throws Exception {
		this.shutdown = true;
		controllerThread.join();
	}
	
	private WeatherStation initInput(Config config) {
		String className = config.input.className;
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
		
		for (Config.Parameter p : config.input.parameters) {
			try {
				ws.setParameter(p.param, p.value);
			} catch (IllegalArgumentException | WeatherStationException e) {
				logger.error("Input set parameter failed for: {}", p.param, e);
				return null;
			}
		}
		
		return ws;
	}
	
	private List<WeatherReadingObserver> initOutputs(Config config) {
		List<WeatherReadingObserver> wroList = new ArrayList<WeatherReadingObserver>();
		
		for (Config.Output output : config.outputs) {
			WeatherReadingObserver wro;
			try {
				Class<?> clazz = Class.forName(output.className);
				Constructor<?> constructor = clazz.getConstructor();
				wro = (WeatherReadingObserver) constructor.newInstance();
			} catch (ClassNotFoundException | NoSuchMethodException
					| SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				logger.error("Failed to load output class: {}", output.className, e);
				return null;
			}

			if (output.parameters != null) {
				for (Config.Parameter p : output.parameters) {
					try {
						wro.setParameter(p.param, p.value);
					} catch (Exception e) {
						logger.error("output {} set parameter failed for: {}", output.className, p.param, e);
						return null;
					}
				}
			}
			
			try {
				wro.initialize();
			} catch (Exception e) {
				logger.error("initialize() failed for {}", output.className, e);
				return null;
			}
			
			wroList.add(wro);
		}
		
		return wroList;
	}
	
}
