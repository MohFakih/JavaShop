package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart implements Serializable {
	public List<Item> items;
	public int ID;
	public Cart() {
		items = new ArrayList<Item>();
	}
	
	public void addItem(Item e) {
		items.add(e);
	}
	public double getTotalPrice() {
		double total = 0;
		for(Item i : items) {
			total += i.getTotal();
		}
		return total;
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
