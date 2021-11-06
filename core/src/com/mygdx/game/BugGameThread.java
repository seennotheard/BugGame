package com.mygdx.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;


public class BugGameThread extends Thread {
	private Socket socket = null;
	private boolean running = true;
	
	public BugGameThread (Socket socket) {
		super("BananagramsClientThread");
		this.socket = socket;
	}
	
	public void terminate() {
		running = false;
	}
	
	public void run() {
		try (
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        ) {
				Scanner scanner = new Scanner(System.in);
				String str;
				while (running) {
					str = scanner.nextLine();
					out.println(str);
				}
				
	            scanner.close();
	            socket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
}
