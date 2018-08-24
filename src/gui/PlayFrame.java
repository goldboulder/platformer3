/*

 */
package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import static support.PlayScene.GAME_TICK;



public class PlayFrame extends JFrame{

    public static final int GAME_PANEL_XSIZE = 1000;
    public static final int GAME_PANEL_YSIZE = 1000;
    // rember options between sessions? move to respective fields
    public static final int GAME_FIELD_PANEL_YSIZE = (int) (PlayFrame.GAME_PANEL_YSIZE * 0.9);
    public static final int GAME_FIELD_PANEL_XSIZE = PlayFrame.GAME_PANEL_XSIZE;
    
    
    private GamePanel gamePanel;
    
    public PlayFrame(String levelName){
        gamePanel = new GamePanel(this,levelName);
        setContentPane(gamePanel);
        setSize(getPreferredSize());
        initialize();
    }
    
    private void initialize(){
        setTitle("Platformer3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    public void close(){
        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//closes everything
        setVisible(false);
        gamePanel.pause();//stops the timers when you exit to the main menu. Stops these timers from interfering with a new game
        dispose();
    }
    
    public void pause(){
        gamePanel.pause();
    }
    
    public void unpause(){
        gamePanel.unpause();
    }
    
    public void quit(){
        gamePanel.quit();
    }
    
    
    private class GamePanel extends JPanel implements ActionListener{
        private PlayFrame frame;
        private PlayFieldPanel playFieldPanel;
        private PlayStatsPanel playStatsPanel;
        
        private Timer frameTimer;
        public static final int FRAME_RATE = 30;
        
        
        public GamePanel(PlayFrame frame, String levelName){
            this.frame = frame;
            playFieldPanel = new PlayFieldPanel(levelName);
            initializePanel(frame);
        }
        
        private void initializePanel(PlayFrame frame){
            this.frame = frame;
            
            frameTimer = new Timer(FRAME_RATE,this);
            frameTimer.setActionCommand("frameTimer");
            frameTimer.start();
            
            playStatsPanel = new PlayStatsPanel(frame);
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(PlayFrame.GAME_PANEL_XSIZE,PlayFrame.GAME_PANEL_YSIZE));
            setSize(getPreferredSize());
            playFieldPanel.setPreferredSize(new Dimension(PlayFrame.GAME_FIELD_PANEL_XSIZE,(int)(PlayFrame.GAME_FIELD_PANEL_YSIZE)));// keep fields here?
            playStatsPanel.setPreferredSize(new Dimension(PlayStatsPanel.GAME_STATS_PANEL_XSIZE,(int)(PlayStatsPanel.GAME_STATS_PANEL_YSIZE)));
            playFieldPanel.setSize(playFieldPanel.getPreferredSize());
            playStatsPanel.setSize(playStatsPanel.getPreferredSize());
            add(playFieldPanel);
            add(playStatsPanel);
            setVisible(true);
            
        }
        
        public void pause(){//stops the timers when you exit to the main menu. Stops these timers from interfering with a new game
            playFieldPanel.pause();
        }
        
        public void unpause(){
            playFieldPanel.unpause();
        }
        
        public void quit(){
            pause();
            int n = JOptionPane.showConfirmDialog(frame,"Confirm Quit","",JOptionPane.YES_NO_OPTION);
            if (n == 0){
                new MenuFrame();
                frame.close();
            }
            else{
                unpause();
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("frameTimer")){
                if (playFieldPanel != null){
                    playFieldPanel.repaint();
                }
                if (playStatsPanel != null){
                    playStatsPanel.repaint();
                }
            }
        }
    
    
    }
    
    
    
    
}
