package client;

import common.ActionResponse;
import common.Response;
import panels.MainMenuPanel;

public class LoginHandler extends RequestResponseHandler {
	public void handleResponse(Response r) {
		if(r.resStatus == Response.status.OK) {
			ActionResponse res = (ActionResponse) r;
			GUIDriver.prompt("Success!", "You have been logged in");
			Client.mainUser = res.userSent;
			GUIDriver.gotoPanel(new MainMenuPanel());
		}else if(r.resStatus == Response.status.AUTH){
			GUIDriver.prompt("Authentication Failure!", "Wrong password!", 0);
		}else {
			GUIDriver.prompt("Failure!", "The server responded with the following failure: "+r.resMessage, 0);
		}
	}
}
