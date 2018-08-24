/*

 */
package enemies;

import abstractthings.GameObject;
import abstractthings.Spawnable;
import abstractthings.Spawner;
import actions.DelayedAction;
import actions.Gravity;
import actions.RepeatingAction;
import actions.VelocityDrag;
import animations.MultiFrameAnimation;
import animations.SingleFrameAnimation;
import gui.SpawnableSelectionPanel;
import interactions.Interaction;
import items.Material;
import java.awt.Graphics;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import otherobjects.Explosion;
import support.GameObjectList;
import support.InputHandler;
import support.OtherThings;
import support.PlayScene;
import support.PrototypeProvider;


public class Duplicatotron extends Enemy implements Spawner{
    
    private Spawnable spawnPrototype;
    private RepeatingAction spawnTimer;
    
    
    public static final double SPAWN_ANIMATION_TIME = 0.6;
    public static final double SPAWN_TIME_MIN = SPAWN_ANIMATION_TIME + 2;//in mills
    public static final double SPAWN_TIME_MAX = SPAWN_ANIMATION_TIME + 4;//in mills
    public static final double EXPLOSION_WAIT_TIME = 2.6;
    public static final double EXPLOSION_DIAMETER = 5.5;
    public static final double EXPLOSION_DAMAGE = 21;
    public static final double EXPLOSION_DURATION = 0.8;
    
    
    public Duplicatotron(){//for spawnPrototype
        super();
        facingRight = true;
        graphics.addAnimation("RightIdle",new SingleFrameAnimation("Enemies/Duplicatotron/Right/Idle"),true);
        graphics.addAnimation("LeftIdle",new SingleFrameAnimation("Enemies/Duplicatotron/Left/Idle"));
        graphics.addAnimation("LeftSpawn",new MultiFrameAnimation("Enemies/Duplicatotron/Left/Spawn",SPAWN_ANIMATION_TIME));
        graphics.addAnimation("RightSpawn",new MultiFrameAnimation("Enemies/Duplicatotron/Right/Spawn",SPAWN_ANIMATION_TIME));
        graphics.addAnimation("RightDead",new SingleFrameAnimation("Enemies/Duplicatotron/Right/Dead"));
        graphics.addAnimation("LeftDead",new SingleFrameAnimation("Enemies/Duplicatotron/Left/Dead"));// dead pic is the same as idle
        
        
        
        spawnTimer = new RepeatingAction("spawn",OtherThings.randomRange(SPAWN_TIME_MIN,SPAWN_TIME_MAX));
        actionList.addAction(spawnTimer);
        actionList.addAction(new Gravity("Gravity"));
        actionList.addAction(new VelocityDrag("Drag"));
        
        spawnPrototype = PrototypeProvider.getDefaultSpawnPrototype();
    }
    
