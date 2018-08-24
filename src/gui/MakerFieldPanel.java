/*

 */
package gui;

import gui.MakerFrame.MakerPanel;
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
import javax.swing.JPanel;
import support.MakerScene;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.Timer;
import support.PlayScene;


public class MakerFieldPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener, ActionListener{
    
    private JPanel parent;
    protected MakerScene scene;
    
    //keep track of last known mouse position and have a thread move the camera based on the mouse being near the edge, mouseExited event stops this
    private int mouseXPos;
    private int mouseYPos;
    private Timer mouseTimer;
    
    
    public MakerFieldPanel(JFrame frame, MakerPanel parent){//this() constructor call?
        this.scene = new MakerScene(frame,this);
        initialize();
    }
    
    public MakerFieldPanel(JFrame frame, MakerPanel parent, String fileName) throws FileNotFoundException{
        this.scene = new MakerScene(frame,this,fileName);
        initialize();
    }
    
    public void initialize(){
        this.parent = parent;
        
        mouseXPos = -1;//outside of bounds, so it doesn't cause the screen to move right away
        mouseYPos = -1;
        mouseTimer = new Timer(PlayScene.GAME_TICK,this);
        mouseTimer.setActionCommand("mouse");
        mouseTimer.start();
        
        //populatePrototypePool();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        addComponentListener(this);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        scene.paintScene(g, getWidth(), getHeight());
    }
    
    public void setCursorObject(String actionCommand) {
        scene.setCursorObject(actionCommand);
        repaint();
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        scene.keyPressed(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        scene.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        scene.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        scene.mousePressed(e);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        scene.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.requestFocus();
        scene.mouseEntered(e);
        mouseTimer.start();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        scene.mouseExited(e);
        mouseTimer.stop();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        scene.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        scene.mouseMoved(e);
        mouseXPos = e.getX();
        mouseYPos = e.getY();
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scene.mouseWheelMoved(e);
        repaint();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        scene.componentResized(e);
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

    void save(String fileName) throws FileNotFoundException {
        scene.save(fileName);
    }

    boolean justSaved() {
        return scene.justSaved();
    }
    
    public void setCursorObjectToPickUp() {
        scene.setCursorObjectToPickUp();
    }
    
    public void setCursorObjectToCopy() {
        scene.setCursorObjectToCopy();
    }
    
    public void setCursorObjectToFlip() {
        scene.setCursorObjectToFlip();
    }

    public void setCursorObjectToCustomize() {
        scene.setCursorObjectToCustomize();
    }

    public void setCursorObjectToDelete() {
        scene.setCursorObjectToDelete();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("mouse")){
            scene.moveCameraWithMouse(this,mouseXPos/(double)this.getWidth(),mouseYPos/(double)this.getHeight());
        }
        
    }


    

    
    
}
