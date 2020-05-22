package panels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.ActionHandler;
import client.Client;
import client.GUIDriver;
import client.HistoryHandler;
import client.ItemInfoHandler;
import common.AddItemRequest;
import common.Cart;
import common.HistoryRequest;
import common.Item;
import common.ItemInfoRequest;

public class HistoryListPanel extends JPanel {

	public static JScrollPane   Scroller;
	public static JList<String> CartJList;
	public static JButton 		BackBtn;
	public static JButton 		ViewDetailsBtn;
	public static List<Cart>	carts;
	public HistoryListPanel() {
		initializeComponents();
		
		add(Scroller);
		add(ViewDetailsBtn);
		add(BackBtn);
	}
	public void initializeHistory(){
		HistoryRequest req = new HistoryRequest(Client.mainUser);
		HistoryHandler handler = new HistoryHandler();
		handler.handleRequest(req);
		carts = handler.carts;
	}
	public String[] listEntries() {
		String[] out = new String[carts.size()];
		for(int i =0; i<carts.size(); i++) {
			out[i] ="Cart #"+i;
		}
		return out;
	}
	public void initializeComponents() {
		initializeHistory();
		Scroller = new JScrollPane();
		CartJList = new JList<String>();
		CartJList.setListData(listEntries());
		
		Scroller.getViewport().setView(CartJList);
		
		ViewDetailsBtn = new JButton("View details");
		ViewDetailsBtn.addActionListener(new Listener());
		
		BackBtn = new JButton("< Back");
		BackBtn.addActionListener(new Listener());
	}
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Component source = (Component) e.getSource();
            System.out.println(e.getID());
			if(source == BackBtn) {
            	GUIDriver.gotoPanel(new MainMenuPanel());
            }else if (source == ViewDetailsBtn) {
            	if(CartJList.getSelectedValue() == null) {
            		GUIDriver.prompt("Error!", "Please select a cart first!", 0);
            	}else {
            		GUIDriver.gotoPanel(new HistoryCartPanel(carts.get(CartJList.getSelectedIndex())));
            	}
            }
        }
	}
}
