/*

 */
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;

public class GoFoward extends Action{
    
    private double speed;
    
    public GoFoward(String command, double speed){
        super(command);
        this.speed = speed;
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,speed,s);
    }
    
    public static void doAction(GameObject owner, double speed, StatEffectHandler s){
        speed *= s.getMultiplier(StatEffectHandler.StatMultiplier.SPEED);
        double rotation = owner.getRotation();
        owner.changePosition(speed * Math.cos(rotation)*PlayScene.D_TIME,-speed * Math.sin(rotation)*PlayScene.D_TIME);
    }
    
    public double getSpeed(){
        return speed;
    }
    
    public void setSpeed(double speed){
        this.speed = speed;
    }
    
}
