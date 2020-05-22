package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.Encrypt;
import common.LoginRequest;
import common.ActionResponse;
import common.Request;
import common.Response;
import common.SignupRequest;
import common.User;

public class LoginHandler implements RequestHandler {
	LoginRequest req;
	Statement stmt;
	public LoginHandler(Request r, Statement s) throws Exception {
		req = (LoginRequest) r;
		if(req.verifyFields()) {
			stmt = s;
		}else {//This should not happen unless we really mess up some other part
			throw new Exception("Request invalid");
		}
	}
	@Override
	public Response resolve() {
		Response res = null;
		User user = new User();
		user.username = req.username;
		user.hashedPass = req.hashedPass;
		if(!DBHandler.verifyUserExists(user, stmt)) {
			return new Response(Response.status.FAIL, "Username does not exist");
		}
		
		if(!DBHandler.verifyUserPassword(user, stmt)) {
			return new Response(Response.status.AUTH, "Wrong Password!");
		}
		user = DBHandler.getUserInfo(user, stmt);
		if(user == null) {
			return new Response(Response.status.FATAL, "Our DB did an oopsie");
		}
		
		user = DBHandler.getNewToken(user, stmt);
		
		return new ActionResponse(Response.status.OK, "You are now logged in", user);
		
	}

}
