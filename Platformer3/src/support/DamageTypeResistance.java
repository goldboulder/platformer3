/*

 */
package support;

import abstractthings.Copyable;
import java.util.HashMap;
import java.util.Scanner;


public class DamageTypeResistance implements Copyable{
    
    private double[] multiplyArray;
    private double[] subtractArray;
    
    
    public DamageTypeResistance(){
        multiplyArray = new double[DamageType.values().length];
        subtractArray = new double[DamageType.values().length];
        
        for (int i = 0; i < DamageType.values().length; i++){
            multiplyArray[i] = 1;
        }
    }
    
    public DamageTypeResistance(Scanner sc){
        multiplyArray = new double[DamageType.values().length];
        subtractArray = new double[DamageType.values().length];
        
        for (DamageType d : DamageType.values()){
            sc.next();
            multiplyArray[d.ordinal()] = sc.nextDouble();
            subtractArray[d.ordinal()] = sc.nextDouble();
        }
    }

    public double modifyDamage(DamageType damageType, double damage) {// multiply damage, then subtract
        
        if (damageType == null){
            return damage;
        }
        
        damage *= multiplyArray[damageType.ordinal()];
        if (damage < 0){//multiplier can be negative, in this case, the agent heals HP (covered in Agent class)
            return damage;
        }
        
        damage -= subtractArray[damageType.ordinal()];
        if (damage < 0){
            return 0;
        }
        else{
            return damage;
        }
    }
    
    public double getMultiplier(DamageType d){
        return multiplyArray[d.ordinal()];
    }
    
    public double getSubtractor(DamageType d){
        return subtractArray[d.ordinal()];
    }
    
    public void setMultiplier(DamageType d, double mult){
        multiplyArray[d.ordinal()] = mult;
    }
    
    public void setSubtractor(DamageType d, double sub){
        subtractArray[d.ordinal()] = sub;
    }
    
    public void multiplyMultiplier(DamageType d, double mult){
        multiplyArray[d.ordinal()] *= mult;
    }
    
    public void addSubtractor(DamageType d, double sub){
        subtractArray[d.ordinal()] += sub;
    }
    
    public DamageTypeResistance getCopy(){
        DamageTypeResistance copy = new DamageTypeResistance();
        for (int i = 0; i < DamageType.values().length; i++){
            copy.multiplyArray[i] = multiplyArray[i];
        }
        for (int i = 0; i < DamageType.values().length; i++){
            copy.subtractArray[i] = subtractArray[i];
        }
        return copy;
    }
    
}
