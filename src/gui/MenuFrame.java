/*

 */
package gui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MenuFrame extends JFrame{

    public MenuFrame(){
        setTitle("Platformer3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MenuPanel(this));
        pack();
        setLocationRelativeTo(null);//centers the frame on the screen
        setVisible(true);
        
    }
    
    public class MenuPanel extends JPanel{
        
        public static final int X_SIZE = 600;// divisible by 3
        public static final int Y_SIZE = 130;
        public static final int TITLE_Y_SIZE = 30;
        private MenuFrame frame;
        private JPanel titlePanel;
        private JPanel optionsPanel;
        private JLabel titleLabel;
        private MenuMakerPanel menuMakerPanel;
        private MenuPlayPanel menuPlayPanel;
        private MenuCampainPanel menuCampainPanel;
        
        public MenuPanel(MenuFrame frame){
            this.frame = frame;
            titlePanel = new JPanel();
            optionsPanel = new JPanel();
            titleLabel = new JLabel("Platformer 3");
            menuMakerPanel = new MenuMakerPanel(this);
            menuPlayPanel = new MenuPlayPanel(this);
            menuCampainPanel = new MenuCampainPanel(this);
            
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(X_SIZE,Y_SIZE));
            optionsPanel.setLayout(new BoxLayout(optionsPanel,BoxLayout.X_AXIS));
            titlePanel.setPreferredSize(new Dimension(X_SIZE,TITLE_Y_SIZE));
            optionsPanel.setPreferredSize(new Dimension(X_SIZE,Y_SIZE-TITLE_Y_SIZE));
            menuMakerPanel.setPreferredSize(new Dimension(X_SIZE/3,Y_SIZE-TITLE_Y_SIZE));
            menuPlayPanel.setPreferredSize(new Dimension(X_SIZE/3,Y_SIZE-TITLE_Y_SIZE));
            menuCampainPanel.setPreferredSize(new Dimension(X_SIZE/3,Y_SIZE-TITLE_Y_SIZE));
            
            add(titlePanel);
            add(optionsPanel);
            titlePanel.add(titleLabel);
            optionsPanel.add(menuMakerPanel);
            optionsPanel.add(menuPlayPanel);
            optionsPanel.add(menuCampainPanel);
        }
        
        public MenuFrame getFrame(){
            return frame;
        }
        
        public void close(){
            //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//closes everything
            frame.setVisible(false); //you can't see me!
            frame.dispose();
        }
        
    }
    
    

    
    
}
