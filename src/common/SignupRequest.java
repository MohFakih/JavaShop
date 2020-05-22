package common;

import java.util.regex.*;

public class SignupRequest extends Request {
	public type getType() { 
		return type.REGISTER;
	}

	public boolean verifyFields() {
		return Verifier.verifyUser(user);
	}
}
