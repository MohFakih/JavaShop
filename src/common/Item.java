package common;
import java.io.*;
import java.util.Comparator;

public class Item implements Serializable{
	public 	static enum 	category{HOUSEHOLD, FOOD, CLOTHES, EDUCATION, HYGIENE, LUXURY, MISC};
	public 	String 			name;
	public 	category 		Category;
	public 	double 			price; 
	public  int				ID;
	public  String			Description;
	public  int				stock;
	public  String 			pathToPic;
	public  int 			count;
	public Item (String n, category cat, double Price, int id) {
		name = n;
		Category = cat;
		price = Price;
		ID = id;
	}
	public Item() {
	}
	public void setCount(int newCount) {
		count = newCount;
	}
	public double getTotal() {
		return count*price;
	}
}

class singlePriceItemComparator implements Comparator<Item> {

	@Override
	public int compare(Item o1, Item o2) {
			return o1.price > o2.price ? 1 : -1;
	}
}

class totalPriceItemComparator implements Comparator<Item>{
	@Override
	public int compare(Item o1, Item o2) {
			return o1.getTotal() > o2.getTotal() ? 1 : -1;
	}
}

class alphabeticItemComparator implements Comparator<Item>{

	@Override
	public int compare(Item o1, Item o2) {
		return o1.name.compareTo(o2.name);
	}
	
	
	
}
