package com.mygdx.game;

import java.util.Scanner;


public class ConsoleThread extends Thread {
	private boolean running = true;
	private BugGameConnectionThread connectionThread;
	
	public ConsoleThread (BugGameConnectionThread connectionThread) {
		super("BugGameThread");
		this.connectionThread = connectionThread;
	}
	
	public void terminate() {
		running = false;
	}
	
	public void run() {

		Scanner scanner = new Scanner(System.in);
		String str;
		while (running) {
			str = scanner.nextLine();
			connectionThread.consoleInput(str);
		}
		scanner.close();
	}
}
