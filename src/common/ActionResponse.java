package common;

public class ActionResponse extends Response{
	public User userSent;
	public ActionResponse(status S, String message, User user) {
		super(S, message);
		userSent = user;
	}
	
}
