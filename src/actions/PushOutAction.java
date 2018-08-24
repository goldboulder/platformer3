/*

 */
package actions;

import abstractthings.GameObject;
import interactions.HitCieling;
import interactions.HitFloor;
import interactions.HitWall;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class PushOutAction extends Action{

    public PushOutAction(String command) {
        super(command);
    }
// crushable parameter?
    
    

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,o);
    }
    
    public static void doAction(GameObject owner, GameObjectList o) {
        boolean[] direction;// 0: up, 1: right, 2: down, 3: left
        for (GameObject other : o){
            if (other.isPushable() && owner.colliding(other) && owner != other){
                direction = other.getHitBox().moveOutOfHitBox(other,owner.getHitBox());
                
                
                // crushing?
                
                if (direction[0] && owner.reasonablyVertical(other)){
                    other.react(owner, new HitFloor(owner));
                }
                //up/down takes priority. test
                if ((direction[1] || direction[3])){
                    if (owner.reasonablyHorizontal(other)){
                        other.react(owner, new HitWall(owner));
                    }
                    else{
                        if (other.getCenter().y < owner.getCenter().y){//move up to top of block. Think automatically going up a staircase
                            other.setPosition(other.getUpperLeftCorner().x,owner.getUpperLeftCorner().y - other.getSize().getY());
                        }
                        else{//move down to bottom of block
                            other.setPosition(other.getUpperLeftCorner().x,owner.getUpperLeftCorner().y + owner.getSize().getY());
                        }
                    }
                    
                }
                
                if (direction[2] && owner.reasonablyVertical(other)){
                    other.react(owner, new HitCieling(owner));
                }
                
            }
        }
    }
    
}
