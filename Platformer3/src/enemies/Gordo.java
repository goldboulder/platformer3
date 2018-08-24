/*

 */
package enemies;

import abstractthings.GameObject;
import actions.ContinuousDamageOnContact;
import actions.FollowPath;
import actions.GoFoward;
import actions.Gravity;
import animations.SingleFrameAnimation;
import gui.PathSelectionPanel;
import interactions.HitCieling;
import interactions.HitFloor;
import interactions.HitWall;
import interactions.Interaction;
import items.Material;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import support.DamageType;
import support.GameObjectList;
import support.InputHandler;
import support.PrototypeProvider;
import support.WhoToDamage;


public class Gordo extends Enemy{
    
    private FollowPath followPath;
    private GoFoward goFoward;
    
    public Gordo(){//for prototype
        super();
        //speed = 0;
        graphics.addAnimation("gordo",new SingleFrameAnimation("Enemies/Gordo/Standard"),true);
        followPath = new FollowPath(FollowPath.ID,PrototypeProvider.getDefaultPath(),0,getCenter());
        goFoward = new GoFoward("Go Foward",0);
        actionList.addAction(new ContinuousDamageOnContact("Damage Player",DamageType.STAB,8,WhoToDamage.PLAYER,null,0.9));
        
        actionList.addAction(goFoward);//default
    }
    
    @Override
    public void draw(Graphics g){
        drawPlain(g);//extra draw methods for drawing pathName?
    }
    
    @Override
    public void drawEditInfo(Graphics g){
        if (!hasPathAction()){
            drawArrow(g);
        }
        else{
            followPath.getPath().draw(g);
        }
    }
    
    private void drawArrow(Graphics g){
        //picture of arrow? drawLine won't cooperate
        
        
    }

    
    ////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
     public void act(GameObjectList o, InputHandler i){// is this nessesary? do with action objects instead? what if actions need to communicate?
        super.act(o,i);
    }
    
    @Override
    public void react(GameObject g, Interaction i) {
        super.react(g, i);
        if (!hasPathAction()){
            if (i instanceof HitWall && g != justBouncedOff){
                bounceRotation(Math.toRadians(90),g);
            }
            if (((i instanceof HitFloor) || (i instanceof HitCieling)) && g != justBouncedOff){
                bounceRotation(0,g);
            }
        }
    }
    
    private double speed(){
        if (hasPathAction()){
            return followPath.getSpeed();
        }
        else{
            return goFoward.getSpeed();
        }
    }
    
    @Override
    public void fillInventory(){
        inventory.addItem(Material.COIN,1);
    }
    
    /////////////////////////////////////////////////////////////////////

    @Override
    public String saveFileString() {
        
        if (!hasPathAction()){//followPath and goFoward are mutually exclusive
            return fileStringStart() + " " + "null" + " " + speed() + " " + Math.toDegrees(rotation) + " " + actionList.hasAction("gravity");//need gravity, goFoward messes it up?*****
        }
        else{
            String str = fileStringStart() + " " + followPath.getPath().getId() + " " + speed();
            for (String s : followPath.getPath().getParameters()){// getParameters returns double[]
                str = str + " " + s;
            }
            return str;
        }
    }

    @Override
    public void readFileString(String[] str) {
        this.setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
        if (!str[3].equals("null")){
            followPath.setPath(PrototypeProvider.getPath(str[3]));
            followPath.setSpeed(Double.parseDouble(str[4]));
            followPath.getPath().buildPath(Arrays.copyOfRange(str, 5, str.length));
            followPath.setOrigin(getCenter());
            actionList.addActionNow(followPath);
            actionList.removeAction(goFoward);//removeNow?
            pushable = false;
        }
        else{
            //goFoward is on by default
            // if pathName = null, str[4] and str[5] stand for speed and direction
            goFoward.setSpeed(Double.parseDouble(str[4]));
            rotation = Math.toRadians(Double.parseDouble(str[5]));
            actionList.addAction(goFoward);
            
            if (Boolean.parseBoolean(str[6])){
                actionList.addAction(new Gravity("gravity"));
            }
        }
    }
    
