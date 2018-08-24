/*

 */
package actions;

import abstractthings.GameObject;
import interactions.CollectItem;
import interactions.Collected;
import items.Item;
import java.util.Iterator;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class CollectItems extends Action{// add filter? subclass for just coins?

    public static final int AGE_LIMIT = 500;

    public CollectItems(String command) {
        super(command);
    }
    
    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,o,s);
    }
    
    public static void doAction(GameObject owner, GameObjectList o, StatEffectHandler s){
        Iterator<GameObject> items = o.getItemIterator();
        while(items.hasNext()){
            Item item = (Item) items.next();
            if (owner.colliding(item) && item.getAge() > AGE_LIMIT){
                item.addToInventory(owner);
                owner.react(item,new CollectItem(item));
                item.react(owner, new Collected());
            }
        }
    }
    
}
