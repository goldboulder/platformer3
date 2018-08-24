/*

 */
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;


public class Move extends Action{
    
    private double xSpeed;
    private double ySpeed;
    
    public Move(String command, double xSpeed, double ySpeed){
        super(command);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    
    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,xSpeed,ySpeed,s);
    }
    
    public static void doAction(GameObject owner, double xSpeed, double ySpeed, StatEffectHandler s){//slowDown stat?
        xSpeed *= s.getMultiplier(StatEffectHandler.StatMultiplier.SPEED);
        ySpeed *= s.getMultiplier(StatEffectHandler.StatMultiplier.SPEED);
        owner.changePosition(xSpeed*PlayScene.D_TIME, ySpeed*PlayScene.D_TIME);
    }
    
}
