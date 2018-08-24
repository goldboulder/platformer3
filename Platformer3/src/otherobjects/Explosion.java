/*

 */
package otherobjects;

import abstractthings.GameObject;
import actions.DamageOnceOnContact;
import actions.DelayedAction;
import animations.MultiFrameAnimation;
import java.awt.geom.Point2D;
import support.DamageType;
import support.GameObjectList;
import support.HitBox;
import support.WhoToDamage;

/**
 *
 * @author Nathan
 */
public class Explosion extends GameObject{
    
    private static final double DAMAGE_PERCENT = 0.55;//when the explosion shoud no longer damage you
    
    public Explosion(double centerX, double centerY, double size, double damage, double duration){
        super();
        pushable = false;
        this.position = new Point2D.Double(centerX - size/2,centerY - size/2);//initialize position... 
        this.hitBox = new HitBox(position,new Point2D.Double(size,size),HitBox.Shape.CIRCLE);
        this.size = new Point2D.Double(size,size);
        
        
        actionList.addAction(new DamageOnceOnContact("explode",DamageType.EXPLOSION,damage,WhoToDamage.ALL,null));
        actionList.addAction(new DelayedAction("Harmless",duration * DAMAGE_PERCENT));
        actionList.addAction(new DelayedAction("delete",duration));
        graphics.addAnimation("explode", new MultiFrameAnimation("Others/Explosion",duration), true);
    }
    
    public Explosion(GameObject source, double size, double damage, double duration){
        this(source.getCenter().x,source.getCenter().y,size,damage,duration);
    }
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {
        if (command.equals("Harmless")){
            actionList.removeAction("explode");
        }
        if (command.equals("delete")){
            delete();
        }
        
        
    }
    
}
