package net.tilens.serialdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;

import jssc.SerialPortList;

public class App {
	public static DrawingPanel canvas;

	public static void main(String[] args) {
		JFrame okno = new JFrame();
		okno.setLayout(new BorderLayout());
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setSize(new Dimension(976, 999));

		okno.setResizable(false);
		/*okno.setUndecorated(true);
		okno.setExtendedState(Frame.MAXIMIZED_BOTH);*/
		
		canvas = new DrawingPanel(60, 60, 0, Color.WHITE);// to je tko k Div sam da smo naredl svojo implementacijo...
		ControlPanel control = new ControlPanel(SerialPortList.getPortNames());// za lepoto
		okno.add(control, BorderLayout.PAGE_START);
		okno.add(canvas, BorderLayout.CENTER);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);
		okno.repaint();
		//tukaj spodaj je koda, ki popravi višino
		int stBelihPixlov = canvas.getHeight() - canvas.getGoodHeight();
		okno.setSize(new Dimension(okno.getSize().width, okno.getSize().height-stBelihPixlov )); 
		okno.repaint();
		canvas.connectRequest();
	}
}
//i see caki zdej pa se en test, neki se pol dogaja