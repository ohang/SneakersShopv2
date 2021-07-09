package com.mymario.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mymario.game.Screens.PlayScreen;

public  abstract  class Enemy extends Sprite {
    protected  World world;
    protected  PlayScreen screen;
    public Body b2body;
    public Body shootbody;
    public static Vector2   velocity;
    public static Vector2 shootVelocity;
    public Enemy(PlayScreen screen,float x, float y){
         this.world = screen.getWorld();
         this.screen= screen;
         setPosition(x,y);
         defineEnemy();



         velocity = new Vector2(-0.2f,0);
         shootVelocity = new Vector2(-1f,0);
        b2body.setActive(false);

    }

    public abstract void update(float delta);
    protected abstract  void  defineEnemy();
  //  public abstract  void  defineshooting();

    public abstract  void  hitOnHead();

    public   void reverseVelocity(boolean x ,boolean y){

        if(x){
            velocity.x =- velocity.x;
        }
        if (y){
            velocity.y=-velocity.y;
        }
    };

}
