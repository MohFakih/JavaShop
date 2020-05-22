package common;

import java.util.List;

public class HistoryResponse extends Response {
	public List<Cart> history;
	public HistoryResponse(status S, String message, List<Cart> carts) {
		super(S, message);
		history = carts;
	}

}
