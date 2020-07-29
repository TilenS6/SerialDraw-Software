package net.tilens.serialdraw;

import java.awt.Color;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class SerialHandler {
	private static SerialPort comPort;
	private static boolean triggered;
	
	public static void connect(String serialPort) {
		comPort = SerialPort.getCommPort(serialPort);
		comPort.setBaudRate(1000000);
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
		comPort.addDataListener(new SerialPortDataListener() {
			@Override
			public int getListeningEvents() {
				return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
			}
			
			@Override
			public void serialEvent(SerialPortEvent event) {
				byte[] newData = event.getReceivedData();
				String line = new String(newData, StandardCharsets.UTF_8).trim();
				if(line.trim().equals("trigger")) {
					triggered = true;
				}
				
				if(triggered) {
					String[] parameters = line.split(",");
					
					if(parameters[0].equals("p")) {
						if(parameters.length < 4) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("nepopolna komanda");
						} else {
							App.canvas.risiKvadrat(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), strToColor(parameters[3]));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					
					else if(parameters[0].equals("b")){
						if(parameters.length < 2) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("nepopolna komanda");
						} else {
							App.canvas.setBackground(strToColor(parameters[1]));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					
					else if(parameters[0].equals("a")){
						if(parameters.length < 6) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("nepopolna komanda");
						} else {
							App.canvas.fillArea(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]), Integer.parseInt(parameters[4]), strToColor(parameters[5]));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
					
					else if(parameters[0].equals("c")){
						if(parameters.length < 3) {
							comPort.writeBytes("k".getBytes(), 1);
							System.err.println("nepopolna komanda");
						} else {
							App.canvas.brisiKvadrat(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]));
							comPort.writeBytes("k".getBytes(), 1);
						}
					}
				}
			}
		});
		comPort.openPort();
		if(comPort.isOpen()) {
			ControlPanel.toogleConnectStatus();
		}
	}
	
	public static void disconnect() {
		triggered = false;
		comPort.closePort();
		comPort.removeDataListener();
		ControlPanel.toogleConnectStatus();
		App.canvas.connectRequest();
	}
	
	public static void refresh() {
		
	}
	
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
