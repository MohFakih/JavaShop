package server;

import java.sql.Statement;
import java.util.List;

import common.ActionResponse;
import common.Cart;
import common.HistoryRequest;
import common.HistoryResponse;
import common.Request;
import common.Response;
import common.Verifier;

public class HistoryHandler implements RequestHandler {
	HistoryRequest req;
	Statement 	stmt;
	public HistoryHandler(Request r, Statement s) throws Exception {
		req = (HistoryRequest) r;
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
		List<Cart> hist = DBHandler.getHistory(req.user, stmt);
		if (hist == null) {
			return Response.DBFail();
		}
		return new HistoryResponse(Response.status.OK, "OK", hist);
	}

}
