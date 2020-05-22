package server;

import java.sql.Statement;

import common.Cart;
import common.CartRequest;
import common.Request;
import common.Response;
import common.CartResponse;
import common.Verifier;

public class CartHandler implements RequestHandler {
	CartRequest req;
	Statement stmt;
	
	public CartHandler(Request r, Statement s) throws Exception {
		req = (CartRequest) r;
		stmt = s;
		if(!Verifier.verifyUser(req.user)) {
			throw new Exception("Oopsie");
		}
	}
	
	@Override
	public Response resolve() throws Exception {
		Response check = DBHandler.verifyUserAndToken(req.user, stmt);
		if(check != null) {return check;}
		Cart c = DBHandler.getActiveCart(req.user, stmt);
		CartResponse res = new CartResponse(Response.status.OK, "Serving cart now", c);
		return res;
	}

}
