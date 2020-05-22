package panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.LoginHandler;
import client.VerifyHandler;
import common.Encrypt;
import common.LoginRequest;
import common.VerifyRequest;


public class VerifyPanel extends JPanel {
	public static Entry			UserEntry;
	public static Entry			VerifEntry;
	public static JButton		LoginBtn;
	public static NavButton		BackBtn;
	public static JPanel		Buttons;
	public VerifyPanel() {
		initializeComponents();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		add(UserEntry);
		add(VerifEntry);
		add(Buttons);
	}
	
	private void initializeComponents() {
		UserEntry = new Entry("username: ");
		VerifEntry = new Entry("verification code: ",80, true);
		
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
            	VerifyRequest VReq = new VerifyRequest( UserEntry.Field.getText(), VerifEntry.Field.getText());
                VerifyHandler handler = new VerifyHandler();
                handler.handleRequest(VReq);
            }
        }
	}
}
