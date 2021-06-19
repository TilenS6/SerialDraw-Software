package net.tilens.serialdraw;

import java.awt.Color;
import java.nio.charset.StandardCharsets;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Class for handling Serial Communication
 * @author TilenS
 * @author Znidi
 * @author JurijTSL
 */
public class SerialHandler {
	private static SerialPort comPort;
	private static boolean triggered;
	
	/**
	 * Function connects to Serial and handles parsing of commands
	 * @param serialPort Serial port connected
	 */
	public static void connect(String serialPort) {
		//connect to serial and set parameters
		comPort = SerialPort.getCommPort(serialPort);
		comPort.setBaudRate(1000000);
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
		
		//add event which indicated that data is received
		comPort.addDataListener(new SerialPortDataListener() {
			@Override
			public int getListeningEvents() {
				return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
			}
			
			@Override
			public void serialEvent(SerialPortEvent event) {
				//read bytes from serial
				byte[] newData = event.getReceivedData();
				
				//trim it and convert to String
				String line = new String(newData, StandardCharsets.UTF_8).trim();
				
				//check wheater line equals trigger
				if(line.trim().equals("trigger")) {
					//line equals trigger which means that Arduino is initialized and we can proceed with parsing lines
					triggered = true;
				}
				if(triggered) {
					//firstly add command parameters to String[] array and separate them by comma
					String[] parameters = line.split(",");
					//first parameter equals "p". Proceed with drawing pixel accordingly
					if(parameters[0].equals("p")) {
						if(parameters.length < 4) {
							//there was and error in communication. Send finished trigger to Arduino and print error
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("Command unfinished");
						} else {
							App.canvas.drawPixel(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), strToColor(parameters[3]));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					//first parameter equals "b", proceed with setting background accordingly
					else if(parameters[0].equals("b")){
						if(parameters.length < 2) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("Command unfinished");
						} else {
							App.canvas.setBackground(strToColor(parameters[1]));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					//first parameter equals "a", proceed with filling an area accordingly
					else if(parameters[0].equals("a")){
						if(parameters.length < 6) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("Command unfinished");
						} else {
							App.canvas.fillArea(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]), Integer.parseInt(parameters[4]), strToColor(parameters[5]));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					//first parameter equals "c", proceed with removing pixel accordingly
					else if(parameters[0].equals("c")){
						if(parameters.length < 3) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("Command unfinished");
						} else {
							App.canvas.removePixel(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					//first parameter equals "l", proceed with clearing the canvas
					else if(parameters[0].equals("l")){
						if(parameters.length < 1) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("Command unfinished");
						} else {
							App.canvas.clearScreen();
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					//first parameter equals "t", proceed with setting window title
					else if(parameters[0].equals("t")){
						if(parameters.length < 2) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("Command unfinished");
						} else {
							App.window.setTitle("Serial Draw - " + parameters[1]);
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					//first parameter equals "r", proceed with setting resolution
					else if(parameters[0].equals("r")){
						if(parameters.length < 2) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("Command unfinished");
						} else {
							App.newCanvas(new DrawingPanel(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), 0, Color.WHITE));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
				}
			}
		});
		
		//open port
		comPort.openPort();
		
		//check wheater port is opened
		if(comPort.isOpen()) {
			//toggle status on button
			ControlPanel.toogleConnectStatus();
		}
	}
	
	/**
	 * Function disconnects to Serial
	 */
	public static void disconnect() {
		//Set trigger point to false
		triggered = false;
		
		//Close serial port and remove listener
		comPort.closePort();
		comPort.removeDataListener();
		
		//Toggle button and display prompt to connect Serial
		ControlPanel.toogleConnectStatus();
		App.canvas.connectRequest();
	}
	
	/**
	 * Function refreshed list of available serial ports
	 */
	public static void refresh() {
		
	}
	
	/**
	 * Function converts String to corresponding color
	 * @param toConvert String that is converted
	 * @return Converted color
	 */
	private static Color strToColor(String toConvert) {
		Color toReturn = null;
		
		switch(toConvert.trim()) {
			case "1":
				toReturn = Color.BLACK;
				break;
			case "2":
				toReturn = Color.WHITE;
				break;
			case "3":
				toReturn = Color.CYAN;
				break;
			case "4":
				toReturn = Color.DARK_GRAY;
				break;
			case "5":
				toReturn = Color.GRAY;
				break;
			case "6":
				toReturn = Color.GREEN;
				break;
			case "7":
				toReturn = Color.LIGHT_GRAY;
				break;
			case "8":
				toReturn = Color.MAGENTA;
				break;
			case "9":
				toReturn = Color.ORANGE;
				break;
			case "10":
				toReturn = Color.PINK;
				break;
			case "11":
				toReturn = Color.RED;
				break;
			case "12":
				toReturn = Color.BLUE;
				break;
			case "13":
				toReturn = Color.YELLOW;
				break;
			case "14":
				toReturn = Color.decode("#007500");
				break;
			default:
				toReturn = Color.decode("#4A412A");
				break;
		}
		return toReturn;
	}
}