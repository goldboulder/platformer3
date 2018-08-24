/*

 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import player.Player;
import support.PercentBar;


public class PlayStatsPanel extends JPanel implements ActionListener{// use same timer for updating?
    
    public static final int HOT_BAR_HEIGHT = 40;
    public static final int HOT_BAR_WIDTH = HOT_BAR_HEIGHT * HotBarPanel.HOT_BAR_SIZE;
    public static final int GAME_STATS_PANEL_YSIZE = (int) (PlayFrame.GAME_PANEL_YSIZE * 0.1);
    public static final int GAME_STATS_PANEL_XSIZE = PlayFrame.GAME_PANEL_XSIZE;
    
    private PlayFrame frame;
    private HPBarPanel hpBarPanel;
    private HotBarPanel hotBarPanel;
    private JButton quitButton;
    
    public PlayStatsPanel(PlayFrame frame){
        
        this.frame = frame;
        hpBarPanel = new HPBarPanel();
        hpBarPanel.setPreferredSize(new Dimension(300,30));
        hotBarPanel = new HotBarPanel();
        hotBarPanel.setPreferredSize(new Dimension(HOT_BAR_WIDTH,HOT_BAR_HEIGHT));// define constant here?
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        quitButton.setActionCommand("quit");
        
        //format***
        add(hpBarPanel);
        add(hotBarPanel);
        add(quitButton);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
                case "quit":
                        frame.quit();
                break;
                        
                default:
                    
            }
    }
    
    private class HPBarPanel extends JPanel{
        private Color healthColor = new Color(200,0,0);
        private Color hurtColor = new Color(50,50,50);
        private Color numberColor = Color.WHITE;
        @Override
        public void paintComponent(Graphics g){
            PercentBar.drawPlainPercentBar(g, 0, 0, getWidth(), getHeight(), Player.getPlayer().getHealth(), Player.getPlayer().getMaxHealth(), healthColor, hurtColor,numberColor, true,true);
        }
    }
    
}
