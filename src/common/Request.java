package common;

import java.util.List;
 
public abstract class Request implements java.io.Serializable{
	public User user;
	public static enum type {NONE, REGISTER, LOGIN, ITEMINFO, SEARCH, ADDITEM, ITEMCOUNT, CHECKOUT, HISTORY, RESTOCK, NEWITEM, REMOVE, CART, VERIFY};
	public abstract type getType();
	public abstract boolean verifyFields();
}
