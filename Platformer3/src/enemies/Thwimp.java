/*

 */
package enemies;

import abstractthings.GameObject;
import abstractthings.Spawnable;
import actions.ContinuousDamageOnContact;
import actions.Gravity;
import actions.Leap;
import actions.RepeatingAction;
import actions.VelocityDrag;
import animations.SingleFrameAnimation;
import interactions.HitFloor;
import interactions.HitWall;
import interactions.Interaction;
import items.Material;
import java.awt.Graphics;
import javax.swing.JFrame;
import player.Player;
import support.DamageType;
import support.GameObjectList;
import support.InputHandler;
import support.OtherThings;
import support.WhoToDamage;


public class Thwimp extends Enemy implements Spawnable{
    
    private static final double HORIZONTAL_JUMP_POWER = 4.2;
    private static final double VERTICAL_JUMP_POWER = 10;
    private static final double JUMP_DELAY = 1;
    
    private ContinuousDamageOnContact damageAction;
    private RepeatingAction jumpTimer;
    private boolean onGround;
    
    public Thwimp(){//for prototype
        super();
        damageAction = new ContinuousDamageOnContact("Damage Player",DamageType.CRUSH,8,WhoToDamage.PLAYER,null,0.9);
        jumpTimer = new RepeatingAction("Jump",JUMP_DELAY);
        onGround = false;
        
        graphics.addAnimation("Thwimp",new SingleFrameAnimation("Enemies/Thwimp/Thwimp"),true);
        
        actionList.addAction(new Gravity("Gravity"));
        //actionList.addAction(new VelocityDrag("Drag"));
        actionList.addAction(damageAction);
        actionList.addAction(jumpTimer);
    }
    
    @Override
    public void draw(Graphics g){
        drawPlain(g);//extra draw methods for drawing pathName?
    }

    
    ////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
     public void act(GameObjectList o, InputHandler i){// is this nessesary? do with action objects instead? what if actions need to communicate?
        super.act(o,i);
        onGround = onGround(o);
    }
    
    @Override
    public void react(GameObject actor, Interaction i) {
        super.react(actor, i);
        System.out.println(i);
        if (i instanceof HitFloor){
            
            if (velocity.y > 0){
                jumpTimer.restart();//sometimes jumps right away after landing***
            }
            
            if (velocity.y >= 0){
                setXVelocity(0);
                
                //onGround = true;
                actionList.removeAction(damageAction);
                damageAction.reset();
            }
        }
        
        if (i instanceof HitWall){//to get up steps
            if (!onGround){
                setXVelocity(OtherThings.facingRightMultiplier(facingRight) * HORIZONTAL_JUMP_POWER / 4);
            }
        }
        
    }
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {
        System.out.println(onGround);
        if (command.equals("Jump") && onGround){
            facingRight = (Player.getPlayer().getCenter().x > getCenter().x);
            Leap.doAction(this, OtherThings.facingRightMultiplier(facingRight) * HORIZONTAL_JUMP_POWER,VERTICAL_JUMP_POWER, o, statEffectHandler);
            onGround = false;
            actionList.addAction(damageAction);
        }
        
    }
    
    
    @Override
    public void fillInventory(){
        inventory.addItem(Material.COIN,1);
    }
    
    /////////////////////////////////////////////////////////////////////

    @Override
    public String saveFileString() {
        return fileStringStart();
    }

    @Override
    public void readFileString(String[] str) {
        this.setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
    }

    @Override
    public void customize(JFrame frame) {
        //nothing, make sound instead?
    }
    
}
