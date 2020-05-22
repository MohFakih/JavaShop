package common;

public class CartRequest extends Request {
	
	public CartRequest(User u) {
		user = u;
	}
	
	@Override
	public type getType() {
		return Request.type.CART;
	}

	@Override
	public boolean verifyFields() {
		// TODO Auto-generated method stub
		return true;
	}

}
