package common;

public class SearchRequest extends Request{
	public String query;
	@Override
	public type getType() {
		return Request.type.SEARCH;
	}

	@Override
	public boolean verifyFields() {
		return 		Verifier.verifyUser(user) 
				&& 	Verifier.verifyQuery(query);
	}

}
