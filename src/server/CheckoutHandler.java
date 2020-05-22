package server;

import java.sql.Statement;

import common.ActionResponse;
import common.AddItemRequest;
import common.Cart;
import common.CheckoutRequest;
import common.Encrypt;
import common.Item;
import common.Request;
import common.Response;
import common.Verifier;

public class CheckoutHandler implements RequestHandler {
	CheckoutRequest req;
	Statement 	stmt;
	public CheckoutHandler(Request r, Statement s) throws Exception {
		req = (CheckoutRequest) r;
		stmt = s;
		if(!Verifier.verifyUser(req.user)) {
			throw new Exception("Oopsie");
		}
	}
	
	@Override
	public Response resolve() throws Exception {
		Response check = DBHandler.verifyUserAndToken(req.user, stmt);
		if(check != null) {return check;}
		req.user = DBHandler.getUserInfo(req.user, stmt);
		if(DBHandler.moveCartToHistroy(req.user, stmt)) {
			return new ActionResponse(Response.status.OK, "You have checked out!", DBHandler.getNewToken(req.user, stmt));
		}else{
			return Response.DBFail();
		}
	}

}
