/*

 */
package player;

import abstractthings.Copyable;
import items.Item;
import items.Material;
import items.StackableItem;
import items.YellowCoin;
import java.util.LinkedList;
import support.PrototypeProvider;


public class MaterialInventory implements Copyable{// exactly one slot for each item  (set),hash map?
    
    private int[] contents;
    
    public MaterialInventory(){
        contents = new int[Material.values().length];
    }
    
    
    public void addItem(Material m, int amount){
        contents[m.ordinal()] += amount;
    }
    
    public int amountOfItem(Material m){
        return contents[m.ordinal()];
    }
    
    
    
    public void removeItem(Material m, int amount){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

    public void removeAll() {
        for (int i = 0; i < Material.values().length; i++){
            contents[i] = 0;
        }
    }
    
    // returns a list consisting of the minimum amount of items to equal <amount> of <m>
    public static LinkedList<Item> materialsToItems(Material m, int amount){
        LinkedList<Item> result = new LinkedList<>();
        if (amount <= 0){
            return result;
        }
        int denomination = (int)Math.pow(10,(int)Math.log10(amount));// round down to nearest power of 10
        
        while (amount > 0){
            while(amount >= denomination){
                
                //if denomination does not exist, go to lower tier
                Item i = PrototypeProvider.materialAmountToItem(m,denomination);
                if (i == null){
                    break;
                }
                
                result.add(i);
                amount -= denomination;
            }
            denomination /= 10;
        }
        return result;
        
    }
    
    @Override
    public Object getCopy() {//copies the array of materials
        MaterialInventory m = new MaterialInventory();
        System.arraycopy(contents, 0, m.contents, 0, contents.length);
        return m;
    }
    
    
    
    
    
}
