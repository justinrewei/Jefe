package com.xyxean.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.xyxean.MainGame;
import com.xyxean.sprites.Player;

/**
 * Created by justinwei on 2/28/2016.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case MainGame.PLAYER_BIT | MainGame.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MainGame.PLAYER_BIT)
                    if (fixB.getUserData() != null)
                        ((Player) fixB.getUserData()).playerHit();
                    else if (fixA.getUserData() != null)
                        ((Player) fixA.getUserData()).playerHit();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
