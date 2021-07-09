package com.mymario.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mymario.game.Items.ItemDef;
import com.mymario.game.Items.Mushroom;
import com.mymario.game.MyMario;
import com.mymario.game.Scenes.Hud;
import com.mymario.game.Screens.PlayScreen;

public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet= map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);

        setCategoryFilter(MyMario.COIN_BTI);


    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin ", "Collsiio");
        setCategoryFilter(MyMario.DESTROYED_BIT); // jarduma pat@

        getCell().setTile(tileSet.getTile(BLANK_COIN)); // ID_ov poxuma coini tex urish nkara dnum
      //  screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,body.getPosition().y),Mushroom.class));
        Hud.addScore(100);
        MyMario.manager.get("audio/sounds/hu.wav", Sound.class).play();



    }
}
