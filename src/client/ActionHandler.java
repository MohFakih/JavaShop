package client;

import common.ActionResponse;
import common.Response;
import panels.MainMenuPanel;

public class ActionHandler extends RequestResponseHandler {
	public boolean success;
	@Override
	public void handleResponse(Response r) {
		if(r.resStatus == Response.status.OK) {
			ActionResponse res = (ActionResponse) r;
			GUIDriver.prompt("Success!", res.resMessage);
			Client.mainUser = res.userSent;
			success = true;
		}else if(r.resStatus == Response.status.AUTH){
			GUIDriver.prompt("Authentication Failure!", "Bad Token!", 0);
			success = false;
		}else {
			GUIDriver.prompt("Failure!", "The server responded with the following failure: "+r.resMessage, 0);
			success = false;
		}
	}

}
