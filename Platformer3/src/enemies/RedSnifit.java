/*

 */
package enemies;

import abstractthings.GameObject;
import abstractthings.Spawnable;
import actions.DelayedAction;
import actions.Gravity;
import actions.RepeatingAction;
import actions.VelocityDrag;
import actions.WalkForward;
import animations.LoopingAnimation;
import animations.MultiFrameAnimation;
import animations.SingleFrameAnimation;
import interactions.HitWall;
import interactions.Interaction;
import interactions.TakeDamage;
import items.Material;
import javax.swing.JFrame;
import projectiles.SnifitBullet;
import support.GameObjectList;
import support.InputHandler;
import support.OtherThings;


public class RedSnifit extends Enemy implements Spawnable{
    
    
    private static final double WAIT_TO_SHOOT_MIN = 0.8;
    private static final double WAIT_TO_SHOOT_MAX = 3;
    private static final double SHOOT_PREP_TIME = 0.55;
    private static final double STUN_TIME = 0.4;
    private static final double BULLET_SPEED = 5;
    private static final double BULLET_DAMAGE = 3;
    private static final double WALK_SPEED = 2;
    
    private RepeatingAction shootTimer;
    
    public RedSnifit(){//for prototype
        super();
        facingRight = false;
        shootTimer = new RepeatingAction("Shoot",SHOOT_PREP_TIME + OtherThings.randomRange(WAIT_TO_SHOOT_MIN, WAIT_TO_SHOOT_MAX));
        
        graphics.addAnimation("Walking",new LoopingAnimation("Enemies/RedSnifit/Walking",0.7),true);
        graphics.addAnimation("Hurt",new SingleFrameAnimation("Enemies/RedSnifit/Hurt"));
        graphics.addAnimation("Shooting",new MultiFrameAnimation("Enemies/RedSnifit/Shooting",SHOOT_PREP_TIME));
        
        actionList.addAction(new Gravity("Gravity"));
        actionList.addAction(new WalkForward("Walk",WALK_SPEED));
        actionList.addAction(shootTimer);
        actionList.addAction(new VelocityDrag("Drag"));
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
     public void act(GameObjectList o, InputHandler i){// is this nessesary? do with action objects instead? what if actions need to communicate?
        super.act(o,i);
    }
    
    @Override
    public void react(GameObject actor, Interaction i) {
        super.react(actor, i);
        if (i instanceof HitWall){
            faceOtherWay();
        }
        if (i instanceof TakeDamage){
            actionList.stallAction("Walk", STUN_TIME);
            actionList.stallAction("Shoot", STUN_TIME);
            actionList.removeAction("Shoot Prep");// if shooting at the time
            graphics.setTempAnimation("Hurt", STUN_TIME);
        }
    }
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {
        
        if (command.equals("Shoot")){
            graphics.setTempAnimation("Shooting", SHOOT_PREP_TIME);
            actionList.addAction(new DelayedAction("Shoot Prep",SHOOT_PREP_TIME));
            shootTimer.setPeriod(SHOOT_PREP_TIME + OtherThings.randomRange(WAIT_TO_SHOOT_MIN, WAIT_TO_SHOOT_MAX));
            actionList.stallAction("Walk", SHOOT_PREP_TIME);
        }
        if (command.equals("Shoot Prep")){//spawn bullet
            this.spawnObject(new SnifitBullet(this,getCenter(),BULLET_DAMAGE,BULLET_SPEED,facingRight ? 0 : Math.toRadians(180)));//reuse bouncyBullet, different picture?
        }
    }
    
    @Override
    public void fillInventory(){
        inventory.addItem(Material.COIN,1);
    }
    
    /////////////////////////////////////////////////////////////////////

    @Override
    public String saveFileString() {
        return fileStringStart() + " " + facingRight;
    }

    @Override
    public void readFileString(String[] str) {
        this.setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
        facingRight = Boolean.parseBoolean(str[3]);
    }

    @Override
    public void customize(JFrame frame) {
        //nothing, make sound instead?
    }
    
}
