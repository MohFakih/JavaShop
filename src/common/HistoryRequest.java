package common;

public class HistoryRequest extends Request {
	
	public HistoryRequest(User u) {
		user = u;
	}
	
	@Override
	public type getType() {
		return Request.type.HISTORY;
	}

	@Override
	public boolean verifyFields() {
		return Verifier.verifyUser(user);
	}

}
