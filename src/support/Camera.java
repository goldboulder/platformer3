/*

 */
package support;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class Camera {
    
    public static final double DEFAULT_ZOOM_SCALE = 1.05;
    public static final double DEFAULT_SCROLL_PERCENT = 0.01;
    public static final double DEFAULT_MIN_SIZE = 1;
    public static final double DEFAULT_MAX_SIZE = 1000;
    
    private Point2D.Double position;//top left corner
    private Point2D.Double size;
    
    private double zoomScale = DEFAULT_ZOOM_SCALE;
    private double scrollPercent = DEFAULT_SCROLL_PERCENT;
    private double minSize = DEFAULT_MIN_SIZE;
    private double maxSize = DEFAULT_MAX_SIZE;
    
    
    public Camera(double xPosition, double yPosition, double xSize, double ySize){
        position = new Point2D.Double(xPosition - xSize/2,yPosition - ySize/2);
        size = new Point2D.Double(xSize,ySize);
    }
    public Camera(Point2D.Double position, Point2D.Double size){
        this.position = new Point2D.Double(position.x - size.x/2, position.y - size.y/2);
        this.size = new Point2D.Double(size.x,size.y);
    }
    
    public void transform(Graphics2D g2, int screenWidth, int screenHeight){
        g2.scale(screenWidth/size.x, screenHeight/size.y);
        g2.translate(-position.x, -position.y);
    }
    
    public void transformBack(Graphics2D g2, int screenWidth, int screenHeight){
        g2.translate(position.x, position.y);
        g2.scale(size.x/screenWidth, size.y/screenHeight);
    }
    
    public double getXPosition(){
        return position.x;
    }
    
    public double getYPosition(){
        return position.y;
    }
    
    public double getXSize(){
        return size.x;
    }
    
    public double getYSize(){
        return size.y;
    }
    
    // multiplies x and y sizes by amount specified
    // x and y position change so center is still at same point
    public void zoom(double scale){
        setSize(size.x / scale, size.y / scale);
    }
    
    // zooms so that x and y sizes are those specified.
    // x and y position change so center is still at same point
    public void setSize(double xSize, double ySize){
        if (xSize < maxSize && ySize < maxSize && xSize > minSize && ySize > minSize){
        translate(size.x/2, size.y/2);
        size.x = xSize;
        size.y = ySize;
        translate(-size.x/2, -size.y/2);
        }
    }
    
    public void setSize(Point2D.Double newSize){
        setSize(newSize.x,newSize.y);
    }
    
    //returns the game point at the center of the screen
    public Point2D.Double getCenter(){
        return new Point2D.Double(position.x + size.x/2, position.y + size.y/2);
    }
    
    public void translate(double dx, double dy){
        position.x += dx;
        position.y += dy;
    }
    
    public void translatePercent(double dx, double dy){// here dx = 1 means move the screen over by one screen
        position.x += size.x * dx;
        position.y += size.y * dy;
    }
    
    public void translate(Point2D.Double ds){
        translate(ds.x,ds.y);
    }
    
    //moves the camera so that the center of the screen is the specified point
    public void setCenter(double x, double y){
        position.x = x - size.x/2;
        position.y = y - size.y/2;
    }
    
    public void setCenter(Point2D.Double position){
        Camera.this.setCenter(position.x,position.y);
    }

    public double getMaxSize() {
        return maxSize;
    }
    
    public void setMaxSize(double maxSize) {
        this.maxSize = maxSize;
    }

    public double getMinSize() {
        return minSize;
    }

    public void setMinSize(double minSize) {
        this.minSize = minSize;
    }
    
    public double getZoomScale() {
        return zoomScale;
    }
    
    public void setZoomScale(double zoomScale) {
        this.zoomScale = zoomScale;
    }

    public double getScrollPercent() {
        return scrollPercent;
    }
    
    public void setScrollPercent(double scrollPercent) {
        this.scrollPercent = scrollPercent;
    }
    
    public double percentOnScreenX(double x) {//decouple?
        return (x - position.x)/size.x;
    }

    public double percentOnScreenY(double y) {
        return (y - position.y)/size.y;
    }
    
    public boolean onScreen(Point2D.Double point){
        return onScreen(point.x,point.y);
    }
    
    public boolean onScreen(double x, double y){
        return x > position.x && (x < position.x + size.x) && y > position.y && (y < position.y + size.y);
    }
    
    public boolean onScreen(Rectangle2D.Double rect){
        Rectangle2D.Double screenRect = new Rectangle2D.Double(position.x,position.y,size.x,size.y);
        return screenRect.intersects(rect);
    }
    
}
