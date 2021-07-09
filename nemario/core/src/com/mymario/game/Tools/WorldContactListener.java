package com.mymario.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mymario.game.MyMario;
import com.mymario.game.Screens.PlayScreen;
import com.mymario.game.Sprites.Enemy;
import com.mymario.game.Sprites.InteractiveTileObject;
import com.mymario.game.Sprites.Nikol;
import com.mymario.game.Sprites.Police;
import com.mymario.game.Sprites.Sanitek;
import com.mymario.game.Sprites.VoiceShoot;



public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        PlayScreen screen;

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
int cdef= fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if (fixA.getUserData()=="head"  ||fixB.getUserData()=="head"){

            Fixture head = fixA.getUserData()=="head" ? fixA : fixB;
            Fixture  object  = head == fixA? fixB:fixA;

            if (object.getUserData()!= null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {

                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }

        }

       switch (cdef){
            case MyMario.ENEMY_HEAD_BIT | MyMario.MARIO_BIT: // tshnamu gux kpnuma mario in
                if (fixA.getFilterData().categoryBits == MyMario.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
              else  if (fixB.getFilterData().categoryBits == MyMario.ENEMY_HEAD_BIT)
                    ((Enemy)fixB.getUserData()).hitOnHead();

              break;
           case  MyMario.ENEMY_BIT | MyMario.OBJECT_BIT://yete enemy kpnuma obyekti
               if (fixA.getFilterData().categoryBits == MyMario.ENEMY_BIT)
                   ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
               else
                   ((Enemy)fixB.getUserData()).reverseVelocity(true,false);

               break;


           case  MyMario.POLICE_BIT | MyMario.COIN_BTI:


               Gdx.app.log("POLICE WAS SHOOT"," SHOOT");




               break;//yete enemy kpnuma obyekti

           case  MyMario.VOICE_BIT | MyMario.POLICE_BIT:

               VoiceShoot.setToDestroy();
               if (fixA.getFilterData().categoryBits == MyMario.POLICE_BIT){

                   ((Police)fixA.getUserData()).Policedead();}
               else {
                   ((Police) fixB.getUserData()).Policedead();
               }
               break;




          case  MyMario.MARIO_BIT | MyMario.SANITEK_BIT:

              Nikol.setToDestroy();
              if(Nikol.life==3 || Nikol.life==2){
                  PlayScreen.playadcontrol.showAd();
              }

              // PlayScreen.playadcontrol.showAd();



              break;





           case  MyMario.MARIO_BIT | MyMario.POLICE_BIT:


             //  PlayScreen.voiceShoot=null;
               Nikol.setToDestroy();
               if(Nikol.life==3 || Nikol.life==2){
                   PlayScreen.playadcontrol.showAd();
               }
               //  PlayScreen.playadcontrol.showAd();






               break;





       }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
