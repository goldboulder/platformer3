/*

 */
package actions;

// slows down velocity

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;

public class VelocityDrag extends Action{
    
    private double xDrag;
    private double yDrag;
    public static final double DEFAULT_X_DRAG = 0.75;
    public static final double DEFAULT_Y_DRAG = 0.75;
    
    public VelocityDrag(String command, double xDrag, double yDrag){
        super(command);
        this.xDrag = xDrag;
        this.yDrag = yDrag;
    }
    
    public VelocityDrag(String command){
        super(command);
        this.xDrag = DEFAULT_X_DRAG;
        this.yDrag = DEFAULT_Y_DRAG;
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,xDrag,yDrag);
    }
    
    public static void doAction(GameObject owner, double xDrag, double yDrag){
        if (owner.getVelocity().getX() < 0){
            xDrag *= -1;
        }
        if (owner.getVelocity().getY() < 0){
            yDrag *= -1;
        }
        owner.changeVelocity(-xDrag*PlayScene.D_TIME, -yDrag*PlayScene.D_TIME);
        if(Math.abs(owner.getVelocity().getX()) <= xDrag*PlayScene.D_TIME){
            owner.setXVelocity(0);
        }
        if(Math.abs(owner.getVelocity().getY()) <= yDrag*PlayScene.D_TIME){
            owner.setYVelocity(0);
        }
    }
    
    public static void doAction(GameObject owner){
        doAction(owner,DEFAULT_X_DRAG,DEFAULT_Y_DRAG);
    }
    
}
