package server;

import java.sql.Statement;

import common.Request;

public class HandlerFactory {
	public static RequestHandler getRequestHandler(Request req, Statement stmt) throws Exception {
		switch(req.getType()) {
		case ADDITEM:
				return new AddItemHandler(req, stmt);
		case CHECKOUT:
				return new CheckoutHandler(req, stmt);
		case HISTORY:
				return new HistoryHandler(req, stmt);
		case ITEMCOUNT:
				break;
		case ITEMINFO:
				return new ItemInfoHandler(req, stmt);
		case LOGIN:
				return new LoginHandler(req, stmt);
		case NEWITEM:
				return new StockItemHandler(req, stmt);
		case VERIFY:
				return new VerifyHandler(req, stmt);
		case NONE:
				return null;
		case REGISTER:
				return new SignupHandler(req, stmt);
		case RESTOCK:
				return new StockItemHandler(req, stmt);
		case SEARCH:
				return new SearchHandler(req, stmt);
		case REMOVE:
				return new RemoveItemHandler(req, stmt);
		case CART:
				return new CartHandler(req, stmt);
		default:
			return null;
		}
		return null;
	}

}
