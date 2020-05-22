package common;

public class Response implements java.io.Serializable{
	public static enum status{OK, FAIL, FATAL, TERMINATE, AUTH};
	public Response(status S, String message) {
		resStatus = S;
		resMessage = message;
	}
	public status resStatus;
	public String resMessage;
	public static Response unknownUser() {
		return new Response(Response.status.AUTH, "Username does not exist, please login again");
	}
	public static Response badToken() {
		return new Response(Response.status.AUTH, "Token invalid, please login again");
	}
	public static Response DBFail() {
		return new Response(Response.status.FATAL, "Our Database has failed!");
	}
	public static Response unkownItem() {
		return new Response(Response.status.FAIL, "The item with such ID does not exist");
	}
	public static Response notAdmin() {
		return new Response(Response.status.AUTH, "Invalid action for user type, please login again");
	}
}
