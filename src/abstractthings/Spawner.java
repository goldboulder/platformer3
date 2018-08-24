/*

 */
package abstractthings;


public interface Spawner {
    Spawnable getSpawnPrototype();
    void setSpawnPrototype(Spawnable s);
    int getMaxChildren();
    void setMaxChildren(int max);
}
