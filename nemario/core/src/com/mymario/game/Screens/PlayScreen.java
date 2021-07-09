package com.mymario.game.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mymario.game.AdsController;
import com.mymario.game.Items.Item;
import com.mymario.game.Items.ItemDef;
import com.mymario.game.Items.Mushroom;
import com.mymario.game.MyMario;
import com.mymario.game.Scenes.Hud;
import com.mymario.game.Sprites.Enemy;
import com.mymario.game.Sprites.Nikol;
import com.mymario.game.Sprites.Police;
import com.mymario.game.Sprites.Sanitek;
import com.mymario.game.Sprites.Shoot;
import com.mymario.game.Sprites.VoiceShoot;
import com.mymario.game.Tools.B2WorldCreator;
import com.mymario.game.Tools.WorldContactListener;


import java.awt.Font;
import java.util.PriorityQueue;

public class PlayScreen extends ActorGestureListener implements Screen, InputProcessor {

  public   ShapeRenderer shap = new ShapeRenderer();


    private MyMario game;
    public static AdsController playadcontrol;


    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    public Sanitek san;
    public static Table Dialogtable;
    public static OrthogonalTiledMapRenderer renderer;
    public static int MySteps;
    public  static VoiceShoot voiceShoot;
    public static Sound jumpsound;
    public static  Sound MerjirSound;
    public  static long id;
    public  static boolean SoundOn;
    public static FreeTypeFontGenerator fontGenerator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public static BitmapFont font;


    // button
    private JsonValue recordJSON;
    private Texture myTexture;
    private Texture myTextureup;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private TextureRegion myTextureRegionup;
    private static ImageButton soundButton;
    private ImageButton musicButton;
    private TextureRegionDrawable myTexRegionDrawableup;
    public static ImageButton buttonmega;
    public static ImageButton buttonup;

    public ImageButton pauseButton;
    public float Nikx;
    public float Niky;
    public String myLevel;
    public static int Life;
    public Shoot shoot;
    public  static float stateTime;
    public  int endlev;

   // public  static   VoiceShoot voiceShoot;


    Array<Police> goombas;
    // box2d
    private Stage stage;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Nikol player;
    public  static  B2WorldCreator creator;
    public static Vector2 NikolVelocity ;

    private static Music music;
    private static Boolean musicPlay;
    private  static  Integer count;
    private  Integer saveCount;

    private Array<Item> items;
    private PriorityQueue<ItemDef> itemsToSpawn;



    public  PlayScreen(MyMario game,String level,Integer count,float Nikx,float Niky,int Lifein,AdsController playadcontrol) {

        atlas = new TextureAtlas("megapack.pack");

        this.count=count;
        this.Nikx=Nikx;
        this.Niky=Niky;
        this.playadcontrol=playadcontrol;
        this.myLevel=level;
       Nikol.life=Lifein;
       Gdx.app.log(Nikol.life+"   LIFE OF BIKOL", " ");
        MySteps=count;
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MyMario.V_WITDH / MyMario.PPM, MyMario.V_HEIGHT / MyMario.PPM, gamecam);
        hud = new Hud(game.batch,300,0,0);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(level);




        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyMario.PPM);

        gamecam.position.set((gamePort.getWorldWidth() / 2), (gamePort.getWorldHeight() / 2), 0);

        world = new World(new Vector2(0, -10), true);

        b2dr = new Box2DDebugRenderer();
       // Gdx.app.log("START","goo"+Life);
        hud.levelLabel.setText("x"+Lifein);
      NikolVelocity=new Vector2(0.6f,0);
      // creator= new B2WorldCreator(this);
        player = new Nikol(this,Nikx,Niky);

        Gdx.input.setInputProcessor(this);

        world.setContactListener(new WorldContactListener());
        creator= new B2WorldCreator(this);
        B2WorldCreator.goombas = new Array<Police>();

        jumpsound =MyMario.manager.get("audio/sounds/jump.wav", Sound.class);
      //  jumpsound.setVolume(jumpsound.play(),0.0f);
        MerjirSound = MyMario.manager.get("audio/sounds/merjir.wav", Sound.class);





        music= MyMario.manager.get("audio/music/imqayl.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();

        items= new Array<Item>();
        itemsToSpawn = new PriorityQueue<ItemDef>();

        voiceShoot = new VoiceShoot(this, (Nikx+20)/100, Niky/100);
        voiceShoot.shootbody.setActive(false);

        musicPlay=true;
        SoundOn=true;
        TiledMap tiledMap = new TmxMapLoader().load(myLevel);

        MapProperties prop = tiledMap.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int mapPixelWidth = mapWidth * tilePixelWidth;
         endlev=mapPixelWidth/100;


    }


    public Nikol getPlayer(){
        return player;
    }

    public void handleSpawninItems(){
        if (!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type== Mushroom.class){
                items.add(new Mushroom(this,idef.position.x ,idef.position.y));

            }
        }
    }

    public TextureAtlas getAtlas(){
        return  atlas;
    }

