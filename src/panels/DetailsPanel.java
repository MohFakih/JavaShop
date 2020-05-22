package panels;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ActionHandler;
import client.Client;
import client.GUIDriver;
import common.AddItemRequest;
import common.Item;

public class DetailsPanel extends JPanel {
	public static int IMG_WIDTH = 200;
	public static int IMG_HEIGHT= 200;
	public static JButton BackBtn;
	public static JButton AddItemToCart;
	public static JLabel  ImageLabel;
	public static JLabel  NameLabel;
	public static JLabel  DescriptionLabel;
	public static JLabel  priceLabel;
	public static JLabel  stockLabel;
	public static JLabel  categoryLabel;
	public static BufferedImage ProductImage;
	public static Item Product;
	public static JPanel previousPanel;
	public DetailsPanel(Item i, JPanel previous, boolean canAddItemToCart) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		Product = i;
		previousPanel = previous;
		try {
			URL imgURL = new URL(i.pathToPic);
			ProductImage = ImageIO.read(imgURL);
		} catch (Exception e) {
			e.printStackTrace();
			GUIDriver.prompt("Error!", "We have had a problem in fetchin the image, please check the logs!", 0);
		}
		
		initializeComponents();
		
		add(BackBtn);
		if(canAddItemToCart) {
			add(AddItemToCart);
		}
		try {
		add(ImageLabel);
		}catch(Exception e) {
			e.printStackTrace();
		}
		add(NameLabel);
		add(priceLabel);
		add(categoryLabel);
		add(stockLabel);
		add(DescriptionLabel);
	}
	private void initializeComponents() {
		BackBtn = new JButton("< Back");
		BackBtn.addActionListener(new Listener());
		
		AddItemToCart = new JButton("Add to cart");
		AddItemToCart.addActionListener(new Listener());
		try {
		ImageLabel = new JLabel(new ImageIcon(ProductImage.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_FAST)));
		}catch(Exception e) {
			e.printStackTrace();
		}
		NameLabel = new JLabel("Product name: "+Product.name);
		DescriptionLabel = new JLabel("Product Description: " + Product.Description);
		priceLabel = new JLabel("Prodcut price per unit: " + Product.price);
		stockLabel = new JLabel("Stock: "+Product.stock);
		categoryLabel = new JLabel("Category: "+Product.Category.toString());
	}
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == BackBtn)
            {
                GUIDriver.gotoPanel(previousPanel);
            }
            else if(source == AddItemToCart) {
        		int num = GUIDriver.promptIntInput("Input requires", "Enter number of "+Product.name+" you want to purchase:");
        		if(num>-1) {
	            	AddItemRequest req = new AddItemRequest(Client.mainUser, Product.ID, num);
	            	ActionHandler handler = new ActionHandler();
	            	handler.handleRequest(req);
        		}
            }
        }
	}
}
