package com.mygdx.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class BugGameClient extends ApplicationAdapter {
	
	
	
	List<Disposable> assetList = new LinkedList<Disposable>();
	

	@Override
	public void create() {
		//todo: load any assets
		assetList.add(new Texture(Gdx.files.internal("droplet.png"))); //just a placeholder
		connectToServer("Placeholder", 5555);
	}
	
	@Override
	public void render() {
		
	}
	
	@Override
	public void dispose() {
		// dispose of all the native resources
		for (Disposable asset : assetList) {
            asset.dispose();
        }
	}
	
	private void connectToServer(String hostName, int portNumber) {
        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        ) {
        	new BugGameThread(socket).start();
            String fromServer;
            while (true) {
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
	
}
