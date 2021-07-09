package com.mymario.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mymario.game.AdsController;
import com.mymario.game.MyMario;

public class MainMenuScreen implements Screen {
    private SpriteBatch batch;
    private Texture ttrSplash;
    private Stage stage;
    private ImageButton pauseButton;
    private ImageButton playButton;
    private ImageButton optionButton;
    private ImageButton quitButton;
    private ImageButton musicButton;
    private ImageButton soundButton;
    private final AdsController adsControllerr;

    public  boolean MusicOn;
    private Music music;
   // public  static  Preferences prefs;

    private   FreeTypeFontGenerator fontGenerator;
    private   FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private   BitmapFont font;

    private MyMario game;

    public MainMenuScreen(MyMario game,AdsController adsControllerr) {

        super();
        this.game=game;
        this.adsControllerr=adsControllerr;
        batch = new SpriteBatch();
        ttrSplash = new Texture("mainback.png");
        MusicOn=true;
        music= MyMario.manager.get("audio/music/imqayl.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();



    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void show() {



        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui



        final ImageButton.ImageButtonStyle playstyle = new ImageButton.ImageButtonStyle();
        playstyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("play_normal.png")));
        playstyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("play_hover.png")));

        playButton = new ImageButton(playstyle);
        playButton.setStyle(playstyle);
        playButton.setPosition(stage.getWidth()*0.7f,stage.getHeight()*0.55f);
        playButton.setSize(stage.getWidth()*0.2f,stage.getWidth()*0.1f);
        stage.addActor(playButton);



        final ImageButton.ImageButtonStyle quitstyle = new ImageButton.ImageButtonStyle();
        quitstyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("quit_normal.png")));
        quitstyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("quit_hover.png")));


        quitButton = new ImageButton(quitstyle);
        quitButton.setStyle(quitstyle);
        quitButton.setPosition(stage.getWidth()*0.7f,stage.getHeight()*0.25f);
        quitButton.setSize(stage.getWidth()*0.2f,stage.getWidth()*0.1f);
        stage.addActor(quitButton);


        final ImageButton.ImageButtonStyle musicstyle = new ImageButton.ImageButtonStyle();
        musicstyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("music_normal.png")));
        musicstyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("music_hover.png")));
        musicstyle.checked= new TextureRegionDrawable(new Texture(Gdx.files.internal("musicoff_normal.png")));


        musicButton = new ImageButton(musicstyle);
        musicButton.setStyle(musicstyle);
        musicButton.setPosition(stage.getWidth()*0.01f,stage.getHeight()*0.85f);
        musicButton.setSize(stage.getWidth()*0.07f,stage.getWidth()*0.07f);
        stage.addActor(musicButton);

        final ImageButton.ImageButtonStyle soundstyle = new ImageButton.ImageButtonStyle();
        soundstyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("sound_normal.png")));
        soundstyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("sound_hover.png")));
        soundstyle.checked= new TextureRegionDrawable(new Texture(Gdx.files.internal("soundoff_normal.png")));





        fontGenerator= new FreeTypeFontGenerator(Gdx.files.internal("berlin.TTF"));
        fontParameter =new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size=(int)(stage.getHeight()*0.15);
        font = fontGenerator.generateFont(fontParameter);

        Table  table  = new Table();
         table.top();
        table.setFillParent(true);

        Label YourRecord=new Label("Your Steps", new Label.LabelStyle(font, Color.WHITE));
        Label RecordLabel=new Label(MyMario.prefs.getInteger("myrecord")+"", new Label.LabelStyle(font, Color.WHITE));

        table.padTop(stage.getHeight()*0.1f);
        table.add(YourRecord);
        table.row();
        table.add(RecordLabel).expandX();

        stage.addActor(table);


        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //  Gdx.app.log("CHANGE LISTNENR"," kdkkd");
              //  adsControllerr.showAd();
                game.setScreen(new PlayScreen(game.getGame(),"GYUMRILEV.tmx",0,32,32,5 ,adsControllerr));

            }
        });

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //  Gdx.app.log("CHANGE LISTNENR"," kdkkd");

                Gdx.app.exit();
            }
        });

        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //  Gdx.app.log("CHANGE LISTNENR"," kdkkd");
                if(MusicOn) {
                    music.pause();
                    MusicOn=false;
                }else {
                    music.play();
                    MusicOn = false;
                }

            }
        });







        //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void dispose() {
        ttrSplash.dispose();
        batch.dispose();
        stage.dispose();


    }


}