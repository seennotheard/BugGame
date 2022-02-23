package com.mygdx.game;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class BugGameClient extends Game {
	public ArrayList<Ant> players = new ArrayList<Ant>();
	
	int defaultPort = 25565;
	String localHost = "127.0.0.1";
	Screen menu;
	Screen serverSelect = new ServerScreen(this);
	public SideViewScreen gameScreen;
	List<Disposable> assetList = new LinkedList<Disposable>();
	
	SpriteBatch batch;
	Texture img;
	public int idNumber;
	
	static public Skin gameSkin;

	@Override
	public void create() {
		MessageProcessor.initialize(this);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		assetList.add(batch);
		assetList.add(img);
		gameSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		menu = new MenuScreen(this);
		BugGameConnectionThread connectionThread = new BugGameConnectionThread(localHost, defaultPort, this);
		gameScreen = new SideViewScreen(this, connectionThread);
		//gameScreen = new GameScreen(this);
		//todo: load any assets
		assetList.add(new Texture(Gdx.files.internal("droplet.png"))); //just a placeholder
		//connectToServer(localHost, defaultPort);
		connectionThread.start();
		ConsoleThread consoleThread = new ConsoleThread(connectionThread);
		consoleThread.start();
		this.setScreen(menu);
		Gdx.graphics.setContinuousRendering(true);
	}
	
	public void addPlayer(int id) {
		players.add(new Ant(id));
		gameScreen.addSprite();
	}
	
	public void removePlayer() {
		//todo
	}
	
	public void setTileType(int x, int y, int type) {
		gameScreen.setTileType(x, y, type);
	}
	/*
	@Override
	public void render() {
		menu.render((float) 1.0);
	}
	*/
	@Override
	public void dispose() {
		// dispose of all the native resources
		for (Disposable asset : assetList) {
            asset.dispose();
        }
	}
	/*
	private void connectToServer(String hostName, int portNumber) {
        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
           
        ) {
        	new BugGameThread(socket).start();
            String fromServer;
            
            while (true) { //this part prints the stuff from the server, need to move it to the thread.
            	while ((fromServer = in.readLine()) == null) {
            	}
                System.out.println(fromServer);
            }
            
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
	}
	*/
	public void changeScreenToGame() {
		menu.dispose();
		this.setScreen(gameScreen);
	}

	public void updateLocation(int id, float x, float y) {
		for(Ant player : players) {
			if (player.getID() == id) {
				player.setX(x);
				player.setY(y);
				System.out.println("position updated");
				break;
			}
		}
		
	}
	
}
