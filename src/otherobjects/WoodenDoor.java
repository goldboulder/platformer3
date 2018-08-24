/*

 */
package otherobjects;

import abstractthings.GameObject;
import animations.SingleFrameAnimation;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import player.Player;
import support.GameObjectList;
import support.HitBox;
import support.ImagePool;
import support.InputHandler;
import support.PlayScene;
import support.PrototypeProvider;


public class WoodenDoor extends Door{
    
    public static final double WIDTH = 0.9;
    public static final double HEIGHT = 1.8;
    
    
    public WoodenDoor(){
        super();
        position = new Point2D.Double(0,0);
        size = new Point2D.Double(WIDTH,HEIGHT);
        this.hitBox = new HitBox(position.getX(),position.getY(),WIDTH,HEIGHT);
        destinationLevelName = "";
        destinationLevelPosition = new Point2D.Double(0,0);
        graphics.addAnimation("wooden_door",new SingleFrameAnimation(getProfileImage()),true);
    }
    

    public void act(GameObjectList o, InputHandler i){
        if (i.keyPressed(KeyEvent.VK_W) && this.colliding(Player.getPlayer())){
            active = true;
        }
        
    }
    
    

    
    
    @Override
    public String fileStringStart(){
        return getId() + " " + Double.toString(position.getX()) + " " + Double.toString(position.getY());
    }

    @Override
    public String saveFileString() {
        return fileStringStart() + " " + getDestinationLevelName() + " " + getDestinationLevelPosition().x + " " + getDestinationLevelPosition().y;
    }

    @Override
    public void readFileString(String[] str) {
        setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
        setDestinationLevelName(str[3]);
        destinationLevelPosition.x = Double.parseDouble(str[4]);
        destinationLevelPosition.y = Double.parseDouble(str[5]);
    }

    @Override
    public void customize(JFrame frame) {
        levelAndPositionUI(frame);
    }
    
    @Override
    public GameObject getCopy() {
        WoodenDoor door = (WoodenDoor) PrototypeProvider.getPrototype(getId());
        door.copyFields(this);
        
        return door;
    }
    
}
