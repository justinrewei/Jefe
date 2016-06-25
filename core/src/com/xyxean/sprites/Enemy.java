package com.xyxean.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.xyxean.screens.PlayScreen;

/**
 * Created by justinwei on 2/28/2016.
 */
public abstract class Enemy extends Sprite implements Disposable {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity, velocity1, velocity2, velocity3, xvelocity, xvelocity1, xvelocity2, xvelocity3, yvelocity, yvelocity1, yvelocity2, yvelocity3, xyvelocity, xyvelocity1, xyvelocity2, xyvelocity3;
    public Vector2 zvelocity, zvelocity1, zvelocity2, zvelocity3, xzvelocity, xzvelocity1, xzvelocity2, xzvelocity3;


    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2((float) -0.2, 0);
        velocity1 = new Vector2((float) -0.4, 0);
        velocity2 = new Vector2((float) -0.7, 0);
        velocity3 = new Vector2((float) -1, 0);

        xvelocity = new Vector2((float) 0.2, 0);
        xvelocity1 = new Vector2((float) 0.4, 0);
        xvelocity2 = new Vector2((float) 0.7, 0);
        xvelocity3 = new Vector2((float) 1, 0);

        xyvelocity = new Vector2(0,(float) 0.2);
        xyvelocity1 = new Vector2(0,(float) 0.4);
        xyvelocity2 = new Vector2(0,(float) 0.7);
        xyvelocity3 = new Vector2(0,(float) 1);

        yvelocity = new Vector2(0,(float) -0.2);
        yvelocity1 = new Vector2(0,(float) -0.4);
        yvelocity2 = new Vector2(0,(float) -0.7);
        yvelocity3 = new Vector2(0,(float) -1);

        xzvelocity = new Vector2((float) 0.2, (float) 0.2);
        xzvelocity = new Vector2((float) 0.4, (float) 0.4);
        xzvelocity = new Vector2((float) 0.7, (float) 0.7);
        xzvelocity = new Vector2((float) 1, (float) 1);

        zvelocity = new Vector2((float) -0.2, (float) -0.2);
        zvelocity = new Vector2((float) -0.4, (float) -0.4);
        zvelocity = new Vector2((float) -0.7, (float) -0.7);
        zvelocity = new Vector2((float) -1, (float) -1);


    }

    protected abstract void defineEnemy();

    @Override
    public void dispose() {
        dispose();
    }
}
