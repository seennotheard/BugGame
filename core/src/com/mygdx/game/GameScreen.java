package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import java.util.Random;

public class GameScreen implements InputProcessor, Screen {
	
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    BugGameClient parent;
    
    private final Random random = new Random();
    
    public GameScreen(BugGameClient bugGameClient) {
		// TODO Auto-generated constructor stub
    	parent = bugGameClient;
    	this.create();
	}

	public GameScreen() {
		this.create();
	}

	public void create () {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
        Texture dirt;
        Texture grass;
        { //texture scaling
	        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("dirt.jpg"));
	        Pixmap pixmap100 = new Pixmap(32, 32, pixmap200.getFormat());
	        pixmap100.drawPixmap(pixmap200,
	                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
	                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
	        );
	        dirt = new Texture(pixmap100);
        }
        { //texture scaling
	        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("grass.jpg"));
	        Pixmap pixmap100 = new Pixmap(32, 32, pixmap200.getFormat());
	        pixmap100.drawPixmap(pixmap200,
	                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
	                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
	        );
	        grass = new Texture(pixmap100);
        }
        
        TextureRegion[][] dirtTile = TextureRegion.split(dirt, 32, 32);
        //dirtTile.setTexture(dirt);
        TextureRegion[][] grassTile = TextureRegion.split(grass, 32, 32);
        tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();
		for (int l = 0; l < 20; l++) {
			TiledMapTileLayer layer = new TiledMapTileLayer(150, 100, 32, 32);
			for (int x = 0; x < 150; x++) {
				for (int y = 0; y < 100; y++) {
					//int ty = (int)(Math.random() * splitTiles.length);
					//int tx = (int)(Math.random() * splitTiles[ty].length);
					Cell cell = new Cell();
					if (random.nextBoolean()) {
						cell.setTile(new StaticTiledMapTile(dirtTile[0][0]));
					} else {
						cell.setTile(new StaticTiledMapTile(grassTile[0][0]));
					}

					layer.setCell(x, y, cell);
				}
			}
			layers.add(layer);
		}
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
	@Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    @Override
    public boolean keyDown(int keycode) {
    	System.out.println("key pressed");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
    	System.out.println("key pressed");
        if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(32,0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,-32);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0,32);
        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    //@Override (commented out bc there was an error until override annotation was removed?)
    public boolean scrolled(int amount) {
        return false;
    }

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
