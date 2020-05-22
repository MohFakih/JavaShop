package server;

import java.sql.Statement;

import common.HistoryRequest;
import common.Item;
import common.ItemRequest;
import common.ItemResponse;
import common.Request;
import common.Response;
import common.Verifier;

public class ItemInfoHandler implements RequestHandler {
	ItemRequest req;
	Statement 	stmt;
	public ItemInfoHandler(Request r, Statement s) throws Exception {
		req = (ItemRequest) r;
		stmt = s;
		if(!Verifier.verifyUser(req.user)) {
			throw new Exception("Oopsie");
		}
	}
	
	@Override
	public Response resolve() throws Exception {
		Response check = DBHandler.verifyUserAndToken(req.user, stmt);
		if(check != null) {return check;}
		Item i = DBHandler.getItemInfo(req.itemID, stmt);
		if(i == null) {
			return Response.DBFail();
		}
		return new ItemResponse(Response.status.OK, "Serving item now", i);
	}

}
