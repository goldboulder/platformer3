/*

 */
package abstractthings;

import java.awt.Graphics;


public interface Killable extends Destroyable{//have health,maxHealth methods?
    void drawHealthBar(Graphics g);
}
