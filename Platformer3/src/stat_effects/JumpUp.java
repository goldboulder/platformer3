/*

 */
package stat_effects;

import abstractthings.GameObject;


public class JumpUp extends PositiveEffect{
    
    private double multiplier;
    
    public JumpUp(double timeLeft, double multiplier){
        super(timeLeft);
        this.multiplier = multiplier;
    }

    @Override
    public void applyEffect(GameObject g, StatEffectHandler s) {
        
    }

    @Override
    public void applyInitialEffect(GameObject g, StatEffectHandler s) {
        s.multiplyMultiplier(StatEffectHandler.StatMultiplier.JUMP_POWER, multiplier);
    }

    @Override
    public void applyRemoveEffect(GameObject g, StatEffectHandler s) {
        s.multiplyMultiplier(StatEffectHandler.StatMultiplier.JUMP_POWER, 1/multiplier);
    }

    @Override
    public double effectPriority() {
        return timeLeft * multiplier;
    }

    @Override
    public Object getCopy() {
        return new JumpUp(timeLeft,multiplier);
    }
    
}
