/*

 */
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class Leap extends Action{//extra functionality for leaping to an exact spot?
    
    private double xVelocity;
    private double yVelocity;
    
    public Leap(String command, double xVelocity, double jumpVelocity){
        super(command);
        this.xVelocity = xVelocity;
        this.yVelocity = jumpVelocity;
    }

    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,xVelocity,yVelocity,o,s);
    }
    
    public static boolean doAction(GameObject owner, double x, double y, GameObjectList o, StatEffectHandler s){
        x *= s.getMultiplier(StatEffectHandler.StatMultiplier.JUMP_POWER);
        y *= s.getMultiplier(StatEffectHandler.StatMultiplier.JUMP_POWER);
        if (owner.onGround(o)){
            owner.setXVelocity(x);
            owner.setYVelocity(-y);
            return true;
        }
        return false;
    }
    
    public double getXVelocity(){
        return xVelocity;
    }
    
    public double getYVelocity(){
        return yVelocity;
    }
    
    public void setXVelocity(double x){
        xVelocity = x;
    }
    
    public void setYVelocity(double y){
        xVelocity = y;
    }
    
}