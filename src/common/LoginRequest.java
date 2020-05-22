package common;

public class LoginRequest extends Request {
	public String username;
	public String hashedPass;
	
	public type getType() {
		return type.LOGIN;
	}
	
	public boolean verifyFields() {
		return 		Verifier.verifyUsernameField(username)
				&& 	Verifier.verifyPasswordField(hashedPass);
	}
	
}
