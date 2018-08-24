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


public class SnifitBullet extends Projectile{
    
    
    private static final double SIZE = 0.2;
    private static final double LIFETIME = 5;
    
    public SnifitBullet(GameObject owner, Point2D.Double position, double damage, double speed, double rotation){// clean, prototype Pattern? stats stored in files?
        super();
        this.owner = owner;
        this.position = new Point2D.Double(position.x,position.y);
        this.size = new Point2D.Double(SIZE,SIZE);
        this.hitBox = new HitBox(position.x,position.y,size.getX(),size.getY());
        this.rotation = rotation;
        this.lifeTime = lifeTime;
        
        graphics.addAnimation("animation",new SingleFrameAnimation("Projectiles/Bouncy Bullet/Profile"),true);
        
        
        actionList.addAction(new GoFoward("GoFoward",speed));
        actionList.addAction(new DamageOnceOnContact("Damage",DamageType.CRUSH,damage,WhoToDamage.PLAYER,owner));
        actionList.addAction(new DelayedAction("timeout",LIFETIME));
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
        if ((i instanceof HitWall) || (i instanceof HitFloor) || (i instanceof HitCieling)|| (i instanceof DamageEnemy)){
            delete();
            
        }
        
    }
    
    
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {//command not used?**
        delete();
    }

    @Override
    public GameObject getCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
