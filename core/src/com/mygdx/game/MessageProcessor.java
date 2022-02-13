package com.mygdx.game;

import java.util.HashMap;

public class MessageProcessor {
	
	static HashMap<String, LineParser> hashMap = new HashMap<String, LineParser>();
	static LineParser currentRunnable;
	static private BugGameClient parent;

	static class MapRunnable implements LineParser {
		
		boolean takingMapInfo = false;
		int y = 0;
		public MapRunnable() {
		}

		public void parseLine(String line) {
			//System.out.println("true");
			int x = 0;
			for (char ch : line.toCharArray()) {
				//System.out.println("setTile" + y);
				parent.setTileType(x, y, Character.getNumericValue(ch));
				x++;
				//System.out.print(scientistName.charAt(i) + " ");
			}
			y++;
		}
		
		public void end() {
			takingMapInfo = false;
			y = 0;
		}
		
	}
	
	static class IdNumberThing implements LineParser {
			
			public IdNumberThing() {
			}
	
			public void parseLine(String line) {
				parent.idNumber = Integer.parseInt(line);
			}
			
			public void end() {
			}
			
		}
	
	static class MoveProcessor implements LineParser {
		
		boolean takingMapInfo = false;
		int lineNumber = 0;
		int id = 0;
		float x = 0;
		float y = 0;
		public MoveProcessor() {
		}

		public void parseLine(String line) {
			if (lineNumber == 0) {
				id = Integer.parseInt(line);
			}
			else if (lineNumber == 1) {
				x = Float.parseFloat(line);
			}
			else if (lineNumber == 2) {
				y = Float.parseFloat(line);
			}
			lineNumber++;
		}
		
		public void end() {
			if (id != parent.idNumber) {
				parent.gameScreen.movePlayer2(x, y);
			}
			id = 0;
			x = 0;
			y = 0;
			lineNumber = 0;
		}
		
	}
	
	static MapRunnable mapRunnable = new MapRunnable();
	static IdNumberThing idNumberThing = new IdNumberThing();
	static MoveProcessor moveProcessor = new MoveProcessor();
	
	public static void initialize(BugGameClient bugGame) {
		hashMap.put("<map>", mapRunnable);
		hashMap.put("<id>", idNumberThing);
		hashMap.put("<move>", moveProcessor);
		currentRunnable = null;
		parent = bugGame;
	}
	
	public static void processLine(String line) {
		if (currentRunnable == null) {
			currentRunnable = hashMap.get(line);
		}
		else {
			if (line.equals("</end>")) {
				currentRunnable.end();
				currentRunnable = null;
			}
			else {
				currentRunnable.parseLine(line);
			}
		}
	}
	
}
