package com.mymario.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mymario.game.MyMario;
import com.mymario.game.Screens.PlayScreen;
import com.mymario.game.Tools.BaseActor;

public class Hud  implements Disposable {
    public Stage stage;
    private Viewport viewport;
    public static String HEARTIMG;
    public  ImageButton buttry;
    private Integer worldTimer;
    private float timeCount;
    private ImageButton musicButton;
    private  ImageButton soundButton;
    private static Integer score;
    public static FreeTypeFontGenerator fontGenerator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public static BitmapFont font;


    public Label countdownLabel;

    public static BaseActor healthIcon;
    static Label scoreLabel;
    public Label timeLabel;
    public Label levelLabel;
    Label worldLabel;
    Label marioLabel;
    public  ImageButton pauseButton;

    TextureAtlas atlas;
    Skin skin;
    public Table table;


    public Hud(SpriteBatch sb,Integer worldTimer,float timeCount,Integer score){
        this.worldTimer=worldTimer;
        this.timeCount=timeCount;
        this.score=score;


        viewport = new FitViewport(MyMario.V_WITDH,MyMario.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport ,sb);




          table  = new Table();
        table.top();
        table.setFillParent(true);

         healthIcon = new BaseActor(0,0,stage);
        healthIcon.loadTexture("healticon.png");
        healthIcon.setSize(stage.getWidth()*0.05f,stage.getWidth()*0.05f);

        BaseActor run = new BaseActor(0,0,stage);
        run.loadTexture("runicon.png");
        run.setSize(stage.getWidth()*0.05f,stage.getWidth()*0.05f);


        fontGenerator= new FreeTypeFontGenerator(Gdx.files.internal("berlin.TTF"));
        fontParameter =new FreeTypeFontGenerator.FreeTypeFontParameter();
       // fontParameter.size=(int)(stage.getHeight()*0.1);
        font = fontGenerator.generateFont(fontParameter);



        countdownLabel = new Label(String.format("%06d",worldTimer), new Label.LabelStyle(font, Color.WHITE));
         scoreLabel=new Label(String.format("%06d",score), new Label.LabelStyle(font,Color.WHITE));

        levelLabel=new Label(" ", new Label.LabelStyle(font, Color.WHITE));

        // table.pad(10);
       //  table.add(pauseicon).top().left();
         table.padTop(stage.getHeight()*0.02f);
         table.add(healthIcon).padLeft(stage.getWidth()*0.2f).left();
        table.add(levelLabel).padTop(0).expandX().left();
        table.add(run).expandX().right();
         table.add(countdownLabel).right().padRight(10);




         /*
         table.add(healthIcon).expandX().padTop(10);
         table.add(worldLabel).expandX().padTop(10);
         table.add(timeLabel).expandX().padTop(10);
         table.row();
         table.add(scoreLabel).expandX();
         table.add(levelLabel).expandX();
         table.add(marioLabel).expandX();

          */
       //  stage.addActor(pauseIcon);
         stage.addActor(table);



    }

    public void update(float dt){
        timeCount +=dt;
        if (timeCount >=1){
         //   worldTimer--;
        //    countdownLabel.setText(String.format("%03d",worldTimer));
            timeCount =0;
        }
    }

    public static void addScore(int value){

   score+= value;
   scoreLabel.setText(String.format("%06d",score));
    }

    public Integer getWorldTimer(){
        return worldTimer;
    }
    @Override
    public void dispose() {
    //  stage.dispose();
    }
}
