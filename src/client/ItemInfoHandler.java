package client;

import common.ActionResponse;
import common.Item;
import common.ItemResponse;
import common.Response;
import panels.MainMenuPanel;

public class ItemInfoHandler extends RequestResponseHandler {
	public Item fetchedItem;
	@Override
	public void handleResponse(Response r) {
		if(r.resStatus == Response.status.OK) {
			ItemResponse res = (ItemResponse) r;
			fetchedItem = res.item;
		}else if(r.resStatus == Response.status.AUTH){
			GUIDriver.prompt("Authentication Failure!", "Token does not match", 0);
		}else {
			GUIDriver.prompt("Failure!", "The server responded with the following failure: "+r.resMessage, 0);
		}
	}

}
