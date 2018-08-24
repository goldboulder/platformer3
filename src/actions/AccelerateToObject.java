/*

 */
package actions;

import abstractthings.GameObject;
import java.awt.event.ActionEvent;
import java.util.Collection;
import stat_effects.StatEffectHandler;

import support.GameObjectList;



public class AccelerateToObject extends Action{//pulls a single object to another object
    private GameObject objectAttractedTo;
    private double acceleration;
    
    public AccelerateToObject(String command, GameObject g, double acceleration){
        super(command);
        objectAttractedTo = g;//null?
        this.acceleration = acceleration;
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,objectAttractedTo);
    }

    public static void doAction(GameObject owner, GameObject objectAttractedTo){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    
    
}
