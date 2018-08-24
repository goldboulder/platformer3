
package abstractthings;

import otherobjects.Door;
import interactions.Interaction;
import interactions.TakeDamage;
import items.UpgradeItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import support.DamageTypeResistance;
import support.DamageType;
import support.HitBox;
import otherobjects.HitSplat;
import support.PercentBar;
import support.PrototypeProvider;


public abstract class Agent extends GameObject implements Killable{// stores enemy stats
    
    public static final double HP_BAR_WIDTH = 1.5;// text is stretched for non-1.5 values
    public static final double HP_BAR_HEIGHT = 0.3;
    
    protected DamageTypeResistance damageTypeResistance;
    
    protected double health;
    protected double maxHealth;
    protected boolean invincible = false;// for phase changing, death animations, ect...
    
    @Override
    public void draw(Graphics g){
        super.draw(g);
        drawHealthBar(g);
    }
    
    @Override
    public void drawWithoutRotation(Graphics g){
        super.drawWithoutRotation(g);
        drawHealthBar(g);
    }
    
    @Override
    public void drawWithoutFlipping(Graphics g){
        super.drawWithoutFlipping(g);
        drawHealthBar(g);
    }
    
    @Override
    public void drawPlain(Graphics g){
        super.drawPlain(g);
        drawHealthBar(g);
    }
    
    @Override
    public void drawHealthBar(Graphics g) {
        if (health == maxHealth){
            return;
        }
        else{
            PercentBar.drawPlainPercentBar(g,getCenter().x - HP_BAR_WIDTH/2,position.getY() - HP_BAR_HEIGHT - 0.1,HP_BAR_WIDTH,HP_BAR_HEIGHT,health,maxHealth,Color.GREEN,Color.RED,Color.BLACK,true,false);//constants****
        }
    }
//(Graphics g, double xStart, double yStart, double width, double height, double currentPercent, double max, Color filledColor, Color emptyColor,Color textColor, boolean drawNumbers, boolean forGUI)
    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double h) {
        if (h > maxHealth){
            health = maxHealth;
        }
        else if (h < 0){
            health = 0;
        }
        else{
            health = h;
        }
    }
    
    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(double h) {
        if (h < 0){
            h = 0;
        }
        maxHealth = h;
        if (health > maxHealth){
            health = maxHealth;
        }
    }
    
    public boolean isInvincible(){
        return invincible;
    }
    
    public void setInvincible(boolean invincible){
        this.invincible = invincible;
    }
    
    public static final double NORMAL_HIT_SPLAT_SIZE = 0.6;// crits later? should this be in the hitBox class? Maybe, if different sizes are needed
    @Override
    public void takeDamage(DamageType damageType, double damage) {// hitSplat, hitBox array of size 0?, art for hitspats? *dink* for 0 hitsplat
        damage = damageTypeResistance.modifyDamage(damageType, damage);
        if (invincible && damage > 0){
            return;
        }
        
        if (damage == 0){
            spawnQueue.add(new HitSplat(this,0,damageType,HitSplat.NumberColor.ZERO,NORMAL_HIT_SPLAT_SIZE));
            return;
        }
        
        if (damage < 0){
            heal(-damage);
            return;
        }
        setHealth(health-damage);
        
        if (health <= 0){
            delete();
        }
        
        spawnQueue.add(new HitSplat(this,damage,damageType,HitSplat.NumberColor.DAMAGE,NORMAL_HIT_SPLAT_SIZE));
        
    }
    
    @Override
    public void heal(double amount) {// have heal interaction?
        if (amount == 0){
            spawnQueue.add(new HitSplat(this,0,null,HitSplat.NumberColor.HEAL,NORMAL_HIT_SPLAT_SIZE));
            return;
        }
        
        if (amount < 0){
            takeDamage(null,-amount);
            return;
        }
        setHealth(health+amount);
        spawnQueue.add(new HitSplat(this,amount,null,HitSplat.NumberColor.HEAL,NORMAL_HIT_SPLAT_SIZE));
    }
    
    public boolean isDead(){
        return (health <= 0);
    }
    
    @Override
    public void react(GameObject g, Interaction i){
        super.react(g,i);
        if (i instanceof TakeDamage){
            TakeDamage t = (TakeDamage) i;
            takeDamage(t.getDamageType(),t.getDamage());
        }
    }
    
    protected void readEnemyFile(String enemyName){
        position = new Point2D.Double(0,0);
        try {
            Scanner sc = new Scanner(new File("enemy stats/" + enemyName + ".txt"));
            sc.next();
            maxHealth = sc.nextDouble();
            health = maxHealth;
            sc.next();
            size = new Point2D.Double(sc.nextDouble(),sc.nextDouble());
            sc.next();
             
            hitBox = new HitBox(position,size,HitBox.parseShape(sc.next()));// square or circle
            
            sc.next();
            sc.next();
            sc.next();
            
            damageTypeResistance = new DamageTypeResistance(sc);
            
            sc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void copyBasicStats(Agent a){
        maxHealth = a.maxHealth;
        health = maxHealth;
        size = a.size;
        hitBox = new HitBox(a.position,a.size,a.getHitboxShape());
        damageTypeResistance = a.damageTypeResistance.getCopy();
    }
    
    @Override
    public void copyFields(GameObject g){
        super.copyFields(g);
        copyBasicStats((Agent)g);
    }
    
    
}
