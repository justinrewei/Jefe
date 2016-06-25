package com.xyxean.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.xyxean.MainGame;
import com.xyxean.screens.PlayScreen;
import com.xyxean.sprites.Player;

/**
 * Created by justinwei on 2/27/2016.
 */
public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //BOX2d Variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PPM, (rect.getY() + rect.getHeight() / 2) / MainGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MainGame.PPM, rect.getHeight() / 2 / MainGame.PPM);
            fdef.shape = shape;

            fdef.filter.categoryBits = MainGame.GROUND_BIT;
            body.createFixture(fdef);
        }
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PPM, (rect.getY() + rect.getHeight() / 2) / MainGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MainGame.PPM, rect.getHeight() / 2 / MainGame.PPM);
            fdef.shape = shape;

            fdef.filter.categoryBits = MainGame.COLLISION_BIT;
            body.createFixture(fdef);
        }
    }
}
