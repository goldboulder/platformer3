/*

 */
package player;

import abstractthings.Copyable;
import abstractthings.GameObject;
import items.UniqueItem;
import java.util.ArrayList;


public class UniqueInventory implements Copyable{//need interface to see the rest of the items (beyond 10)
    
    private ArrayList<UniqueItem> items;
    
    public UniqueInventory(){
        items = new ArrayList<>();
    }
    
    
    public void addItem(UniqueItem i){
        items.add(i);
    }
    
    public void removeItem(UniqueItem i){
        items.remove(i);
    }
    
    public UniqueItem getItem(int index){
        try{
            return items.get(index);
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
    }
    
    public void useItem(GameObject user, int index){
        if (index < items.size()){
            items.get(index).use(user);
        }
    }
    
    public void itemCoolDownTick(){
        for (UniqueItem u : items){
            u.coolDownTick();
        }
    }

    public boolean hasItem(UniqueItem i) {//string instead?
        return items.contains(i);
    }

    public ArrayList<UniqueItem> getContents() {
        return items;
    }
    
    public int size(){
        return items.size();
    }

    void removeAll() {
        items.clear();
    }

    @Override
    public Object getCopy() {//copy the array list of items
        UniqueInventory u = new UniqueInventory();
        for (UniqueItem i : items){
            u.items.add((UniqueItem)i.getCopy());
        }
        return u;
    }
    
}
