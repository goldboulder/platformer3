/*

 */
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;


public class AccelerateToDirection extends Action{
    
    double xAcceleration;
    double yAcceleration;
    
    public AccelerateToDirection(String command, double xAcc, double yAcc){
        super(command);
        xAcceleration = xAcc;
        yAcceleration = yAcc;
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,xAcceleration,yAcceleration);
    }
    
    public static void doAction(GameObject owner, double xAcc, double yAcc){
        owner.changeVelocity(xAcc*PlayScene.D_TIME,yAcc*PlayScene.D_TIME);
    }
    
}
