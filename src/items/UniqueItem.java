/*

 */
package items;

import abstractthings.Agent;
import abstractthings.GameObject;
import player.Player;
import support.PlayScene;


public abstract class UniqueItem extends Item{

    protected int coolDown;//in mills
    protected int reloadTime;// in mills
    
    public UniqueItem(){
        super();
        coolDown = 0;
    }
    
    @Override
    public void addToInventory(GameObject g){
        g.getInventory().addItem(this);
    }
    
    public void use(GameObject g){
        if (!onCoolDown()){
            g.useItem(this);
            coolDown = reloadTime;//global cooldown?
        }
    }
    
    public abstract void useItem(GameObject user);
    
    
    public boolean onCoolDown(){
        return (coolDown > 0);
    }
    
    public void coolDownTick(){
        if (coolDown > 0){
            coolDown -= PlayScene.GAME_TICK;
        }
    }
    
    @Override
    public void copyFields(GameObject g){
        super.copyFields(g);
        UniqueItem u = (UniqueItem) g;
        this.coolDown = u.coolDown;
        this.reloadTime  = u.reloadTime;
    }
    
    
    
}
