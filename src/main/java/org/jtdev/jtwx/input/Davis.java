package org.jtdev.jtwx.input;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jtdev.jtwx.WeatherReading;
import org.jtdev.jtwx.WeatherReadingBuilder;
import org.jtdev.jtwx.WeatherStation;
import org.jtdev.jtwx.WeatherStationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;

@Component
@ConditionalOnProperty(prefix = "jtwx", name = "input", havingValue = "davis")
public class Davis implements WeatherStation {

	@Value("${jtwx.input.portName}")
	private String portName;

	private SerialPort serialPort;

	private BufferedInputStream input;

	private OutputStream output;

	private static final int[] CRC_TABLE = { 0x0, 0x1021, 0x2042, 0x3063,
			0x4084, 0x50a5, 0x60c6, 0x70e7, 0x8108, 0x9129, 0xa14a, 0xb16b,
			0xc18c, 0xd1ad, 0xe1ce, 0xf1ef, 0x1231, 0x0210, 0x3273, 0x2252,
			0x52b5, 0x4294, 0x72f7, 0x62d6, 0x9339, 0x8318, 0xb37b, 0xa35a,
			0xd3bd, 0xc39c, 0xf3ff, 0xe3de, 0x2462, 0x3443, 0x0420, 0x1401,
			0x64e6, 0x74c7, 0x44a4, 0x5485, 0xa56a, 0xb54b, 0x8528, 0x9509,
			0xe5ee, 0xf5cf, 0xc5ac, 0xd58d, 0x3653, 0x2672, 0x1611, 0x630,
			0x76d7, 0x66f6, 0x5695, 0x46b4, 0xb75b, 0xa77a, 0x9719, 0x8738,
			0xf7df, 0xe7fe, 0xd79d, 0xc7bc, 0x48c4, 0x58e5, 0x6886, 0x78a7,
			0x0840, 0x1861, 0x2802, 0x3823, 0xc9cc, 0xd9ed, 0xe98e, 0xf9af,
			0x8948, 0x9969, 0xa90a, 0xb92b, 0x5af5, 0x4ad4, 0x7ab7, 0x6a96,
			0x1a71, 0x0a50, 0x3a33, 0x2a12, 0xdbfd, 0xcbdc, 0xfbbf, 0xeb9e,
			0x9b79, 0x8b58, 0xbb3b, 0xab1a, 0x6ca6, 0x7c87, 0x4ce4, 0x5cc5,
			0x2c22, 0x3c03, 0x0c60, 0x1c41, 0xedae, 0xfd8f, 0xcdec, 0xddcd,
			0xad2a, 0xbd0b, 0x8d68, 0x9d49, 0x7e97, 0x6eb6, 0x5ed5, 0x4ef4,
			0x3e13, 0x2e32, 0x1e51, 0x0e70, 0xff9f, 0xefbe, 0xdfdd, 0xcffc,
			0xbf1b, 0xaf3a, 0x9f59, 0x8f78, 0x9188, 0x81a9, 0xb1ca, 0xa1eb,
			0xd10c, 0xc12d, 0xf14e, 0xe16f, 0x1080, 0x00a1, 0x30c2, 0x20e3,
			0x5004, 0x4025, 0x7046, 0x6067, 0x83b9, 0x9398, 0xa3fb, 0xb3da,
			0xc33d, 0xd31c, 0xe37f, 0xf35e, 0x02b1, 0x1290, 0x22f3, 0x32d2,
			0x4235, 0x5214, 0x6277, 0x7256, 0xb5ea, 0xa5cb, 0x95a8, 0x8589,
			0xf56e, 0xe54f, 0xd52c, 0xc50d, 0x34e2, 0x24c3, 0x14a0, 0x481,
			0x7466, 0x6447, 0x5424, 0x4405, 0xa7db, 0xb7fa, 0x8799, 0x97b8,
			0xe75f, 0xf77e, 0xc71d, 0xd73c, 0x26d3, 0x36f2, 0x0691, 0x16b0,
			0x6657, 0x7676, 0x4615, 0x5634, 0xd94c, 0xc96d, 0xf90e, 0xe92f,
			0x99c8, 0x89e9, 0xb98a, 0xa9ab, 0x5844, 0x4865, 0x7806, 0x6827,
			0x18c0, 0x08e1, 0x3882, 0x28a3, 0xcb7d, 0xdb5c, 0xeb3f, 0xfb1e,
			0x8bf9, 0x9bd8, 0xabbb, 0xbb9a, 0x4a75, 0x5a54, 0x6a37, 0x7a16,
			0x0af1, 0x1ad0, 0x2ab3, 0x3a92, 0xfd2e, 0xed0f, 0xdd6c, 0xcd4d,
			0xbdaa, 0xad8b, 0x9de8, 0x8dc9, 0x7c26, 0x6c07, 0x5c64, 0x4c45,
			0x3ca2, 0x2c83, 0x1ce0, 0x0cc1, 0xef1f, 0xff3e, 0xcf5d, 0xdf7c,
			0xaf9b, 0xbfba, 0x8fd9, 0x9ff8, 0x6e17, 0x7e36, 0x4e55, 0x5e74,
			0x2e93, 0x3eb2, 0x0ed1, 0x1ef0, };

