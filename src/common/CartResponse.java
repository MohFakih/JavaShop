package common;

public class CartResponse extends Response {
	public Cart cart;
	public CartResponse(status S, String message, Cart c) {
		super(S, message);
		cart = c;
	}

}
