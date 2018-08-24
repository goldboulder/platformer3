/*

 */
package actions;

import abstractthings.GameObject;
import static actions.CollectItems.AGE_LIMIT;
import interactions.CollectItem;
import interactions.Collected;
import items.Item;
import java.util.Iterator;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PrototypeProvider;


public class CollectSpecificItems extends CollectItems{// use string item id
    
    private String id;
    
    public CollectSpecificItems(String command, String id){//use method,field instead? prototypeProvider.get...(.getId()
        super(command);
        this.id = id;
    }
    
    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        doAction(owner,o,s,id);
    }
    
    public static void doAction(GameObject owner, GameObjectList o, StatEffectHandler s, String id){
        Iterator<GameObject> items = o.getItemIterator();
        while(items.hasNext()){
            Item item = (Item) items.next();
            if (item.getId().equals(id) && owner.colliding(item) && item.getAge() > AGE_LIMIT){
                item.addToInventory(owner);
                owner.react(item,new CollectItem(item));
                item.react(owner, new Collected());
            }
        }
    }
    
}
