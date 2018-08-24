/*

 */
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class Jump extends Action{
    
    private double jumpVelocity;
    
    public Jump(String command, double jumpVelocity){
        super(command);
        this.jumpVelocity = jumpVelocity;
    }

    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,jumpVelocity,o,s);
    }
    
    public static void doAction(GameObject owner, double jumpVelocity, GameObjectList o, StatEffectHandler s){
        jumpVelocity *= s.getMultiplier(StatEffectHandler.StatMultiplier.JUMP_POWER);
        if (owner.onGround(o)){
            owner.setYVelocity(-jumpVelocity);
        }
    }
    
    public double getJumpVelocity(){
        return jumpVelocity;
    }
    
    public void getJumpVelocity(double j){
        jumpVelocity = j;
    }
    
}
