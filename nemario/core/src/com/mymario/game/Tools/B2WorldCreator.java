package com.mymario.game.Tools;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mymario.game.MyMario;
import com.mymario.game.Screens.PlayScreen;
import com.mymario.game.Sprites.Police;
import com.mymario.game.Sprites.Sanitek;

public class B2WorldCreator {

    World world;
   TiledMap map;
   PlayScreen screen;
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    Body body;


    public Array<Police> getGoombas() {
        return goombas;
    }
    public Array<Sanitek> getSaniteks() {
        return saniteks;
    }
    public static Array<Police> goombas;
    private Array<Sanitek> saniteks;
    public ShapeRenderer shapeRenderer;

    public  B2WorldCreator(PlayScreen screen) {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.screen=screen;



    //    CreateObjects();








    }




        public void CreateObjects(){


            for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {

                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyMario.PPM, (rect.getY() + rect.getHeight() / 2) / MyMario.PPM);

                body = world.createBody(bdef); // avelacnum enw  worldum


                shape.setAsBox(rect.getWidth() / 2 / MyMario.PPM, rect.getHeight() / 2 / MyMario.PPM);
                fdef.shape = shape;
                body.createFixture(fdef);


            }

            for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {

                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                goombas.add(new Police(screen, rect.getX() / MyMario.PPM, rect.getY() / MyMario.PPM));


            }

            for (MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

                Rectangle rect = ((RectangleMapObject) object).getRectangle();


                new Sanitek(screen,rect);

            }

    }

    public void  updateObjects(){




        }





}

