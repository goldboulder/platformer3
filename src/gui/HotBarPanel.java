/*

 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import player.Player;


public class HotBarPanel extends JPanel{
    
    public static final int HOT_BAR_SIZE = 10;
    private Color backgroundColor = new Color(50,50,50);
    private Color dividerColor = new Color(150,150,150);
    private Color highlightColor = Color.WHITE;
    private Color numberColor = Color.WHITE;
    
    private int lineWidth = 3;
    
    public HotBarPanel(){
        
    }

    
    @Override
    public void paintComponent(Graphics g){
        paintBar(g,Player.getPlayer());
    }
    
    public void paintBar(Graphics g, Player p){
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());//paint background
        
        paintPictures(g,p);
        paintDividers(g,p);
        paintNumbers(g);
    }
    
    public void paintPictures(Graphics g, Player p){
        BufferedImage image;
        for (int i = 0; i < HOT_BAR_SIZE; i++){
            image = null;
            try{
                image = p.getInventory().getUniqueItem(i).getImage();
            }
            catch(NullPointerException e){
                
            }
            if (image != null){
                g.drawImage(image, i*getWidth()/HOT_BAR_SIZE, 0, getWidth()/HOT_BAR_SIZE, getHeight(), null);
            }
        }
    }

    private void paintDividers(Graphics g, Player p) {
        g.setColor(dividerColor);
        
        //outer rectangle
        for (int i = 0; i < lineWidth; i++){
            g.drawRect(i, i, getWidth()-(2*i+1), getHeight()-(2*i+1));
        }
        
        // diver lines
        for (int i = 1; i < HOT_BAR_SIZE; i++){
            g.fillRect(getWidth()*i/HOT_BAR_SIZE - lineWidth/2, 0, lineWidth, getHeight());
        }
        
        // drawHighlight
        g.setColor(highlightColor);
        int xStart = getWidth()*p.getHotBarFocus()/HOT_BAR_SIZE;
        for (int i = 0; i < lineWidth; i++){
            g.drawRect(xStart+i, i, getWidth()/HOT_BAR_SIZE-(2*i+1), getHeight()-(2*i+1));
        }
        
    }

    private void paintNumbers(Graphics g) {
        g.setColor(numberColor);
        for (int i = 0; i < HOT_BAR_SIZE; i++){
            g.drawString(Integer.toString((i+1)%HOT_BAR_SIZE), getWidth()*i/HOT_BAR_SIZE + lineWidth, getHeight()-lineWidth - 2);
        }
        
    }

    
}
