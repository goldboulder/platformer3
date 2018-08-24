/*

 */
package projectiles;

import abstractthings.GameObject;
import actions.DamageOnceOnContact;
import actions.DelayedAction;
import actions.GoFoward;
import animations.SingleFrameAnimation;
import interactions.DamageEnemy;
import interactions.HitCieling;
import interactions.HitFloor;
import interactions.HitWall;
import interactions.Interaction;
import interactions.WasBouncedOffOf;
import items.Material;
import items.YellowCoin;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import stat_effects.StatEffectHandler;
import support.DamageType;
import support.HitBox;
import support.GameObjectList;
import support.InputHandler;
import support.PrototypeProvider;
import support.WhoToDamage;


public class BouncyBullet extends Projectile{
    
    
    private double speed;
    private int numBouncesLeft;
    private double damage;
    
    public BouncyBullet(GameObject owner, Point2D.Double position, double rotation, double damage, double speed, int numBounces, double lifeTime){//extend StraightLineBullet***
        super();
        this.owner = owner;
        this.position = new Point2D.Double(position.x,position.y);
        this.size = new Point2D.Double(0.2,0.2);
        this.rotation = rotation;
        this.damage = damage;
        this.speed = speed;
        this.numBouncesLeft = numBounces;
        this.lifeTime = lifeTime;
        this.hitBox = new HitBox(position.x,position.y,0.2,0.2);
        graphics.addAnimation("bullet",new SingleFrameAnimation("Projectiles/Bouncy Bullet/Profile"),true);
        
        
        actionList.addAction(new GoFoward("GoFoward",speed));
        actionList.addAction(new DamageOnceOnContact("Damage",DamageType.CRUSH,damage,WhoToDamage.ENEMY,owner));
        actionList.addAction(new DelayedAction("timeout",lifeTime));
    }
    
    @Override
    public void draw(Graphics g){
        drawPlain(g);
    }
    
    @Override
     public void act(GameObjectList o, InputHandler i){
        super.act(o,i);
    }
    
    @Override
    public void react(GameObject g, Interaction i){
        super.react(g,i);
        if (i instanceof HitWall && g != justBouncedOff){
            bounceRotation(Math.toRadians(90),g);
            bouncePostProcessing(g);
            
        }
        if (((i instanceof HitFloor) || (i instanceof HitCieling)) && g != justBouncedOff){
            bounceRotation(0,g);
            bouncePostProcessing(g);
        }
        
        if (i instanceof DamageEnemy && g != justBouncedOff){
            bounceRotation(angleTo(g)+Math.toRadians(90),g);
            bouncePostProcessing(g);
            
        }
    }
    
    private void bouncePostProcessing(GameObject actor){
        if (--numBouncesLeft < 0){
            delete();
        }
        
        
        
    }
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {//command not used
        delete();
    }

    @Override
    public GameObject getCopy() {
        BouncyBullet bullet = new BouncyBullet(owner,new Point2D.Double(position.x,position.y),rotation,damage,speed,numBouncesLeft,lifeTime);
        bullet.copyFields(this);
        bullet.justBouncedOff = justBouncedOff;
        bullet.speed = speed;
        bullet.numBouncesLeft = numBouncesLeft;
        return bullet;
    }
    
    
    
}
