/*

 */
package interactions;

import support.DamageType;

// reactor takes damage from actor
public class TakeDamage extends Interaction{
    
    private DamageType damageType;
    private double damage;
    
    public TakeDamage(DamageType damageType, double damage){
        this.damageType = damageType;
        this.damage = damage;
    }
    
    public DamageType getDamageType(){
        return damageType;
    }
    
    public double getDamage(){
        return damage;
    }
    
}
