/*

 */
package actions;

import abstractthings.GameObject;
import stat_effects.StatEffectHandler;
import support.GameObjectList;

// makes it so that enemies turn around when they reach the edge of a platform instead of falling off
// example: red koopa
public class TurnOnEdge extends Action{

    public TurnOnEdge(String command) {
        super(command);
    }

    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void doAction(){
        
    }
    
}
