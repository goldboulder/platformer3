/*

 */
package support;

import abstractthings.Copyable;
import abstractthings.GameObject;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import player.Player;
import abstractthings.Placeable;
import gui.MakerFieldPanel;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MakerScene {
    private JFrame frame;
    private JPanel parent;
    private Camera camera;
    private GameObjectList gameObjects;
    private static double initialZoom = 32;
    private static double keyCameraScroll = 0.01;
    private static double mouseCameraScroll = 0.3;
    private static final double GRID_RESOLUTION = 0.1;
    public GameObject selectedObject;
    private boolean justSaved;
    
    
    public MakerScene(JFrame frame, JPanel parent){// new blank scene, not loaded
        this.frame = frame;
        this.parent = parent;
        initialize();
    }
    
    public MakerScene(JFrame frame, JPanel parent, String fileName) throws FileNotFoundException{// loaded
        this(frame,parent);
        loadLevel(fileName);    
    }
    
    public void loadLevel(String fileName) throws FileNotFoundException{
        justSaved = true;// also in initialize, but might want to load level directly from another level later
        gameObjects.clear();
        Scanner sc = new Scanner(new File("levels/" + fileName + ".txt"));
        String[] tokens;
        
        while (sc.hasNext()){
            tokens = sc.nextLine().split(" ");
            Placeable p = PrototypeProvider.getPrototype(tokens[0]);//gets a new copy of a prototype, then changes some attributes based on string
            p.readFileString(tokens);
            GameObject g = (GameObject) p;
            gameObjects.add(g);
            g.pauseAnimations();
        }
        // set camera?
        
    }
    
    private void initialize(){
        camera = new Camera(0,0,initialZoom,initialZoom);
        gameObjects = new GameObjectList();
        selectedObject = new PickUpObject(new Point2D.Double(0,0),new Point2D.Double(0.01,0.01));
        Player.deletePlayer();
        gameObjects.add(Player.getPlayer());
        justSaved = true;
    }

    
    public void paintScene(Graphics g, int screenWidth, int screenHeight){// duplicate code?
        Graphics2D g2 = (Graphics2D)g;
        g2.clearRect(-1000000, -1000000,2000000,2000000);//background?
        
        camera.transform(g2,screenWidth,screenHeight);
        
        gameObjects.draw(g,true);
        selectedObject.draw(g);
        selectedObject.drawEditInfo(g);
        g.dispose();
    }
    
    public Point2D getApproxCursorPosition(){// based on center of selected object, who follows the cursor
        return selectedObject.getCenter();
    }
    
    public void setCursorObject(String id) {
        Point2D.Double position = selectedObject.getCenter();//for player positioning
        GameObject object = PrototypeProvider.getPrototype(id).getPrototype();
        
        if (object != null){
            object.setPosition(selectedObject.getCenter());
            selectedObject.snapToGrid(GRID_RESOLUTION);
            selectedObject = object;
            GameObject o = (GameObject) selectedObject;
            o.pauseAnimations();
        }
        
        if (object instanceof Player){// only 1 player may exist at a time
            gameObjects.removePlayers();
            justSaved = false;
            selectedObject.setPosition(position);
        }
        
    }
    

    public void keyPressed(KeyEvent e) {//additional ways to do flip,delete, copy... with right-click menu?
        switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE: 
                setCursorObjectToPickUp();
            break;
            // shortcut keys
            case KeyEvent.VK_C: 
                CopyObject copy = new CopyObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
                copy.doEditAction();
            break;
            case KeyEvent.VK_V: //paste,delete misalligning block and cursor?
                if (selectedObject instanceof Placeable){
                    GameObject g = (GameObject)((Copyable)(selectedObject)).getCopy();
                    g.pauseAnimations();
                    gameObjects.add(g);
                    justSaved = false;
                }
            break;
            case KeyEvent.VK_E: 
                CustomizeObject custom = new CustomizeObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
                custom.doEditAction();
            break;
            case KeyEvent.VK_F: 
                FlipObject flip = new FlipObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
                flip.doEditAction();
            break;
            case KeyEvent.VK_D: 
                DeleteObject delete = new DeleteObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
                delete.doEditAction();
            break;
            // make camera move when mouse is near edges of screen
            case KeyEvent.VK_UP: // wasd keys as well? interfere with delete?
                camera.translatePercent(0, -keyCameraScroll);//constant?
            break;
            case KeyEvent.VK_DOWN: 
                camera.translatePercent(0, keyCameraScroll);//constant?
            break;
            case KeyEvent.VK_LEFT: 
                camera.translatePercent(-keyCameraScroll, 0);//constant?
            break;
            case KeyEvent.VK_RIGHT: 
                camera.translatePercent(keyCameraScroll, 0);//constant?
            break;
            
        }
    }

    public void keyReleased(KeyEvent e) {
        
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        if (selectedObject instanceof MakerObject){
            MakerObject m = (MakerObject) selectedObject;
            m.doEditAction();
        }
        else if (selectedObject instanceof Placeable){// code so you can't stack blocks?
            placeObject();
        }
    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }

    public void mouseDragged(MouseEvent e) {
        
    }

    public void mouseMoved(MouseEvent e) {
        selectedObject.setPosition(OtherThings.screenCoToGraphCo(e, camera, new Dimension(parent.getSize().width,parent.getSize().height)));
        selectedObject.goToCenterOfMouse();
        selectedObject.snapToGrid(GRID_RESOLUTION);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0){
            camera.zoom(Camera.DEFAULT_ZOOM_SCALE);
        }
        if (e.getWheelRotation() > 0){
            camera.zoom(1/Camera.DEFAULT_ZOOM_SCALE);
        }
    }

    public void componentResized(ComponentEvent e) {
        
    }

    public GameObject selectFirstObjectClickedOn(){// returns the object clicked on
        GameObject pointObject = new PickUpObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.01,0.01));
        for (GameObject g : gameObjects){
            if (g.colliding(pointObject)){
                return g;
            }
        }
        return null;
    }
    
    public void pickUpObject(GameObject g){//takes an object from the field and places it in the cursor's hand
        if (g instanceof Placeable){
            gameObjects.remove(g);
            justSaved = false;
            selectedObject = g;
        }
    }
    
    public void placeObject(){
        gameObjects.add(selectedObject);// every time gameObjects is changed, set justSaved to fasle
        justSaved = false;
        selectedObject = new PickUpObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.01,0.01));
    }
    
    // if something goes wrong during saving, the file is blanked******
    public void save(String fileName) throws FileNotFoundException{// no blank files, blank names*
        PrintWriter file = new PrintWriter("levels/" + fileName + ".txt");
        for (GameObject g : gameObjects){
            Placeable p = (Placeable) g;
            file.println(p.saveFileString());
        }
        file.close();
        justSaved = true;
    }

    public boolean justSaved() {
        return justSaved;
    }
    
    public void setCursorObjectToPickUp() {
        selectedObject = new PickUpObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
    }
    
    public void setCursorObjectToCopy() {
        selectedObject = new CopyObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
    }
    
    public void setCursorObjectToFlip() {
        selectedObject = new FlipObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
    }
    
    public void setCursorObjectToCustomize() {
        selectedObject = new CustomizeObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
    }
    
    public void setCursorObjectToDelete() {
        selectedObject = new DeleteObject(selectedObject.getUpperLeftCorner(),new Point2D.Double(0.5,0.5));
    }

    public void moveCameraWithMouse(MakerFieldPanel m, double x, double y) {
        double moveAmount = mouseCameraScroll/PlayScene.GAME_TICK;
        double edgeDisnance = 0.05;// how close to the edge the mouse needs to be to move the camera, in %
        if (x > 0 && x < edgeDisnance){
            camera.translatePercent(-moveAmount, 0);
            selectedObject.changePosition(camera.getXSize() * -moveAmount, 0);
            m.repaint();
        }
        if (x > 1-edgeDisnance && x < 1){
            camera.translatePercent(moveAmount, 0);
            selectedObject.changePosition(camera.getXSize() * moveAmount, 0);
            m.repaint();
        }
        if (y > 0 && y < edgeDisnance){
            camera.translatePercent(0, -moveAmount);
            selectedObject.changePosition(0, camera.getXSize() * -moveAmount);
            m.repaint();
        }
        if (y > 1-edgeDisnance && y < 1){
            camera.translatePercent(0, moveAmount);
            selectedObject.changePosition(0, camera.getXSize() * moveAmount);
            m.repaint();
        }
    }
    
    
    
    public class PickUpObject extends MakerObject{//public because the Door class needs it. Same Package?
        
        public PickUpObject(Point2D.Double position, Point2D.Double size){// more parameters?
            super(position,size);
        }
        
        @Override
        public void doEditAction() {
            GameObject g = selectFirstObjectClickedOn();
            
            if (g != null){
                pickUpObject(g);
            }
        }

        @Override
        public void drawLines(Graphics2D g2) {
            //g2.fillRect(0,0,1,1);
        }
        
        
        
    }
    
    public class CopyObject extends MakerObject{//public because the Door class needs it. Same Package?
        
        public CopyObject(Point2D.Double position, Point2D.Double size){// more parameters?
            super(position,size);
        }
        
        @Override
        public void doEditAction() {
            GameObject g = selectFirstObjectClickedOn();
            
            if (g != null && g instanceof Copyable){
                Copyable c = (Copyable) g;
                GameObject copy = (GameObject) c.getCopy();// some copyables are not gameObjects, but will not be selected
                copy.pauseAnimations();
                selectedObject = copy;
                
            }
        }

        @Override
        public void drawLines(Graphics2D g2) {
            g2.setColor(new Color(128,128,255,128));
            g2.drawLine(0,0,1,1);
            g2.drawLine(0,1,1,0);
        }
        
        
        
    }
    
    private class FlipObject extends MakerObject{
        
        public FlipObject(Point2D.Double position, Point2D.Double size){// more parameters?
            super(position,size);
        }
        
        @Override
        public void doEditAction() {
            
                GameObject g = selectFirstObjectClickedOn();
                if (g != null){
                    g.faceOtherWay();
                    justSaved = false;
                }
            
        }

        @Override
        public void drawLines(Graphics2D g2) {
            g2.setColor(new Color(0,0,200,128));
            g2.drawLine(0,0,1,1);
            g2.drawLine(0,1,1,0);
        }
        
        
        
    }
    
    
    private class CustomizeObject extends MakerObject{
        
        public CustomizeObject(Point2D.Double position, Point2D.Double size){// more parameters?
            super(position,size);
        }
        
        @Override
        public void doEditAction() {
            GameObject g = selectFirstObjectClickedOn();
            if (g instanceof Placeable){
                Placeable p = (Placeable) g;
                p.customize(frame);
                justSaved = false;
            }
        }

        @Override
        public void drawLines(Graphics2D g2) {
            g2.setColor(new Color(0,200,0,128));
            g2.drawLine(0,0,1,1);
            g2.drawLine(0,1,1,0);
        }
        
        
        
    }
    
    private class DeleteObject extends MakerObject{
        
        public DeleteObject(Point2D.Double position, Point2D.Double size){// more parameters?
            super(position,size);
        }
        
        @Override
        public void doEditAction() {
            GameObject g = selectFirstObjectClickedOn();
            gameObjects.remove(g);
            if (g != null){
                justSaved = false;
            }
        }

        @Override
        public void drawLines(Graphics2D g2) {
            g2.setColor(new Color(200,0,0,128));
            g2.drawLine(0,0,1,1);
            g2.drawLine(0,1,1,0);
        }
        
        
        
    }
    
    
}