public  void vShoot(){


if (!voiceShoot.shootbody.isActive() && Nikol.b2body.isActive()) {

    this.getSprite().begin();
    voiceShoot.shootbody.setTransform(player.getX()+0.4f, player.getY() + .2f, 0);
    voiceShoot.draw(this.getSprite());
    this.getSprite().end();
    voiceShoot.shootbody.setActive(true);

}
//}



}

    public void Shoot(){


        if (shoot == null) {

            shoot = new Shoot(this, player.getX()+0.1f, player.getY() + player.getY() * 1.5f);
            shoot.shootbody.setLinearVelocity(new Vector2(2f, 0f));
        }


    }
    @Override
    public void show() {
        creator.CreateObjects();
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui

        final ImageButton.ImageButtonStyle musicstyle = new ImageButton.ImageButtonStyle();
        musicstyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("music_normal.png")));
        musicstyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("music_hover.png")));
        musicstyle.checked= new TextureRegionDrawable(new Texture(Gdx.files.internal("musicoff_normal.png")));


        musicButton = new ImageButton(musicstyle);
        musicButton.setStyle(musicstyle);
        musicButton.setPosition(stage.getWidth()*0.01f,stage.getHeight()*0.87f);
        musicButton.setSize(stage.getWidth()*0.07f,stage.getWidth()*0.07f);
        stage.addActor(musicButton);

        final ImageButton.ImageButtonStyle soundstyle = new ImageButton.ImageButtonStyle();
        soundstyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("sound_normal.png")));
        soundstyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("sound_hover.png")));
        soundstyle.checked= new TextureRegionDrawable(new Texture(Gdx.files.internal("soundoff_normal.png")));


        soundButton = new ImageButton(soundstyle);
        soundButton.setStyle(soundstyle);
        soundButton.setPosition(stage.getWidth()*0.1f,stage.getHeight()*0.87f);
        soundButton.setSize(stage.getWidth()*0.07f,stage.getWidth()*0.07f);
        stage.addActor(soundButton);

        final ImageButton.ImageButtonStyle pausestyle = new ImageButton.ImageButtonStyle();
        pausestyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("pause.png")));
        pausestyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("pause_hover.png")));

        pauseButton = new ImageButton(pausestyle);
        pauseButton.setStyle(pausestyle);
        pauseButton.setPosition(5,stage.getHeight()-stage.getWidth()/14);
        pauseButton.setSize(stage.getWidth()/16,stage.getWidth()/16);




        final ImageButton.ImageButtonStyle megastyle = new ImageButton.ImageButtonStyle();
        megastyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("mega.png")));
        megastyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("mega_hover.png")));

        buttonmega = new ImageButton(megastyle);
        buttonmega.setStyle(megastyle);
        buttonmega.setPosition(stage.getWidth()/40,10);
        buttonmega.setSize(stage.getWidth()/8,stage.getWidth()/8);


        ImageButton.ImageButtonStyle upstyle = new ImageButton.ImageButtonStyle();
        upstyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("up_normal.png")));
        upstyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("up_hover.png")));

        buttonup = new ImageButton(upstyle);
        buttonup.setStyle(upstyle);
        buttonup.setPosition(stage.getWidth()-stage.getWidth()/40-stage.getWidth()/8,10);
        buttonup.setSize(stage.getWidth()/8,stage.getWidth()/8);

       if (PlayScreen.SoundOn){
           soundButton.isPressed();
       }

      // stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonup);
       // stage.addActor(pauseButton);
        stage.addActor(buttonmega);
        //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage);

        buttonup.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button){

             //   Gdx.app.log("taaap", "touch started at (" + x + ", " + y + ")");
            if (player.b2body.getLinearVelocity().y==0 ){
                 if (SoundOn) {
                     long id = jumpsound.play();
                     jumpsound.setVolume(id, 0.7f);
                   //  jumpsound.play();
                 }



               player.b2body.applyLinearImpulse(new Vector2(0.065f, 4.2f), player.b2body.getWorldCenter(), true);
            }

            }
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            };


            public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
               //Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
             //   player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);

            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            ///   Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
            }

            public boolean longPress(Actor screenX, float screenY, float pointer) {
             //   player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);

                return false;
            }




        });

        buttonmega.addListener(new ActorGestureListener() {
                                       public void tap(InputEvent event, float x, float y, int count, int button){

                                           if(SoundOn) {
                                               long id = MerjirSound.play();
                                               MerjirSound.setVolume(id, 0.7f);
                                         //      MerjirSound.play();
                                           }

                                           vShoot();



                                           if (player.b2body.getLinearVelocity().x<=1){
                                               player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);
                                           }

                                       }
                                       public boolean touchDragged(int screenX, int screenY, int pointer) {
                                           return false;
                                       };


                                       public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
                                           //Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                                           //   player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);

                                       }

                                       public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                                           ///   Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                                       }

            public boolean longPress(Actor screenX, float screenY, float pointer) {
                //   player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);

                return false;
            }




        });

        musicButton.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button){

          if(musicPlay) {
              music.pause();
              musicPlay=false;
          } else {
              music.play();
              musicPlay=true;
          }
            }
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            };


            public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //   player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);

            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                ///   Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
            }

            public boolean longPress(Actor screenX, float screenY, float pointer) {
                //   player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);

                return false;
            }




        });

        soundButton.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button){
             //   playadcontrol.showRewardad();
             if(SoundOn){
                 SoundOn=false;
             } else SoundOn=true;

                //jumpsound.setVolume(id,0);
            }
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            };


            public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //   player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);

            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                ///   Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
            }

            public boolean longPress(Actor screenX, float screenY, float pointer) {
                //   player.b2body.applyLinearImpulse(new Vector2(0.4f, 0), player.b2body.getWorldCenter(), true);

                return false;
            }




        });




