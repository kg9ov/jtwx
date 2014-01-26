package org.jtdev.jtwx;

import java.io.IOException;
import java.util.Observable;
import java.util.Properties;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.jtdev.jtwx.input.Davis;
import org.jtdev.jtwx.output.Slf4jLogger;
import org.jtdev.jtwx.output.WeatherUndergroundRapidFire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller extends Observable implements Daemon, Runnable {
	
	private final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	private Thread controllerThread;
	private volatile boolean shutdown = false;
	
	@Override
	public void run() {
		// TODO Add some kind of real config system (commons configuration?)
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("jtwx.properties"));
		} catch (IOException e) {
			logger.error("Unable to load jtwx.properties", e);
		}
		
		logger.info("Init weather station");
		Davis davis = new Davis("/dev/ttyS0");
		try {
			davis.connect();
		} catch (Exception e) {
			logger.info("weather station init failed", e);
			System.exit(1);
		}
		
		logger.info("Init outputs");
		try {
			WeatherReadingObserver wro;
			wro = new Slf4jLogger();
			wro.initialize();
			this.addObserver(wro);
			wro = new WeatherUndergroundRapidFire();
			wro.setParameter("wurf.id", props.getProperty("wurf.id"));
			wro.setParameter("wurf.password", props.getProperty("wurf.password"));
			wro.initialize();
			this.addObserver(wro);
			wro = null;
		} catch (Exception e) {
			logger.info("output init failed", e);
		}
		
		while (!shutdown) {
			long start = System.currentTimeMillis();
			try {
				logger.debug("getting weather reading");
				WeatherReading wr = davis.getWeatherReading();
				logger.debug("notify observers");
				setChanged();
				notifyObservers(wr);
			} catch (Exception e) {
				logger.info("failed to get weather reading", e);
			}
			logger.debug("Elapsed time (ms): " + (System.currentTimeMillis() - start));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		logger.info("disconnect weather station");
		davis.disconnect();
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
	
}
