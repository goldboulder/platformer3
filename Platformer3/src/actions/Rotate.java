/*

 */
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class Rotate extends Action{
    
    private double rotateAmount;
    
    public Rotate(String command, double rotateAmount){
        super(command);
        this.rotateAmount = rotateAmount;
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,rotateAmount,s);
    }
    
    public static void doAction(GameObject owner, double rotateAmount, StatEffectHandler s){
        owner.setRotation(owner.getRotation()+ rotateAmount);
    }
    
}
