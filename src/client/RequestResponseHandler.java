package client;

import common.Request;
import common.Response;
import panels.LoginPanel;

public abstract class RequestResponseHandler {
	public void handleRequest(Request req) {
		if(!req.verifyFields()) {GUIDriver.prompt("Field error", "Please check that you filled all fields, and that you didn't exceed the maximum number of characters per field.", 0);return;}
		if(!Client.sendRequest(req)) {return;}
		Response res = Client.getResponse();
		if(res == null) {return;}
		handleResponse(res);
	};
	public abstract void handleResponse(Response res) ;
}
