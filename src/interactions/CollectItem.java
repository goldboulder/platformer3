/*

 */
package interactions;

import items.Item;


public class CollectItem extends Interaction{//item field not needed. already given in react method
    
    private Item item;
    
    public CollectItem(Item i){
        item = i;
    }
    
    public Item getItem(){
        return item;
    }
    
}
