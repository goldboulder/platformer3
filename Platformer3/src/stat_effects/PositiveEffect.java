/*

 */
package stat_effects;


public abstract class PositiveEffect extends StatEffect{
    
    public PositiveEffect(double timeLeft) {
        super(timeLeft);
    }
    
    @Override
    public Type getType(){
        return StatEffect.Type.POSITIVE;
    }
    
}
