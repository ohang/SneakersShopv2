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

public class tobecontinued implements Screen {
    private SpriteBatch batch;
    private Texture ttrSplash;

    private MyMario game;
    private Stage stage;
    private ImageButton playButton;



    public tobecontinued(MyMario game) {
        super();
        this.game=game;
        batch = new SpriteBatch();
        ttrSplash = new Texture("tobee.jpg");
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