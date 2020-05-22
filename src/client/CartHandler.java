package client;

import common.Cart;
import common.CartResponse;
import common.ItemResponse;
import common.Response;

public class CartHandler extends RequestResponseHandler {
	public Cart cart;
	@Override
	public void handleResponse(Response r) {
		if(r.resStatus == Response.status.OK) {
			CartResponse res = (CartResponse) r;
			cart = res.cart;
		}else if(r.resStatus == Response.status.AUTH){
			GUIDriver.prompt("Authentication Failure!", "Token does not match", 0);
		}else {
			GUIDriver.prompt("Failure!", "The server responded with the following failure: "+r.resMessage, 0);
		}
	}

}
