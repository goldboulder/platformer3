/*

 */
package actions;

import abstractthings.GameObject;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class TerminalVelocity extends Action{

    public TerminalVelocity(String command) {
        super(command);
    }

    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void doAction(GameObject owner){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
