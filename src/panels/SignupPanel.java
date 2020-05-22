package panels;
import javax.swing.*;

import client.Client;
import client.GUIDriver;
import client.SignupHandler;
import common.Encrypt;
import common.LoginRequest;
import common.Response;
import common.SignupRequest;
import common.User;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
public class SignupPanel extends JPanel {
	public static Entry			UserEntry;
	public static Entry			PassEntry;
	public static Entry			EmailEntry; 
	public static Entry			NameEntry;
	public static JButton		SgnpBtn;
	public static NavButton		BackBtn;
	public static JPanel 		Buttons;
	public SignupPanel() {
		initializeComponents();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		add(NameEntry);
		add(UserEntry);
		add(PassEntry);
		add(EmailEntry);
		add(Buttons);
	}
	
	private void initializeComponents() {
		UserEntry = new Entry("username: ");
		PassEntry = new Entry("password: ",20, true);
		EmailEntry = new Entry("email: ");
		NameEntry = new Entry("Name: ", 30);
		
		SgnpBtn   	= new JButton("Sign up");
		SgnpBtn.addActionListener(new Listener());
		BackBtn     = new NavButton("< Back", LoginMenuPanel.class);
		
		Buttons = new JPanel();
		Buttons.add(BackBtn);
		Buttons.add(SgnpBtn);
	}
	
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == SgnpBtn)
            {
            	SignupRequest SGNPReq = new SignupRequest();
            	SGNPReq.user = new User(UserEntry.Field.getText(), NameEntry.Field.getText(), EmailEntry.Field.getText());
                SGNPReq.user.hashedPass = Encrypt.encrypt(PassEntry.Field.getText());
                SignupHandler handler = new SignupHandler();
                handler.handleRequest(SGNPReq);
            }
        }
	}
}
