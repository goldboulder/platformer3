/*

 */
package actions;

import abstractthings.GameObject;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;

/**
 *
 * @author Nathan
 */
public class RepeatingAction extends Action{
    
    private int period;
    private int timeLeft;
    private boolean paused = false;
    
    public RepeatingAction(String command, double time){
        super(command);
        this.period = (int) (time * 1000);
        timeLeft = period;
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        if (!paused){
            timeLeft -= PlayScene.GAME_TICK;
            if (getTimeLeft() <= 0){
                timeLeft = period;
                owner.doScheduledAction(o,command);
            }
        }
        
        
    }
    
    public double getPeriod() {
        return period/1000.0;
    }
    
    public void setPeriod(double period) {
        int intPeriod = (int) (period * 1000);
        this.timeLeft += (intPeriod - this.period);
        this.period = intPeriod;
        
    }
    
    public double getTimeLeft() {
        return timeLeft/1000.0;
    }
    
    public void setTimeLeft(double timeLeft) {
        this.timeLeft = (int) (timeLeft * 1000);
    }
    
    public void restart(){
        setTimeLeft(period/1000.0);
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }

    public void pause() {
        paused = true;
    }
    
    public void unpause() {
        paused = false;
    }
    
    public boolean isPaused(){
        return paused;
    }
}
