package panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.Client;
import client.GUIDriver;
import client.SearchHandler;
import common.SearchRequest;

public class SearchPanel extends JPanel {
	public static Entry SearchEntry;
	public static JButton SearchBtn;
	public static JButton BackBtn;
	public SearchPanel() {
		
		initializeComponents();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		add(SearchEntry);
		add(SearchBtn);
		add(BackBtn);
	}
	
	private void initializeComponents() {
		SearchEntry = new Entry("Enter your search query: ");
		SearchBtn = new JButton("Search");
		SearchBtn.addActionListener(new Listener());
		BackBtn = new JButton("< Back");
		BackBtn.addActionListener(new Listener());
	}
	private class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == SearchBtn)
            {
            	SearchRequest req = new SearchRequest();
            	req.query = SearchEntry.Field.getText();
            	req.user = Client.mainUser;
            	SearchHandler handler = new SearchHandler();
            	handler.handleRequest(req);
            }else if(source == BackBtn) {
            	GUIDriver.gotoPanel(new MainMenuPanel());
            }
        }
	}
}
