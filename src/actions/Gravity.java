/*

 */
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;


public class Gravity extends Action{//extends constant global force?

    public static double gravity = 18;// may change in game

    public Gravity(String command) {
        super(command);
    }
    
    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner);
    }
    
    public static void doAction(GameObject owner){
        owner.changeVelocity(0,gravity*PlayScene.D_TIME);
    }
    
    
    
    
}
