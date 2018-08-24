/*

 */
package animations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import support.ImagePool;


public class LoopingAnimation implements Animation, ActionListener{

    private BufferedImage[] images;
    private int imageNumber;
    private Timer frameChangeTimer;
    
    public LoopingAnimation(String dir, double loopLength){// loopLength: how long the loop is in seconds
        images = ImagePool.getPictures(dir);
        int timeMills = (int)(1000*loopLength/images.length);
        frameChangeTimer = new Timer(timeMills,this);
        if (frameChangeTimer.getDelay() != 0){
            frameChangeTimer.start();
        }
    }
    
    @Override
    public BufferedImage getImage() {
        return images[imageNumber];
    }

    @Override
    public void actionPerformed(ActionEvent e) {// do not change pic if frozen? call pauseAnimation
        imageNumber = ((imageNumber + 1) % images.length);
    }

    @Override
    public void reset() {
        imageNumber = 0;
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
