/*

 */
package gui;

import abstractthings.GameObject;
import support.PrototypeProvider;
import otherobjects.Block;
import enemies.Enemy;
import items.Item;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import otherobjects.Door;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import player.Player;


public class MakerFrame extends JFrame{
    
    public MakerFrame(){
        setContentPane(new MakerPanel(this));
        setSize(getPreferredSize());
        initialize();
    }
    
    public MakerFrame(String levelName) throws FileNotFoundException{
        setContentPane(new MakerPanel(this,levelName));// similar to PlayFrame*
        setSize(getPreferredSize());
        initialize();
    }

    public void initialize(){
        setTitle("Platformer3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    public class MakerPanel extends JPanel implements ActionListener{
        private JFrame frame;
        private MakerFieldPanel makerFieldPanel;
        
        private JPanel blocksPanel;
        private JPanel enemiesPanel;
        private JPanel itemsPanel;
        
        private JPanel editPanel;
        private JLabel otherLabel;
        private JLabel blocksLabel;
        private JLabel enemiesLabel;
        private JLabel itemsLabel;
        
        
        private JPanel otherLabelPanel;
        private JPanel blocksLabelPanel;
        private JPanel enemiesLabelPanel;
        private JPanel itemsLabelPanel;
        
        private JButton pickUpButton;
        private JButton copyButton;
        private JButton flipButton;
        private JButton customizeButton;
        private JButton deleteButton;
        
        private JPanel otherPanel;
        private JPanel savePanel;
        private JButton saveButton;
        private JButton quitButton;
        private JTextField saveTextField;
        
        private MakerSelectionPanel blockSelectionPanel;
        private MakerSelectionPanel enemySelectionPanel;
        private MakerSelectionPanel itemSelectionPanel;
        
        public static final int X_SIZE = 1400;
        public static final int Y_SIZE = 1000;
        public static final int MAKER_X_SIZE = 1000;
        public static final int OTHER_PANEL_HEIGHT = 100;
        public static final int SAVE_PANEL_HEIGHT = 100;
        public static final int EDIT_PANEL_WIDTH = X_SIZE - MAKER_X_SIZE;
        public static final int CONTENT_PANEL_HEIGHT = (Y_SIZE - OTHER_PANEL_HEIGHT - SAVE_PANEL_HEIGHT)/3;
        public static final int LABEL_PANEL_HEIGHT = 20;
        
        
        public MakerPanel(JFrame frame){
            makerFieldPanel = new MakerFieldPanel(frame,this);
            initializePanel(frame);
        }
        
        public MakerPanel(JFrame frame, String levelName) throws FileNotFoundException{
            makerFieldPanel = new MakerFieldPanel(frame,this,levelName);
            initializePanel(frame);
            saveTextField.setText(levelName);
        }
        
        public void initializePanel(JFrame frame){
            this.frame = frame;
            setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            setPreferredSize(new Dimension(X_SIZE,Y_SIZE));
            
            
            editPanel = new JPanel();
            otherLabel = new JLabel("other");
            blocksLabel = new JLabel("blocks");
            blocksPanel = new JPanel();
            enemiesLabel = new JLabel("enemies");
            enemiesPanel = new JPanel();
            itemsLabel = new JLabel("items");
            itemsPanel = new JPanel();
            otherPanel = new JPanel();
            savePanel = new JPanel();
            pickUpButton = new JButton("Pick Up");
            copyButton = new JButton("Copy");
            flipButton = new JButton("Flip");
            customizeButton = new JButton("Customize");
            deleteButton = new JButton("Delete");
            saveButton = new JButton("Save");
            quitButton = new JButton("Quit");
            saveTextField = new JTextField();
            otherLabelPanel = new JPanel();
            blocksLabelPanel = new JPanel();
            enemiesLabelPanel = new JPanel();
            itemsLabelPanel = new JPanel();
            blockSelectionPanel = new MakerSelectionPanel(this,5,EDIT_PANEL_WIDTH , CONTENT_PANEL_HEIGHT - LABEL_PANEL_HEIGHT);
            enemySelectionPanel = new MakerSelectionPanel(this,5,EDIT_PANEL_WIDTH, CONTENT_PANEL_HEIGHT - LABEL_PANEL_HEIGHT);
            itemSelectionPanel = new MakerSelectionPanel(this,5,EDIT_PANEL_WIDTH ,CONTENT_PANEL_HEIGHT - LABEL_PANEL_HEIGHT);
            
            add(makerFieldPanel);
            add(editPanel);
            
            editPanel.add(otherPanel);
            editPanel.add(blocksPanel);
            editPanel.add(enemiesPanel);
            editPanel.add(itemsPanel);
            editPanel.add(savePanel);
            
            otherLabelPanel.add(otherLabel);
            blocksLabelPanel.add(blocksLabel);
            enemiesLabelPanel.add(enemiesLabel);
            itemsLabelPanel.add(itemsLabel);
            
            
            otherPanel.add(otherLabelPanel);
            otherPanel.add(pickUpButton);
            otherPanel.add(copyButton);
            otherPanel.add(flipButton);
            otherPanel.add(customizeButton);
            otherPanel.add(deleteButton);
            blocksPanel.add(blocksLabelPanel);
            blocksPanel.add(blockSelectionPanel);
            enemiesPanel.add(enemiesLabelPanel);
            enemiesPanel.add(enemySelectionPanel);
            itemsPanel.add(itemsLabelPanel);
            itemsPanel.add(itemSelectionPanel);
            savePanel.add(saveTextField);
            savePanel.add(saveButton);
            savePanel.add(quitButton);
            
            editPanel.setLayout(new BoxLayout(editPanel,BoxLayout.Y_AXIS));
            blocksPanel.setLayout(new BoxLayout(blocksPanel,BoxLayout.Y_AXIS));
            enemiesPanel.setLayout(new BoxLayout(enemiesPanel,BoxLayout.Y_AXIS));
            itemsPanel.setLayout(new BoxLayout(itemsPanel,BoxLayout.Y_AXIS));
            savePanel.setLayout(new BoxLayout(savePanel,BoxLayout.X_AXIS));
            
            saveTextField.setMaximumSize(new Dimension(200,20));
            makerFieldPanel.setPreferredSize(new Dimension(MAKER_X_SIZE,Y_SIZE));
            editPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,Y_SIZE));
            
            
            otherPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,OTHER_PANEL_HEIGHT));
            blocksPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,CONTENT_PANEL_HEIGHT));
            enemiesPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,CONTENT_PANEL_HEIGHT));
            itemsPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,CONTENT_PANEL_HEIGHT));
            savePanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,SAVE_PANEL_HEIGHT));
            
            otherLabelPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,LABEL_PANEL_HEIGHT));
            blocksLabelPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,LABEL_PANEL_HEIGHT));
            enemiesLabelPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,LABEL_PANEL_HEIGHT));
            itemsLabelPanel.setPreferredSize(new Dimension(EDIT_PANEL_WIDTH,LABEL_PANEL_HEIGHT));
            
            PrototypeProvider.populateContents(this);
            
            pickUpButton.addActionListener(this);
            copyButton.addActionListener(this);
            flipButton.addActionListener(this);
            customizeButton.addActionListener(this);
            deleteButton.addActionListener(this);
            saveButton.addActionListener(this);
            quitButton.addActionListener(this);
            pickUpButton.setActionCommand("pickUp");
            copyButton.setActionCommand("copy");
            flipButton.setActionCommand("flip");
            customizeButton.setActionCommand("customize");
            deleteButton.setActionCommand("delete");
            saveButton.setActionCommand("save");
            quitButton.setActionCommand("quit");
        }

        public void setFieldCursor(String actionCommand) {
            makerFieldPanel.setCursorObject(actionCommand);
        }
        
        
        
        public void addToContentPanel(GameObject g){
            if (g instanceof Block || g instanceof Door){
                blockSelectionPanel.addContent(g);
            }
            else if (g instanceof Enemy || g instanceof Player){
                enemySelectionPanel.addContent(g);
            }
            else if (g instanceof Item){
                itemSelectionPanel.addContent(g);
            }
        }
        
        public void save(){
            //if file already exists, show warning
            String str = saveTextField.getText();
            
            if (!validFileName(str)){
                JOptionPane.showMessageDialog(frame,"This file name is not valid.","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (new File("levels/" + str + ".txt").exists()){
                if (JOptionPane.showConfirmDialog(frame,str + " already exists. Overwrite?","",JOptionPane.YES_NO_OPTION) != 0){
                    return;
                }
            }
            
            //save
            try{// cannot save if file name is invalid?***
                makerFieldPanel.save(str);
                System.out.println("Saved!");
            }
            catch(FileNotFoundException r){
                System.out.println("FileNotFoundException");
            }

        }
        
        private boolean validFileName(String str){
            if (str.length() == 0 || str.startsWith(" ")){
                return false;
            }
            
            for (int i = 0; i < str.length(); i ++){
                char c = str.charAt(i);
                if (!(Character.isLetterOrDigit(c) || c == ' ')){
                    return false;
                }
            }
            return true;
        }
        
        
        private void quit(){
            new MenuFrame();
            close();
        }
        
        public void close(){
            //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));//closes everything
            frame.setVisible(false); //you can't see me!
            frame.dispose();
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "pickUp":// too intermingled?
                    makerFieldPanel.setCursorObjectToPickUp();
                break;
                case "copy":// too intermingled?
                    makerFieldPanel.setCursorObjectToCopy();
                break;
                case "flip":// too intermingled?
                    makerFieldPanel.setCursorObjectToFlip();
                break;
                case "customize":// too intermingled?
                    makerFieldPanel.setCursorObjectToCustomize();
                break;
                case "delete":// too intermingled?
                    makerFieldPanel.setCursorObjectToDelete();
                break;
                case "save":
                    save();// confirmation here*********
                break;
                case "quit":
                        if (!makerFieldPanel.justSaved()){
                            int n = JOptionPane.showConfirmDialog(frame," Quit without saving?","",JOptionPane.YES_NO_OPTION);
                            if (n == 0){
                                quit();
                            }
                        }
                        else{
                            quit();
                        }
                break;
                        
                default:
                    try{
                        setFieldCursor(e.getActionCommand());
                    }
                    catch(Exception ex){
                        
                    }
                    
            }
        }
        
        
        
    }
}
