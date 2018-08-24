/*

 */
package enemies;

import abstractthings.GameObject;
import abstractthings.Spawnable;
import actions.ContinuousDamageOnContact;
import actions.FaceObject;
import actions.Gravity;
import actions.Leap;
import actions.RepeatingAction;
import actions.VelocityDrag;
import actions.WalkForward;
import animations.SingleFrameAnimation;
import interactions.HitFloor;
import interactions.HitWall;
import interactions.Interaction;
import items.Material;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import player.Player;
import support.DamageType;
import support.GameObjectList;
import support.InputHandler;
import support.OtherThings;
import support.PlayScene;
import support.WhoToDamage;


public class Slime extends Enemy implements Spawnable{
    
    private static final double JUMP_TIME_MIN = 0.8;
    private static final double JUMP_TIME_MAX = 2.5;
    private int slimeSize;
    private RepeatingAction jumpTimer;
    private static final int WALK_FORWARD_TIME = 600;
    private int walkForwardTime = 0;
    
    public Slime(){//for prototype
        super();
        facingRight = false;
        
        graphics.addAnimation("slime",new SingleFrameAnimation("Enemies/Slime/Standard"),true);
        
        actionList.addAction(new Gravity("Gravity"));
        jumpTimer = new RepeatingAction("Jump",OtherThings.randomRange(JUMP_TIME_MIN,JUMP_TIME_MAX));
        actionList.addAction(jumpTimer);
        actionList.addAction(new ContinuousDamageOnContact("Damage Player",DamageType.CRUSH,0,WhoToDamage.PLAYER,null,0.9));
        actionList.addAction(new WalkForward("cliff climb",0.15));// stops the slime's momentum being killed from hitting a small wall preventing slime from going over it
        actionList.addAction(new VelocityDrag("Drag"));
        //add invincibility for a short while?
    }
    
    @Override
    public GameObject getPrototype() {// have default constructor for all GameObjects? No, non placeables don't have default constructors
        Slime slime = new Slime();
        slime.setSlimeSize(0);
        return slime;
    }
    
    

    
    ////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
     public void act(GameObjectList o, InputHandler i){// is this nessesary? do with action objects instead? what if actions need to communicate?
        super.act(o,i);
        
        if (walkForwardTime > 0){
            walkForwardTime -= PlayScene.GAME_TICK;
            WalkForward.doAction(this, 0.6, statEffectHandler);
        }
    }
    
    @Override
    public void react(GameObject actor, Interaction i) {
        super.react(actor, i);
        
        if (i instanceof HitFloor){
            this.setXVelocity(velocity.x * 0.6);
        }
        
        if (i instanceof HitWall){
            walkForwardTime = WALK_FORWARD_TIME;
        }
        
    }
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {
        if (command.equals("Jump")){
            FaceObject.doAction(this, Player.getPlayer());//target other enemies? have "target" be a field,face only when jumping?
            Leap.doAction(this, horizontalJumpPower(slimeSize),verticalJumpPower(slimeSize), o, statEffectHandler);
            jumpTimer.setPeriod(OtherThings.randomRange(JUMP_TIME_MIN, JUMP_TIME_MAX));
        }
        
    }
    
    private void setSlimeSize(int size){
        if (size < 0){//size cannot be negative
            size = 0;
        }
        
        this.slimeSize = size;
        this.maxHealth = HPFunction(size);
        this.health = maxHealth;
        double newSize = sizeFunction(size);
        setSize(newSize,newSize);
        actionList.replaceAction(new ContinuousDamageOnContact("Damage Player",DamageType.CRUSH,damageFunction(size),WhoToDamage.PLAYER,null,0.9));
    }
    
    private double HPFunction(int size){
        return 1 + 3*size;
    }
    
    private double sizeFunction(int size){
        return 0.5*(size+1);
    }
    
    private double damageFunction(int size){
        return 2*size;
    }
    
    private double verticalJumpPower(int size){
        return Math.sqrt(25*(size+1));//balance?
    }
    
    private double horizontalJumpPower(int size){
        double x = Math.sqrt(4.5*(size+1));
        if (!facingRight){
            x = -x;
        }
        return x;
    }
    
    public void delete(){
        super.delete();
        if (slimeSize > 0){
            split();
        }
    }
    
    private void split(){
        Slime leftSlime = new Slime();
        leftSlime.setSlimeSize(slimeSize/2);
        leftSlime.setFaceRight(false);
        leftSlime.setCenter(getCenter());
        leftSlime.setVelocity(-1, -1);
        
        Slime rightSlime = new Slime();
        rightSlime.setSlimeSize(slimeSize/2);
        rightSlime.setFaceRight(true);
        rightSlime.setCenter(getCenter());
        rightSlime.setVelocity(1, -1);
        
        this.spawnObject(leftSlime);
        this.spawnObject(rightSlime);
    }
    
    
    @Override
    public void fillInventory(){
        inventory.addItem(Material.COIN,1);
    }
    
    /////////////////////////////////////////////////////////////////////

    @Override
    public String saveFileString() {
        return fileStringStart() + " " + slimeSize + " " + facingRight;
    }

    @Override
    public void readFileString(String[] str) {
        this.setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
        setSlimeSize(Integer.parseInt(str[3]));
        facingRight = Boolean.parseBoolean(str[4]);
    }

    @Override
    public void customize(JFrame frame) {
        try{
            setSlimeSize(Integer.parseInt(JOptionPane.showInputDialog(frame, "Set the slime's size", slimeSize)));
        }
        catch(NumberFormatException e){
            
        }
    }
    
}
