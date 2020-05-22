package common;

public class User implements java.io.Serializable{
	public String 	username;
	public String 	email; 
	public String 	name;
	public String 	token;
	public String 	hashedPass;
	public int		purchaseHistoryCount;
	public userType type;
	public static enum userType {CUSTOMER, ADMIN, MANAGER};
	public User() {
		this("PLACEHOLDER");
	}
	public User(String user) {
		this(user, "PLACEHOLDER", "PLACEHOLDER");
	}
	
	public User(String user, String nam, String ema) {
		username = user;
		name = nam;
		email = ema;
		type = userType.CUSTOMER;
	}
	
	public void setType(userType newType) {
		type = newType;
	}
	
	public void setToken(String newToken) {
		token = newToken;
	}
}
