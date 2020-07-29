package net.tilens.serialdraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = -4483181462675610401L;
	private static JButton connect;
	private JButton refresh;
	private JComboBox<String> dropdown;
	
	public ControlPanel(String...comPorts) {
		connect = new JButton("connect");
		refresh = new JButton("refresh");
		dropdown = new JComboBox<String>(comPorts);
		if(comPorts.length>0) {
			dropdown.setSelectedIndex(0);
		}
		connect.addActionListener(this);
		refresh.addActionListener(this);
		dropdown.addActionListener(this);
		connect.setActionCommand("connect");
		refresh.setActionCommand("refresh");
		dropdown.setActionCommand("dropdown");
		add(dropdown);
		add(connect);
		add(refresh);
	}
	
	/**
	 * glede na podan parameter nastavi besedilo gumba
	 * @param connected
	 */
	public void setConnectButtonStatus(boolean connected) {
		connect.setText(connected?"disconnect":"connect");
		connect.setActionCommand(connected?"disconnect":"connect");
	}
	
	/**
	 * zamenja besedilo gumba z njegovim nasprotjem
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
