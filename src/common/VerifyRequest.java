package common;

public class VerifyRequest extends Request {
	public String username;
	public String verifCode;
	
	public VerifyRequest(String user, String code) {
		username = user;
		verifCode = code;
	}
	
	@Override
	public type getType() {
		return Request.type.VERIFY;
	}

	@Override
	public boolean verifyFields() {
		// TODO Auto-generated method stub
		return true;
	}

}
