package com.xyxean.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xyxean.MainGame;
import com.xyxean.Tools.B2WorldCreator;

import com.xyxean.Tools.WorldContactListener;
import com.xyxean.scenes.DifficultyHud;
import com.xyxean.scenes.Hud;
import com.xyxean.sprites.Cat;
import com.xyxean.sprites.Player;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by justinwei on 2/25/2016.
 */
public class PlayScreen extends InputListener implements Screen{
    public MainGame game;

    //Basic variables
    public static OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private Vector2 groundPos1, groundPos2;

    //TILED MAP VARIABLES
    private TmxMapLoader mapLoader; //load map into game
    private TiledMap map; //reference to map
    private OrthogonalTiledMapRenderer renderer;//renders map to screen

    //Box2D VARIABLES
    private World world;
    private Box2DDebugRenderer b2dr; //graphical representation of fixtures and bodies in box2d

    //sprites
    private Player player;
    private Cat enemy, enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7, enemy8, enemy9, enemy10, enemy11, enemy12, enemy13, enemy14, enemy15;
    private TextureAtlas atlas, groundEnemyAtlas, flyAtlas;
    private  Texture background;
    private int randomInt, aRandomInt;
    private Music music;

    public TextureAtlas getAtlas(){ return atlas; }
    public TextureAtlas getGroundEnemyAtlas() { return groundEnemyAtlas; }
    public TextureAtlas getFlyAtlas(){ return flyAtlas; }


