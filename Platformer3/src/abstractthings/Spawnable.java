/*

 */
package abstractthings;


public interface Spawnable extends Placeable{// can be spawned by enemy spawners
    GameObject getCopy();
}
