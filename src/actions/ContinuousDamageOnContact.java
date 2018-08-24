/*

 */
package actions;

import abstractthings.GameObject;
import interactions.DamageEnemy;
import interactions.TakeDamage;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import stat_effects.StatEffectHandler;
import support.DamageType;
import support.GameObjectList;
import support.OtherThings;
import support.PlayScene;
import support.WhoToDamage;


public class ContinuousDamageOnContact extends Action{
    
    protected DamageType damageType;
    protected double damage;
    protected HashMap<GameObject,Integer> timeSinceDamaged;
    protected WhoToDamage whoToDamage;
    protected GameObject doNotDamage;
    private final int TIME_BETWEEN_HITS;

    public ContinuousDamageOnContact(String command, DamageType damageType, double damage, WhoToDamage whoToDamage, GameObject doNotDamage, double timeBetweenHits){// objects to not damage? enemy or player
        super(command);
        this.damageType = damageType;
        this.damage = damage;
        this.whoToDamage = whoToDamage;
        this.doNotDamage = doNotDamage;
        timeSinceDamaged = new HashMap<>();
        this.TIME_BETWEEN_HITS = (int) (timeBetweenHits * 1000);
    }
    
    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        for (GameObject other : o){
            if(OtherThings.shouldDamage(owner,other,whoToDamage,doNotDamage) && owner.colliding(other)){
                if (timeSinceDamaged.get(other) == null || timeSinceDamaged.get(other) >= TIME_BETWEEN_HITS){
                    other.react(owner, new TakeDamage(damageType,damage));
                    owner.react(other, new DamageEnemy(damageType,damage));
                    timeSinceDamaged.put(other, TIME_BETWEEN_HITS);
                }
            }
        }
        
        for (GameObject g : timeSinceDamaged.keySet()){
            int time = timeSinceDamaged.get(g)-PlayScene.GAME_TICK;
            if (time <= 0){
                timeSinceDamaged.remove(g);
            }
            else{
                timeSinceDamaged.put(g, time);
            }
        }
        
    }

    //clears the hash map so that all entities can be harmed again
    public void reset() {
        timeSinceDamaged.clear();
    }
    
}




/*
public class ContinuousDamageOnContact extends DamageOnceOnContact{// no leave re-enter damage*

    private HashMap<GameObject,Integer> timeSinceDamaged;
    int TIME_BETWEEN_HITS;
    
    public ContinuousDamageOnContact(DamageType damageType, double damage, WhoToDamage whoToDamage, GameObject doNotDamage, double TIME_BETWEEN_HITS){// objects to not damage? enemy or player
        super(damageType,damage,whoToDamage,doNotDamage);
        timeSinceDamaged = new HashMap<>();
        this.TIME_BETWEEN_HITS = (int) (TIME_BETWEEN_HITS * 1000);
    }
    
    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        super.performAction(owner,o,s);
        Set<GameObject> damagedEnemies = timeSinceDamaged.keySet();
        for (GameObject g : damagedEnemies){
            int time = timeSinceDamaged.replace(g, timeSinceDamaged.get(g) - PlayScene.GAME_TICK);
            if (time < PlayScene.GAME_TICK){
                timeSinceDamaged.remove(g);
                justDamaged.remove(g);
            }
        }
    }
    
    @Override
    protected void addToJustHit(GameObject other){
        super.addToJustHit(other);
        timeSinceDamaged.put(other, TIME_BETWEEN_HITS);
    }
    
}
*/