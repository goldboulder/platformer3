/*

 */
package paths;

import abstractthings.Copyable;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;


public abstract class Path implements Copyable{
    
    public static final String ID = "Path";
    public static final String NO_PATH = "No Path";//needed?

    
    public abstract Point2D.Double getPathPosition(double time);
    public abstract JPanel getPanel(JFrame p);// handles actionCommands too
    public abstract void buildPath(String[] params);// changes path based on string argument from panel
    public abstract String[] getParameters();// returns a list of parameters
    public abstract double xNormalVector(double time);// assumes a speed of 1
    public abstract double yNormalVector(double time);
    public abstract void draw(Graphics g);

    public String getId(){
        return this.getClass().getSimpleName();
    }

    
    
}
