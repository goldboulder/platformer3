/*

 */
package enemies;

import abstractthings.Agent;
import abstractthings.Copyable;
import abstractthings.GameObject;
import java.awt.image.BufferedImage;
import support.ImagePool;
import abstractthings.Placeable;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import support.PrototypeProvider;


public abstract class Enemy extends Agent implements Placeable, Copyable{//gordo: line (sine wave), stationary, and circle movement patterns(as actions)
    
    @Override
    public GameObject getPrototype() {// have default constructor for all GameObjects? No, non placeables don't have default constructors
        try {
            return getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(getId() + " doesn't have a default constructor!");
        }
    }
    
    public Enemy(){
        super();
        readEnemyFile(getId());
    }
    
    
    
    @Override
    public BufferedImage getProfileImage(){
        return ImagePool.getPicture("Enemies/" + getId() + "/Profile");//no spaces
    }
    
    
    
    
    
}
