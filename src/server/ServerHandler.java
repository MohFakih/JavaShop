package server;

import java.net.*;
import java.sql.*;
import java.util.*;

import client.GUIDriver;

import java.io.*;
import common.Encrypt;
import common.Request;
import common.Response;

// TO CREATE THE TABLE IN THE DB USE THE FOLLOWING SQL COMMANDS:

// CREATE DATABASE IF NOT EXISTS MAINDB
// USE MAINDB
// CREATE TABLE `USERS` ( `USERNAME` VARCHAR(20) NOT NULL , `PASSWORD` VARCHAR(100) NOT NULL , PRIMARY KEY (`USERNAME`));








public class ServerHandler implements Runnable {
	private Socket socket;
	
	ServerHandler(Socket givenSocket){
		this.socket = givenSocket;
	}

	Connection conn = null;
	Statement stmt = null;
	
	ObjectInputStream fromClient;
	ObjectOutputStream toClient;
	
	@Override
	public void run() {
		System.out.println("Connected to a new client: "+ socket);
		try {
			connectToMySQL();
			
			initializeStreams();
		}catch(Exception e) {
			e.printStackTrace();
		}
		while(true) {
			try {
				Request req = (Request) fromClient.readObject();
				RequestHandler ReqHandler = HandlerFactory.getRequestHandler(req, stmt);
				Response res = ReqHandler.resolve();
				toClient.writeObject(res);
				if(res.resStatus == Response.status.FATAL) {
					break;
				}
			} catch(EOFException e) {
				System.out.println("Client has closed the session from socket: "+ socket);
				break;
			} catch(Exception e) {
				e.printStackTrace();
				break;
			}
		}
		releaseResources();
	}
	public void releaseResources() {
		System.out.println("Closing socket "+ socket);
		try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {socket.close();fromClient.close();toClient.close();} catch(Exception e) {e.printStackTrace();}
	}
	public void connectToMySQL() throws Exception {
		System.out.println(socket + ": Attempting to connect to MySQL...");
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MAINDB", "root", "");
		stmt = conn.createStatement();
		if(stmt == null) {
			System.out.println("Could not connect to MySQL!");
			System.exit(-1);
		}else {
			System.out.println(socket + ": Connected to MySQL");
		}
	}
	public void initializeStreams() throws IOException{
		toClient = new ObjectOutputStream(socket.getOutputStream());
		fromClient = new ObjectInputStream(socket.getInputStream());
	}
}