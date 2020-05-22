package common;

public class RemoveItemRequest extends ItemRequest {

	public RemoveItemRequest(User u, int i) {
		super(u, i);
	}

	@Override
	public type getType() {
		return Request.type.REMOVE;
	}

	@Override
	public boolean verifyFields() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
