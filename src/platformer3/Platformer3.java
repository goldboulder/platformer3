
package platformer3;

import gui.MenuFrame;
import javax.swing.JFrame;
import support.PrototypeProvider;


public class Platformer3 extends JFrame{

    public static void main(String[] args) {
        PrototypeProvider.instantiate();
        new MenuFrame();
    }
    
}