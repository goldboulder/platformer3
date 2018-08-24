/*

 */
package items;

import abstractthings.Agent;
import abstractthings.GameObject;
import player.Player;


public abstract class UpgradeItem extends Item{

    @Override
    public void addToInventory(GameObject g) {//other monsters can collect it?
        g.collectUpgradeItem(this);
    }
    
    public abstract void upgrade(Player p);

    
}
