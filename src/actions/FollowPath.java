/*

 */
package actions;

import abstractthings.GameObject;
import java.awt.geom.Point2D;
import paths.Path;
import stat_effects.StatEffectHandler;
import support.GameObjectList;
import support.PlayScene;


public class FollowPath extends Action{
    
    public static final String ID = "Follow Path";

    private Point2D.Double origin;// usually the same as the owner's position
    private Path path;
    private double speed;
    private double time = 0;
    
    public FollowPath(String command, Path path, double speed, Point2D.Double origin) {
        super(command);
        this.path = path;
        this.origin = origin;
        this.speed = speed;
        
    }
    
    @Override
    protected void performAction(GameObject owner, GameObjectList o, StatEffectHandler s) {
        Point2D.Double offset = path.getPathPosition(time);
        time += PlayScene.D_TIME * speed;
        owner.setCenter(origin.x + offset.x, origin.y - offset.y);
    }


    public Point2D.Double getOrigin() {
        return origin;
    }
    
    public double getTime(){
        return time;
    }
    
    public void setTime(double time){
        this.time = time;
    }
    
    public void incrementTime(double dt){
        time += dt;
    }


    public void setOrigin(Point2D.Double origin) {
        this.origin = origin;
    }
    
    public Path getPath(){
        return path;
    }
    
    public void setPath(Path path){
        this.path = path;
    }
    
    public double getSpeed(){
        return speed;
    }
    
    public void setSpeed(double speed){
        this.speed = speed;
    }
    
    public void reset(){
        time = 0;
    }

    public void moveOrigin(double dx, double dy) {
        origin = new Point2D.Double(origin.x + dx,origin.y + dy);
    }
    

    
}
