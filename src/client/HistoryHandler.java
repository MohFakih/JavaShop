package client;

import java.util.List;

import common.Cart;
import common.HistoryResponse;
import common.Response;

public class HistoryHandler extends RequestResponseHandler {

	public List<Cart>	carts;
	@Override
	public void handleResponse(Response r) {
		if(r.resStatus == Response.status.OK) {
			HistoryResponse res = (HistoryResponse) r;
			carts = res.history;
		}else {
			carts = null;
			GUIDriver.prompt("Error!", r.resMessage, 0);
		}
	}

}
