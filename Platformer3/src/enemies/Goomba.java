/*

 */
package enemies;

import abstractthings.GameObject;
import abstractthings.Spawnable;
import actions.ContinuousDamageOnContact;
import actions.Gravity;
import actions.VelocityDrag;
import actions.WalkForward;
import animations.LoopingAnimation;
import animations.SingleFrameAnimation;
import interactions.HitWall;
import interactions.Interaction;
import interactions.TakeDamage;
import items.Material;
import javax.swing.JFrame;
import support.DamageType;
import support.GameObjectList;
import support.InputHandler;
import support.WhoToDamage;


public class Goomba extends Enemy implements Spawnable{
    
    private static final double WALK_SPEED = 1.25;
    
    public Goomba(){//for prototype
        super();
        facingRight = false;
        
        graphics.addAnimation("goomba",new LoopingAnimation("Enemies/Goomba/Walking",0.6),true);
        graphics.addAnimation("Hurt",new SingleFrameAnimation("Enemies/Goomba/Hurt"));
        
        actionList.addAction(new Gravity("Gravity"));
        actionList.addAction(new VelocityDrag("Drag"));
        actionList.addAction(new WalkForward("Walk",WALK_SPEED));
        actionList.addAction(new ContinuousDamageOnContact("Damage Player",DamageType.CRUSH,2,WhoToDamage.PLAYER,null,0.9));
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
            actionList.stallAction("Walk",0.5);
            graphics.setTempAnimation("Hurt", 0.5);
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
