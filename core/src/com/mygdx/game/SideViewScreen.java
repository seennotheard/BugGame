package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SideViewScreen implements InputProcessor, Screen {
	
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    BugGameClient parent;
    SpriteBatch sb;
    Texture texture; //I think this texture is for the sprite, not sure what img is for
    
    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    Sprite sprite;
    Sprite player2;
    
    Texture dirt;
    Texture grass;
    TextureRegion[][] dirtTile;
    TextureRegion[][] grassTile;
    Texture textureTree;
    //Sprite tree;
    BitmapFont counter = new BitmapFont();
    int foodCounter = 0;
    BugGameConnectionThread connectionThread;
    
    private final Random random = new Random();
    
    public SideViewScreen(BugGameClient bugGameClient, BugGameConnectionThread connectionThread) {
		// TODO Auto-generated constructor stub
    	parent = bugGameClient;
    	this.connectionThread = connectionThread;
    	this.create();
	}


	public void create () {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        

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
        /**
        { //texture scaling
	        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("tree.png"));
	        Pixmap pixmap100 = new Pixmap(32, 32, pixmap200.getFormat());
	        pixmap100.drawPixmap(pixmap200,
	                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
	                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
	        );
	        textureTree = new Texture(pixmap100);
        }
        **/

        dirtTile = TextureRegion.split(dirt, 32, 32);
        //dirtTile.setTexture(dirt);

        grassTile = TextureRegion.split(grass, 32, 32);

        //TextureRegion[][] treeTile = TextureRegion.split(textureTree, 32, 32);

        tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();
		for (int l = 0; l < 1; l++) {

			TiledMapTileLayer layer = new TiledMapTileLayer(10, 10, 32, 32);
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {

					//int ty = (int)(Math.random() * splitTiles.length);
					//int tx = (int)(Math.random() * splitTiles[ty].length);
					/*
					Cell cell = new Cell();
					if (random.nextBoolean()) {
						cell.setTile(new StaticTiledMapTile(dirtTile[0][0]));
					} else {
						cell.setTile(new StaticTiledMapTile(grassTile[0][0]));
					}

					layer.setCell(x, y, cell);
					*/
				}
			}
			layers.add(layer);
		}
		/**
		{
			TiledMapTileLayer layer = new TiledMapTileLayer(10, 10, 32, 32);
			layer = new TiledMapTileLayer(10, 10, 32, 32);
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					//int ty = (int)(Math.random() * splitTiles.length);
					//int tx = (int)(Math.random() * splitTiles[ty].length);
					Cell cell = new Cell();
					if (random.nextBoolean()) {
						cell.setTile(new StaticTiledMapTile(treeTile[0][0]));
					} else {
						cell = null;
					}

					layer.setCell(x, y, cell);
				}
			}
			layers.add(layer);
		}
		**/
		
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //Gdx.input.setInputProcessor(this); //shows up in this spot in sprite tutorial
        
        { //texture scaling
	        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("ant.png"));
	        Pixmap pixmap100 = new Pixmap(32, 32, pixmap200.getFormat());
	        pixmap100.drawPixmap(pixmap200,
	                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
	                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
	        );
	        texture = new Texture(pixmap100);
        }
        
        sb = new SpriteBatch();
        //texture = new Texture(Gdx.files.internal("ant.png")); ^texture is set to scaled kirbs already       
        
        sprite = new Sprite(texture);
        player2 = new Sprite(texture);
        //TextureRegion treeRegion = new TextureRegion(textureTree, 10, 10, 32, 32);
        //tree = new Sprite(treeRegion);
        Gdx.graphics.setContinuousRendering(true);
	}
	
	public void setTileType(int x, int y, int type) { //0 for dirt, 1 for grass
        Cell cell = new Cell();
        MapLayers layers = tiledMap.getLayers();
        TiledMapTileLayer layer = (TiledMapTileLayer) layers.get(0);
        if (type == 0) {
            cell.setTile(new StaticTiledMapTile(dirtTile[0][0]));
            cell.getTile().setId(0);
        }
        else {
            cell.setTile(new StaticTiledMapTile(grassTile[0][0]));
            cell.getTile().setId(1);
        }
        layer.setCell(x,  y, cell);
    }
	
	 public boolean isCellCollidable(int x, int y) {
	        MapLayers layers = tiledMap.getLayers();
	        TiledMapTileLayer layer = (TiledMapTileLayer) layers.get(0); //0 for dirt and grass layer
	        Cell cell = layer.getCell(x, y);
	        if (cell == null) {
	            return false;
	        }
	        return (cell.getTile().getId() == 1);
	    }
	
	boolean moving = false;
	boolean movementKeyHold = false;
	int framesSinceLastKeypress = 0;
	int lastKeycode;
	float n = 20;
	int rotation = 0;
	@Override
    public void render (float delta) { 
		Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (moving) {
        	//System.out.println("moving!");
        	framesSinceLastKeypress++;
        	if (framesSinceLastKeypress == n) {
        		//if (movementKeyHold == false) {
        			moving = false;
        		//}
        		framesSinceLastKeypress = 0;
        	}
        	
        	if(lastKeycode == Input.Keys.A || lastKeycode == Input.Keys.LEFT) {
                camera.translate(-32/n,0);
        	}
            if(lastKeycode == Input.Keys.D || lastKeycode == Input.Keys.RIGHT) {
                camera.translate(32/n,0);             
        	}
            if(lastKeycode == Input.Keys.S || lastKeycode == Input.Keys.DOWN) {
                camera.translate(0,-32/n);
        	}
            if(lastKeycode == Input.Keys.W || lastKeycode == Input.Keys.UP) {
                camera.translate(0,32/n);
        	}
            
            connectionThread.consoleInput("<move>");
            connectionThread.consoleInput(String.valueOf(camera.position.x - 16));
            connectionThread.consoleInput(String.valueOf(camera.position.y - 16));
            connectionThread.consoleInput(String.valueOf(rotation));
            connectionThread.consoleInput("</end>");
        }
        sprite.setRotation(rotation);
        if(lastKeycode == Input.Keys.Q)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(lastKeycode == Input.Keys.E)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sprite.setPosition(camera.position.x - 16, camera.position.y - 16);
        //player2.setPosition(camera.position.x + 10, camera.position.y);

        for (int i = 0; i < parent.players.size(); i++) {
        	sprites.get(i).setPosition(parent.players.get(i).getX(), parent.players.get(i).getY());
        	sprites.get(i).draw(sb);
        }
        
        sprite.draw(sb);
        //player2.draw(sb);

        counter.draw(sb, Integer.toString(foodCounter), camera.position.x + (12 * 32), camera.position.y + (7 * 32));
        //sb.draw(textureTree, new Rectangle(150, 250, 10, 10), Color.WHITE);
        //tree.draw(sb);
        sb.end();		
      
        //System.out.println("rendering!");
    }
	
	public void addSprite() {
		sprites.add(new Sprite(texture));
	}
	
	/*
	public void movePlayer2(float x, float y) {
		player2.setPosition(x, y);
	}
	*/
	
    @Override
    public boolean keyDown(int keycode) {
    	System.out.println("key pressed");
        if (!moving && ((keycode == Input.Keys.W || keycode == Input.Keys.UP) && !(isCellCollidable((int) camera.position.x / 32, (int) (camera.position.y + 32) / 32))
                || ((keycode == Input.Keys.A || keycode == Input.Keys.LEFT) && !(isCellCollidable((int) (camera.position.x - 32) / 32, (int) (camera.position.y) / 32)))
                || ((keycode == Input.Keys.S || keycode == Input.Keys.DOWN) && !(isCellCollidable((int) (camera.position.x) / 32, (int) ((camera.position.y) - 32) / 32)))
                || ((keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) && !(isCellCollidable((int) (camera.position.x + 32) / 32, (int) (camera.position.y) / 32))))) {
    		lastKeycode = keycode;
    		moving = true;
    		movementKeyHold = true;
    	}
    	else if (keycode == Input.Keys.E) {
    		MapLayers layers = tiledMap.getLayers(); 
        	TiledMapTileLayer layer = (TiledMapTileLayer) layers.get(1);
        	Cell cell = layer.getCell((int) camera.position.x / 32, (int) camera.position.y / 32);
        	if (cell != null) {
        		foodCounter ++ ;
        		layer.setCell((int) camera.position.x / 32, (int) camera.position.y / 32, null);
        		System.out.println("tree nom");
        	}
        	System.out.println(foodCounter); 
    	}
    	
    	if(keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            rotation = 90;
    	}
        if(keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {            
            rotation = -90;
    	}
        if(keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            rotation = 180;
    	}
        if(keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            rotation = 0;
    	}
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
    	System.out.println("key released");
    	movementKeyHold = false;
    	if (keycode == Input.Keys.W || keycode == Input.Keys.A 
    			|| keycode == Input.Keys.S || keycode == Input.Keys.D 
    			|| keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT 
    			|| keycode == Input.Keys.DOWN || keycode == Input.Keys.UP) {
    		movementKeyHold = false;
    	}
    	/**
    	if(keycode == Input.Keys.A | keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.D | keycode == Input.Keys.RIGHT)
            camera.translate(32,0);
        if(keycode == Input.Keys.S | keycode == Input.Keys.DOWN)
            camera.translate(0,-32);
        if(keycode == Input.Keys.W | keycode == Input.Keys.UP)
            camera.translate(0,32);
        if(keycode == Input.Keys.Q)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.E)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());	
        **/
    	return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	/**
    	MapLayers layers = tiledMap.getLayers(); 
    	TiledMapTileLayer layer = (TiledMapTileLayer) layers.get(1);
   
    	Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
    	Vector3 position = camera.unproject(clickCoordinates);
    	//sprite.setPosition(camera.position.x, camera.position.y);
    	
    	//Cell cell = null;//layer.getCell((int) position.x / 32, (int) position.y / 32);
    	//cell.setTile(new StaticTiledMapTile(dirtTile[0][0]));
    	Cell cell = layer.getCell((int) position.x / 32, (int) position.y / 32);
    	if (cell != null) {
    		foodCounter ++ ;
    		layer.setCell((int) position.x / 32, (int) position.y / 32, null);
    		System.out.println("tree nom");
    	}
    	System.out.println(foodCounter);   
    	**/
    	return true;
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

    //@Override (??? error unless override is removed)
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
