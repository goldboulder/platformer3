/*

 */
package actions;

import abstractthings.GameObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.Timer;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;


public abstract class Action {
    
    protected boolean enabled;
    protected int disabledTime;
    protected String command;
    
    public Action(String command){
        this.command = command;
        enabled = true;
        
    }
    
    protected void attemptAction(GameObject owner, GameObjectList o, StatEffectHandler s){//use for frozen effects too...
        if (enabled){
            performAction(owner,o,s);
        }
        else{
            disabledTime -= PlayScene.GAME_TICK;
            if (disabledTime <= 0){
                enabled = true;
            }
        }
    }
    
    protected abstract void performAction(GameObject owner, GameObjectList o, StatEffectHandler s);
    
    
    protected void stall(double time){// in seconds
        enabled = false;
        disabledTime = (int)(time*1000);
    }
    
    public String getCommand(){
        return command;
    }
    
    
}
