/*

 */
package stat_effects;

import abstractthings.Copyable;
import abstractthings.GameObject;
import support.PlayScene;


public abstract class StatEffect implements Copyable{// +/- tag, class?*
    
    protected int timeLeft;
    public enum Type{POSITIVE,NEUTRAL,NEGATIVE};
    
    public StatEffect(double timeLeft){
        this.timeLeft = (int) (timeLeft*1000);
    }
    
    public abstract Type getType();
    
    public void act(GameObject g, StatEffectHandler s){
        applyEffect(g,s);
        timeLeft -= PlayScene.GAME_TICK;
    }
    
    
    public abstract void applyInitialEffect(GameObject g, StatEffectHandler s);
    public abstract void applyEffect(GameObject g, StatEffectHandler s);
    public abstract void applyRemoveEffect(GameObject g, StatEffectHandler s);
    public abstract double effectPriority();// function for determining which effect to keep if there is more than one of the same effect********
    
    public boolean finished(){
        return (timeLeft <= 0);
    }
}
