
package player;

import actions.Gravity;
import animations.SingleFrameAnimation;
import abstractthings.Agent;
import abstractthings.GameObject;
import abstractthings.Placeable;
import actions.CollectItems;
import actions.Jump;
import actions.WalkForward;
import gui.HotBarPanel;
import items.UniqueItem;
import items.UpgradeItem;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import support.GameObjectList;
import support.ImagePool;
import support.InputHandler;
import support.PlayScene;


public class Player extends Agent implements Placeable{// rename?
    
    private double walkSpeed;
    private int globalItemCoolDown; // in mills. shared for all weapons
    public static final int DOOR_COOL_DOWN = 1000;
    public static final int GLOBAL_ITEM_COOL_DOWN = 200;
    private int doorCoolDown;
    private double jumpVelocity;
    
    
    private int hotBarFocus;
    
    
    private static Player player;
    
    
    public static void newPlayer(){
        player = new Player();
        
    }
    
    public static void setPlayer(String file){
        player = new Player(file);
    }
    
    public static Player getPlayer(){
        if (player == null){
            player = new Player();
        }
        return player;
    }
    
    public static Player getPlayer(String file){
        if (player == null){
            player = new Player(file);
        }
        return player;
    }
    
    public static void deletePlayer(){
        player = null;
    }
    
    private Player(){//new player
        super();
        
        this.position = new Point2D.Double(0,0);
        this.facingRight = true;
        
        readEnemyFile("Player");// prototype?
        
        
        graphics.addAnimation("default",new SingleFrameAnimation("Default Image"),true);
        
        actionList.addAction(new Gravity("Gravity"));
        actionList.addAction(new CollectItems("CollectItems"));
        
        walkSpeed = 3.5;
        globalItemCoolDown = 0;
        doorCoolDown = 0;
        jumpVelocity = 9;
        
    }
    
    private Player(String file){// data taken from file (later)
        super();
        
    }

    
    
    @Override
    public void act(GameObjectList o, InputHandler i){
        super.act(o,i);
        
        if (i.keyPressed(KeyEvent.VK_A)){
            this.facingRight = false;
            WalkForward.doAction(this, walkSpeed, statEffectHandler);
        }
        if (i.keyPressed(KeyEvent.VK_D)){
            this.facingRight = true;
            WalkForward.doAction(this, walkSpeed, statEffectHandler);
        }
        
        if (i.keyPressed(KeyEvent.VK_SPACE)){
            Jump.doAction(this, jumpVelocity, o, statEffectHandler);
        }
        if (i.mouseScrollWheel() != 0){
            hotBarFocus = Math.abs((hotBarFocus + i.mouseScrollWheel() + HotBarPanel.HOT_BAR_SIZE) % HotBarPanel.HOT_BAR_SIZE);
        }
        
        if (i.leftMouseButtonPressed()){//switch statement
            inventory.useItem(this,hotBarFocus);
        }
        if (i.keyPressed(KeyEvent.VK_1)){
            inventory.useItem(this, 0);
        }
        if (i.keyPressed(KeyEvent.VK_2)){
            inventory.useItem(this, 1);
        }
        if (i.keyPressed(KeyEvent.VK_3)){
            inventory.useItem(this, 2);
        }
        if (i.keyPressed(KeyEvent.VK_4)){
            inventory.useItem(this, 3);
        }
        if (i.keyPressed(KeyEvent.VK_5)){
            inventory.useItem(this, 4);
        }
        if (i.keyPressed(KeyEvent.VK_6)){
            inventory.useItem(this, 5);
        }
        if (i.keyPressed(KeyEvent.VK_7)){
            inventory.useItem(this, 6);
        }
        if (i.keyPressed(KeyEvent.VK_8)){
            inventory.useItem(this, 7);
        }
        if (i.keyPressed(KeyEvent.VK_9)){
            inventory.useItem(this, 8);
        }
        if (i.keyPressed(KeyEvent.VK_0)){
            inventory.useItem(this, 9);
        }
        globalItemCoolDown -= PlayScene.GAME_TICK;
       
        //cooldowns all in one method if there are enough of them?
        
        if (doorCoolDown > 0){
            doorCoolDown -= PlayScene.GAME_TICK;
        }
        rotation = this.angleTo(i.mousePosition());
        
    }
    
    @Override
    public void useItem(UniqueItem u) {
        if (!onGlobalItemCooldown()){
            u.useItem(this);
            globalItemCoolDown = GLOBAL_ITEM_COOL_DOWN;//set to item's cooldown instead?
        }
    }
    
    @Override
    public void draw(Graphics g){
        drawWithoutRotation(g);
    }
    
    public int getGlobalCoolDown(){
        return globalItemCoolDown;
    }
    
    public void setGlobalCoolDown(int coolDown){
        globalItemCoolDown = coolDown;
    }
    
    public boolean onGlobalItemCooldown(){
        return (globalItemCoolDown > 0);
    }
    
    public boolean onDoorCoolDown(){
        return (doorCoolDown > 0);
    }
    
    public void goThroughDoorHandling(){
        doorCoolDown = DOOR_COOL_DOWN;
    }
    
    public int getHotBarFocus(){
        return hotBarFocus;
    }
    
    @Override
    public void collectUpgradeItem(UpgradeItem u) {
        u.upgrade(this);
    }

    @Override
    public GameObject getPrototype() {
        return getPlayer();
    }

    

    @Override
    public BufferedImage getProfileImage() {
        return ImagePool.getPicture("Default Image");
    }

    @Override
    public String saveFileString() {
        return getId() + " " + Double.toString(position.getX()) + " " + Double.toString(position.getY());
    }

    @Override
    public void readFileString(String[] str) {
        this.setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));// read another file for player stats?
    }
    
    @Override
    public void customize(JFrame frame) {
        //nothing, make sound instead?
    }
    
    
    
}
