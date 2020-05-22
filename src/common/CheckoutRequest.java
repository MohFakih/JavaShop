package common;

public class CheckoutRequest extends Request {
	
	public CheckoutRequest(User mainUser) {
		user = mainUser;
	}

	@Override
	public type getType() {
		return Request.type.CHECKOUT;
	}

	@Override
	public boolean verifyFields() {
		// TODO Auto-generated method stub
		return true;
	}

}
