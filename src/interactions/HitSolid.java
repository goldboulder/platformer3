/*

 */
package interactions;

import abstractthings.GameObject;


public class HitSolid extends Interaction{
    
    private GameObject solid;
    
    public HitSolid(GameObject solid){
        this.solid = solid;
    }
    
    public GameObject getSolid(){
        return solid;
    }
}
