/*

 */
package items;

import abstractthings.GameObject;
import animations.LoopingAnimation;
import java.awt.geom.Point2D;
import player.Player;
import support.GameObjectList;
import support.HitBox;
import support.InputHandler;
import support.MakerScene;
import support.PlayScene;
import support.PrototypeProvider;


public class YellowCoin extends StackableItem{

    private static double SIZE;
    public static final Material TYPE = Material.COIN;
    public static final int VALUE = 1;
    
    public YellowCoin(){
        super();
        SIZE = 0.25;
        this.facingRight = false;
        graphics.addAnimation("coin",new LoopingAnimation("Items/YellowCoin/Spin",1),true);
        size = new Point2D.Double(SIZE,SIZE);
        hitBox = new HitBox(position.getX(),position.getY(),SIZE,SIZE);
    }

    @Override
    public Material getType() {
        return TYPE;
    }
    
    @Override
    public int getValue() {
        return VALUE;
    }

    /*
    @Override
    public GameObject getCopy() {// all in one method?
        YellowCoin coin = (YellowCoin) PrototypeProvider.getPrototype(getId());
        coin.copyFields(this);
        return coin;
    }
*/
    
    
}
