package panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.GUIDriver;

public class NavButton extends JPanel{
	public JButton btn;
	public Class<? extends JPanel> nextPanel;
	public NavButton(String msg, Class<? extends JPanel> gotoPanel) {
		nextPanel = gotoPanel;
		btn = new JButton(msg);
		btn.addActionListener(new Listener());
		add(btn);
	}
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == btn)
            {
            	try {
					GUIDriver.gotoPanel(nextPanel.newInstance());
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
            }
        }
	}
}
