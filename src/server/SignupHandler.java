package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import com.sun.javafx.fxml.builder.URLBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import client.GUIDriver;
import common.Encrypt;
import common.Request;
import common.Response;
import common.SignupRequest;
import sun.net.www.http.*;
public class SignupHandler implements RequestHandler {
	SignupRequest req;
	Statement stmt;
	public SignupHandler(Request r, Statement s) throws Exception {
		req = (SignupRequest) r;
		stmt = s;
		if(!req.verifyFields()) { //This should not happen unless we really mess up some other part
			throw new Exception("Request invalid");
		}
	}
	
	@Override
	public Response resolve(){
		try {
	        if(DBHandler.verifyUserExists(req.user, stmt)) {
	        	return new Response(Response.status.FAIL, "Username is already taken!");
	        }
	        
	        if(DBHandler.verifyEmailExists(req.user, stmt)) {
	        	return new Response(Response.status.FAIL, "Email is already taken!");
	        }
	        String code = Encrypt.generateToken();
	        
	        sendCodeByEmail(code, req.user.email);
	        
	        String sql = "REPLACE INTO `pending_users` (`USERNAME`, `PASSWORD`, `name`, `email`, `verification`) VALUES ('"+req.user.username+"', '"+req.user.hashedPass+"', '"+req.user.name+"', '"+req.user.email+"', '"+code+"');";
	        stmt.execute(sql);
	        return new Response(Response.status.OK, "You are now registered!");
	        
		}catch(Exception e) {
			e.printStackTrace();
			return Response.DBFail();
		}
	}
	public boolean sendCodeByEmail(String code, String email) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("https://coursis.org/be/EECE350.php");

			// Request parameters and other properties.
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("code", code));
			httppost.setEntity(new UrlEncodedFormEntity(params));

			//Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = ((CloseableHttpResponse) response).getEntity();
			if (entity != null) {
			    try (InputStream instream = entity.getContent()) {
			        return true;
			    }
			}
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
