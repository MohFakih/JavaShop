package panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.ActionHandler;
import client.Client;
import client.GUIDriver;
import client.ItemInfoHandler;
import client.LoginHandler;
import common.AdminRequest;
import common.Encrypt;
import common.Item;
import common.ItemInfoRequest;
import common.LoginRequest;

public class AdminPanel extends JPanel {
	public static JButton restockBtn;
	public static NavButton BackBtn;
	public static JButton newItemBtn;
	public static JButton changePriceBtn;
	public AdminPanel() {
		initializeComponents();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		add(restockBtn);
		add(newItemBtn);
		add(changePriceBtn);
		add(BackBtn);
	}
	
	private void initializeComponents() {

		BackBtn     = new NavButton("< Back", MainMenuPanel.class);
		
		restockBtn = new JButton("Restock existing item");
		restockBtn.addActionListener(new Listener());
		
		changePriceBtn = new JButton("Change price of exisiting item");
		changePriceBtn.addActionListener(new Listener());
		
		newItemBtn = new JButton("Add new item");
		newItemBtn.addActionListener(new Listener());
		
	}
	
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == restockBtn)
            {
            	restock();
            }else if (source == changePriceBtn) {
            	price();
            }else if(source == newItemBtn) {
            	newItem();
            }
        }
	}
	
	void restock() {
		int itemID = GUIDriver.promptIntInput("itemID", "Please input the id of the item you wish to restock:");
    	if(itemID >-1) {
    		Item item;
    		ItemInfoRequest itemReq = new ItemInfoRequest(Client.mainUser, itemID);
    		ItemInfoHandler itemHandler = new ItemInfoHandler();
    		itemHandler.handleRequest(itemReq);
    		item = itemHandler.fetchedItem;
    		int newStock = GUIDriver.promptIntInput("DeltaStock", "Please input the number of new items we got in the shop:");
    		if(newStock>-1) {
    			item.stock += newStock;
    			AdminRequest req = new AdminRequest(Client.mainUser, item);
    			ActionHandler handler = new ActionHandler();
    			handler.handleRequest(req);
    		}
    	}
	}
	
	void price() {
		int itemID = GUIDriver.promptIntInput("itemID", "Please input the id of the item you wish to restock:");
    	if(itemID >-1) {
    		Item item;
    		ItemInfoRequest itemReq = new ItemInfoRequest(Client.mainUser, itemID);
    		ItemInfoHandler itemHandler = new ItemInfoHandler();
    		itemHandler.handleRequest(itemReq);
    		item = itemHandler.fetchedItem;
    		double newPrice = GUIDriver.promptDoubleInput("new price", "Please input the new price of the item in the shop:");
    		if(newPrice>0) {
    			item.price = newPrice;
    			AdminRequest req = new AdminRequest(Client.mainUser, item);
    			ActionHandler handler = new ActionHandler();
    			handler.handleRequest(req);
    		}
    	}
	}
	
	void newItem() {
		Item item = new Item();
		String name = GUIDriver.promptInput("Input needed", "Enter name of new item");
		String desc = GUIDriver.promptInput("Input needed", "Enter description of new item");
		String[] categories = {"HOUSEHOLD", "FOOD", "CLOTHES", "EDUCATION", "HYGIENE", "LUXURY", "MISC"};
		Item.category category = Item.category.valueOf((String) JOptionPane.showInputDialog(GUIDriver.frame, "Chose category", "Chose the category of the item", JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]));
		int    stock= GUIDriver.promptIntInput("DeltaStock", "Please input the number of new items we got in the shop:");
		double price= GUIDriver.promptDoubleInput("Price", "Please input the price of the item in the shop:");
		String path = GUIDriver.promptInput("Input needed", "Enter URL of the picture:");
		item.name = name;
		item.Description =desc;
		item.Category = category;
		item.stock = stock;
		item.price = price;
		item.pathToPic = path;
		AdminRequest req = new AdminRequest(Client.mainUser, item);
		ActionHandler handler = new ActionHandler();
		handler.handleRequest(req);
	}
}
