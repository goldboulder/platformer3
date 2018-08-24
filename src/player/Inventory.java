/*

 */
package player;

import abstractthings.Copyable;
import abstractthings.GameObject;
import items.Item;
import items.Material;
import items.UniqueItem;
import items.UpgradeItem;
import items.YellowCoin;
import java.util.LinkedList;
import support.OtherThings;


public class Inventory implements Copyable{// move to items package?
    
    private MaterialInventory materialInventory;
    private UniqueInventory uniqueInventory;
    
    public Inventory(){
        materialInventory = new MaterialInventory();
        uniqueInventory = new UniqueInventory();
        
    }
    
    public void addItem(Material m, int amount){
        materialInventory.addItem(m,amount);
    }
    
    public int amountOfItem(Material m){
        return materialInventory.amountOfItem(m);
    }
    
    public void removeItem(Material m, int amount){
        materialInventory.removeItem(m,amount);
    }
    
    public void addItem(UniqueItem i){
        uniqueInventory.addItem(i);
    }
    
    public boolean hasItem(UniqueItem i){
        return uniqueInventory.hasItem(i);
    }
    
    public void removeItem(UniqueItem i){
        uniqueInventory.removeItem(i);
    }
    
    public UniqueItem getUniqueItem(int index){
        return uniqueInventory.getItem(index);
    }
    
    public void useItem(GameObject user, int index){
        uniqueInventory.useItem(user,index);
    }
    
    public void itemCoolDownTick(){
        uniqueInventory.itemCoolDownTick();
    }
    
    public void dropItem(GameObject owner, Material m, int amount){
        // stops dropping more items than you have
        int realAmount = materialInventory.amountOfItem(m);
        if (realAmount < amount){
            amount = realAmount;
        }
        
        LinkedList<Item> items = MaterialInventory.materialsToItems(m,amount);
        for (Item i : items){
            spawnItemOnGround(owner,i);
        }
        materialInventory.removeItem(m, amount);
    }
    
    public void dropItem(GameObject owner, UniqueItem i){
        spawnItemOnGround(owner,i);
        uniqueInventory.removeItem(i);
    }
    
    public void dropAll(GameObject owner){
        Material[] materials = Material.values();
        
        for (Material m : materials){
            LinkedList<Item> mItems = MaterialInventory.materialsToItems(m,materialInventory.amountOfItem(m));
            for (Item i : mItems){
                spawnItemOnGround(owner,i);
            }
        }
        materialInventory.removeAll();
        
        for (UniqueItem i : uniqueInventory.getContents()){
            spawnItemOnGround(owner,i);
            i.setDeleteReady(false);
        }
        uniqueInventory.removeAll();
        
    }
    
    
    public void spawnItemOnGround(GameObject owner, Item i){
        i.resetAge();
        i.setCenter(owner.getCenter());// set bottom to this point?
        i.setXVelocity(OtherThings.randomRange(-2, 2));
        i.setYVelocity(OtherThings.randomRange(-4, -1));
        owner.spawnObject(i);
        
    }
    
    public UniqueInventory getUniqueInventory(){
        return uniqueInventory;
    }

    @Override
    public Object getCopy() {
        Inventory i = new Inventory();
        i.materialInventory = (MaterialInventory)materialInventory.getCopy();
        i.uniqueInventory = (UniqueInventory)uniqueInventory.getCopy();
        return i;
    }
    
}
