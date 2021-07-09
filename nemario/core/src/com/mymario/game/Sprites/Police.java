package com.mymario.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mymario.game.MyMario;
import com.mymario.game.Scenes.Hud;
import com.mymario.game.Screens.PlayScreen;

public class Police extends Enemy {
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    public   boolean setToDestroy ;
    public boolean Destroyed;
    protected float x;
    protected  float y;

    public Police(PlayScreen screen, float x, float y) {

        super(screen, x, y);



        frames = new Array<TextureRegion>();
        for (int i =2; i<6;i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("walk"), i * 301, 0, 308, 350));
        }

        walkAnimation = new Animation(0.1f,frames);
            //stateTime=0;
            setBounds(getX(),getY(),32/MyMario.PPM,32/MyMario.PPM);
            setToDestroy=false;
            Destroyed=false;

    }

    public void update(float dt){
        stateTime+=dt;


    if(setToDestroy && !Destroyed){

          world.destroyBody(b2body);
        Destroyed=true;
           setRegion(new TextureRegion(screen.getAtlas().findRegion("NIKStand"),32,32,16,16));
         stateTime=0;
    }
     else if (!Destroyed) {
           b2body.setLinearVelocity(velocity);





        setPosition(b2body.getPosition().x - getWidth() / 2,getY() );

        setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));

        if(b2body.getLinearVelocity().x==0){
            Gdx.app.log("police","Speed");
            setRegion(new TextureRegion(screen.getAtlas().findRegion("walk"),300,0,286,350));

        }


        }


    }
    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(),getY()+16/MyMario.PPM);

        bdef.type =BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();


        CircleShape shape = new CircleShape();


        shape.setRadius(16/MyMario.PPM);



        fdef.filter.categoryBits =MyMario.POLICE_BIT;
        fdef.filter.maskBits =MyMario.GROUND_BIT | MyMario.MARIO_BIT|MyMario.OBJECT_BIT | MyMario.VOICE_BIT;
        ;






        fdef.shape =shape;
        b2body.createFixture(fdef).setUserData(this);






    }


    @Override
    public void hitOnHead() {
        setToDestroy =true;
    }

    public void Policedead(){
        setToDestroy=true;
    }
    public void draw(Batch batch){
        if(!Destroyed || stateTime < 1)
            super.draw(batch);
    }



}
