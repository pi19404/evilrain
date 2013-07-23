package com.alex.rain.models;

import com.alex.rain.managers.TextureManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

/**
 * @author: Alexander Shubenkov
 * @since: 16.07.13
 */

public class Home extends DynamicActor {
    public Home() {
        texture = TextureManager.getInstance().getTexture("home1.png");
        sprite = new Sprite(texture);
        setBodyBox(32, 100);
    }

    @Override
    public void createPhysicsActor(World physicsWorld) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth() / 2, getHeight() / 2);
        offset.set(-getWidth() / 2, -getHeight() / 2);
        sprite.setOrigin(getWidth() / 2, getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;
        fixtureDef.friction = 10.4f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.resetMassData();

        polygonShape.dispose();
    }
}
