/*

 */
package animations;

import abstractthings.Copyable;
import java.awt.image.BufferedImage;


public interface Animation extends Copyable{
    BufferedImage getImage();
    void reset();
    int numImages();
    void pause();
    void unPause();
}
