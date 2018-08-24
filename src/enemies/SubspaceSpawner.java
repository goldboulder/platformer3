/*

 */
package enemies;

import abstractthings.GameObject;
import abstractthings.Spawnable;
import abstractthings.Spawner;
import actions.RepeatingAction;
import animations.SingleFrameAnimation;
import gui.SpawnableSelectionPanel;
import interactions.Interaction;
import interactions.TakeDamage;
import items.Material;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import support.GameObjectList;
import support.InputHandler;
import support.OtherThings;
import support.PrototypeProvider;


public class SubspaceSpawner extends Enemy implements Spawner{
    
    private Spawnable spawnPrototype;
    private RepeatingAction spawnTimer;
    
    
    
    public static final double SPAWN_TIME_MIN = 1.5;//in mills
    public static final double SPAWN_TIME_MAX = 3.5;//in mills
    
    public SubspaceSpawner(){//for spawnPrototype
        super();
        pushable = false;
        graphics.addAnimation("normal",new SingleFrameAnimation("Enemies/SubSpaceSpawner/Profile"),true);
        graphics.addAnimation("hurt",new SingleFrameAnimation("Enemies/SubSpaceSpawner/Hurt"));
        spawnTimer = new RepeatingAction("spawn",OtherThings.randomRange(SPAWN_TIME_MIN,SPAWN_TIME_MAX));
        actionList.addAction(spawnTimer);
       
        spawnPrototype = PrototypeProvider.getDefaultSpawnPrototype();
    }
    
    @Override
    public void copyFields(GameObject g){
        super.copyFields(g);
        SubspaceSpawner s = (SubspaceSpawner) g;
        this.spawnPrototype = s.spawnPrototype;
        this.maxChildren = s.maxChildren;
    }
    
    public static final double ENEMY_DISPLAY_SIZE = 0.6;
    @Override
    public void drawEditInfo(Graphics g){
        
        drawString(g, position.x + (size.getX())/2, position.y - 0.3, 0.5, Integer.toString(maxChildren));
        
        drawPlainPicture(g,position.x + (size.getX() - ENEMY_DISPLAY_SIZE)/2,position.y + (size.getY() - ENEMY_DISPLAY_SIZE)/2,ENEMY_DISPLAY_SIZE,ENEMY_DISPLAY_SIZE,spawnPrototype.getProfileImage(),0.5);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
     public void act(GameObjectList o, InputHandler i){
        super.act(o,i);
    }
    
    @Override
    public void react(GameObject actor, Interaction i) {
        super.react(actor, i);
        if (i instanceof TakeDamage){
            graphics.setTempAnimation("hurt", 0.4);
        }
    }
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {
        if (command.equals("spawn")){
            if (spawnPrototype != null && children.size() < maxChildren){
                spawn();
                spawnTimer.setPeriod(OtherThings.randomRange(SPAWN_TIME_MIN,SPAWN_TIME_MAX));
            }
        }
    }
    
    private void spawn(){
        GameObject g = spawnPrototype.getCopy();//must be copy, not original
        g.setCenter(getCenter());
        spawnObject(g);
    }
    
    @Override
    public Spawnable getSpawnPrototype() {// these methods will need to be copied for more Spawners
        return spawnPrototype;
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
        return fileStringStart() + " " + proto + " " + Integer.toString(maxChildren);
    }

    @Override
    public void readFileString(String[] str) {
        this.setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
        if (!str[3].equals("null")){
            spawnPrototype = (Spawnable) PrototypeProvider.getOriginal(str[3]);
        }
        maxChildren = Integer.parseInt(str[4]);
    }

    @Override
    public void customize(JFrame frame) {
        JDialog customDialog = new JDialog(frame, "Customize Subspace Spawner", true);//getId?
        customDialog.setLocationRelativeTo(null);
        
        customDialog.getContentPane().add(new JScrollPane(new SpawnableSelectionPanel(frame, this)));
        
        customDialog.pack();
        customDialog.setVisible(true);
    }

    

    
    
}
