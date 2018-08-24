/*

 */
package support;

// for if different parts of the enemy hurt differently

import abstractthings.GameObject;
import java.awt.geom.Rectangle2D;

public class RelitiveHitBox {
    
    private Rectangle2D.Double rect;
    
    public RelitiveHitBox(GameObject owner, double xRelPos, double yRelPos, double xRelSize, double yRelSize, DamageType damageType, double damage, WhoToDamage whoToDamage, GameObject doNotDamage, double timeBetweenHits){
        
    }
    
}
