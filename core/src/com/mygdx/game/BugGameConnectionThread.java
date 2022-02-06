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
	private BugGameClient parent;
	public BugGameConnectionThread (Socket socket) {
		super("BugGameThread");
		this.socket = socket;
	}
	
	public BugGameConnectionThread (String hostName, int portNumber, BugGameClient bugGame) {
		super("BugGameThread");
		parent = bugGame;
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
					//System.out.println("new line");
					System.out.println(fromServer);
					MessageProcessor.processLine(fromServer);
					/*
					if (takingMapInfo) {
						if (fromServer.equals("</map>")) {
							takingMapInfo = false;
							break;
							
						}
						
						//System.out.println("true");
						
						int x = 0;
						for (char ch : fromServer.toCharArray()) {
							//System.out.println("setTile" + y);
							parent.setTileType(x, y, Character.getNumericValue(ch));
							x++;
					    	//System.out.print(scientistName.charAt(i) + " ");
					    }
						y++;
					}
					//hashmap.get(fromServer);
					else if (fromServer.equals("<map>")) {
						takingMapInfo = true;
						y = 0;
					}
					*/
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
