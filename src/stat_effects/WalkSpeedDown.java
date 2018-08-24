/*

 */
package stat_effects;

import abstractthings.GameObject;


public class WalkSpeedDown extends NegativeEffect{
    
    private double multiplier;
    
    public WalkSpeedDown(double timeLeft, double multiplier){// change to speed and seperate slow down class************
        super(timeLeft);
        if (multiplier > 1){
            multiplier = 1;
        }
        this.multiplier = multiplier;
    }

    @Override
    public void applyEffect(GameObject g, StatEffectHandler s) {
        
    }

    @Override
    public void applyInitialEffect(GameObject g, StatEffectHandler s) {
        s.multiplyMultiplier(StatEffectHandler.StatMultiplier.SPEED, multiplier);
    }

    @Override
    public void applyRemoveEffect(GameObject g, StatEffectHandler s) {
        s.multiplyMultiplier(StatEffectHandler.StatMultiplier.SPEED, 1/multiplier);
    }

    @Override
    public double effectPriority() {
        return timeLeft / multiplier;
    }

    @Override
    public Object getCopy() {
        return new WalkSpeedDown(timeLeft,multiplier);
    }

    
    
}
