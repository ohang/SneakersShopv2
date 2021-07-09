package com.mymario.game.Sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mymario.game.MyMario;
import com.mymario.game.Scenes.Hud;
import com.mymario.game.Screens.PlayScreen;



import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled;

public class Nikol extends Sprite {
    public enum State{ FALLING,JUMPING,STANDING,MEGING,RUNNING};
    public State currentState ;
    public State previousState;

    public static  World world;
    public  static  Body b2body;
    public float Nikx;
    public float Niky;
    public float x;
    public float y;
    private TextureRegion marioStand;
    private BodyDef bdef;

    private Animation marioRun;
    private Animation megaRun;
    private Animation marioJump;
    private float stateTimer;
    private float stateTime;
    private boolean RunningRight;
    boolean destroyed;
    static boolean  setToDestroy;
    public static int life;
    public MyMario game;
    protected  PlayScreen screen;




    public Nikol(PlayScreen screen,float Nikx,float Niky){
        super(screen.getAtlas().findRegion("RUNNIKOL"));
       // super(screen.getAtlas().findRegion("little_mario"));
        this.screen = screen;

        this.world = screen.getWorld();
        this.Nikx=Nikx;
        this.Niky=Niky;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer=0;
        stateTime=0;
        //life=3;
        RunningRight=true;
        defineMario(Nikx,Niky);



        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 2,354,409,350));
     //   frames.add(new TextureRegion(getTexture(), 411,354,409,350));

        //  frames.add(new TextureRegion(getTexture(), 411,354,409,350));
      //  frames.add(new TextureRegion(getTexture(), 820,354,409,350));



        for ( int i = 1;i<5;i++)

            frames.add(new TextureRegion(getTexture(), (i*400),354,400,350));


        marioRun = new Animation(0.08f,frames);
        frames.clear();

        for ( int i = 0;i<3;i++)

            frames.add(new TextureRegion(getTexture(), i*435+3,1058,434,350));
        megaRun = new Animation(0.08f,frames);
        frames.clear();


        for ( int i = 0;i<5;i++)

            frames.add(new TextureRegion(getTexture(), i*400+10,706,394,350));
        marioJump = new Animation(0.1f,frames);
        frames.clear();


        marioStand = new TextureRegion(getTexture(),2,1410,400,350);
        setBounds(0,-10,36/MyMario.PPM,36/MyMario.PPM);
        setRegion(marioStand); // skzbi  patker

    }

    public void update(float dt){
        stateTime+=dt;

        if(setToDestroy && !destroyed &&life!=0) {
            life-=1;


         x= b2body.getPosition().x;
         y = b2body.getPosition().y;
       //    setRegion(new TextureRegion(getTexture(),0,0,0,0));

           setBounds(getX(),getY(),0/MyMario.PPM,0/MyMario.PPM);

           b2body.setActive(false);
           PlayScreen.voiceShoot.shootbody.setActive(false);


            destroyed = true;
            setToDestroy=false;
            stateTime=0;
        }

        if (stateTime>2 && destroyed && life!=0) {
            b2body.setActive(true);

            setBounds(0,0,36/MyMario.PPM,36/MyMario.PPM);
            setRegion(marioStand);


            screen.getSprite().begin();

            b2body.setTransform(b2body.getPosition().x+0.7f,Niky/MyMario.PPM,0);
            this.draw(screen.getSprite());
            screen.getSprite().end();


            PlayScreen.buttonmega.setTouchable(Touchable.enabled);
            PlayScreen.buttonup.setTouchable(Touchable.enabled);


            destroyed=false;
           stateTime=0;
        }




        setPosition(b2body.getPosition().x -getWidth()/2,b2body.getPosition().y-getHeight()/2);
        //kapuma patker animaciayi het
        setRegion(getFrame(dt)); // texadruma animacian

        if (PlayScreen.voiceShoot !=null){
            Gdx.app.log("exela","update");
            PlayScreen.voiceShoot.update(dt);}


    }

    public TextureRegion getFrame(float dt){

        currentState=getState();
        TextureRegion region;

        switch (currentState){
            case JUMPING:
                region= (TextureRegion) marioJump.getKeyFrame(stateTimer);
            break;

            case RUNNING:
                region= (TextureRegion) marioRun.getKeyFrame(stateTimer,true);
                break;
            case MEGING:
                region= (TextureRegion) megaRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        if ((b2body.getLinearVelocity().x<0 || !RunningRight)&& region.isFlipX()){
            region.flip(true,false);
            RunningRight = false;
        } else if ((b2body.getLinearVelocity().x>0 || RunningRight) && region.isFlipX()){
            region.flip(true,false);
            RunningRight= true;

        }

        stateTimer = currentState == previousState ? stateTimer + dt:0;
        previousState=currentState;
        return  region;
    };



    public State getState(){
        if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y<0 && previousState==State.JUMPING))
            return State.JUMPING;

        if (b2body.getLinearVelocity().y<0)
            return State.FALLING;
        if(PlayScreen.buttonmega.getClickListener().isPressed()){
            return State.MEGING;
        }

        else if(b2body.getLinearVelocity().x !=0)
            return State.RUNNING;
        else return State.STANDING;
    }
    public  void defineMario(float Nikx,float Niky){



        bdef = new BodyDef();
        bdef.position.set(Nikx/MyMario.PPM,Niky/MyMario.PPM);
        bdef.type =BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

         FixtureDef  fdef = new FixtureDef();
        CircleShape shape = new CircleShape();




        shape.setRadius(16/MyMario.PPM);


        fdef.filter.categoryBits =MyMario.MARIO_BIT;
        fdef.filter.maskBits =MyMario.GROUND_BIT | MyMario.POLICE_BIT |
        MyMario.ENEMY_BIT |MyMario.OBJECT_BIT|MyMario.ENEMY_HEAD_BIT|MyMario.SANITEK_BIT;




        fdef.shape =shape;

        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();

        head.set(new Vector2(-2/MyMario.PPM,17/MyMario.PPM),new Vector2(2/MyMario.PPM,17/MyMario.PPM));


        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");

    }

    public static void GameOver(){

        Gdx.app.log("GAME  OVER  ", "SANITEK");
        ;
    }

    public static void setToDestroy(){
        setToDestroy = true;
        PlayScreen.buttonmega.setTouchable(Touchable.disabled);
        PlayScreen.buttonup.setTouchable(Touchable.disabled);


    }


    public void draw(Batch batch){
        super.draw(batch);


        if (PlayScreen.voiceShoot.shootbody.isActive()) {
            PlayScreen.voiceShoot.draw(batch);
        }
    }



}
