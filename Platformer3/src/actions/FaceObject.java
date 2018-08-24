/*

 */
package actions;

import abstractthings.GameObject;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class FaceObject extends Action{

    private GameObject target;
    
    public FaceObject(String command, GameObject target) {
        super(command);
        this.target = target;
    }

    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,target);
    }
    
    public static void doAction(GameObject owner, GameObject target){
        if (target == null) return;
        
        if (owner.getCenter().x < target.getCenter().x){
            owner.setFaceRight(true);
        }
        else{
            owner.setFaceRight(false);
        }
    }
    
}