    @Override
    public void copyFields(GameObject g){
        super.copyFields(g);
        Gordo go = (Gordo) g;
        if (go.hasPathAction()){
            actionList.addAction(followPath);
            actionList.removeAction(goFoward);
        }
    }

    @Override
    public void customize(JFrame frame) {
        JDialog customDialog = new JDialog(frame, "Customize Path", true);
        customDialog.setLocationRelativeTo(null);
        customDialog.getContentPane().add(new CustomizePanel(frame,this));
        customDialog.pack();
        customDialog.setVisible(true);
    }

    
    
    private class CustomizePanel extends JPanel implements ActionListener, DocumentListener{
        
        private static final int X_SIZE = 300;
        
        private JFrame frame;
        
        private JPanel checkBoxPanel;
        private JCheckBoxMenuItem usePathCheckBox;
        
        private JPanel speedRotationPanel;
        
        private JPanel speedPanel;
        private JPanel rotationPanel;
        
        private JLabel speedLabel;
        private JLabel rotationLabel;
        private JTextField speedTextField;
        private JTextField rotationTextField;
        
        private PathSelectionPanel pathSelectionPanel;
        
        public CustomizePanel(JFrame frame, Gordo gordo){
            this.frame = frame;
            
            checkBoxPanel = new JPanel();
            usePathCheckBox = new JCheckBoxMenuItem("Use Path",gordo.hasPathAction());
            usePathCheckBox.addActionListener(this);
            usePathCheckBox.setActionCommand("CheckBox");
            
            speedRotationPanel = new JPanel();
            speedPanel = new JPanel();
            rotationPanel = new JPanel();
            speedLabel = new JLabel("Speed");
            rotationLabel = new JLabel("Rotation");
            speedTextField = new JTextField(Double.toString(speed()));
            speedTextField.getDocument().addDocumentListener(this);
            rotationTextField = new JTextField(Double.toString(Math.toDegrees(rotation)));
            rotationTextField.getDocument().addDocumentListener(this);
            
            
            pathSelectionPanel = new PathSelectionPanel(frame,followPath);
            
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            speedRotationPanel.setLayout(new BoxLayout(speedRotationPanel,BoxLayout.Y_AXIS));
            
            checkBoxPanel.add(usePathCheckBox);
            
            speedPanel.add(speedLabel);
            speedPanel.add(speedTextField);
            rotationPanel.add(rotationLabel);
            rotationPanel.add(rotationTextField);
            
            speedRotationPanel.add(speedPanel);
            speedRotationPanel.add(rotationPanel);
            
            add(checkBoxPanel);
            
            if (usePathCheckBox.getState()){
                add(pathSelectionPanel);
            }
            else{
                add(speedRotationPanel);
            }
            
            
            setPreferredSize(new Dimension(X_SIZE,300));
            speedRotationPanel.setPreferredSize(new Dimension(X_SIZE,60));
            speedRotationPanel.setMaximumSize(new Dimension(X_SIZE,60));
            checkBoxPanel.setPreferredSize(new Dimension(X_SIZE,20));
            checkBoxPanel.setMaximumSize(new Dimension(X_SIZE,20));
            
        }
        
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("CheckBox")){
                if (usePathCheckBox.getState()){
                    remove(speedRotationPanel);
                    add(pathSelectionPanel);
                    revalidate();
                    actionList.addAction(followPath);
                    actionList.removeAction(goFoward);
                    pushable = false;
                }
                else{
                    remove(pathSelectionPanel);
                    add(speedRotationPanel);
                    revalidate();
                    actionList.addAction(goFoward);
                    actionList.removeAction(followPath);
                    pushable = true;
                }
            }
            repaint();
            frame.repaint();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            setSpeedRotation();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setSpeedRotation();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            setSpeedRotation();
        }
        
        private void setSpeedRotation(){
            try{
                goFoward.setSpeed(Double.parseDouble(speedTextField.getText()));
            }
            catch(NumberFormatException e){
                
            }
            
            try{
                rotation = Math.toRadians(Double.parseDouble(rotationTextField.getText()));
            }
            catch(NumberFormatException e){
                
            }
            frame.repaint();
        }
        
        
        
    }
    
}
