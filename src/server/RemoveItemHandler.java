package server;

import java.sql.Statement;

import common.ActionResponse;
import common.AddItemRequest;
import common.RemoveItemRequest;
import common.Request;
import common.Response;
import common.Verifier;

public class RemoveItemHandler implements RequestHandler {

	RemoveItemRequest req;
	Statement 	stmt;
	public RemoveItemHandler(Request r, Statement s) throws Exception {
		req = (RemoveItemRequest) r;
		stmt = s;
		if(!Verifier.verifyUser(req.user)) {
			throw new Exception("Oopsie");
		}
	}
	
	@Override
	public Response resolve() throws Exception {
		Response check = DBHandler.verifyUserAndToken(req.user, stmt);
		if(check != null) {return check;}
		
		if(!DBHandler.verifyItemWithIDExists(stmt, req.itemID)) {
			return Response.unkownItem();
		}
		
		if(!DBHandler.isItemInActiveCart(req.user, stmt, req.itemID)) {
			return Response.unkownItem();
		}
		
		if(DBHandler.removeItemFromActiveCart(req.user, stmt, req.itemID)) {
			req.user = DBHandler.getNewToken(req.user, stmt);
			return new ActionResponse(Response.status.OK, "Added item removed from cart!", req.user);
		}else {
			return Response.DBFail();
		}
	}

}
