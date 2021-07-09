package com.mymario.game.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mymario.game.MyMario;
import com.mymario.game.Screens.PlayScreen;

import javax.lang.model.element.VariableElement;

public class Mushroom extends Item {
    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("RUNNIKOL"),0,0,400,365);
        velocity = new Vector2(0,0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type =BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(8/ MyMario.PPM);

        fdef.shape =shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use() {
          destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        body.setLinearVelocity(velocity);
    }
}
