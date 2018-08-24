/*

 */
package support;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


public class PercentBar {
    
    
    public static void drawPlainPercentBar(Graphics g, double xStart, double yStart, double width, double height, double currentPercent, double max, Color filledColor, Color emptyColor,Color textColor, boolean drawNumbers, boolean forGUI){
        Graphics2D g2 = (Graphics2D) g;//rounded rectangle?
        
        g2.translate(xStart, yStart);
        g2.scale(width,height);
        
        g2.setColor(emptyColor);
        g2.fillRect(0, 0, 1, 1);
        g2.setColor(filledColor);
        
        if (currentPercent > 0 && max > 0){
            g2.scale(currentPercent/max, 1);
            g2.fillRect(0, 0, 1, 1);
            g2.scale(max/currentPercent, 1);
        }
        
        if (!forGUI && drawNumbers){// scaling for GUI and scenes are different
            drawNumbersForScene(g2,width,height,currentPercent,max,textColor);
        }
        
        g2.scale(1/width,1/height);
        g2.translate(-xStart, -yStart);
        
        if (forGUI && drawNumbers){
            drawNumbersForGUI(g2,width,height,currentPercent,max,textColor);
        }
        
        
        
    }
    
    private static final double STRING_WIDTH_APPROX = 0.4375;//may depend on font, improve? method in OtherThings?
    private static void drawNumbersForScene(Graphics2D g2,double width, double height, double currentPercent, double max, Color textColor){
        
        
        g2.setColor(textColor);
        g2.setFont(new Font(g2.getFont().getName(),Font.BOLD,1));//standard-character size font***
        
        //get numerator shown on screen (whole number)
        double numerator = currentPercent;
        if (numerator < 0) numerator = 0;
        if (numerator > 0 && numerator < 1) numerator = 1;
        
        String health = (int)numerator + " / " + (int)(max);
        
        double x = (0.5*width/height - STRING_WIDTH_APPROX*health.length()/2.0);// only this is iffy
        double y = g2.getFontMetrics().getMaxAscent() - 0.125;//estimation
        
        g2.scale(height/width,1);
        g2.translate(x,y);
        
        g2.drawString(health , 0, 0);
        
        g2.translate(-x,-y);
        g2.scale(width/height,1);
        
    }
    
    
    
    public static void drawNumbersForGUI(Graphics2D g2,double width, double height, double percent, double max, Color textColor){
        g2.setColor(textColor);
        g2.setFont(new Font(g2.getFont().getName(),Font.BOLD,(int)height-3));
        String health = (int)percent + " / " + (int)(max);
        int x = g2.getFontMetrics(g2.getFont()).stringWidth(health);
        g2.drawString(health , (int)(width - x)/2, (int)(height-3));
    }
    
    
}
