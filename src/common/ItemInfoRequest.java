package common;

public class ItemInfoRequest extends ItemRequest {

	public ItemInfoRequest(User u, int i) {
		super(u, i);
	}

	@Override
	public type getType() {
		return Request.type.ITEMINFO;
	}

	@Override
	public boolean verifyFields() {
		// TODO Auto-generated method stub
		return true;
	}

}
