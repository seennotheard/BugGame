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
	
	
	static MapRunnable mapRunnable = new MapRunnable();
	
	public static void initialize(BugGameClient bugGame) {
		hashMap.put("<map>", mapRunnable);
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
