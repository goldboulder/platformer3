/*

 */
package otherobjects;

import abstractthings.GameObject;
import actions.DelayedAction;
import actions.Move;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import support.DamageType;
import support.GameObjectList;
import support.HitBox;
import support.ImagePool;
import support.OtherThings;


public class HitSplat extends GameObject{
    
    public enum NumberColor{DAMAGE,ZERO,HEAL};
     
    private double number;
    private double textSize;
    private Color color;
    private DamageType damageType;
    private BufferedImage icon;
    private static final double RANDOM_OFFSET_LIMIT = 0.9;
    
    private static HashMap<DamageType,String> damageIconMap;
    private static HashMap<NumberColor,Color> numColorMap;

    public HitSplat(GameObject g, double number, DamageType damageType, NumberColor color, double textSize){
        super();
        pushable = false;
        this.number = number;
        this.damageType = damageType;
        this.textSize = textSize;
        
        this.position = new Point2D.Double(g.getCenter().getX(),g.getCenter().getY()+(textSize/2));
        randomizePosition();
        
        this.size = new Point2D.Double(textSize,textSize);
        this.hitBox = new HitBox();
        actionList.addAction(new Move("Move",0,-1));
        actionList.addAction(new DelayedAction("timeout",1));
        if (damageIconMap == null){
            initializeDamageIconMap();
        }
        if (damageType != null){
            icon = ImagePool.getPicture("Damage Icons/" + damageIconMap.get(damageType));
        }
        
        if (numColorMap == null){
            initializeNumColorMap();
        }
        this.color = numColorMap.get(color);
        
        
    }
    
    private static void initializeDamageIconMap(){
        damageIconMap = new HashMap<>();
        for (DamageType type : DamageType.values()){
            damageIconMap.put(type, (type.name().toLowerCase()));
        }
    }
    
    private static void initializeNumColorMap(){
        numColorMap = new HashMap<>();
        numColorMap.put(NumberColor.ZERO, Color.BLUE);
        numColorMap.put(NumberColor.DAMAGE, Color.RED);
        numColorMap.put(NumberColor.HEAL, Color.MAGENTA);
    }
    
    
    
    @Override
    public void draw(Graphics g){// fade out? font?
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, 1));
        
        g2.translate(position.getX(), position.getY());
        g2.scale(size.getX(),size.getY());
        
        double imageScale = 0.7;
        g2.scale(imageScale,imageScale);
        g.drawImage(icon, -1, -1, 1, 1, null);
        g2.scale(1/imageScale,1/imageScale);
        
        g.drawString(OtherThings.niceNumberFormat(number), 0, 0);
        
        g2.scale(1/size.getX(),1/size.getY());
        g2.translate(-position.getX(), -position.getY());
        
    }
    
    private void randomizePosition(){
        position.x += OtherThings.randomRange(-RANDOM_OFFSET_LIMIT,RANDOM_OFFSET_LIMIT);
        position.y += OtherThings.randomRange(-RANDOM_OFFSET_LIMIT,RANDOM_OFFSET_LIMIT);
    }
    
    @Override
    public void doScheduledAction(GameObjectList o, String command) {//command not used
        delete();
    }
    
    
}
