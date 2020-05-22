package client;

import common.Response;
import panels.LoginMenuPanel;

public class VerifyHandler extends RequestResponseHandler {

	@Override
	public void handleResponse(Response res) {
		if(res.resStatus == Response.status.OK) {
			GUIDriver.prompt("Success!", "You have verified your account!");
			GUIDriver.gotoPanel(new LoginMenuPanel());
		}else {
			GUIDriver.prompt("Failure!", res.resMessage, 0);
		}
	}

}
