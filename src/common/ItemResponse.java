package common;

public class ItemResponse extends Response{
	public Item item;
	public ItemResponse(status S, String message, Item e) {
		super(S, message);
		item = e;
	}
	
}
