package server;

import java.sql.Statement;

import common.ActionResponse;
import common.AdminRequest;
import common.Request;
import common.Response;
import common.SearchRequest;

public class StockItemHandler implements RequestHandler {
	public AdminRequest req;
	public Statement stmt;
	public StockItemHandler(Request r, Statement s) throws Exception {
		req = (AdminRequest) r;
		stmt = s;
		if(!r.verifyFields()) {
			throw new Exception("Request Invalid");
		}
	}

	@Override
	public Response resolve() throws Exception {
		if(!DBHandler.verifyUserIsAdmin(req.user, stmt)) {
			return Response.notAdmin();
		}
		DBHandler.addItemToStock(req.user, stmt, req.item);
		return new ActionResponse(Response.status.OK, "Added item to stock", DBHandler.getNewToken(req.user, stmt));
	}

}
