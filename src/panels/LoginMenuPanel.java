package panels;
import javax.swing.*;

import client.GUIDriver;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class LoginMenuPanel extends JPanel {
	public static NavButton		LoginBtn;
	public static NavButton 	VerifBtn;
	public static NavButton		RegisterBtn;
	public static JButton		ExitBtn;
	public LoginMenuPanel() {
		initializeComponents();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		add(LoginBtn);
		add(RegisterBtn);
		add(VerifBtn);
		add(ExitBtn);
	}
	
	private void initializeComponents() {
		LoginBtn    = new NavButton("Login", LoginPanel.class);
		RegisterBtn = new NavButton("Register", SignupPanel.class);
		VerifBtn 	= new NavButton("Verify", VerifyPanel.class);
		ExitBtn = new JButton("Exit");
		ExitBtn.addActionListener(new Listener());
	}
	
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == LoginBtn)
            {
                GUIDriver.gotoPanel(new LoginPanel());
            }
            else if(source == RegisterBtn) {
            	GUIDriver.gotoPanel(new SignupPanel());
            }else if (source == ExitBtn) {
            	GUIDriver.frame.dispatchEvent(new WindowEvent(GUIDriver.frame, WindowEvent.WINDOW_CLOSING));
            }
        }
	}
}
