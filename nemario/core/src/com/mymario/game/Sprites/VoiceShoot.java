package com.mymario.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mymario.game.MyMario;
import com.mymario.game.Screens.PlayScreen;



public class VoiceShoot extends Sprite {
    public  static  boolean setToDestroy ;
    public boolean Destroyed;

    private float stateTime;


    protected World world;
    protected  PlayScreen screen;
    public Body shootbody;
    public TextureRegion vshoot;
    public VoiceShoot(PlayScreen screen,float x, float y) {

        this.world = screen.getWorld();
        this.screen = screen;

        setPosition(x, y);


        definevoice();
        setToDestroy=false;
        Destroyed=false;





    }
    public void update(float dt){
        stateTime+=dt;

            Gdx.app.log(shootbody.getLinearVelocity().x + "kfkdfjd", "          ");





        if(setToDestroy && !Destroyed){

            shootbody.setActive(false);
          //  setBounds(shootbody.getPosition().x+1f, shootbody.getPosition().y - shootbody.getPosition().y / 10, 0 / MyMario.PPM, 0 / MyMario.PPM);


            setToDestroy=false;
        }
            setRegion(new TextureRegion(screen.getAtlas().findRegion("walk"), 440, 1415, 60, 90)); // skzbi  patker
            setBounds(shootbody.getPosition().x, shootbody.getPosition().y - shootbody.getPosition().y / 20, 10/ MyMario.PPM, 10 / MyMario.PPM);




    }


    protected void definevoice() {


        BodyDef shootdef = new BodyDef();

        shootdef.position.set(getX(),getY());

        shootdef.type =BodyDef.BodyType.KinematicBody;

        shootbody = world.createBody(shootdef);

        FixtureDef shootfdef = new FixtureDef();


        CircleShape shoot = new CircleShape();


        shoot.setRadius(8/MyMario.PPM);



        shootfdef.filter.categoryBits =MyMario.VOICE_BIT;
      shootfdef.filter.maskBits =MyMario.GROUND_BIT | MyMario.POLICE_BIT |
              MyMario.ENEMY_BIT |MyMario.OBJECT_BIT|MyMario.ENEMY_HEAD_BIT|MyMario.SANITEK_BIT;


        shootbody.setLinearVelocity(new Vector2(2f, 0f));

        shootfdef.shape =shoot;
        shootbody.createFixture(shootfdef).setUserData(this);





    }


    public static void setToDestroy(){
        setToDestroy = true;


    }

}
