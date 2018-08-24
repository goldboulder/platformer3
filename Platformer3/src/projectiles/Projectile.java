/*

 */
package projectiles;

import abstractthings.Copyable;
import abstractthings.GameObject;


public abstract class Projectile extends GameObject implements Copyable{//maxDistance/time?
    protected GameObject owner;
    protected double lifeTime;
    
}
