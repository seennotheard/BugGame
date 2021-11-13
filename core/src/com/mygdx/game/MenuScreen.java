package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
 
public class MenuScreen implements Screen{
	
	private BugGameClient parent;
	private Stage stage;
	
	public MenuScreen(BugGameClient game){
		parent = game;
		
		/// create stage and set it as input processor
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void show() {
		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
        	table.setDebug(true);
        	stage.addActor(table);
        
        	// temporary until we have asset manager in
        	Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        	//create buttons
        	TextButton newGame = new TextButton("New Game", skin);
        	TextButton preferences = new TextButton("Preferences", skin);
        	TextButton exit = new TextButton("Exit", skin);
        
       		//add buttons to table
        	table.add(newGame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(preferences).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();
		
		// create button listeners
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();				
			}
		});
		
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreenToGame();
				//parent.changeScreen(Box2DTutorial.APPLICATION);			
			}
		});
		
		preferences.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//parent.changeScreen(Box2DTutorial.PREFERENCES);					
			}
		});
		
	}
 
	@Override
	public void render(float delta) {
		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
 
	@Override
	public void resize(int width, int height) {
		// change the stage's viewport when teh screen size is changed
		stage.getViewport().update(width, height, true);
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
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
 
	@Override
	public void dispose() {
		// dispose of assets when not needed anymore
		stage.dispose();
	}
 
}