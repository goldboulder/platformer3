/*

 */
package otherobjects;

import abstractthings.Copyable;
import abstractthings.GameObject;
import abstractthings.Placeable;
import gui.MakerFieldPanel;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import player.Player;
import support.ImagePool;
import support.PlayScene;


public abstract class Door extends GameObject implements Placeable, Copyable{
    
    protected boolean active = false; // if turned on, switches levels at the end of the game tick
    protected String destinationLevelName;
    protected Point2D.Double destinationLevelPosition;
    
    @Override
    public GameObject getPrototype() {// have default constructor for all GameObjects?
        try {
            return getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(getId() + " doesn't have a default constructor!");
        }
    }
    
    @Override
    public BufferedImage getProfileImage(){
        return ImagePool.getPicture("Others/Door/" + getId() + "/Profile");
    }
    
    public boolean isActive(){
        return active;
    }
    
    public void deactivate(){
        active = false;
    }

    public String getLevelName() {
        return getDestinationLevelName();
    }
    
    public void setLevelName(String levelName) {
        this.setDestinationLevelName(levelName);
    }
    
    public Point2D.Double getLevelPosition() {
        return getDestinationLevelPosition();
    }
    
    public void setLevelPosition(Point2D.Double levelPosition) {
        this.setDestinationLevelPosition(levelPosition);
    }

    public void goThrough(PlayScene scene) {
        if (Player.getPlayer().onDoorCoolDown() || Player.getPlayer().isDead()){
            return;
        }
        
        if (!scene.getLevelName().equals(destinationLevelName) && !destinationLevelName.equals("")){
            try {
                scene.loadLevel(getDestinationLevelName(), destinationLevelPosition);
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to load level " + destinationLevelName);
            }
        }
        else{
            Player.getPlayer().setPosition(getDestinationLevelPosition());
        }
        
        Player.getPlayer().goThroughDoorHandling();
        
    }
    
    public void levelAndPositionUI(JFrame frame){// if you click on a door in edit mode. Overrideable in subclasses call this
        String otherLevel = JOptionPane.showInputDialog(frame, "Enter level name");
        if (otherLevel.equals("")){// need to save level before connecting doors in same level*
            return;
        }
        try {
            JDialog customDialog = new JDialog(frame, "Select Door", true);
            customDialog.setLocationRelativeTo(null);
            customDialog.getContentPane().add(new DoorConnectionPanel(frame,customDialog,this,otherLevel));
            customDialog.pack();
            customDialog.setVisible(true);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "File not found");
        }
    }

    
    public String getDestinationLevelName() {
        return destinationLevelName;
    }

    
    public void setDestinationLevelName(String destinationLevelName) {
        this.destinationLevelName = destinationLevelName;
    }

    
    public Point2D.Double getDestinationLevelPosition() {
        return destinationLevelPosition;
    }

    
    public void setDestinationLevelPosition(Point2D.Double destinationLevelPosition) {
        this.destinationLevelPosition = destinationLevelPosition;
    }
    
    @Override
    public String getId() {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public void copyFields(GameObject g){
        super.copyFields(g);
        Door d = (Door) g;
        this.active = d.active;
        this.destinationLevelName = d.destinationLevelName;
        this.destinationLevelPosition = new Point2D.Double(d.destinationLevelPosition.x,d.destinationLevelPosition.y);
    }
    
    private class DoorConnectionPanel extends JPanel{
        
        private JFrame parent;
        private JDialog dialog;
        private Door owner;
        private String fileName;
        
        private JPanel infoPanel;
        private JLabel infoLabel;
        
        private MakerDoorSelectionPanel makerSelectionPanel;
        
        public DoorConnectionPanel(JFrame parent, JDialog dialog, Door owner, String fileName) throws FileNotFoundException{
            this.parent = parent;
            this.dialog = dialog;
            this.owner = owner;
            this.fileName = fileName;
            
            infoPanel = new JPanel();
            infoLabel = new JLabel("Select a door to connect to");
            makerSelectionPanel = new MakerDoorSelectionPanel(this,fileName);
            infoPanel.add(infoLabel);
            add(infoPanel);
            add(makerSelectionPanel);
            
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            makerSelectionPanel.setPreferredSize(new Dimension(600,600));
        }
        
        private void recievePoint(Point2D.Double p){
            owner.setDestinationLevelName(fileName);
            owner.setDestinationLevelPosition(p);
            close();
        }
        
        public void close(){
            //frame.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));//closes everything
            setVisible(false);
            dialog.dispose();
        }
        
    }
    
    private class MakerDoorSelectionPanel extends MakerFieldPanel{
        
        Door.DoorConnectionPanel parent;
        
        public MakerDoorSelectionPanel(Door.DoorConnectionPanel parent, String fileName) throws FileNotFoundException {
            super(null, null, fileName);
            this.parent = parent;
        }
        
        @Override
        public void mousePressed(MouseEvent e){
            GameObject g = scene.selectFirstObjectClickedOn();
            if (g instanceof Door){
                parent.recievePoint(g.getUpperLeftCorner());
            }
        }
        
    }
    
    
    
}
