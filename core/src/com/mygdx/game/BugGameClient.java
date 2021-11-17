package com.mygdx.game;
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
	int defaultPort = 25565;
	String localHost = "127.0.0.1";
	Screen menu;
	Screen serverSelect = new ServerScreen(this);
	GameScreen gameScreen;
	List<Disposable> assetList = new LinkedList<Disposable>();
	
	SpriteBatch batch;
	Texture img;
	
	static public Skin gameSkin;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		assetList.add(batch);
		assetList.add(img);
		gameSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		menu = new MenuScreen(this);
		gameScreen = new GameScreen();
		//gameScreen = new GameScreen(this);
		//todo: load any assets
		assetList.add(new Texture(Gdx.files.internal("droplet.png"))); //just a placeholder
		//connectToServer(localHost, defaultPort);
		BugGameConnectionThread connectionThread = new BugGameConnectionThread(localHost, defaultPort);
		connectionThread.start();
		ConsoleThread consoleThread = new ConsoleThread(connectionThread);
		consoleThread.start();
		this.setScreen(menu);
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
	
}
