/*

 */
package animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import support.ImagePool;


public class SingleFrameAnimation implements Animation{// texture?******
    private BufferedImage image;

    public SingleFrameAnimation(BufferedImage image){
        this.image = image;
    }
    
    public SingleFrameAnimation(String address){
        image = ImagePool.getPicture(address);
    }
    
    @Override
    public BufferedImage getImage() {//remember zooming feature
        return image;
    }
    
    @Override
    public void reset() {
        //nothing
    }
    
    @Override
    public int numImages(){
        return 1;
    }

    @Override
    public void pause() {
        //nothing
    }
    
    @Override
    public void unPause() {
        //nothing
    }

    @Override
    public Object getCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
