package panels;
import javax.swing.*;

import client.Client;
import client.GUIDriver;
import client.LoginHandler;
import common.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
public class LoginPanel extends JPanel {
	public static Entry			UserEntry;
	public static Entry			PassEntry;
	public static JButton		LoginBtn;
	public static NavButton		BackBtn;
	public static JPanel		Buttons;
	public LoginPanel() {
		initializeComponents();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		add(UserEntry);
		add(PassEntry);
		add(Buttons);
	}
	
	private void initializeComponents() {
		UserEntry = new Entry("username: ");
		PassEntry = new Entry("password: ",20, true);
		
		LoginBtn   	= new JButton("Login");
		LoginBtn.addActionListener(new Listener());
		BackBtn     = new NavButton("< Back", LoginMenuPanel.class);
		
		Buttons = new JPanel();
		Buttons.add(BackBtn);
		Buttons.add(LoginBtn);
	}
	
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == LoginBtn)
            {
                LoginRequest LGNReq = new LoginRequest();
                LGNReq.username = UserEntry.Field.getText();
                LGNReq.hashedPass = Encrypt.encrypt(PassEntry.Field.getText());
                LoginHandler handler = new LoginHandler();
                handler.handleRequest(LGNReq);
            }
        }
	}
}
