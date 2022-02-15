package com.mygdx.game;

public class Ant {
	private int id;
	private int x;
	private int y;
	private int rotation;
	
	public static final int UP = 0;
	public static final int DOWN = 180;
	public static final int LEFT = 90;
	public static final int RIGHT = 270;
	
	//players is an ArrayList<Ant> from BugGameClient
	
	public Ant(int number) {
		id = number;
		x = 0;
		y = 0;
		rotation = UP;
	}
	
	public int getID() {
		return id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRotation() {
		return rotation;
	}
}