/*        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
              //  Gdx.app.log("CHANGE LISTNENR"," kdkkd");

                NikolVelocity=new Vector2(0,0);
                Enemy.velocity=new Vector2(0,0);
                Enemy.shootVelocity=new Vector2(0,0);


                buttonup.setVisible(false);
                buttonmega.setVisible(false);
               }
        });




 */

        Dialogtable  = new Table();
        Dialogtable.top();
        Dialogtable.padTop(stage.getHeight()*0.3f);
        Dialogtable.setFillParent(true);
        Dialogtable.setVisible(false);

        final ImageButton.ImageButtonStyle closestyle = new ImageButton.ImageButtonStyle();
        closestyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("close_normal.png")));
        closestyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("close_hover.png")));

        ImageButton closeButton;
        closeButton = new ImageButton(closestyle);
        closeButton.setStyle(closestyle);
        //  continueButton.setPosition(stage.getWidth()*0.5f,stage.getHeight()*0.5f);
        // continueButton.setSize(stage.getWidth()*0.02f,stage.getWidth()*0.02f);
        stage.addActor(closeButton);


        final ImageButton.ImageButtonStyle continuestyle = new ImageButton.ImageButtonStyle();
        continuestyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("con_normal.png")));
        continuestyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("con_hover.png")));

        ImageButton continueButton;
        continueButton = new ImageButton(continuestyle);
        continueButton.setStyle(continuestyle);
      //  continueButton.setPosition(stage.getWidth()*0.5f,stage.getHeight()*0.5f);
      // continueButton.setSize(stage.getWidth()*0.02f,stage.getWidth()*0.02f);
    stage.addActor(continueButton);


        fontGenerator= new FreeTypeFontGenerator(Gdx.files.internal("berlin.TTF"));
        fontParameter =new FreeTypeFontGenerator.FreeTypeFontParameter();
         fontParameter.size=(int)(stage.getHeight()*0.1);
        font = fontGenerator.generateFont(fontParameter);


    Label    levelLabel=new Label("watch and continue", new Label.LabelStyle(font, Color.WHITE));
     Dialogtable.add(levelLabel).colspan(2);
     Dialogtable.row();
     Dialogtable.add(closeButton).size(stage.getWidth()*0.08f,stage.getWidth()*0.08f).padRight(stage.getWidth()*0.06f);

     Dialogtable.add(continueButton).size(stage.getWidth()*0.08f,stage.getWidth()*0.08f).padLeft(stage.getWidth()*0.06f);
      //  table.setDebug(true);

        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {

               playadcontrol.showRewardad();
            }
        });
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new GameOverScreen(game));            }
        });


 stage.addActor(Dialogtable);

    }



  // CHANGE MAP CHANGE MAP CHANGE MAP CHANGE MAPC HANGE MAP CHANGE MAP CHANGE MAP CHANGE MAP

  public void chanhgemap(){




       Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {

                game.setScreen(new PlayScreen(game.getGame(),"Yerevan.tmx",384,32,32,Nikol.life ,playadcontrol));


            }
        });

    }

    public void endgame(){




        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {

                game.setScreen(new tobecontinued(game));


            }
        });

    }
    public void handleInput(float dt){

      hud.countdownLabel.setText(String.format("%06d",(count+(int)player.b2body.getPosition().x)));

      saveCount=MySteps+(int)player.b2body.getPosition().x+count;



        if(player.b2body.getLinearVelocity().x<=1){
             player.b2body.applyLinearImpulse(NikolVelocity, player.b2body.getWorldCenter(),true);
        }

          //        CHANGE MAP  CHANGE MAP

      if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y==0.0){
         //   chanhgemap();


          player.b2body.applyLinearImpulse(new Vector2(0.25f,4f), player.b2body.getWorldCenter(),true);
        }