	private static final int LOOP_TYPE_1 = 1;
	private static final int LOOP_TYPE_2 = 2;
	
	public Davis() {
		super();
	}

	@Override
	@PostConstruct
	public void connect() throws WeatherStationException {
		try {
			serialPort = SerialPort.getCommPort(portName);
			serialPort.setComPortParameters(19200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
			serialPort.openPort();
			input = new BufferedInputStream(serialPort.getInputStream());
			output = serialPort.getOutputStream();
		} catch (SerialPortInvalidPortException e) {
			throw new WeatherStationException(e);
		}
	}
	
	@Override
	@PreDestroy
	public void disconnect() throws WeatherStationException {
		try {
			if (output != null) output.close();
			if (input != null) input.close();
		} catch (IOException e) {
			// TODO Log me
			e.printStackTrace();
		}
		if (serialPort != null) serialPort.closePort();
	}

	@Override
	public WeatherReading getWeatherReading() throws WeatherStationException {
//		byte[] loop1;
		byte[] loop2;
		
		try {
			wakeup();
//			loop1 = executeLps(LOOP_TYPE_1);
			loop2 = executeLps(LOOP_TYPE_2);
		} catch (IOException e) {
			throw new WeatherStationException(e);
		}
		
		WeatherReading wr = new WeatherReadingBuilder()
				// use the current system date/time for this reading
				.date(new Date())
				// populate fields from LOOP_TYPE_1 packet
//	some of these overlap with the type 2 packet
//	and type 2 has everything we need for wunderground
//	so only using those for now.
//				.barometer(getShort(loop1, 7, true) / 1000f)
//				.insideTemp(getShort(loop1, 9, true) / 10f)
//				.insideHumidity(getByte(loop1[11], true))
//				.outsideTemp(getShort(loop1, 12, true) / 10f)
//				.windSpeed(getByte(loop1[14], false))
//				.avgWindSpeed10Min(getByte(loop1[15], false))
//				.windDirection(getShort(loop1, 16, false))
//				.outsideHumidity(getByte(loop1[33], true))
//				.dayRainTotal(getShort(loop1, 50, true) / 100f)
//				.monthRainTotal(getShort(loop1, 52, true) / 100f)
//				.yearRainTotal(getShort(loop1, 54, true) / 100f)
				
				// populate fields from LOOP_TYPE_2 packet
				.barometer(getShort(loop2, 7, true) / 1000f)
				.insideTemp(getShort(loop2, 9, true) / 10f)
				.insideHumidity(getByte(loop2[11], true))
				.outsideTemp(getShort(loop2, 12, true) / 10f)
				.windSpeed(getByte(loop2[14], false))
				.windDirection(getShort(loop2, 16, false))
				.outsideHumidity(getByte(loop2[33], true))
				.dayRainTotal(getShort(loop2, 50, true) / 100f)

				.avgWindSpeed10Min(getShort(loop2, 18, false) / 10f)
				.avgWindSpeed2Min(getShort(loop2, 20, false) / 10f)
					// TODO Davis doc wrong for wind gust?
					// Davis doc says this is in 0.1mph resolution, but 
					// the data appears to actually be in whole mph
				.windGust10Min(getShort(loop2, 22, false))
				.windGust10MinDirection(getShort(loop2, 24, false))
				.dewPoint(getShort(loop2, 30, true))
				.rainLast60Min(getShort(loop2, 54, true) / 100f)
				.build();
		        		
		return wr;
	}
	
	
	private void wakeup() throws WeatherStationException, IOException {

		String cmd = "\n";

		// try up to three times to wake it up
		for (int i = 0; i < 3; i++) {
			// empty the input stream
			drainInput();
			// send the wakeup command
			output.write(cmd.getBytes("US-ASCII"));
			// wait up to 1500ms for a response
			for (int j = 0; j < 300; j++) {
				try {
					Thread.sleep(5L);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				if (input.available() >= 2) {
					byte[] b = new byte[2];
					input.read(b, 0, b.length);
					String s = new String(b);
					if ("\n\r".equals(s)) {
						// Success!
						return;
					}
				}
			}	
		}
		
		// if we get this far we failed
		throw new WeatherStationException("Unable to wakeup weather station");
	}

	private byte[] executeLps(int type) throws WeatherStationException, IOException {
		
		// determine which loop command to send
		String cmd;
		switch (type) {
		case LOOP_TYPE_1:
			cmd = "LPS 1 1\n";
			break;
		case LOOP_TYPE_2:
			cmd = "LPS 2 1\n";
			break;
		default:
			throw new IllegalArgumentException("Invalid type argument: " + type);
		}
		
		// make sure the input buffer is empty
		drainInput();

		// Send the loop command to the weather station
		output.write(cmd.getBytes("US-ASCII"));

		// Wait up to 3 seconds for it to respond
		for (int i = 0; i < 300; i++) {
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			if (input.available() == 100) {
				break;
			}
		}

		// make sure we got a full response
		if (input.available() != 100) {
			throw new WeatherStationException("Invalid or no response to LPS command");
		}

		// make sure we got the ACK
		if (input.read() != 6) {
			throw new WeatherStationException("Weather station did not ACK our command");
		}

		// read the data packet
		byte[] data = new byte[99];
		input.read(data, 0, data.length);

		// validate the CRC
		byte[] crc = calculateCrc(data, 0, 97);
		if (crc[0] != data[97] || crc[1] != data[98]) {
			throw new WeatherStationException("Received data failed CRC check");
		}
		
		// all good!
		return data;
	}

	private int getShort(byte[] b, int off, boolean signed) {
		if (signed) {
			return (b[off + 1] << 8) | (b[off] & 0xff);
		} else {
			return ((b[off + 1] & 0xff) << 8) | (b[off] & 0xff);
		}
	}

	private int getByte(byte b, boolean signed) {
		if (signed) {
			return (int) b;
		} else {
			return b & 0xff;
		}
	}

	private void drainInput() throws IOException {
		while (input.available() > 0) {
			input.read();
		}
	}

	private byte[] calculateCrc(byte[] b, int off, int len) {
		int crc = 0;
		for (int i = off; i < len; i++) {
			crc = (CRC_TABLE[(crc >> 8) ^ (b[i] & 0xff)] & 0xffff) ^ ((crc << 8) & 0xffff);
			// System.out.println("i=" + i + "  b[i]=" + (int) b[i] + "  crc=" + crc);
		}
		byte[] r = new byte[2];
		r[0] = (byte) (crc >> 8);
		r[1] = (byte) (crc & 0xff);
		return r;
	}


}
