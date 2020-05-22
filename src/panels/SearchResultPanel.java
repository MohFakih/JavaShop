package panels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.ActionHandler;
import client.Client;
import client.GUIDriver;
import client.ItemInfoHandler;
import common.AddItemRequest;
import common.Item;
import common.ItemInfoRequest;
import common.ItemRequest;

public class SearchResultPanel extends JPanel {
	
	public static JScrollPane   Scroller;
	public static JList<Item> 	ItemJList;
	public static JButton		AddItemBtn;
	public static JButton 		BackBtn;
	public static JButton 		ViewDetailsBtn;
	
	public SearchResultPanel(List<Item> items) {
		Item[] itemArr = {};
		itemArr = items.toArray(itemArr);
		initializeComponents(itemArr);
		
		add(Scroller);
		add(AddItemBtn);
		add(ViewDetailsBtn);
		add(BackBtn);
	}
	
	public void initializeComponents(Item[] items) {
		Scroller = new JScrollPane();
		ItemJList = new JList<Item>();
		ItemJList.setListData(items);
		ItemJList.setCellRenderer(new ItemRenderer());
		ItemJList.setPrototypeCellValue(new Item("A VERY LONG NAME CELL", Item.category.MISC, -1.0, -1));
		Scroller.getViewport().setView(ItemJList);
		
		AddItemBtn = new JButton("Add Item To Cart");
		AddItemBtn.addActionListener(new Listener());
		
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
            }else if (source == AddItemBtn) {
            	if(ItemJList.getSelectedValue() == null) {
            		GUIDriver.prompt("Error!", "Please select an item first!", 0);
            	}else {
            		int num = GUIDriver.promptIntInput("Input requires", "Enter number of "+ItemJList.getSelectedValue().name+" you want to purchase:");
            		if(num>-1) {
		            	AddItemRequest req = new AddItemRequest(Client.mainUser, ItemJList.getSelectedValue().ID, num);
		            	ActionHandler handler = new ActionHandler();
		            	handler.handleRequest(req);
            		}
            	}
            }else if (source == ViewDetailsBtn) {
            	if(ItemJList.getSelectedValue() == null) {
            		GUIDriver.prompt("Error!", "Please select an item first!", 0);
            	}else {
            		ItemInfoRequest IReq = new ItemInfoRequest(Client.mainUser, ItemJList.getSelectedValue().ID);
            		ItemInfoHandler handler = new ItemInfoHandler();
            		handler.handleRequest(IReq);
            		GUIDriver.gotoPanel(new DetailsPanel(handler.fetchedItem, (JPanel) source.getParent(), true));
            	}
            }
        }
	}
}
