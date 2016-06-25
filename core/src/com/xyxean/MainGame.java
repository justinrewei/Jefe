package com.xyxean;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.xyxean.Tools.SimpleDirectionGestureDetector;
import com.xyxean.scenes.Hud;
import com.xyxean.screens.DifficultyScreen;
import com.xyxean.screens.MenuScreen;
import com.xyxean.screens.PlayScreen;
import com.xyxean.screens.StoreScreen;
import com.xyxean.sprites.Player;

public class MainGame extends Game {
	public static final int V_HEIGHT = 252;
	public static final int V_WIDTH = 168;
	public static final float PPM = 100;

	public static final short PLAYER_BIT = 2;
	public static final short GROUND_BIT = 4;
	public static final short ENEMY_BIT = 8;
	public static final short COLLISION_BIT = 16;
	public static final short NULL_BIT = 32;


	public SpriteBatch batch;
	public static AssetManager manager;


	@Override
	public void create() {
		batch = new SpriteBatch();
		manager = new AssetManager();

		//queue up assets to be loaded
		manager.load("audio/piano.mp3", Music.class);
		manager.load("audio/jump.wav", Sound.class);
		manager.load("audio/button.wav", Sound.class);
		manager.load("audio/whoosh.wav", Sound.class);


		manager.finishLoading();

		setScreen(new MenuScreen(this));
		}


	public void changeStateMenu() {
		setScreen(new MenuScreen(this));
		super.dispose();
		dispose();
		this.dispose();

	}


	public void changeStateDifficulty() {
		setScreen(new DifficultyScreen(this));
		super.dispose();
		dispose();
		this.dispose();

	}

	public void changeStateStore() {
		setScreen(new StoreScreen(this));
		super.dispose();
		dispose();
		this.dispose();
	}


	public void changeStatePlay() {
		setScreen(new PlayScreen(this));
		super.dispose();
		dispose();
		this.dispose();

		InputMultiplexer inputMultiplexer = new InputMultiplexer();

		inputMultiplexer.addProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

			@Override
			public void onUp() {
				if (((PlayScreen) getScreen()).getPlayer().currentState != Player.State.DEAD) {
					if (((PlayScreen) getScreen()).getPlayer().b2body.getLinearVelocity().x == 0 && ((PlayScreen) getScreen()).getPlayer().b2body.getLinearVelocity().y == 0)
						((PlayScreen) getScreen()).getPlayer().b2body.setLinearVelocity(0, 2f);
				}
				MainGame.manager.get("audio/whoosh.wav", Sound.class).play();
			}

			@Override
			public void onRight() {
				if (((PlayScreen) getScreen()).getPlayer().currentState != Player.State.DEAD) {
					if (((PlayScreen) getScreen()).getPlayer().b2body.getLinearVelocity().x == 0 && ((PlayScreen) getScreen()).getPlayer().b2body.getLinearVelocity().y == 0)
						((PlayScreen) getScreen()).getPlayer().b2body.setLinearVelocity(2f, 0);
				}
				MainGame.manager.get("audio/whoosh.wav", Sound.class).play();
			}

			@Override
			public void onLeft() {
				if (((PlayScreen) getScreen()).getPlayer().currentState != Player.State.DEAD) {
					if (((PlayScreen) getScreen()).getPlayer().b2body.getLinearVelocity().x == 0 && ((PlayScreen) getScreen()).getPlayer().b2body.getLinearVelocity().y == 0)
						((PlayScreen) getScreen()).getPlayer().b2body.setLinearVelocity(-2f, -0);
				}
				MainGame.manager.get("audio/whoosh.wav", Sound.class).play();
			}

			@Override
			public void onDown() {
				if (((PlayScreen) getScreen()).getPlayer().currentState != Player.State.DEAD) {
					if (((PlayScreen) getScreen()).getPlayer().b2body.getLinearVelocity().x == 0 && ((PlayScreen) getScreen()).getPlayer().b2body.getLinearVelocity().y == 0)
						((PlayScreen) getScreen()).getPlayer().b2body.setLinearVelocity(0, -2f);
				}
				MainGame.manager.get("audio/whoosh.wav", Sound.class).play();
			}
		}));
		Gdx.input.setInputProcessor(inputMultiplexer);



	}


	@Override
	public void render() {
		//delegate render method to PlayScreen
		super.render();

		//calling manager to continue to load assets
		manager.update(); //returns boolean value whether or not assets are loaded
	}
}