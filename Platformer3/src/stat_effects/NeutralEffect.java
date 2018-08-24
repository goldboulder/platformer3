/*

 */
package stat_effects;


public abstract class NeutralEffect extends StatEffect{
    
    public NeutralEffect(double timeLeft) {
        super(timeLeft);
    }
    
    @Override
    public Type getType(){
        return StatEffect.Type.NEUTRAL;
    }
    
}
