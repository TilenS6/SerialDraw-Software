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
	public static DrawingPanel canvas;

	public static void main(String[] args) {
		//add JFrame and set some parameters
		JFrame window = new JFrame();
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(976, 999));
		window.setResizable(false);
		
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
		int numOfPixels = canvas.getHeight() - canvas.getGoodHeight();
		window.setSize(new Dimension(window.getSize().width, window.getSize().height-numOfPixels)); 
		window.repaint();
		canvas.connectRequest();
	}
}