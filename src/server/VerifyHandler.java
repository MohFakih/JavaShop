package server;

import java.sql.ResultSet;
import java.sql.Statement;

import common.AdminRequest;
import common.Request;
import common.Response;
import common.User;
import common.VerifyRequest;

public class VerifyHandler implements RequestHandler {
	public VerifyRequest req;
	public Statement stmt;
	public VerifyHandler(Request r, Statement s) throws Exception {
		req = (VerifyRequest) r;
		stmt = s;
		if(!r.verifyFields()) {
			throw new Exception("Request Invalid");
		}
	}
	@Override
	public Response resolve() throws Exception {
		
		ResultSet rset = stmt.executeQuery("SELECT * FROM `pending_users` WHERE `USERNAME` = '"+req.username+"'");
		rset.next();
		if(rset.getString("verification").equals(req.verifCode)) {
			
	        stmt.executeUpdate("INSERT INTO USERS (`USERNAME`, `PASSWORD`, `name`, `email`, `token`, `purchases`, `isAdmin`) VALUES ('"+req.username+"', '"+ rset.getString("PASSWORD")+"', '"+ rset.getString("name") +"', '"+rset.getString("email")+"', NULL,0,0)");
	        stmt.executeUpdate("DELETE FROM `pending_users` WHERE `USERNAME` = '"+ req.username +"'");
	        stmt.execute("CREATE TABLE "+ req.username+"_history SELECT * FROM userHistoryTemplate");
	        stmt.execute("ALTER TABLE `"+req.username+"_history` ADD INDEX( `cartID`, `itemID`);");
	        stmt.execute("ALTER TABLE `"+req.username+"_history` ADD PRIMARY KEY( `cartID`, `itemID`);");
	        return new Response(Response.status.OK, "You have verified your email");
		}else {
			return Response.badToken();
		}
		
	}

}
