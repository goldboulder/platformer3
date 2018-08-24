/*

 */
package actions;

import abstractthings.Destroyable;
import abstractthings.GameObject;
import interactions.DamageEnemy;
import interactions.TakeDamage;
import java.util.Collection;
import java.util.LinkedList;
import stat_effects.StatEffectHandler;
import support.DamageType;
import support.GameObjectList;
import support.OtherThings;
import support.WhoToDamage;

// damages any 
public class DamageOnceOnContact extends Action{

    private DamageType damageType;
    private double damage;
    private LinkedList<GameObject> justDamaged;
    private WhoToDamage whoToDamage;
    private GameObject doNotDamage;
    
    public DamageOnceOnContact(String command, DamageType damageType, double damage, WhoToDamage whoToDamage, GameObject doNotDamage){// objects to not damage? enemy or player
        super(command);
        this.damageType = damageType;
        this.damage = damage;
        justDamaged = new LinkedList<>();
        this.whoToDamage = whoToDamage;
        this.doNotDamage = doNotDamage;
    }
    
    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        for (GameObject other : o){
            if(OtherThings.shouldDamage(owner,other,whoToDamage,doNotDamage)){
                if (owner.colliding(other)){
                    if (!justDamaged.contains(other)){
                        other.react(owner, new TakeDamage(damageType,damage));
                        owner.react(other, new DamageEnemy(damageType,damage));
                        addToJustHit(other);
                    }
                    
                }
                else{
                    justDamaged.remove(other);
                }
            }
        }
    }
    
    
    protected void addToJustHit(GameObject other){
        justDamaged.add(other);
    }
    
    
}