/*

 */
package stat_effects;

import abstractthings.Copyable;
import abstractthings.GameObject;
import java.util.LinkedList;
import java.util.Queue;


public class StatEffectHandler implements Copyable{
    //damage over time with damage type
    //slow down, speed up
    //should stat effect stacking be a thing? no...? use formula to determine wether to keep it or swap it
    
    private LinkedList<StatEffect> statEffects;
    private Queue<StatEffect> removeQueue;
    
    public enum StatMultiplier{SPEED,JUMP_POWER};
    private double[] multipliers;
    
    
    public StatEffectHandler(){
        statEffects = new LinkedList<>();
        removeQueue = new LinkedList<>();
        multipliers = new double[StatMultiplier.values().length];
        for (int i = 0; i < StatMultiplier.values().length; i++){
            multipliers[i] = 1;
        }
    }
    
    public double getMultiplier(StatMultiplier s){
        return multipliers[s.ordinal()];
    }
    
    public void setMultiplier(StatMultiplier s, double num){
        multipliers[s.ordinal()] = num;
    }
    
    public void multiplyMultiplier(StatMultiplier s, double num){
        multipliers[s.ordinal()] *= num;
    }
    
    public void applyEffects(GameObject g){
        
        for (StatEffect effect : statEffects){
            effect.act(g,this);
            if (effect.finished()){
                removeQueue.add(effect);
            }
        }
        while(!removeQueue.isEmpty()){
            StatEffect s = removeQueue.poll();
            s.applyRemoveEffect(g, this);
            statEffects.remove(s);
        }
        
    }
    
    public void addEffect(GameObject g, StatEffect s){
        StatEffect existingStat = findEffectOfType(s);
        if (existingStat == null){
            statEffects.add(s);
            s.applyInitialEffect(g, this);
        }
        else if (s.effectPriority() > existingStat.effectPriority()){
            existingStat.applyRemoveEffect(g, this);
            statEffects.remove(existingStat);
            statEffects.add(s);
            s.applyInitialEffect(g, this);
        }
    }
    
    public void removeEffect(GameObject g, String statName){
        
        for (StatEffect effect : statEffects){
            if (effect.getClass().getSimpleName().equals(statName)){
                effect.applyRemoveEffect(g, this);
                statEffects.remove(effect);
                break;
            }
        }
        
    }
    
    public void clearEffects(GameObject g){// clears all effects, activating their remove action as well
        for (StatEffect effect : statEffects){
            effect.applyRemoveEffect(g, this);
            statEffects.remove(effect);
        }
    }
    
    public void clearEffects(GameObject g, StatEffect.Type type){// clears all effects of a specified type, activating their remove action as well
        for (StatEffect effect : statEffects){
            if (type == effect.getType()){
                effect.applyRemoveEffect(g, this);
                statEffects.remove(effect);
            }
        }
    }
    
    private StatEffect findEffectOfType(StatEffect effect){
        for (StatEffect s : statEffects){
            if (s.getClass().equals(effect.getClass()));
            return s;
        }
        return null;
    }

    @Override
    public Object getCopy() {
        StatEffectHandler s = new StatEffectHandler();
        for (StatEffect e : statEffects){
            s.statEffects.add((StatEffect)e.getCopy());
        }
        for (StatEffect e : removeQueue){
            s.removeQueue.add((StatEffect)e.getCopy());
        }
        System.arraycopy(multipliers, 0, s.multipliers, 0, StatMultiplier.values().length);
        
        return s;
    }
    
    
}
