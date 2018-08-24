/*

 */
package items;

// any item that can be used/consumed in the hotbar or can be put in inventory

import abstractthings.Copyable;
import abstractthings.GameObject;
import actions.Gravity;
import actions.VelocityDrag;
import interactions.Collected;
import interactions.Interaction;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import player.Player;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.ImagePool;
import support.InputHandler;
import support.PlayScene;
import abstractthings.Placeable;
import abstractthings.Spawnable;
import javax.swing.JFrame;



public abstract class Item extends GameObject implements Placeable, Copyable, Spawnable{//for pick-upable items and items in inventory. actionList only gets called when on the field.
    //packages within this package?
    private int age;// how long it's been in the field, in mills
    // chests could drop stuff like monster drops
    //this.getClass().newInstance();
    
    @Override
    public GameObject getPrototype() { 
        try {
            return getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(getId() + " doesn't have a default constructor!");
        }
    }
    
    public Item(){//gravity parameter?
        actionList.addAction(new Gravity("Gravity"));
        actionList.addAction(new VelocityDrag("Drag"));
        age = 0;
        position = new Point2D.Double(0,0);
    }

    public int getAge() {
        return age;
    }
    
    public void resetAge() {
        age = 0;
    }
    
    @Override
    public void act(GameObjectList o, InputHandler i){
        super.act(o,i);
        age += PlayScene.GAME_TICK;
    }
    
    @Override
    public void react(GameObject g, Interaction i){
        super.react(g, i);
        if (i instanceof Collected){
            delete();
        }
    }
    
    public abstract void addToInventory(GameObject g);

    @Override
    public BufferedImage getProfileImage(){
        return ImagePool.getPicture("Items/" + getId() + "/Profile");
    }
    
    
    @Override
    public String saveFileString() {
        return fileStringStart();//gravity?
    }

    @Override
    public void readFileString(String[] str) {
        this.setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
    }
    
    @Override
    public void customize(JFrame frame) {
        //nothing, setGravity?
    }
    
    @Override
    public void copyFields(GameObject g){
        super.copyFields(g);
        Item i = (Item) g;
        this.age = i.age;
    }
    
    
}
