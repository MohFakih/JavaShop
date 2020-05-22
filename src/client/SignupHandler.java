package client;

import common.Request;
import common.Response;

public class SignupHandler extends RequestResponseHandler{
	public void handleResponse(Response res) {
		if(res.resStatus == Response.status.OK) {
			GUIDriver.prompt("Success!", "You have been sent a verification email.");
			GUIDriver.gotoPanel(new panels.LoginMenuPanel());
		}else {
			GUIDriver.prompt("Failure!", "The server responded with the following failure: "+res.resMessage, 0);
		}
	}
}