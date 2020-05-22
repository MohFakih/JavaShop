package server;

import java.sql.Statement;

import common.ActionResponse;
import common.AddItemRequest;
import common.Encrypt;
import common.Request;
import common.Response;
import common.User;
import common.Verifier;

public class AddItemHandler implements RequestHandler {
	AddItemRequest req;
	Statement 	stmt;
	public AddItemHandler(Request r, Statement s) throws Exception {
		req = (AddItemRequest) r;
		stmt = s;
		if(!Verifier.verifyUser(req.user)) {
			throw new Exception("Oopsie");
		}
	}
	
	@Override
	public Response resolve() throws Exception {
		Response check = DBHandler.verifyUserAndToken(req.user, stmt);
		if(check != null) {return check;}
		if(DBHandler.addItemToActiveCart(req.user, stmt, req.itemID, req.count)) {
			req.user = DBHandler.getNewToken(req.user, stmt);
			return new ActionResponse(Response.status.OK, "Added item successfuly to cart!", req.user);
		}else {
			return Response.DBFail();
		}
	}

}
