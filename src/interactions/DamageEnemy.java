/*

 */
package interactions;

import support.DamageType;

// when reactor damages actor
public class DamageEnemy extends Interaction{
    
    private DamageType damageType;
    private double damage;
    
    public DamageEnemy(DamageType damageType, double damage){
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