    @Override
    public void copyFields(GameObject g){
        super.copyFields(g);
        Duplicatotron dup = (Duplicatotron) g;
        this.spawnPrototype = dup.spawnPrototype;
        this.maxChildren = dup.maxChildren;
    }
    
    


    
    ////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void draw(Graphics g){
        drawPlain(g);
    }
    
    public static final double ENEMY_DISPLAY_SIZE = 0.6;
    @Override
    public void drawEditInfo(Graphics g){
        
        drawString(g, position.x + (size.getX())/2, position.y - 0.3, 0.5, Integer.toString(maxChildren));
        
        //makes enemy appear in the duplicatotron's funnel
        double xOffset;
        if (facingRight){
            xOffset = size.getX()*0.70;
        }
        else{
            xOffset = size.getY()*0.06;
        }
        drawPlainPicture(g,position.x + xOffset,position.y + size.getY() * 0.15 ,ENEMY_DISPLAY_SIZE,ENEMY_DISPLAY_SIZE,spawnPrototype.getProfileImage(),0.5);
    }
    
    @Override
    public void setFaceRight(boolean b) {
        super.setFaceRight(b);
        changeIdleGraphic(b);
    }
    
    private void changeIdleGraphic(boolean b){
        if (b){
            graphics.setCurrentAnimation("RightIdle");
        }
        else{
            graphics.setCurrentAnimation("LeftIdle");
        }
    }
    
    @Override
    public void faceOtherWay(){
        super.faceOtherWay();
        changeIdleGraphic(facingRight);
    }
    
    @Override
     public void act(GameObjectList o, InputHandler i){
        super.act(o,i);
    }
    
    @Override
    public void react(GameObject actor, Interaction i) {
        super.react(actor, i);
    }
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {
        if (command.equals("spawn")){
            if (spawnPrototype != null && children.size() < maxChildren){
                
                if (facingRight){
                    graphics.setTempAnimation("RightSpawn", SPAWN_ANIMATION_TIME);
                }
                else{
                    graphics.setTempAnimation("LeftSpawn", SPAWN_ANIMATION_TIME);
                }
                
                actionList.addAction(new DelayedAction("Spawn Prep",SPAWN_ANIMATION_TIME));
                spawnTimer.setPeriod(OtherThings.randomRange(SPAWN_TIME_MIN,SPAWN_TIME_MAX));
            }
        }
        
        if (command.equals("Spawn Prep")){
            spawn();
        }
        
        if (command.equals("Explode")){
            this.spawnObject(new Explosion(this,EXPLOSION_DIAMETER,EXPLOSION_DAMAGE,EXPLOSION_DURATION));
            super.delete();
        }
    }
    
    private void spawn(){
        GameObject g = spawnPrototype.getCopy();//must be copy, not original
        
        
        int facingMult = facingRight ? 1 : -1;
        
        g.setCenter(getCenter().x + (0.3 * facingMult * size.getX()),getCenter().y - (size.getY() * 0.3));
        g.setVelocity(OtherThings.randomRange(1.2,2.5) * facingMult, OtherThings.randomRange(-2.3,-3.2));
        g.setFaceRight(facingRight);
        spawnObject(g);
    }
    
    @Override
    public void delete(){
        invincible = true;
        actionList.removeAction("spawn");
        actionList.removeAction("Spawn Prep");
        actionList.addAction(new DelayedAction("Explode",EXPLOSION_WAIT_TIME));
        
        if (facingRight){
            graphics.setCurrentAnimation("RightDead");
        }
        else{
            graphics.setCurrentAnimation("LeftDead");
        }
        
        
    }
    
    @Override
    public Spawnable getSpawnPrototype() {// these methods will need to be copied for more Spawners
        return spawnPrototype;//.getCopy?
    }
    
    @Override
    public void setSpawnPrototype(Spawnable s) {
        spawnPrototype = s;
    }
    
    @Override
    public void fillInventory(){
        inventory.addItem(Material.COIN,1);
    }
    
    /////////////////////////////////////////////////////////////////////

    @Override
    public String saveFileString() {
        String proto;
        if (spawnPrototype != null){
            proto = spawnPrototype.getId();
        }
        else{
            proto = "null";
        }
        return fileStringStart() + " " + facingRight + " " + proto + " " + Integer.toString(maxChildren);
    }

    @Override
    public void readFileString(String[] str) {
        this.setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
        this.facingRight = Boolean.parseBoolean(str[3]);
        
        
        changeIdleGraphic(facingRight);
        
        if (!str[4].equals("null")){
            spawnPrototype = (Spawnable) PrototypeProvider.getOriginal(str[4]);
        }
        maxChildren = Integer.parseInt(str[5]);
    }

    @Override
    public void customize(JFrame frame) {// same as other spawners***
        JDialog customDialog = new JDialog(frame, "Customize Subspace Spawner", true);//getId?
        customDialog.setLocationRelativeTo(null);
        
        customDialog.getContentPane().add(new JScrollPane(new SpawnableSelectionPanel(frame, this)));
        
        customDialog.pack();
        customDialog.setVisible(true);
    }

    

    
    
}
