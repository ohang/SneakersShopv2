package com.mymario.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mymario.game.MyMario;

public class GameOverScreen implements Screen {
    private SpriteBatch batch;
    private Texture ttrSplash;

    private MyMario game;
    private Stage stage;
    private ImageButton playButton;



    public GameOverScreen(MyMario game) {
        super();
        this.game=game;
        batch = new SpriteBatch();
        ttrSplash = new Texture("gameov.png");
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
        playstyle.up= new TextureRegionDrawable(new Texture(Gdx.files.internal("newgame.png")));
        playstyle.down= new TextureRegionDrawable(new Texture(Gdx.files.internal("newpress.png")));

        playButton = new ImageButton(playstyle);
        playButton.setStyle(playstyle);
        playButton.setPosition(stage.getWidth()*0.5f-stage.getWidth()*0.15f,stage.getHeight()*0.5f);
        playButton.setSize(stage.getWidth()*0.3f,stage.getWidth()*0.12f);
        stage.addActor(playButton);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //  Gdx.app.log("CHANGE LISTNENR"," kdkkd");
                game.setScreen(new PlayScreen(game.getGame(),"GYUMRILEV.tmx",0,32,32, 3,PlayScreen.playadcontrol));

            }
        });
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void dispose() {
        ttrSplash.dispose();
        batch.dispose();
    }
}