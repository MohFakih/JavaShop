package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;

public class Server {
	private static int port = 2000;
	private static int maxUsers = 20;
	public static void main(String[] args) throws Exception{
		try (ServerSocket listener = new ServerSocket(port) ){
			System.out.println("Authentication server is running on port" + port);
			ExecutorService pool = Executors.newFixedThreadPool(maxUsers);
			while(true) {
				pool.execute(new ServerHandler(listener.accept()));
			}
		}
	}
}
