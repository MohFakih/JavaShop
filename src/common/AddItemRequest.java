package common;

public class AddItemRequest extends ItemRequest {
	public int count;
	public AddItemRequest(User u, int i) {
		super(u, i);
	}

	public AddItemRequest(User mainUser, int id, int num) {
		this(mainUser, id);
		count = num;
	}

	@Override
	public type getType() {
		return Request.type.ADDITEM;
	}

	@Override
	public boolean verifyFields() {
		return Verifier.verifyUser(user);
	}

}
