package common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifier {
	public static boolean verifyEmailField(String email) {
		try {
			Pattern r = Pattern.compile("^(.+)@(.+)$");
			Matcher m = r.matcher(email);
			return m.matches();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean verifyNameField(String name) {
		return name.length() <= 30;
	}
	public static boolean verifyUsernameField(String user) {
		return user.length()<= 50;
	}
	public static boolean verifyPasswordField(String pass) {
		return pass.length() <= 200;
	}
	public static boolean verifyUser(User user) {
		// TODO Auto-generated method stub
		return true;
	}
	public static boolean verifyQuery(String query) {
		// TODO Auto-generated method stub
		return true;
	}
}
