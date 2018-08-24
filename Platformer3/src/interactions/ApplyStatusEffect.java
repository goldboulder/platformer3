/*

 */
package interactions;

import abstractthings.GameObject;
import stat_effects.StatEffect;


public class ApplyStatusEffect extends Interaction{
    private GameObject applier;
    private StatEffect effect;
    
    public ApplyStatusEffect(GameObject applier, StatEffect effect){
        this.applier = applier;
        this.effect = effect;
    }
    
    public GameObject getApplier(){
        return applier;
    }
    
    public StatEffect getEffect(){
        return effect;
    }
}
