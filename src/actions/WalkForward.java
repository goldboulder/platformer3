/*

 */
// goes left or right depending on direction
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;


public class WalkForward extends Action{
    
    private double walkSpeed;// accelerate?
    
    public WalkForward(String command, double walkSpeed){
        super(command);
        this.walkSpeed = walkSpeed;
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,walkSpeed,s);
    }
    
    public static void doAction(GameObject owner, double walkSpeed, StatEffectHandler s){
        walkSpeed *= s.getMultiplier(StatEffectHandler.StatMultiplier.SPEED);
        
        if (owner.isFacingRight()){
            owner.changePosition(walkSpeed*PlayScene.D_TIME,0);
        }
        else{
            owner.changePosition(-walkSpeed*PlayScene.D_TIME,0);
        }
    }
    
}
