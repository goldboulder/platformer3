/*

 */
package support;

import gui.PlayFrame;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;


public class InputHandler {
    
    private boolean leftMouseButton;
    private boolean rightMouseButton;
    private boolean middleMouseButton;
    private int mouseScrollWheel;
    private Point mousePosition;//screen co.
    private Point mouseMovement;//how much the mouse moved in one tick, not implemented yet
    private final HashMap<Integer,Boolean> keyButtonPressed;
    
    private Camera camera;
    private int screenWidth;
    private int screenHeight;
    
    
    public InputHandler(Camera camera){
        this.camera = camera;
        mousePosition = new Point(0,0);
        mouseMovement = new Point(0,0);
        screenWidth = PlayFrame.GAME_FIELD_PANEL_XSIZE;
        screenHeight = PlayFrame.GAME_FIELD_PANEL_YSIZE;
        keyButtonPressed = new HashMap<>();
        
        keyButtonPressed.put(KeyEvent.VK_A, false);
        keyButtonPressed.put(KeyEvent.VK_B, false);
        keyButtonPressed.put(KeyEvent.VK_C, false);
        keyButtonPressed.put(KeyEvent.VK_D, false);
        keyButtonPressed.put(KeyEvent.VK_E, false);
        keyButtonPressed.put(KeyEvent.VK_F, false);
        keyButtonPressed.put(KeyEvent.VK_G, false);
        keyButtonPressed.put(KeyEvent.VK_H, false);
        keyButtonPressed.put(KeyEvent.VK_I, false);
        keyButtonPressed.put(KeyEvent.VK_J, false);
        keyButtonPressed.put(KeyEvent.VK_K, false);
        keyButtonPressed.put(KeyEvent.VK_L, false);
        keyButtonPressed.put(KeyEvent.VK_M, false);
        keyButtonPressed.put(KeyEvent.VK_N, false);
        keyButtonPressed.put(KeyEvent.VK_O, false);
        keyButtonPressed.put(KeyEvent.VK_P, false);
        keyButtonPressed.put(KeyEvent.VK_Q, false);
        keyButtonPressed.put(KeyEvent.VK_R, false);
        keyButtonPressed.put(KeyEvent.VK_S, false);
        keyButtonPressed.put(KeyEvent.VK_T, false);
        keyButtonPressed.put(KeyEvent.VK_U, false);
        keyButtonPressed.put(KeyEvent.VK_V, false);
        keyButtonPressed.put(KeyEvent.VK_W, false);
        keyButtonPressed.put(KeyEvent.VK_X, false);
        keyButtonPressed.put(KeyEvent.VK_Y, false);
        keyButtonPressed.put(KeyEvent.VK_Z, false);
        
        keyButtonPressed.put(KeyEvent.VK_0, false);
        keyButtonPressed.put(KeyEvent.VK_1, false);
        keyButtonPressed.put(KeyEvent.VK_2, false);
        keyButtonPressed.put(KeyEvent.VK_3, false);
        keyButtonPressed.put(KeyEvent.VK_4, false);
        keyButtonPressed.put(KeyEvent.VK_5, false);
        keyButtonPressed.put(KeyEvent.VK_6, false);
        keyButtonPressed.put(KeyEvent.VK_7, false);
        keyButtonPressed.put(KeyEvent.VK_8, false);
        keyButtonPressed.put(KeyEvent.VK_9, false);
        
        keyButtonPressed.put(KeyEvent.VK_LEFT, false);
        keyButtonPressed.put(KeyEvent.VK_RIGHT, false);
        keyButtonPressed.put(KeyEvent.VK_UP, false);
        keyButtonPressed.put(KeyEvent.VK_DOWN, false);
        
        keyButtonPressed.put(KeyEvent.VK_NUMPAD0, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD1, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD2, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD3, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD4, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD5, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD6, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD7, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD8, false);
        keyButtonPressed.put(KeyEvent.VK_NUMPAD9, false);
        
        keyButtonPressed.put(KeyEvent.VK_SPACE, false);
        
        // add more?
        
        
    }
    
    
    
    
    public boolean keyPressed(int keyCode){
        return keyButtonPressed.getOrDefault(keyCode, false);
    }
    
    public Point2D mousePosition(){// returns game coordinates of mouse
        double xPercent = (double)mousePosition.x/screenWidth;
        double yPercent = (double)mousePosition.y/screenHeight;
        double x = camera.getXPosition() + camera.getXSize() * xPercent;
        double y = camera.getYPosition() + camera.getYSize() * yPercent;
        
        return new Point2D.Double(x,y);
    }
    
    public boolean leftMouseButtonPressed(){
        return leftMouseButton;
    }
    
    public boolean rightMouseButtonPressed(){
        return rightMouseButton;
    }
    
    public boolean middleMouseButtonPressed(){
        return middleMouseButton;
    }
    
    public int mouseScrollWheel(){
        return mouseScrollWheel;
    }
    
    
    
    
    
    public void keyTyped(KeyEvent e) {
        
    }

    
    public void keyPressed(KeyEvent e) {
        keyButtonPressed.replace(e.getKeyCode(), true);
    }

    
    public void keyReleased(KeyEvent e) {
        keyButtonPressed.replace(e.getKeyCode(), false);
    }

    
    public void mouseClicked(MouseEvent e) {
        
    }

    
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case 1:
                leftMouseButton = true;
                break;
            case 2:
                middleMouseButton = true;
                break;
            case 3:
                rightMouseButton = true;
                break;
            default:
                break;
        }
        
    }

    
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case 1:
                leftMouseButton = false;
                break;
            case 2:
                middleMouseButton = false;
                break;
            case 3:
                rightMouseButton = false;
                break;
            default:
                break;
        }
    }

    
    public void mouseEntered(MouseEvent e) {
        
    }

    
    public void mouseExited(MouseEvent e) {
        
    }

    
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    
    public void mouseMoved(MouseEvent e) {
        mousePosition.move(e.getX(), e.getY());
    }

    
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0){
            mouseScrollWheel --;
        }
        if (e.getWheelRotation() > 0){
            mouseScrollWheel ++;
        }
    }
    
    public void componentResized(ComponentEvent e) {
        screenWidth = e.getComponent().getSize().width;
        screenHeight = e.getComponent().getSize().height;
    }
    
    

    void resetMovement() {
        mouseScrollWheel = 0;
        mouseMovement.x = 0;
        mouseMovement.y = 0;
    }

    
    
}
