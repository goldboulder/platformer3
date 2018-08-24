/*

 */
package stat_effects;


public abstract class NegativeEffect extends StatEffect{
    
    public NegativeEffect(double timeLeft) {
        super(timeLeft);
    }
    
    @Override
    public Type getType(){
        return StatEffect.Type.NEGATIVE;
    }
    
}
