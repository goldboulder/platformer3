/*

 */
package animations;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Timer;
import support.ImagePool;


public class MultiFrameAnimation implements Animation, ActionListener{// make looping animation a sub-class of this?

    private BufferedImage[] images;
    private int imageNumber = 0;
    private Timer frameChangeTimer;
    
    public MultiFrameAnimation(String dir, double loopLength){// loopLength: how long the loop is in seconds
        images = ImagePool.getPictures(dir);
        //System.out.println(images.length + " " + dir);
        int timeMills = (int)(1000*loopLength/images.length);
        frameChangeTimer = new Timer(timeMills,this);
        if (frameChangeTimer.getDelay() != 0){//needed?
            frameChangeTimer.start();
        }
    }
    
    @Override
    public BufferedImage getImage() {
        return images[imageNumber];
        
    }

    @Override//this is the only thing different from loopingAnimation
    public void actionPerformed(ActionEvent e) {// do not change pic if frozen? call pauseAnimation
        if (imageNumber < images.length - 1){
            imageNumber ++;
        }
        
    }

    @Override
    public void reset() {
        imageNumber = 0;
        frameChangeTimer.restart();
    }
    
    @Override
    public int numImages(){
        return images.length;
    }

    @Override
    public void pause() {
        frameChangeTimer.stop();
    }

    @Override
    public void unPause() {
        frameChangeTimer.start();
    }

    @Override
    public Object getCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
