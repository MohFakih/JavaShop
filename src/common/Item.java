package common;

import java.io.*;
import java.util.Comparator;



public class Item implements Serializable {
	public static enum category {
		HOUSEHOLD, FOOD, CLOTHES, EDUCATION, HYGIENE, LUXURY, MISC
	}

	private String name;
	private category itemCategory;
	private double price;
	private int ID;
	private String description;
	private int stock;
	private String pathToPic;
	private int count;
	
	
	public Item() {
		name = "NULL-ITEM";
		itemCategory = category.MISC;
		price = 0.0;
		ID = 0;
		description = "if a shop customer sees this, we messed up";
		stock = -1;
		pathToPic = "https://i.imgur.com/iWKad22.jpg";
		count = 0;
	}

	public Item(String n, category cat, double Price, int id) {
		name = n;
		itemCategory = cat;
		price = Price;
		ID = id;
	}

	public category getCategory() {
		return itemCategory;
	}

	public int getCount() {
		return count;
	}

	public String getDescription() {
		return description;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getPathToPic() {
		return pathToPic;
	};

	public double getPrice() {
		return price;
	}
	public int getStock() {
		return stock;
	}
	public double getTotal() {
		return count * price;
	}
	public void setCategory(category category) {
		itemCategory = category;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setDescription(String newDescription) {
		description = newDescription;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setPathToPic(String pathToPic) {
		if(Verifier.verifyPathToPic(pathToPic)) {
			this.pathToPic = pathToPic;
		}
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getCategoryString() {
		return itemCategory.toString();
	}
	public String toString() {
		return getName()+": ["+getStock()+" units in stock]" + ", ["+getPrice()+"$ per unit], [Category: "+getCategoryString()+"]";
	}
}

// Unused comparators

class alphabeticItemComparator implements Comparator<Item> {

	@Override
	public int compare(Item o1, Item o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
class singlePriceItemComparator implements Comparator<Item> {

	@Override
	public int compare(Item o1, Item o2) {
		return o1.getPrice() > o2.getPrice() ? 1 : -1;
	}
}

class totalPriceItemComparator implements Comparator<Item> {
	@Override
	public int compare(Item o1, Item o2) {
		return o1.getTotal() > o2.getTotal() ? 1 : -1;
	}
}
