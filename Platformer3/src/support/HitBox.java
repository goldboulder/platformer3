/*

 */
package support;

import abstractthings.GameObject;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;



public class HitBox {

    
    public enum Shape{SQUARE,CIRCLE};
    private Shape shape;
    
    private ArrayList<Rectangle2D.Double> rectangles;
    
    // no hitbox, no collision detection
    public HitBox(){
        rectangles = new ArrayList<>();
    }
    
    public HitBox(double xPos, double yPos, double xSize, double ySize){
        rectangles = new ArrayList<>();
        rectangles.add(new Rectangle2D.Double(xPos,yPos,xSize,ySize));
        shape = Shape.SQUARE;
    }
    
    public HitBox(Point2D position, Point2D size, Shape type){
        rectangles = new ArrayList<>();
        shape = type;
        if (type == Shape.SQUARE){
            rectangles.add(new Rectangle2D.Double(position.getX(),position.getY(),size.getX(),size.getY()));
        }
        else if (type == Shape.CIRCLE){
            rectangles.add(new Rectangle2D.Double(position.getX() + 0.31*size.getX(),position.getY() + 0.0125*size.getY(),size.getX()*0.38,size.getY()*0.975));
            rectangles.add(new Rectangle2D.Double(position.getX() + 0.2175*size.getX(),position.getY() + 0.065*size.getY(),size.getX()*0.565,size.getY()*0.87));
            rectangles.add(new Rectangle2D.Double(position.getX() + 0.125*size.getX(),position.getY() + 0.125*size.getY(),size.getX()*0.75,size.getY()*0.75));
            rectangles.add(new Rectangle2D.Double(position.getX() + 0.065*size.getX(),position.getY() + 0.2175*size.getY(),size.getX()*0.87,size.getY()*0.565));
            rectangles.add(new Rectangle2D.Double(position.getX() + 0.0125*size.getX(),position.getY() + 0.31*size.getY(),size.getX()*0.975,size.getY()*0.38));
        }
    }
    
    
    public boolean colliding(HitBox h){
        for (Rectangle2D r : rectangles){
            for (Rectangle2D r2 : h.rectangles){
                if (r.intersects(r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight())){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void move(double dx, double dy){
        for (Rectangle2D r : rectangles){
            r.setRect(r.getX() + dx, r.getY() + dy, r.getWidth(), r.getHeight());
        }
    }
    
    public Point2D.Double getRelitiveCenter(){// handle multiple hitBoxes later
        return new Point2D.Double(rectangles.get(0).width/2 + rectangles.get(0).x,rectangles.get(0).height/2  + rectangles.get(0).y);
    }
    
    //returns the rectangle enclosing all the rectangles
    public Rectangle2D.Double getBounds(){
        if (rectangles.size() == 1){
            return rectangles.get(0);
        }
        
        double[] leftBounds = new double[rectangles.size()];
        double[] rightBounds = new double[rectangles.size()];
        double[] topBounds = new double[rectangles.size()];
        double[] bottomBounds = new double[rectangles.size()];
        
        for (int i = 0; i < rectangles.size(); i ++){
            leftBounds[i] = rectangles.get(i).x;
            rightBounds[i] = rectangles.get(i).x + rectangles.get(i).width;
            topBounds[i] = rectangles.get(i).y;
            bottomBounds[i] = rectangles.get(i).y + rectangles.get(i).height;
        }
        
        double left = OtherThings.min(leftBounds);
        double right = OtherThings.max(rightBounds);
        double top = OtherThings.min(topBounds);
        double bottom = OtherThings.max(bottomBounds);
        
        return new Rectangle2D.Double(left,top,right-left,bottom-top);
        
    }
    
    // moves the owner of this hitBox out of the other's hitBox
    public boolean[] moveOutOfHitBox(GameObject owner, HitBox otherHitBox){
        LinkedList<Rectangle2D[]> collidingRectangles = new LinkedList<>();
        
        for (Rectangle2D r : rectangles){
            for (Rectangle2D r2 : otherHitBox.rectangles){
                if (r.intersects(r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight())){
                    collidingRectangles.add(new Rectangle2D[]{r,r2});
                }
            }
        }
        
        boolean[] directionCollisions = new boolean[4];// 0: up, 1: right, 2: down, 3: left
        
        for (Rectangle2D[] rectanglePair : collidingRectangles){
            if (rectanglePair[0].intersects(rectanglePair[1])){
                directionCollisions[handleCollisions(owner,rectanglePair[1],rectanglePair[0])] = true;//determines which of 4 types of collisions have occured
            }
        }
        return directionCollisions;
    }
    
    //moves thisRect out of otherRect, returns number representing which direction it moved to get out (shortest distance)
    private int handleCollisions(GameObject owner, Rectangle2D otherRect, Rectangle2D thisRect){
        double moveRightDistance = otherRect.getMaxX() - thisRect.getMinX();
        double moveLeftDistance = thisRect.getMaxX() - otherRect.getMinX();
        double moveUpDistance = thisRect.getMaxY() - otherRect.getMinY();
        double moveDownDistance = otherRect.getMaxY() - thisRect.getMinY();
        
        int decision = whichNumberIsLeast(moveUpDistance,moveRightDistance,moveDownDistance,moveLeftDistance); // 0: up, 1: right, 2: down, 3: left
        
        switch(decision){
            case 0: 
                owner.changePosition(0, -moveUpDistance);
                if (owner.getVelocity().getY() > 0){
                    owner.setYVelocity(0);// have the owner do this? what about boos?
                }
            return 0;
            case 1: 
                owner.changePosition(moveRightDistance, 0);
                if (owner.getVelocity().getX() < 0){
                    owner.setXVelocity(0);
                }
                
            return 1;
            case 2: 
                owner.changePosition(0, moveDownDistance);
                if (owner.getVelocity().getY() < 0){
                    owner.setYVelocity(0);
                }
            return 2;
            case 3: 
                owner.changePosition(-moveLeftDistance, 0);
                if (owner.getVelocity().getX() > 0){
                    owner.setXVelocity(0);
                }
            return 3;
        }
        
        throw new RuntimeException("This shouldn't happen");
    }
    
    private int whichNumberIsLeast(double n1, double n2, double n3, double n4){
        double currentLowest = n1;
        int ans = 0;
        if (n2 < currentLowest){
            currentLowest = n2;
            ans = 1;
        }
        if (n3 < currentLowest){
            currentLowest = n3;
            ans = 2;
        }
        if (n4 < currentLowest){
            currentLowest = n4;
            ans = 3;
        }
        return ans;
    }

    public Shape getShape() {
        return shape;
    }
    
    public static Shape parseShape(String str) {
        if (str.equals("Square")){
            return Shape.SQUARE;
        }
        if (str.equals("Circle")){
            return Shape.CIRCLE;
        }
        throw new IllegalArgumentException("Not a valid shape");
    }

    
    
}
