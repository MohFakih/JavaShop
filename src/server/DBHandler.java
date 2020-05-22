package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Cart;
import common.Encrypt;
import common.Item;
import common.Response;
import common.User;

public class DBHandler {
	public static boolean verifyUserToken(User user, Statement stmt) {
		try {
			ResultSet rset = stmt.executeQuery("SELECT * FROM USERS WHERE USERNAME = '"+user.username+"'");
			rset.next();
			return rset.getString("token").equals(user.token);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean verifyEmailExists(User user, Statement stmt) {
		ResultSet rset;
		try {
			rset = stmt.executeQuery("SELECT * FROM USERS WHERE email = '"+ user.email +"'");
			return rset.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean verifyItemWithIDExists(Statement stmt, int itemID) {
		ResultSet rset;
		try {
			rset = stmt.executeQuery("SELECT * FROM shop WHERE ID = '"+itemID+"'");
			return rset.next();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean verifyUserIsAdmin(User user, Statement stmt) {
		user = getUserInfo(user, stmt);
		return user.type == User.userType.ADMIN;
	}
	public static boolean addItemToStock(User user, Statement stmt, Item i) {
		if(!verifyUserIsAdmin(user, stmt)) {
			return false;
		}else {
			String id = "NULL";
			if(i.ID != 0) {
				id = ""+i.ID;
			}
			String sql = "REPLACE INTO `shop` (`ITEMID`, `name`, `description`, `price`, `stock`, `category`, `pathToPic`) VALUES ("+id+", '"+i.name+"', '"+i.Description+"', '"+i.price+"', '"+i.stock+"', '"+i.Category.toString()+"', '"+i.pathToPic+"');";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
	public static boolean addItemToActiveCart(User user, Statement stmt, int itemID, int count) {
		return addItemToCart(user, stmt, itemID, 0, count, -1);
	}
	public static boolean addItemToCart(User user, Statement stmt, int itemID, int cartID, int count, double price) {
		String sql = "REPLACE INTO `"+user.username+"_history` (`cartID`, `itemID`, `count`, `priceAtTimeOfPurchase`) VALUES ('"+cartID+"', '"+itemID+"', '"+count+"', '"+price+"')";
		try {
			stmt.executeUpdate(sql);
			return  true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static Response verifyUserAndToken(User user, Statement stmt) {
		if(!DBHandler.verifyUserExists(user, stmt)) {
			return Response.unknownUser();
		}
		if(!DBHandler.verifyUserToken(user, stmt)) {
			return Response.badToken();
		}
		return null;
	}
	public static boolean isItemInActiveCart(User user, Statement stmt, int itemID) {
		String sql = "SELECT * FROM '"+user.username+"_history' WHERE cartID = '0' AND itemID = '"+itemID+"'";
		ResultSet rset;
		try {
			rset = stmt.executeQuery(sql);
			return rset.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean removeItemFromActiveCart(User user, Statement stmt, int itemID) {
		String sql = "DELETE FROM '"+user.username+"_history' WHERE cartID = '0' AND itemID = '"+itemID+"'";
		try {
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static Item 		 getItemInfo(int itemID, Statement stmt) {
		ResultSet rset;
		try {
			rset = stmt.executeQuery("SELECT * FROM shop WHERE ITEMID = '"+itemID+"'");
			rset.next();
			Item out = new Item(rset.getString("name"), Item.category.valueOf(rset.getString("category")), rset.getDouble("price"), rset.getInt("ITEMID"));
			out.pathToPic = rset.getString("pathToPic");
			out.stock = rset.getInt("stock");
			out.Description = rset.getString("Description");
			return out;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Cart getActiveCart(User user, Statement stmt){
		ResultSet rset;
		try {
			Cart c = new Cart();
			rset = stmt.executeQuery("SELECT * FROM `"+user.username+"_history` WHERE cartID = '0'");
			Map<Integer, Integer> itemCountMap = new HashMap<Integer, Integer>();
			while(rset.next()) {
				itemCountMap.put(rset.getInt("itemID"), rset.getInt("count"));
			}
			for(Map.Entry<Integer, Integer> entry : itemCountMap.entrySet()) {
				Item e = getItemInfo(entry.getKey(), stmt);
				e.setCount(entry.getValue());
				c.addItem(e);
			}
			return c;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static boolean moveCartToHistroy(User user, Statement stmt) {
		user = getUserInfo(user, stmt);
		for (Item item : getActiveCart(user, stmt).items) {
			if(!addItemToCart(user, stmt, item.ID, user.purchaseHistoryCount+1, item.count, getItemInfo(item.ID, stmt).price)) {
				return false;
			}
			String sql = "UPDATE `shop` SET `stock` = `stock`-"+item.count+" WHERE `itemID` = "+item.ID+";";
			try {
				stmt.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		String nuke = "DELETE FROM `"+user.username+"_history` WHERE `"+user.username+"_history`.`cartID` = 0";
		try {
			stmt.executeUpdate(nuke);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		String increment = "UPDATE `users` SET `purchases` = `purchases`+1 WHERE `users`.`USERNAME` = '"+user.username+"'";
		try {
			stmt.executeUpdate(increment);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static List<Cart> getHistory(User user, Statement stmt){
		ResultSet rset;
		ArrayList<Cart> tempCartList = new ArrayList<Cart>();
		ArrayList<Cart> out = new ArrayList<Cart>();
		try {
			rset = stmt.executeQuery("SELECT * FROM `"+user.username+"_history` WHERE cartID != '0'");
			while(rset.next()) {
				Item i = new Item("PLACEHOLDER", Item.category.MISC, -1, rset.getInt("itemID"));
				i.count = rset.getInt("count");
				i.price = rset.getDouble("priceAtTimeOfPurchase");
				int cartIndex = rset.getInt("cartID");
				while(cartIndex > tempCartList.size()) {
					tempCartList.add(new Cart());
				}
				cartIndex -= 1;
				Cart c =tempCartList.get(cartIndex);
				c.addItem(i);
				tempCartList.remove(cartIndex);
				tempCartList.add(cartIndex, c);
			}
			
			for(Cart c : tempCartList) {
				Cart newC = new Cart();
				for(Item e : c.items) {
					Item newE = getItemInfo(e.ID,stmt);
					newE.price = e.price;
					newE.count = e.count;
					newC.addItem(newE);
				}
				out.add(newC);
			}
			return out;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static List<Item> searchItems(String query, Statement stmt){
		ResultSet rset;
		try {
			rset = stmt.executeQuery("SELECT * FROM shop WHERE name LIKE '%"+query+"%'");
			List<Item> out = new ArrayList<Item>();
			while(rset.next()) {
				Item o =new Item(rset.getString("name"), Item.category.valueOf(rset.getString("category")), rset.getDouble("price"), rset.getInt("ITEMID")); 
				o.pathToPic = rset.getString("pathToPic");
				o.stock = rset.getInt("stock");
				o.Description = rset.getString("Description");
				out.add(o);
			}
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public static User getNewToken(User user, Statement stmt) {
		user.setToken(Encrypt.generateToken());
		String sql = "UPDATE `USERS` SET `token` = '"+user.token+"' WHERE `USERS`.`USERNAME` = '"+user.username+"';";
		try {
			if(stmt.executeUpdate(sql) == 1) {
				return user;
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static User getUserInfo(User user, Statement stmt) {
		ResultSet rset;
		try {
			rset = stmt.executeQuery("SELECT * FROM USERS WHERE USERNAME = '"+ user.username +"'");
			rset.next();
			User u = new User(rset.getString("USERNAME"), rset.getString("name"), rset.getString("email"));
			u.purchaseHistoryCount = rset.getInt("purchases");
			u.token = rset.getString("token");
			u.setType(rset.getBoolean("isAdmin")?User.userType.ADMIN:User.userType.CUSTOMER);
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static boolean verifyUserExists(User user, Statement stmt) {
		String sql = "SELECT token FROM USERS WHERE USERNAME = '"+user.username+"'";
		try {
			ResultSet rset = stmt.executeQuery(sql);
	        return rset.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean verifyUserPassword(User user, Statement stmt) {
		try {
			ResultSet rset = stmt.executeQuery("SELECT * FROM USERS WHERE USERNAME = '"+ user.username +"'");
			rset.next();
			return user.hashedPass.equals(rset.getString("PASSWORD"));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
