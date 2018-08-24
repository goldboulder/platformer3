/*

 */
package otherobjects;

import abstractthings.GameObject;
import actions.PushOutAction;
import java.awt.image.BufferedImage;
import animations.SingleFrameAnimation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import support.ImagePool;
import support.PrototypeProvider;
import gui.MakerSelectionPanel;


public class BasicBlock extends Block{// any picture, in file
    
    
    private static final String PROFILE_IMAGE = "Blocks/BasicBlocks/BlackBlock";
    private static final int PICTURE_PANEL_SIZE = 200;
    private String picture = PROFILE_IMAGE;
    
    public BasicBlock(){
        super();
        pushable = false;
        graphics.addAnimation("block",new SingleFrameAnimation(getProfileImage()),true);
        actionList.addAction(new PushOutAction("pushout"));
    }
    
    public void setPicture(String key){
        graphics.removeAnimation(picture);
        picture = key;
        graphics.addAnimation("block",new SingleFrameAnimation(ImagePool.getPicture(picture)),true);
        
    }
        
    
    @Override
    public BufferedImage getProfileImage(){
        return ImagePool.getPicture(PROFILE_IMAGE);
    }
    
    

    @Override
    public String saveFileString() {// picture type?
        return fileStringStart() + " " + Double.toString(size.getX()) + " " + Double.toString(size.getY()) + " " + picture;
    }

    @Override
    public void readFileString(String[] str) {
        setPosition(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
        setSize(Double.parseDouble(str[3]),Double.parseDouble(str[4]));
        picture = str[5];
        graphics.addAnimation("block",new SingleFrameAnimation(ImagePool.getPicture(picture)),true);
    }

    
    @Override
    public void customize(JFrame frame) {
        JDialog customDialog = new JDialog(frame, "Customize Block", true);
        customDialog.setLocationRelativeTo(null);
        
        JPanel rootPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(new PictureChangePanel(frame,this));
        
        customDialog.getContentPane().add(rootPanel);
        rootPanel.add(new SizeChangePanel(frame,this));
        rootPanel.add(scrollPane);
        
        rootPanel.setLayout(new BoxLayout(rootPanel,BoxLayout.Y_AXIS));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(PICTURE_PANEL_SIZE + (Integer)UIManager.get("ScrollBar.width"),PICTURE_PANEL_SIZE));
        
        customDialog.pack();
        customDialog.setVisible(true);
    }

    @Override
    public void copyFields(GameObject g) {
        super.copyFields(g);
        BasicBlock block = (BasicBlock) g;
        this.picture = block.picture;
        setPicture(block.picture);
    }
    
    
    private class PictureChangePanel extends JPanel implements ActionListener{
        
        private String baseBlockAddress = "Blocks/BasicBlocks";
        private int columns = 4;
        private int buttonSize = PICTURE_PANEL_SIZE/columns;
        private BasicBlock block;
        private JFrame frame;
        private JButton[] imageButtons;
        
        
        private JPanel gridPanel;
        private JPanel blankPanel;
        
        public PictureChangePanel(JFrame frame, BasicBlock block){
            this.frame = frame;
            this.block = block;
            
            gridPanel = new JPanel();
            blankPanel = new JPanel();
            
            File pictureDirectory = new File("pictures/" + baseBlockAddress);
            String[] pictures = pictureDirectory.list();
            imageButtons = new JButton[pictures.length];
            
            for (int i = 0; i < imageButtons.length; i++){
                imageButtons[i] = new JButton(new ImageIcon(ImagePool.getPicture(baseBlockAddress + "/" + pictures[i]).getScaledInstance(buttonSize,buttonSize,Image.SCALE_SMOOTH)));
                imageButtons[i].setPreferredSize(new Dimension(buttonSize,buttonSize));
                imageButtons[i].addActionListener(this);
                imageButtons[i].setActionCommand(pictures[i]);
                gridPanel.add(imageButtons[i]);
            }
            
            setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            gridPanel.setLayout(new GridLayout(0,columns));
            blankPanel.setPreferredSize(new Dimension((Integer)UIManager.get("ScrollBar.width"),PICTURE_PANEL_SIZE));
            
            add(gridPanel);
            add(blankPanel);
            
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            block.setPicture(baseBlockAddress + "/" + e.getActionCommand());
            frame.repaint();
        }
       
        
        

        
        
        
        
    }
    
}
