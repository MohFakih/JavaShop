package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResponse extends Response{
	public User user;
	public List<Item> items;
	public SearchResponse(List<Item> its, User us) {
		super(Response.status.OK, "Query success!");
		user = us;
		items = its;
	}
	public void sortBySinglePrice() {
		Collections.sort(items, new singlePriceItemComparator());
	}
	public void sortByTotalPrice() {
		Collections.sort(items, new totalPriceItemComparator());
	}
	public void sortAlphabetical() {
		Collections.sort(items, new alphabeticItemComparator());
	}
}