//
     //   if (Gdx.input.isTouched()){

      //      chanhgemap();
      //      player.b2body.applyLinearImpulse(new Vector2(0.5f,0f), player.b2body.getWorldCenter(),true);

      //  }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <=2)
         {



           //if( player.b2body.getPosition().x>=25.6){
          //     chanhgemap();
         //  }
              player.b2body.applyLinearImpulse(new Vector2(0.04f, 0), player.b2body.getWorldCenter(), true);
              //Gdx.app.log("Player position x",  player.b2body.getPosition().x +"");






         }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >=-2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
        }
    };

    public void  update(float dt){
        if(Nikol.life==0){
       //     game.setScreen(new GameOverScreen(game));
           Dialogtable.setVisible(true);

           // game.setScreen(new GameOverScreen(game));
        } else Dialogtable.setVisible(false);





        stateTime+=dt;
        handleInput(dt);
        handleSpawninItems();
        world.step(1/60f,6,2);
    //    gamecam.translate(new Vector2(0.001f,0));
        player.update(dt);

        for (Enemy enemy :creator.getGoombas()) {
            enemy.update(dt);
            if(enemy.getX() -player.getX()>=2f && enemy.getX() -player.getX()<=2.5f ){ // stexcum enq enemyin yerb player motikanum a
                     enemy.b2body.setActive(true);

            }}

        Gdx.app.log("MAP MAP MAP",map.getProperties().get("width",Integer.class)+"");
        Gdx.app.log("MAP MAP ",player.b2body.getPosition().x+"");



        if( player.b2body.getPosition().x>=endlev){
            if (myLevel=="GYUMRILEV.tmx"){
                chanhgemap();}
             else  endgame();
            }

            if (player.b2body.getPosition().y<=-1){
                Nikol.setToDestroy();
                player.b2body.setTransform(player.getX(),32/MyMario.PPM,0);
            }

    /*  if(enemy.b2body.isActive() && enemy.getX()-player.getX() >1.47 &&  enemy.getX()-player.getX() <1.5){
              voiceShoot = new VoiceShoot(this,player.getX(),player.getY());
             voiceShoot.shootbody.setLinearVelocity(new Vector2(2f,0f));
                voiceShoot.update(dt);

            }

     */




       if (voiceShoot!=null){
       voiceShoot.update(dt);}

       if (voiceShoot!=null){
           if (voiceShoot.getX()-player.getX()>1.5){
           // voiceShoot.shootbody.setActive(false);
             VoiceShoot.setToDestroy();

           }

       }

        hud.levelLabel.setText("x"+Nikol.life);
















        MyMario.prefs=Gdx.app.getPreferences("My Preferences");
        Gdx.app.log(MyMario.prefs.getInteger("myrecord")+"","RECORD RECORD");
        Gdx.app.log(count+"","RECORD COUNT COUNT");

        if (saveCount > MyMario.prefs.getInteger("myrecord")) {
        Gdx.app.log("preferanse","prefertANSE");
          MyMario.prefs.putInteger("myrecord",saveCount);
          MyMario.prefs.flush();

}











        for (Item item:items)
            item.update(dt);

            hud.update(dt);


     if (player.b2body.getPosition().x>2 && player.b2body.getPosition().x<endlev-2)  {
            gamecam.position.x = player.b2body.getPosition().x;
      }gamecam.update();
     renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();




    // b2dr.render(world,gamecam.combined); // yete komenti mech qces dzvuma sax

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        player.draw(game.batch);

       for (Enemy enemy :creator.getGoombas())
          enemy.draw(game.batch);






        for(Item item:items)
            item.draw(game.batch);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
       // Gdx.gl.glDisable(GL20.GL_BLEND);

    }



    @Override
    public void resize(int width, int height) {

        ;
        gamePort.update(width,height);

    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {





    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }


    public SpriteBatch getSprite(){
        return game.batch;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
