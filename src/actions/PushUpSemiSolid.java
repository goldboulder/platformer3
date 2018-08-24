/*

 */
package actions;

import abstractthings.GameObject;
import java.util.Collection;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class PushUpSemiSolid extends Action{

    public PushUpSemiSolid(String command) {
        super(command);
    }

    @Override
    public void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,o);
    }
    
    public void doAction(GameObject owner, GameObjectList o){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
