package com.mygdx.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;


public class BugGameConnectionThread extends Thread {
	private Socket socket = null;
	private boolean running = true;
	private PrintWriter out;
	private BufferedReader in;
	
	public BugGameConnectionThread (Socket socket) {
		super("BugGameThread");
		this.socket = socket;
	}
	
	public BugGameConnectionThread (String hostName, int portNumber) {
		super("BugGameThread");
		try {
			socket = new Socket(hostName, portNumber);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void terminate() {
		running = false;
	}
	
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String fromServer;
			while (running) {
				if ((fromServer = in.readLine()) != null) {
					System.out.println(fromServer);
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void consoleInput(String str) {
		out.println(str);
	}
}
