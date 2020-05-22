package panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.Client;
import client.GUIDriver;
import common.User;

public class MainMenuPanel extends JPanel {
	public static NavButton 	SearchBtn;
	public static NavButton 	CartBtn;
	public static NavButton		HistBtn;
	public static NavButton		StockBtn;
	public static JButton		ExitBtn;
	public static JPanel		Buttons;
	public MainMenuPanel() {
		initializeComponents();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		add(SearchBtn);
		add(CartBtn);
		add(HistBtn);
		
		if(Client.mainUser.type == User.userType.ADMIN) {
			add(StockBtn);
		}
		
		add(ExitBtn);
	}
	
	private void initializeComponents() {
		
		SearchBtn = new NavButton("Search for a product", SearchPanel.class);
		
		CartBtn = new NavButton("Check your cart", CartPanel.class);
		
		HistBtn = new NavButton("Check your old purchases", HistoryListPanel.class);
		
		StockBtn = new NavButton("(ADMIN) Add items to stock", AdminPanel.class);
		
		ExitBtn = new JButton("Exit");
		ExitBtn.addActionListener(new Listener());
		
		Buttons = new JPanel();
		
		Buttons.add(ExitBtn);
	}
	
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == ExitBtn) {
            	GUIDriver.frame.dispatchEvent(new WindowEvent(GUIDriver.frame, WindowEvent.WINDOW_CLOSING));
            }
        }
	}
}
