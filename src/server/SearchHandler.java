package server;

import java.sql.Statement;

import common.Encrypt;
import common.Request;
import common.Response;
import common.SearchRequest;
import common.SearchResponse;
import common.User;

public class SearchHandler implements RequestHandler {
	SearchRequest req;
	Statement stmt;
	public SearchHandler(Request r, Statement s) throws Exception {
		req = (SearchRequest) r;
		stmt = s;
		if(!r.verifyFields()) {
			throw new Exception("Request Invalid");
		}
	}

	@Override
	public Response resolve() throws Exception {
		
		Response check = DBHandler.verifyUserAndToken(req.user, stmt);
		if(check != null) {return check;}
		req.user = DBHandler.getNewToken(req.user, stmt);
		return new SearchResponse(DBHandler.searchItems(req.query, stmt), req.user);
	}

}
