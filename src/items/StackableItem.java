/*

 */
package items;

import abstractthings.GameObject;
import player.Player;


public abstract class StackableItem extends Item{
    
    public abstract Material getType();
    public abstract int getValue();
    
    @Override
    public void addToInventory(GameObject g){
        g.getInventory().addItem(getType(), getValue());
    }
    
}
