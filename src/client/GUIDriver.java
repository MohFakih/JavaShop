package client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import panels.*;

public class GUIDriver {
	
	public static JFrame frame;
    public static void main(String[] args)
    {
        frame = new JFrame("EECE 350 project");
        frame.setSize(720, 480);
        gotoPanel(new ServerConnectPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Client.releaseResources();
            }
        });
        
    }
    
    public static String promptInput(String Title, String Message) {
    	return JOptionPane.showInputDialog(frame, Message, Title);
    }
    public static int promptIntInput(String Title, String Message) {
    	while(true) {
	    	try {
		    	String e = promptInput(Title, Message);
		    	if(e == null) {
		    		return -1;
		    	}
		    	return Integer.parseInt(e);
	    	}catch(NumberFormatException e) {
	    		prompt("Error!", "Please enter a valid integer!", 0);
	    	}
    	}
    }
    public static void prompt(String Title, String message) {
    	prompt(Title, message, 1);
    }
    public static void prompt(String Title, String message, int type) {
    	JOptionPane.showMessageDialog(frame,
			    message,
			    Title, type);
    }
    
	public static void gotoPanel(JPanel panel) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	public static double promptDoubleInput(String Title, String message) {
		while(true) {
	    	try {
		    	String e = promptInput(Title, message);
		    	if(e == null) {
		    		return -1;
		    	}
		    	return Double.parseDouble(e);
	    	}catch(NumberFormatException e) {
	    		prompt("Error!", "Please enter a valid double!", 0);
	    	}
    	}
	}
}
