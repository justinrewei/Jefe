package com.xyxean.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.xyxean.MainGame;
import com.xyxean.screens.PlayScreen;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by justinwei on 2/28/2016.
 */
public  class Cat extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private PlayScreen screen;
    float xLoc, yLoc;
    private int aRandomInt, randomInt;
    public Cat(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.screen = screen;
         xLoc = x;
         yLoc = y;

        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getGroundEnemyAtlas().findRegion("kitty"), -1, -1, 104, 75));
        frames.add(new TextureRegion(screen.getGroundEnemyAtlas().findRegion("kitty"), 107, -1, 106, 75));
        frames.add(new TextureRegion(screen.getGroundEnemyAtlas().findRegion("kitty"), 223, -1, 105, 75));
        frames.add(new TextureRegion(screen.getGroundEnemyAtlas().findRegion("kitty"), 338, -1, 107, 75));
        walkAnimation = new Animation(0.1f, frames);

        setBounds(getX(), getY(), 30 / MainGame.PPM, 25 / MainGame.PPM);

        setToDestroy = false;
        destroyed = false;

        final Random randomGenerator = new Random();
        final Random arandomGenerator = new Random();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                randomInt = randomGenerator.nextInt(4);
                aRandomInt = arandomGenerator.nextInt(4);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 5000);

    }


    public void update(float dt) {

            if (b2body.getPosition().x > 2.5f) {
                switch (randomInt) {
                    case 0:
                        b2body.setLinearVelocity(velocity);
                        break;
                    case 1:
                        b2body.setLinearVelocity(velocity1);
                        break;
                    case 2:
                        b2body.setLinearVelocity(velocity2);
                        break;
                    case 3:
                        b2body.setLinearVelocity(velocity3);
                        break;
                }
            } else if (b2body.getPosition().x < 0) {
                switch (aRandomInt) {
                    case 0:
                        b2body.setLinearVelocity(xvelocity);
                        break;
                    case 1:
                        b2body.setLinearVelocity(xvelocity1);
                        break;
                    case 2:
                        b2body.setLinearVelocity(xvelocity2);
                        break;
                    case 3:
                        b2body.setLinearVelocity(xvelocity3);
                        break;
                }

            } else if (b2body.getPosition().y < -.7f) {
                switch (randomInt) {
                    case 0:
                        b2body.setLinearVelocity(xyvelocity);
                        break;
                    case 1:
                        b2body.setLinearVelocity(xyvelocity1);
                        break;
                    case 2:
                        b2body.setLinearVelocity(xyvelocity2);
                        break;
                    case 3:
                        b2body.setLinearVelocity(xyvelocity3);
                        break;
                }
            } else if (b2body.getPosition().y > 3.2f) {
                {
                    switch (aRandomInt) {
                        case 0:
                            b2body.setLinearVelocity(yvelocity);
                            break;
                        case 1:
                            b2body.setLinearVelocity(yvelocity1);
                            break;
                        case 2:
                            b2body.setLinearVelocity(yvelocity2);
                            break;
                        case 3:
                            b2body.setLinearVelocity(yvelocity3);
                            break;
                    }
                }
            } else if (b2body.getPosition().y < -2) {
                switch (randomInt) {
                    case 0:
                        b2body.setLinearVelocity(xyvelocity);
                        break;
                    case 1:
                        b2body.setLinearVelocity(xyvelocity1);
                        break;
                    case 2:
                        b2body.setLinearVelocity(xyvelocity2);
                        break;
                    case 3:
                        b2body.setLinearVelocity(xyvelocity3);
                        break;
                }
            } else if (b2body.getPosition().y > 3.5f) {
                {
                    switch (aRandomInt) {
                        case 0:
                            b2body.setLinearVelocity(yvelocity);
                            break;
                        case 1:
                            b2body.setLinearVelocity(yvelocity1);
                            break;
                        case 2:
                            b2body.setLinearVelocity(yvelocity2);
                            break;
                        case 3:
                            b2body.setLinearVelocity(yvelocity3);
                            break;
                    }
                }
            }
            stateTime += dt;
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }



    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / MainGame.PPM);
        fdef.filter.categoryBits = MainGame.ENEMY_BIT;
        fdef.filter.maskBits = MainGame.PLAYER_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1) {
            super.draw(batch);
        }
    }
}
