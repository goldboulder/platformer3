/*

 */
package abstractthings;

import support.DamageType;


public interface Destroyable {
    double getHealth();
    void setHealth(double h);
    double getMaxHealth();
    void setMaxHealth(double h);
    void takeDamage(DamageType damageType, double damage);
    void heal(double amount);
}
