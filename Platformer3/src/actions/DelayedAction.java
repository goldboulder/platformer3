/*

 */
package actions;

import abstractthings.GameObject;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;


public class DelayedAction extends Action implements SelfDeletingAction{//like repeatingAction, but does not repeat. get rid of after? use performScheduledAction
    
    private int time;
    private boolean performed = false;
    
    public DelayedAction(String command, double time){
        super(command);
        this.time = (int) (time * 1000);
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        time -= PlayScene.GAME_TICK;
        if (time <= 0 && !performed){
            owner.doScheduledAction(o,command);
            performed = false;
        }
        
        
    }
    
    //more methods? delayAction?
    
    @Override
    public String getCommand(){
        return command;
    }

    @Override
    public boolean deleteReady() {
        return time <= 0;
    }
}
