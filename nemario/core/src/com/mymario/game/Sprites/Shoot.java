package com.mymario.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mymario.game.MyMario;
import com.mymario.game.Screens.PlayScreen;


public class Shoot extends Enemy {
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    public boolean setToDestroy ;
    public boolean Destroyed;


    public Shoot(PlayScreen screen, float x, float y) {

        super(screen, x, y);



/*
        frames = new Array<TextureRegion>();
        for (int i =0; i<2;i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RUNNIKOL"),i*400,2,400,350));
        walkAnimation = new Animation(0.4f,frames);
        stateTime=0;
        setBounds(getX(),getY(),32/ MyMario.PPM,32/MyMario.PPM);
        setToDestroy=false;
        Destroyed=false;
*/
        TextureRegion shoot;

        shoot =new TextureRegion(screen.getAtlas().findRegion("NIKStand"),70,20,35,35);
        setBounds(getX(),getY(),16/MyMario.PPM,16/MyMario.PPM);
        //setRegion(new TextureRegion(screen.getAtlas().findRegion("NIKStand"),32,32,16,16));

        setRegion(shoot); // skzbi  patker
    }

    public void update(float dt){
        stateTime+=dt;

Gdx.app.log(shootbody.getLinearVelocity().x+"kfkdfjd","          ");



    }
    @Override
    protected void defineEnemy() {
        BodyDef shootdef = new BodyDef();

        shootdef.position.set(getX(),getY()+32/MyMario.PPM);

        shootdef.type =BodyDef.BodyType.KinematicBody;

        shootbody = world.createBody(shootdef);

        FixtureDef shootfdef = new FixtureDef();


        CircleShape shoot = new CircleShape();


        shoot.setRadius(12/MyMario.PPM);


        shootfdef.filter.categoryBits =MyMario.VOICE_BIT;
        shootfdef.filter.maskBits =MyMario.GROUND_BIT | MyMario.POLICE_BIT|MyMario.OBJECT_BIT | MyMario.VOICE_BIT;
        ;


        shootfdef.shape =shoot;
        shootbody.createFixture(shootfdef).setUserData(this);





    }


    @Override
    public void hitOnHead() {
        setToDestroy =true;
    }
}
