
package otherobjects;

import abstractthings.Agent;
import abstractthings.Copyable;
import abstractthings.GameObject;
import java.awt.image.BufferedImage;
import support.ImagePool;
import abstractthings.Placeable;
import actions.PushOutAction;
import animations.SingleFrameAnimation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import support.HitBox;
import support.PrototypeProvider;


public abstract class Block extends GameObject implements Placeable, Copyable{
    
    @Override
    public GameObject getPrototype() { //move to GameObject?************
        try {
            return getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(getId() + " doesn't have a default constructor!");
        }
    }
    
    public Block(){
        super();
        position = new Point2D.Double(0,0);
        this.size = new Point2D.Double(1,1);
        this.hitBox = new HitBox(position.getX(),position.getY(),size.getX(),size.getY());
    }
    
    
    @Override
    public void copyFields(GameObject g){
        super.copyFields(g);
        setSize(g.getXSize(),g.getYSize()); //not working
        
    }
    
    
    
    
    @Override
    public String fileStringStart(){
        return getId() + " " + Double.toString(position.getX()) + " " + Double.toString(position.getY());
    }
    
    
    @Override
    public void customize(JFrame frame) {
        JDialog customDialog = new JDialog(frame, "Customize Block", true);
        customDialog.setLocationRelativeTo(null);
        customDialog.getContentPane().add(new SizeChangePanel(frame,this));
        customDialog.pack();
        customDialog.setVisible(true);
    }
    
    
    
    
    protected class SizeChangePanel extends JPanel implements DocumentListener{//expand for changing the picture in BasicBlock****
        
        private Block block;
        private JFrame frame;
        
        private JPanel labelPanel;
        private JPanel textFieldPanel;
        
        private JLabel xSizeLabel;
        private JLabel ySizeLabel;
        private JTextField xSizeTextField;
        private JTextField ySizeTextField;
        
        public SizeChangePanel(JFrame frame, Block block){
            this.frame = frame;
            this.block = block;
            setPreferredSize(new Dimension(200,40));//constant?*
            labelPanel = new JPanel();
            textFieldPanel = new JPanel();
            xSizeLabel = new JLabel("width");
            ySizeLabel = new JLabel("height");
            xSizeTextField = new JTextField(Double.toString(block.size.getX()));
            ySizeTextField = new JTextField(Double.toString(block.size.getY()));
            
            labelPanel.add(xSizeLabel);
            labelPanel.add(ySizeLabel);
            textFieldPanel.add(xSizeTextField);
            textFieldPanel.add(ySizeTextField);
            add(labelPanel);
            add(textFieldPanel);
            
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            labelPanel.setLayout(new GridLayout(1,2));
            textFieldPanel.setLayout(new BoxLayout(textFieldPanel,BoxLayout.X_AXIS));
            
            xSizeTextField.getDocument().addDocumentListener(this);
            ySizeTextField.getDocument().addDocumentListener(this);
            
        }
        
        private void changeBlock(){
            double x = block.getXSize();
            double y = block.getYSize();
            try{
                double tempX = Double.parseDouble(xSizeTextField.getText());
                if (tempX > 0){
                    x = tempX;
                }
            }
            catch(NumberFormatException e){
                
            }
            try{
                double tempY = Double.parseDouble(ySizeTextField.getText());
                if (tempY > 0){
                    y = tempY;
                }
            }
            catch(NumberFormatException e){
                
            }
            block.setSize(x,y);
            frame.repaint();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            changeBlock();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changeBlock();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            changeBlock();
        }
        
    }
    
}
