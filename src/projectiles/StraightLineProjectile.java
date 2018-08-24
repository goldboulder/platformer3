/*

 */
package projectiles;

import abstractthings.GameObject;
import actions.DamageOnceOnContact;
import actions.DelayedAction;
import actions.GoFoward;
import animations.Animation;
import animations.SingleFrameAnimation;
import interactions.DamageEnemy;
import interactions.HitCieling;
import interactions.HitFloor;
import interactions.HitWall;
import interactions.Interaction;
import interactions.WasBouncedOffOf;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import support.DamageType;
import support.HitBox;
import support.GameObjectList;
import support.InputHandler;
import support.WhoToDamage;


public class StraightLineProjectile extends Projectile{//used?
    
    private double speed;
    private DamageType damageType;
    private double damage;
    private WhoToDamage whoToDamage;
    private Animation animation;
    
    public StraightLineProjectile(GameObject owner, Point2D.Double position, Point2D.Double size, double rotation, double damage, double speed, double lifeTime, WhoToDamage whoToDamage, Animation animation){// clean, prototype Pattern? stats stored in files?
        super();
        this.owner = owner;
        this.position = new Point2D.Double(position.x,position.y);
        this.size = size;
        this.hitBox = new HitBox(position.x,position.y,size.x,size.y);
        this.rotation = rotation;
        this.damage = damage;
        this.speed = speed;
        this.lifeTime = lifeTime;
        this.whoToDamage = whoToDamage;
        this.animation = animation;
        
        graphics.addAnimation("animation",animation,true);
        
        
        actionList.addAction(new GoFoward("GoFoward",speed));
        actionList.addAction(new DamageOnceOnContact("Damage",damageType,damage,whoToDamage,owner));
        actionList.addAction(new DelayedAction("timeout",lifeTime));
    }
    
    @Override
    public void draw(Graphics g){
        drawFull(g);
    }
    
    @Override
     public void act(GameObjectList o, InputHandler i){
        super.act(o,i);
    }
    
    @Override
    public void react(GameObject g, Interaction i){
        super.react(g,i);
        if ((i instanceof HitWall) || (i instanceof HitFloor) || (i instanceof HitCieling)|| (i instanceof DamageEnemy)){
            delete();
            
        }
        
    }
    
    
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {//command not used
        delete();
    }

    @Override
    public GameObject getCopy() {
        StraightLineProjectile projectile = new StraightLineProjectile(owner,new Point2D.Double(position.x,position.y),new Point2D.Double(size.getX(),size.getY()),rotation,damage,speed,lifeTime,whoToDamage,(Animation)animation.getCopy());
        projectile.copyFields(this);
        return projectile;
    }
    
    
    
}
