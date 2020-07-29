package net.tilens.serialdraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * Class for handling ControlPanel
 * @author TilenS
 * @author Znidi
 * @author JurijTSL
 */
public class ControlPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -4483181462675610401L;
	private static JButton connect;
	private JButton refresh;
	private JComboBox<String> dropdown;
	
	/**
	 * Create ControlPanel and add list of available Serial ports to dropdown
	 * @param comPorts list of available Serial ports
	 */
	public ControlPanel(String...comPorts) {
		//Add buttons
		connect = new JButton("connect");
		refresh = new JButton("refresh");
		
		//Add dropdown and add list of available Serial ports to it
		dropdown = new JComboBox<String>(comPorts);
		if(comPorts.length>0) {
			dropdown.setSelectedIndex(0);
		}
		
		//Add listeners for elements
		connect.addActionListener(this);
		refresh.addActionListener(this);
		dropdown.addActionListener(this);
		
		//Set commands
		connect.setActionCommand("connect");
		refresh.setActionCommand("refresh");
		dropdown.setActionCommand("dropdown");
		
		//Display them
		add(dropdown);
		add(connect);
		add(refresh);
	}
	
	/**
	 * According to given parameter it sets text on the button and its command
	 * @param connected Is connected
	 */
	public void setConnectButtonStatus(boolean connected) {
		if(connected) {
			//we are connected
			connect.setText("Disconnect");
			connect.setActionCommand("disconnect");
		} else {
			//we are not connected
			connect.setText("Connect");
			connect.setActionCommand("connect");
		}
	}
	
	/**
	 * Toggles text of the button and its command
	 */
	public static void toogleConnectStatus() {
		if(connect.getText().equals("connect")) {
			connect.setText("disconnect");
			connect.setActionCommand("disconnect");
		} else if(connect.getText().equals("disconnect")) {
			connect.setText("connect");
			connect.setActionCommand("connect");
		}
	}
	
	/**
	 * Action handler for buttons
	 * @param e Event
	 */
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "connect":
				SerialHandler.connect((String) dropdown.getSelectedItem());
				break;
			case "disconnect":
				SerialHandler.disconnect();
				break;
			case "refresh":
				SerialHandler.refresh();
				break;
		}
	}
}
