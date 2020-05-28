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
		return verifyNameField(user.name) && verifyUsernameField(user.username) && verifyPasswordField(user.hashedPass);
	}
	public static boolean verifyPathToPic(String path) {
		try {
			Pattern r = Pattern.compile("/([a-z\\-_0-9\\/\\:\\.]*\\.(jpg|jpeg|png|gif))/i"); //     https://stackoverflow.com/questions/4098415/use-regex-to-get-image-url-in-html-js
			Matcher m = r.matcher(path);
			return m.matches();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean verifyQuery(String query) {
		// No bounds yet on query string
		return true;
	}
}
