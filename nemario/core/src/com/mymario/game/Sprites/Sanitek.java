package com.mymario.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mymario.game.MyMario;
import com.mymario.game.Scenes.Hud;
import com.mymario.game.Screens.PlayScreen;

import javax.swing.text.MaskFormatter;

import sun.java2d.loops.MaskBlit;
import sun.java2d.loops.MaskFill;

public class Sanitek extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;


    public Sanitek(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet= map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);


        setCategoryFilter(MyMario.SANITEK_BIT);



    }


    @Override
    public void onHeadHit() {




    }

    public  void NikolvsSanitek(Sanitek sanitek){
        sanitek =null;

        Gdx.app.log("nikol kpav", "sanitekin");
    };



    public TiledMapTileLayer.Cell getCell(){

        TiledMapTileLayer layer =(TiledMapTileLayer) map.getLayers().get(4);
        return layer.getCell((int)(body.getPosition().x*MyMario.PPM/16)
                ,(int)(body.getPosition().y*MyMario.PPM/16  ));
    }
}
