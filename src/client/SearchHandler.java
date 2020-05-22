package client;

import common.Item;
import common.Response;
import common.SearchResponse;
import panels.SearchResultPanel;

public class SearchHandler extends RequestResponseHandler{
	public void handleResponse(Response r) {
		if(r.resStatus == Response.status.OK) {
			SearchResponse res = (SearchResponse) r;
			Client.mainUser = res.user;
			GUIDriver.gotoPanel(new SearchResultPanel(res.items));
		}else {
			GUIDriver.prompt("Error!", "The server could not carry out the search: " + r.resMessage, 0);
		}
	}
}