package common;

import java.sql.Statement;

public abstract class ItemRequest extends Request{
	public int itemID;
	public ItemRequest(User u, int i) {
		itemID = i;
		user = u;
	}
}
