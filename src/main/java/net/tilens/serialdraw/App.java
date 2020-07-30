package net.tilens.serialdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import jssc.SerialPortList;


/**
 * Main class
 * @author TilenS
 * @author Znidi
 * @author JurijTSL
 */

public class App {
	//add JFrame
	public static JFrame window = new JFrame();
	
	//add Canvas
	public static DrawingPanel canvas;

	public static void main(String[] args) {
		//set some parameters for JFrame
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(976, 999));
		window.setResizable(true);
		window.setTitle("Serial Draw - Waiting for connection");
		
		//Add canvas (the pixels are drawn here)
		canvas = new DrawingPanel(60, 60, 0, Color.WHITE, Color.RED);
		
		//Add control panel, and add available ports to drop(Connect button, Disconnect button, etc.)
		ControlPanel controlPanel = new ControlPanel(SerialPortList.getPortNames());
		
		//Add them to JFrame
		window.add(controlPanel, BorderLayout.PAGE_START);
		window.add(canvas, BorderLayout.CENTER);
		
		//Show window
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.repaint();
		
		//Adjust window height
		resizeWindow();
		canvas.connectRequest();
	}

	/**
	 * Creates new and updated canvas
	 * @param newCanvas
	 */
	public static void newCanvas(DrawingPanel newCanvas) {
		window.remove(canvas);
		canvas = newCanvas;
		window.add(newCanvas, BorderLayout.CENTER);
		window.validate();
		window.repaint();
		resizeWindow();
	}
	
	/**
	 * Resizes window responsively
	 */
	public static void resizeWindow() {
		int numOfPixelsH = canvas.getHeight() - canvas.getGoodHeight();
		int numOfPixelsW = canvas.getWidth() - canvas.getGoodWidth();
		window.setSize(new Dimension(window.getSize().width-numOfPixelsW, window.getSize().height-numOfPixelsH)); 
		window.repaint();
	}
}