    public PlayScreen(MainGame game) {
        background = new Texture("background/newbg.png");
        atlas = new TextureAtlas("sprites/PLAYER.pack");
        groundEnemyAtlas = new TextureAtlas("sprites/cat.pack");
        flyAtlas = new TextureAtlas("sprites/flyer.pack");

        this.game = game;
        gamecam = new OrthographicCamera(); //create camera to follow character
        gamePort = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam); //create FitViewport to maintain aspect ratio
        hud = new Hud(this, game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("background/MICKEY.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0); //set in center

        world = new World(new Vector2(0, 0), true); //gravity
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(this); //create an instance of the ground

        player = new Player(this);

        world.setContactListener(new WorldContactListener());

        groundPos1 = new Vector2((gamecam.position.x - gamecam.viewportWidth / 2) / MainGame.PPM, 0);
        groundPos2 = new Vector2(((gamecam.position.x - gamecam.viewportWidth / 2) / MainGame.PPM + background.getWidth()) / MainGame.PPM, 0);



        if (hud.isEasyDifficulty()) {
            Random aRandomGenerator = new Random();
            aRandomInt = aRandomGenerator.nextInt(3);
            if (aRandomInt == 0)
                enemy10 = new Cat(this, 1.2f, 3.5f);
            else if (aRandomInt == 1)
                enemy10 = new Cat(this, 0.71f, -1.2f);
            else if (aRandomInt == 2)
                enemy10 = new Cat(this, 1.68f, -1.2f);
            enemy = new Cat(this, -.5f, .28f);
            enemy3 = new Cat(this, 3f, .75f);
            enemy4 = new Cat(this, -.5f, 1.21f);
            enemy7 = new Cat(this, 3f, 1.72f);
            enemy8 = new Cat(this, -.5f, 2.2f);
        } else if (hud.isMediumDifficulty()) {
            Random randomGenerator = new Random();
            randomInt = randomGenerator.nextInt(2);
            if (randomInt == 1)
                enemy = new Cat(this, -.5f, .28f);
            else if (randomInt == 0){enemy = new Cat(this, -1.68f, -1.2f);}
            enemy1 = new Cat(this, 3f, .28f);
            enemy2 = new Cat(this, 3f, .75f);
            enemy3 = new Cat(this, 3f, .75f);
            enemy4 = new Cat(this, -.5f, 1.21f);
            enemy5 = new Cat(this, 3f, 1.21f);
            enemy6 = new Cat(this, -.5f, 1.72f);
            enemy7 = new Cat(this, 3f, 1.72f);
            if (randomInt == 0)
                enemy8 = new Cat(this, -.5f, 2.2f);
            else if (randomInt == 1){enemy8 = new Cat(this, 0.71f, 3.5f);}
            enemy9 = new Cat(this, 3f, 2.2f);
        } else if (hud.isHardDifficulty()) {
            enemy = new Cat(this, -.5f, .28f);
            enemy1 = new Cat(this, 3f, .28f);
            enemy2 = new Cat(this, 3f, .75f);
            enemy3 = new Cat(this, 3f, .75f);
            enemy4 = new Cat(this, -.5f, 1.21f);
            enemy5 = new Cat(this, 3f, 1.21f);
            enemy6 = new Cat(this, -.5f, 1.72f);
            enemy7 = new Cat(this, 3f, 1.72f);
            enemy8 = new Cat(this, -.5f, 2.2f);
            enemy9 = new Cat(this, 3f, 2.2f);
            enemy10 = new Cat(this, 1.2f, -1.2f);
            enemy11 = new Cat(this, 0.71f, 3.5f);
            enemy12 = new Cat(this, 1.68f, -1.2f);
        } else if (hud.isReallyHardDifficulty()){
            enemy = new Cat(this, -.5f, .28f);
            enemy1 = new Cat(this, 3f, .28f);
            enemy2 = new Cat(this, 3f, .75f);
            enemy3 = new Cat(this, 3f, .75f);
            enemy4 = new Cat(this, -.5f, 1.21f);
            enemy5 = new Cat(this, 3f, 1.21f);
            enemy6 = new Cat(this, -.5f, 1.72f);
            enemy7 = new Cat(this, 3f, 1.72f);
            enemy8 = new Cat(this, -.5f, 2.2f);
            enemy9 = new Cat(this, 3f, 2.2f);
            enemy10 = new Cat(this, 1.2f, -1.2f);
            enemy11 = new Cat(this, 0.71f, 3.5f);
            enemy12 = new Cat(this, 1.68f, -1.2f);
            enemy13 = new Cat(this, 0.71f, -1.2f);
            enemy14 = new Cat(this, 1.68f, 3.5f);
            enemy15 = new Cat(this, 1.2f, 3.5f);

        }
    }


    @Override
    public void show() {

    }


    public void update(float dt){
        world.step(1 / 60f, 6, 2);

        player.update(dt);
        updateGround();

        if (hud.isEasyDifficulty()) {
            enemy.update(dt);
            enemy3.update(dt);
            enemy4.update(dt);
            enemy7.update(dt);
            enemy8.update(dt);
            enemy10.update(dt);
        } else if (hud.isMediumDifficulty()) {
            enemy.update(dt);
            enemy1.update(dt);
            enemy2.update(dt);
            enemy3.update(dt);
            enemy4.update(dt);
            enemy5.update(dt);
            enemy6.update(dt);
            enemy7.update(dt);
            enemy8.update(dt);
            enemy9.update(dt);
        } else if (hud.isHardDifficulty()) {
            enemy.update(dt);
            enemy1.update(dt);
            enemy2.update(dt);
            enemy3.update(dt);
            enemy4.update(dt);
            enemy5.update(dt);
            enemy6.update(dt);
            enemy7.update(dt);
            enemy8.update(dt);
            enemy9.update(dt);
            enemy10.update(dt);
            enemy11.update(dt);
            enemy12.update(dt);
        } else if (hud.isReallyHardDifficulty()) {
            enemy.update(dt);
            enemy1.update(dt);
            enemy2.update(dt);
            enemy3.update(dt);
            enemy4.update(dt);
            enemy5.update(dt);
            enemy6.update(dt);
            enemy7.update(dt);
            enemy8.update(dt);
            enemy9.update(dt);
            enemy10.update(dt);
            enemy11.update(dt);
            enemy12.update(dt);
            enemy13.update(dt);
            enemy14.update(dt);
            enemy15.update(dt);


        }

        if (player.b2body.getLinearVelocity().x < 0)
            player.flip(true, false);

        if (hud.isEasyDifficulty()) {
            if (enemy.b2body.getLinearVelocity().x < 0)
                enemy.flip(true, false);
            if (enemy3.b2body.getLinearVelocity().x < 0)
                enemy3.flip(true, false);
            if (enemy4.b2body.getLinearVelocity().x < 0)
                enemy4.flip(true, false);
            if (enemy7.b2body.getLinearVelocity().x < 0)
                enemy7.flip(true, false);
            if (enemy8.b2body.getLinearVelocity().x < 0)
                enemy8.flip(true, false);
            if (enemy10.b2body.getLinearVelocity().y < 0)
                enemy10.flip(false, true);
        } else if (hud.isMediumDifficulty()) {
            if (enemy.b2body.getLinearVelocity().x < 0)
                enemy.flip(true, false);
            if (enemy1.b2body.getLinearVelocity().x < 0)
                enemy1.flip(true, false);
            if (enemy2.b2body.getLinearVelocity().x < 0)
                enemy2.flip(true, false);
            if (enemy3.b2body.getLinearVelocity().x < 0)
                enemy3.flip(true, false);
            if (enemy4.b2body.getLinearVelocity().x < 0)
                enemy4.flip(true, false);
            if (enemy5.b2body.getLinearVelocity().x < 0)
                enemy5.flip(true, false);
            if (enemy6.b2body.getLinearVelocity().x < 0)
                enemy6.flip(true, false);
            if (enemy7.b2body.getLinearVelocity().x < 0)
                enemy7.flip(true, false);
            if (enemy8.b2body.getLinearVelocity().x < 0)
                enemy8.flip(true, false);
            if (enemy9.b2body.getLinearVelocity().x < 0)
                enemy9.flip(true, false);
            if (enemy.b2body.getLinearVelocity().y < 0)
                enemy.flip(false, true);
            if (enemy8.b2body.getLinearVelocity().y < 0)
                enemy8.flip(false, true);
        } else if (hud.isHardDifficulty()) {
            if (enemy.b2body.getLinearVelocity().x < 0)
                enemy.flip(true, false);
            if (enemy1.b2body.getLinearVelocity().x < 0)
                enemy1.flip(true, false);
            if (enemy2.b2body.getLinearVelocity().x < 0)
                enemy2.flip(true, false);
            if (enemy3.b2body.getLinearVelocity().x < 0)
                enemy3.flip(true, false);
            if (enemy4.b2body.getLinearVelocity().x < 0)
                enemy4.flip(true, false);
            if (enemy5.b2body.getLinearVelocity().x < 0)
                enemy5.flip(true, false);
            if (enemy6.b2body.getLinearVelocity().x < 0)
                enemy6.flip(true, false);
            if (enemy7.b2body.getLinearVelocity().x < 0)
                enemy7.flip(true, false);
            if (enemy8.b2body.getLinearVelocity().x < 0)
                enemy8.flip(true, false);
            if (enemy9.b2body.getLinearVelocity().x < 0)
                enemy9.flip(true, false);
            if (enemy10.b2body.getLinearVelocity().x < 0)
                enemy10.flip(true, false);
            if (enemy11.b2body.getLinearVelocity().x < 0)
                enemy11.flip(true, false);
            if (enemy12.b2body.getLinearVelocity().x < 0)
                enemy12.flip(true, false);
            if (enemy10.b2body.getLinearVelocity().y < 0)
                enemy10.flip(false, true);
            if (enemy11.b2body.getLinearVelocity().y < 0)
                enemy11.flip(false, true);
            if (enemy12.b2body.getLinearVelocity().y < 0)
                enemy12.flip(false, true);

        } else if (hud.isReallyHardDifficulty()) {
            if (enemy.b2body.getLinearVelocity().x < 0)
                enemy.flip(true, false);
            if (enemy1.b2body.getLinearVelocity().x < 0)
                enemy1.flip(true, false);
            if (enemy2.b2body.getLinearVelocity().x < 0)
                enemy2.flip(true, false);
            if (enemy3.b2body.getLinearVelocity().x < 0)
                enemy3.flip(true, false);
            if (enemy4.b2body.getLinearVelocity().x < 0)
                enemy4.flip(true, false);
            if (enemy5.b2body.getLinearVelocity().x < 0)
                enemy5.flip(true, false);
            if (enemy6.b2body.getLinearVelocity().x < 0)
                enemy6.flip(true, false);
            if (enemy7.b2body.getLinearVelocity().x < 0)
                enemy7.flip(true, false);
            if (enemy8.b2body.getLinearVelocity().x < 0)
                enemy8.flip(true, false);
            if (enemy9.b2body.getLinearVelocity().x < 0)
                enemy9.flip(true, false);
            if (enemy10.b2body.getLinearVelocity().x < 0)
                enemy10.flip(true, false);
            if (enemy11.b2body.getLinearVelocity().x < 0)
                enemy11.flip(true, false);
            if (enemy12.b2body.getLinearVelocity().x < 0)
                enemy12.flip(true, false);
            if (enemy13.b2body.getLinearVelocity().x < 0)
                enemy13.flip(true, false);
            if (enemy14.b2body.getLinearVelocity().x < 0)
                enemy14.flip(true, false);
            if (enemy15.b2body.getLinearVelocity().x < 0)
                enemy15.flip(true, false);
            if (enemy10.b2body.getLinearVelocity().y < 0)
                enemy10.flip(false, true);
            if (enemy11.b2body.getLinearVelocity().y < 0)
                enemy11.flip(false, true);
            if (enemy12.b2body.getLinearVelocity().y < 0)
                enemy12.flip(false, true);
            if (enemy13.b2body.getLinearVelocity().y < 0)
                enemy13.flip(false, true);
            if (enemy14.b2body.getLinearVelocity().y < 0)
                enemy14.flip(false, true);
            if (enemy15.b2body.getLinearVelocity().y < 0)
                enemy15.flip(false, true);

        }



        hud.update(dt);

        //track player with gamecam
        gamecam.position.x = 1.2f;
        if (player.currentState != Player.State.DEAD)
        gamecam.position.y = 1.26f + (player.b2body.getPosition().y * 0.2f);
        gamecam.update();
        //only render what gamecam can see
        renderer.setView(gamecam);
    }


    @Override
    public void render(float delta) { //called repeatedly
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render game map
        renderer.render();

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        //game.batch.draw(background, .36f, 0, MainGame.V_WIDTH / MainGame.PPM, (MainGame.V_HEIGHT + 45) / MainGame.PPM);
        game.batch.draw(background, groundPos1.x + .35f, groundPos1.y, MainGame.V_WIDTH / MainGame.PPM, (MainGame.V_HEIGHT + 45) / MainGame.PPM);
        game.batch.draw(background, groundPos2.x + .35f, groundPos2.y, MainGame.V_WIDTH / MainGame.PPM, (MainGame.V_HEIGHT + 45) / MainGame.PPM);


        player.draw(game.batch);


        if (hud.isEasyDifficulty()) {
            enemy.draw(game.batch);
            enemy3.draw(game.batch);
            enemy4.draw(game.batch);
            enemy7.draw(game.batch);
            enemy8.draw(game.batch);
            enemy10.draw(game.batch);
            game.batch.end();
        }else if (hud.isMediumDifficulty()) {
            enemy.draw(game.batch);
            enemy1.draw(game.batch);
            enemy2.draw(game.batch);
            enemy3.draw(game.batch);
            enemy4.draw(game.batch);
            enemy5.draw(game.batch);
            enemy6.draw(game.batch);
            enemy7.draw(game.batch);
            enemy8.draw(game.batch);
            enemy9.draw(game.batch);
            game.batch.end();
        } else if (hud.isHardDifficulty()) {
            enemy.draw(game.batch);
            enemy1.draw(game.batch);
            enemy2.draw(game.batch);
            enemy3.draw(game.batch);
            enemy4.draw(game.batch);
            enemy5.draw(game.batch);
            enemy6.draw(game.batch);
            enemy7.draw(game.batch);
            enemy8.draw(game.batch);
            enemy9.draw(game.batch);
            enemy10.draw(game.batch);
            enemy11.draw(game.batch);
            enemy12.draw(game.batch);
            game.batch.end();
        } else if (hud.isReallyHardDifficulty()) {
            enemy.draw(game.batch);
            enemy1.draw(game.batch);
            enemy2.draw(game.batch);
            enemy3.draw(game.batch);
            enemy4.draw(game.batch);
            enemy5.draw(game.batch);
            enemy6.draw(game.batch);
            enemy7.draw(game.batch);
            enemy8.draw(game.batch);
            enemy9.draw(game.batch);
            enemy10.draw(game.batch);
            enemy11.draw(game.batch);
            enemy12.draw(game.batch);
            enemy13.draw(game.batch);
            enemy14.draw(game.batch);
            enemy15.draw(game.batch);
            game.batch.end();
        }

        //what is shown via camera
            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
        }




         if (player.currentState != Player.State.DEAD) {

             if (player.b2body.getPosition().y < 0.27 && player.b2body.getPosition().y > 0.25 && player.b2body.getPosition().x < 1.22 && player.b2body.getPosition().x > 1.18) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 0.77 && player.b2body.getPosition().y > 0.74 && player.b2body.getPosition().x < 1.22 && player.b2body.getPosition().x > 1.18) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 1.26 && player.b2body.getPosition().y > 1.23 && player.b2body.getPosition().x < 1.22 && player.b2body.getPosition().x > 1.18) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 1.74 && player.b2body.getPosition().y > 1.7 && player.b2body.getPosition().x < 1.22 && player.b2body.getPosition().x > 1.18) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 2.2 && player.b2body.getPosition().y > 2.17 && player.b2body.getPosition().x < 1.22 && player.b2body.getPosition().x > 1.18) {
                 player.b2body.setLinearVelocity(0, 0);
             }

             if (player.b2body.getPosition().y < 0.27 && player.b2body.getPosition().y > 0.25 && player.b2body.getPosition().x < 0.72 && player.b2body.getPosition().x > 0.7) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 0.77 && player.b2body.getPosition().y > 0.74 && player.b2body.getPosition().x < 0.72 && player.b2body.getPosition().x > 0.7) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 1.26 && player.b2body.getPosition().y > 1.23 && player.b2body.getPosition().x < 0.72 && player.b2body.getPosition().x > 0.7) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 1.74 && player.b2body.getPosition().y > 1.7 && player.b2body.getPosition().x < 0.72 && player.b2body.getPosition().x > 0.7) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 2.2 && player.b2body.getPosition().y > 2.17 && player.b2body.getPosition().x < 0.72 && player.b2body.getPosition().x > 0.7) {
                 player.b2body.setLinearVelocity(0, 0);
             }

             if (player.b2body.getPosition().y < 0.27 && player.b2body.getPosition().y > 0.25 && player.b2body.getPosition().x < 1.69 && player.b2body.getPosition().x > 1.66) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 0.77 && player.b2body.getPosition().y > 0.74 && player.b2body.getPosition().x < 1.69 && player.b2body.getPosition().x > 1.66) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 1.26 && player.b2body.getPosition().y > 1.23 && player.b2body.getPosition().x < 1.69 && player.b2body.getPosition().x > 1.66) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 1.74 && player.b2body.getPosition().y > 1.7 && player.b2body.getPosition().x < 1.69 && player.b2body.getPosition().x > 1.66) {
                 player.b2body.setLinearVelocity(0, 0);
             }
             if (player.b2body.getPosition().y < 2.2 && player.b2body.getPosition().y > 2.17 && player.b2body.getPosition().x < 1.69 && player.b2body.getPosition().x > 1.66) {
                 player.b2body.setLinearVelocity(0, 0);
             }
         }
    }


    public boolean gameOver() {
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 1){
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public void resize(int width, int height) {
        //Viewport is adjusted to know what the screen size is
        gamePort.update(width, height);
    }


    public TiledMap getMap(){
        return  map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        atlas.dispose();
        groundEnemyAtlas.dispose();
        flyAtlas.dispose();
        background.dispose();
        if (hud.isEasyDifficulty()) {

        } else if (hud.isEasyDifficulty()) {
            enemy.dispose();
            enemy3.dispose();
            enemy4.dispose();
            enemy7.dispose();
            enemy8.dispose();
            enemy10.dispose();
        } else if (hud.isMediumDifficulty()) {
            enemy.dispose();
            enemy1.dispose();
            enemy2.dispose();
            enemy3.dispose();
            enemy4.dispose();
            enemy5.dispose();
            enemy6.dispose();
            enemy7.dispose();
            enemy8.dispose();
            enemy9.dispose();
        } else if (hud.isHardDifficulty()) {
            enemy.dispose();
            enemy1.dispose();
            enemy2.dispose();
            enemy3.dispose();
            enemy4.dispose();
            enemy5.dispose();
            enemy6.dispose();
            enemy7.dispose();
            enemy8.dispose();
            enemy9.dispose();
            enemy10.dispose();
            enemy11.dispose();
            enemy12.dispose();
        } else if (hud.isReallyHardDifficulty()) {
            enemy.dispose();
            enemy1.dispose();
            enemy2.dispose();
            enemy3.dispose();
            enemy4.dispose();
            enemy5.dispose();
            enemy6.dispose();
            enemy7.dispose();
            enemy8.dispose();
            enemy9.dispose();
            enemy10.dispose();
            enemy11.dispose();
            enemy12.dispose();
            enemy13.dispose();
            enemy14.dispose();
            enemy15.dispose();
        }
        hud.dispose();

    }

    public Player getPlayer() {
        return player;

    }

    public void updateGround(){
        if (gamecam.position.x - (gamecam.viewportWidth / 2) > groundPos1.x + background.getWidth() / MainGame.PPM) {
            groundPos1.add((background.getWidth() * 2) / MainGame.PPM, 0);
            System.out.print("fuck off");
        }
        System.out.println(gamecam.position.x + "   " + gamecam.viewportWidth + "   " + groundPos1.x + "   " + background.getWidth());
        if (gamecam.position.x - (gamecam.viewportWidth / 2) > groundPos2.x + background.getWidth() / MainGame.PPM)
            groundPos2.add((background.getWidth() * 2) / MainGame.PPM, 0);


    }

}
