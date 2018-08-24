/*

 */
package animations;

import abstractthings.Copyable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.Timer;
import support.ImagePool;


public class GraphicProvider implements ActionListener, Copyable{
    
    private HashMap<String,Animation> animations;
    private Animation currentAnimation;
    private Animation tempAnimation;
    private Timer tempTimer;
    
    public GraphicProvider(){
        animations = new HashMap<>();
    }
    
    
    public BufferedImage getImage(){
        if (tempAnimation != null){
            return tempAnimation.getImage();
        }
        else{
            return currentAnimation.getImage();
        }
    }
    
    public void addAnimation(String key, Animation a){
        animations.put(key,a);
    }
    
    public void addAnimation(String key, Animation a, boolean defaultAnimation){
        animations.put(key,a);
        if(defaultAnimation){
            currentAnimation = a;
        }
    }
    
    public void removeAnimation(String key){
        if (animations.get(key) == currentAnimation){
            currentAnimation = new SingleFrameAnimation(ImagePool.getPicture("Default Image"));
        }
        animations.remove(key);
    }
    
    public Animation getCurrentAnimation(){
        return currentAnimation;
    }
    
    public void setCurrentAnimation(String str){
        Animation a = animations.get(str);
        a.reset();
        currentAnimation = a;
    }
    
    public void setTempAnimation(String str, double time){// in seconds
        Animation a = animations.get(str);
        a.reset();
        tempAnimation = a;
        if (tempTimer != null){
            tempTimer.stop();
        }
        tempTimer = new Timer((int)(time * 1000),this);
        tempTimer.setActionCommand("tempTimer");
        tempTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("tempTimer")){
            tempAnimation = null;
            tempTimer.stop();
        }
    }

    public void pauseAnimations() {
        for (Animation animation : animations.values()){
            animation.pause();
            if (tempAnimation != null){
                tempTimer.stop();
            }
        }
    }
    
    public void unPauseAnimations() {
        for (Animation animation : animations.values()){
            animation.unPause();
            if (tempAnimation != null){
                tempTimer.start();
            }
        }
    }

    @Override
    public Object getCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
