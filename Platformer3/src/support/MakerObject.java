/*

 */
package support;

import abstractthings.GameObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract class MakerObject extends GameObject{
    
    public MakerObject(Point2D.Double position, Point2D.Double size){
        this.position = new Point2D.Double(position.x,position.y);
        this.size = new Point2D.Double(size.x,size.y);
        this.hitBox = new HitBox(position,size,HitBox.Shape.SQUARE);
    }
    
    public abstract void doEditAction();
    public abstract void drawLines(Graphics2D g2);
    
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
        g2.translate(position.getX(), position.getY());
        g2.scale(size.getX(),size.getY());
        
        drawLines(g2);
        
        g2.scale(1/size.getX(),1/size.getY());
        g2.translate(-position.getX(), -position.getY());
    }
}
