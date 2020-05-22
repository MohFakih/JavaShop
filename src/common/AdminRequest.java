package common;

public class AdminRequest extends Request {

	public Item item;
	
	public AdminRequest(User u, Item i) {
		user = u;
		item = i;
	}
	
	@Override
	public type getType() {
		return Request.type.NEWITEM;
	}

	@Override
	public boolean verifyFields() {
		// TODO Auto-generated method stub
		return true;
	}

}
