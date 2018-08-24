/*

 */
package items;

import abstractthings.GameObject;
import animations.SingleFrameAnimation;
import enemies.Goomba;
import java.awt.geom.Point2D;
import projectiles.BouncyBullet;
import support.GameObjectList;
import support.HitBox;
import support.InputHandler;
import support.MakerScene;
import support.PrototypeProvider;


public class PuttyGun extends Weapon{//extends gun?

    private double attackPower;
    private double bulletSpeed;
    private int numBounces;
    public double SIZE;
    private double bulletDuration;
    
    
    public PuttyGun(){
        super();
        SIZE = 0.7;
        bulletDuration = 4;
        graphics.addAnimation("gun",new SingleFrameAnimation("Items/PuttyGun"),true);
        size = new Point2D.Double(SIZE,SIZE);
        hitBox = new HitBox(position.getX(),position.getY(),SIZE,SIZE);
        
        reloadTime = 600;
        attackPower = 2;
        bulletSpeed = 11;
        numBounces = 3;
    }

    @Override
    public void useItem(GameObject user) {
        user.spawnObject(new BouncyBullet(user,user.getCenter(),user.getRotation(),attackPower,bulletSpeed,numBounces,bulletDuration));
    }
    
    
    
    
}
