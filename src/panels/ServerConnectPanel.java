package panels;

import java.awt.event.*;

import javax.swing.*;

import client.Client;
import client.GUIDriver;

public class ServerConnectPanel extends JPanel {
	public static Entry			IPEntry;
	public static Entry			PortEntry;
	public static JButton		ConnectBtn;
	public ServerConnectPanel() {
		initializeComponents();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		add(IPEntry);
		add(PortEntry);
		add(ConnectBtn);
	}
	
	private void initializeComponents() {
		IPEntry = new Entry("IP Address: ");
		PortEntry = new Entry("Port number: ");
		
		ConnectBtn   	= new JButton("Connect");
		ConnectBtn.addActionListener(new Listener());
	}
	
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();
            if(source == ConnectBtn)
            {
            	try {
            		Client.setIpAddress(IPEntry.Field.getText());
            		Client.setPort(Integer.parseInt(PortEntry.Field.getText()));
	        		// Attempt to connect to the server
	        		Client.connectToServer();
	        		
	        		// Initialize Streams
	        		Client.initializeStreams();
	        		
	        		
	        		GUIDriver.prompt( "You are now connected to the server!",
            			    "Connection success", 1);
	        		GUIDriver.gotoPanel(new LoginMenuPanel());
            	}catch(Exception e) {
            		GUIDriver.prompt( "Exception occured: "+ e.getMessage(),
            			    "Connection error",
            			    0);
            	}
            }
        }
	}
}