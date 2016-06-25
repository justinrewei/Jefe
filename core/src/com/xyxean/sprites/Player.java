package com.xyxean.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.xyxean.MainGame;
import com.xyxean.screens.PlayScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.Timer;

/**
 * Created by justinwei on 2/26/2016.
 */
public class Player extends Sprite {

    public enum State { RUNNING, JUMPING, DEAD }
    public State currentState;
    public State previousState;
    public World world; //world to carry sprite
    public Body b2body;
    private TextureRegion playerJump;
    private Animation playerDead;
    private Animation playerRun;
    boolean playerIsDead;
    private float stateTimer; //track how long we are in a state
    private PlayScreen screen;

    public Player(PlayScreen screen){
        super(screen.getFlyAtlas().findRegion("mon3_sprite_base"));
        this.world = screen.getWorld();
        this.screen = screen;

        currentState = getState();
        previousState = State.RUNNING;
        stateTimer = 0;
        definePlayer();

        //create an array of textureregions to pass to constructor
        Array<TextureRegion> frames = new Array<TextureRegion>();

        frames.add(new TextureRegion(getTexture(), 13, 15, 33, 36));
        frames.add(new TextureRegion(getTexture(), 74, 14, 37, 36));
        frames.add(new TextureRegion(getTexture(), 133, 15, 48, 33));
        frames.add(new TextureRegion(getTexture(), 198, 19, 45, 30));
        frames.add(new TextureRegion(getTexture(), 266, 14, 37, 36));
        playerRun = new Animation(0.1f, frames);

        playerJump = new TextureRegion(getTexture(), 136, 0, 18, 24);
        setBounds(0, 0, 24 / MainGame.PPM, 30 / MainGame.PPM);
        setRegion(playerJump);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }




    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case DEAD:
                region = playerRun.getKeyFrame(stateTimer, false);
            case JUMPING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true); //true is whether it loops or not
                break;
            default:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    public State getState() {
         if (playerIsDead)
             return State.DEAD;
        else
            return State.RUNNING;
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(120 / MainGame.PPM, 126 / MainGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / MainGame.PPM);

        fdef.filter.categoryBits = MainGame.PLAYER_BIT;
        fdef.filter.maskBits = MainGame.ENEMY_BIT | MainGame.GROUND_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void playerHit() {
        if (!isDead()) {
            playerIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = MainGame.NULL_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.setLinearVelocity(new Vector2(0, -6f));
        }
    }

    public boolean isDead(){
        return playerIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void draw(Batch batch){
            super.draw(batch);
        }
    }










