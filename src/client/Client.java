package client;

import java.net.*;
import java.util.Scanner;

import common.Request;
import common.Response;
import common.User;

import java.io.*;

public class Client {
	public static boolean running = true;
	public static InetAddress ip;
	public static String sin;
	public static int port;
	public static Socket socket;
	public static ObjectInputStream fromServer;
	public static ObjectOutputStream toServer;
	public static User mainUser;
	public static boolean sendRequest(Request req){
		try {
			toServer.writeObject(req);
			return true;
		} catch (IOException e) {
			GUIDriver.prompt("IO Error", "Could not send the request: "+e.getMessage(), 0);
			e.printStackTrace();
			return false;
		}
	}
	
	public static Response getResponse() {
		Response res = null;
		try {
			res = (Response) fromServer.readObject();
		} catch (ClassNotFoundException e) {
			GUIDriver.prompt("Error", "Client error: "+e.getMessage(), 0);
			e.printStackTrace();
		} catch (IOException e) {
			GUIDriver.prompt("Error", "Could not read from server: "+e.getMessage(), 0);
			e.printStackTrace();
		}
		return res;
	}
	
	public static void releaseResources() {
		System.out.println("Thank you for using our service!");
		System.out.println("Shutting down sockets and streams...");
		try{socket.close();}catch(Exception e) {e.printStackTrace();}
		try{fromServer.close();}catch(Exception e) {e.printStackTrace();};
		try{toServer.close();}catch(Exception e) {e.printStackTrace();};
		System.out.println("Bye!");
	}
	
	public static void setPort(int p) {
		port = p;
	}
	public static void initializeStreams(){
		try {
			fromServer = new ObjectInputStream(socket.getInputStream());
			toServer = new ObjectOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			System.out.println("Could not initilize streams and their buffers.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void connectToServer() throws IOException {
		socket = new Socket(ip, port);
	}
	public static void setIpAddress(String IPStr) throws UnknownHostException {
		ip = InetAddress.getByName(IPStr);
	}
}
