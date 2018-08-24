/*

 */
package support;

import player.Player;
import abstractthings.Destroyable;
import abstractthings.GameObject;
import enemies.Enemy;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.List;


public class OtherThings {
    
    private static DecimalFormat df = new DecimalFormat("###,###.##");
    
    public static String niceNumberFormat(double num){
        if (Math.abs(num) >= 1){
            return Integer.toString((int)num);
        }
        else if (num == 0){
            return "0";
        }
        else{
            return df.format(num);
        }
    }
    
    public static boolean lineOfSight(GameObject observer, GameObject target, List<GameObject> objects){// todo, line,rectangle mehtod in point2D?
        return false;
    }
    
    public static boolean rangesColliding(double a1,double a2,double b1,double b2){
        if (b1 >= a1 && b1 <= a2){
            return true;
        }
        if (b2 >= a1 && b2 <= a2){
            return true;
        }
        if (b1 <= a1 && b2 >= a2){
            return true;
        }
        
        return false;
    }
    
    public static boolean shouldDamage(GameObject damager, GameObject target, WhoToDamage whoToDamage, GameObject doNotDamage){//doNotDamage is for exceptions, usually the attacker
        if(!(target instanceof Destroyable) || damager == target){
            return false;
        }
        switch(whoToDamage){
            case ENEMY:
                return (target instanceof Enemy);
            
            case PLAYER:
                return (target instanceof Player);
            
            case ALL:
                return true;
            
            case ALL_BUT_ONE:
                return target != doNotDamage;
            default:
                return false;
            
        }
    }
    
    public static Point2D screenCoToGraphCo(MouseEvent e, Camera c, Dimension screenDimension){
        double xPercent = (double)e.getX()/screenDimension.width;
        double yPercent = (double)e.getY()/screenDimension.height;
        double x = c.getXPosition() + c.getXSize() * xPercent;
        double y = c.getYPosition() + c.getYSize() * yPercent;
        
        return new Point2D.Double(x,y);
    }
    
    public static double randomRange(double min, double max){
        if(max < min){
            double temp = min;
            min = max;
            max = temp;
        }
        
        return Math.random() * (max-min) + min;
    }
    
    public static double min(double[] nums){
        double ans = nums[0];
        for (int i = 1; i < nums.length; i ++){
            if (nums[i] < ans){
                ans = nums[i];
            }
        }
        return ans;
    }
    
    public static double max(double[] nums){
        double ans = nums[0];
        for (int i = 1; i < nums.length; i ++){
            if (nums[i] > ans){
                ans = nums[i];
            }
        }
        return ans;
    }
    
    public static void drawStringCentered(Graphics g, String str, double x, double y, double size){//*************
        
    }

    public static double facingRightMultiplier(boolean b) {
        return b ? 1.0 : -1.0;
    }
    
}
