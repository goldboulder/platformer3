/*

 */
package abstractthings;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public interface Placeable {// all implementations of this have a constructor that takes in a position
    GameObject getPrototype(); // returns a clone of the prototype
    String getId();
    BufferedImage getProfileImage();
    String fileStringStart();
    String saveFileString();
    void readFileString(String[] str);//starts as prototype, uses string info to customize
    void customize(JFrame frame);// for customizing attributes in edit mode

}
