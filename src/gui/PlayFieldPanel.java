/*

 */
package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import javax.swing.Timer;
import support.PlayScene;


public class PlayFieldPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener{
    
    private PlayScene scene;
    
    public PlayFieldPanel(String levelName){
        this.scene = new PlayScene(levelName,null);
        initialize();
    }
    
    private void initialize(){
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        addComponentListener(this);
    }
    
    
    
    @Override
    public void paintComponent(Graphics g){
        scene.paintScene(g, getWidth(), getHeight());
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        scene.getInputHandler().keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        scene.getInputHandler().keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            scene.triggerPause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        scene.getInputHandler().keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        scene.getInputHandler().mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        scene.getInputHandler().mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        scene.getInputHandler().mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.requestFocus();
        scene.getInputHandler().mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        scene.getInputHandler().mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        scene.getInputHandler().mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        scene.getInputHandler().mouseMoved(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scene.getInputHandler().mouseWheelMoved(e);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        scene.getInputHandler().componentResized(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }

    public void pause() {
        scene.pause();
    }

    public void unpause(){
        scene.unpause();
    }
      
    
    
}
