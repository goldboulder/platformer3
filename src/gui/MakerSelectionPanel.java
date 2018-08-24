/*

 */
package gui;

import abstractthings.GameObject;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;


public class MakerSelectionPanel extends JPanel{
    
    private ActionListener parent;
    private int thickness;// needed?
    
    private ArrayList<JButton> buttons;
    
    private int width;
    private int buttonSize;
    
    private JScrollPane scrollPane;
    private JPanel gridPanel;
    
    
    public MakerSelectionPanel(ActionListener parent, int thickness, int width, int height){// add scrollPane to here instead of in makerPanel?
        this.parent = parent;
        this.width = width;
        buttonSize = width / thickness;
        this.thickness = thickness;
        buttons = new ArrayList();
        
        gridPanel = new JPanel();
        scrollPane = new JScrollPane(gridPanel);
        
        gridPanel.setLayout(new GridLayout(0,thickness));
        gridPanel.setPreferredSize(new Dimension(width,buttonSize));
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        
        
        add(scrollPane);
        
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(width,height));
        
        //test();
    }
    
    public void addContent(GameObject g){
        JButton newButton = new JButton();
        newButton.setActionCommand(g.getId());
        newButton.setIcon(new ImageIcon(g.getProfileImage().getScaledInstance(buttonSize,buttonSize,Image.SCALE_SMOOTH)));
        newButton.setToolTipText(g.getId());
        buttons.add(newButton);
        newButton.addActionListener(parent);
        gridPanel.add(newButton);
        //resize
        gridPanel.setPreferredSize(new Dimension(width - (Integer)UIManager.get("ScrollBar.width"),buttonSize * (int)(Math.ceil(buttons.size()/thickness))));
    }
    
    public void test(){
        JButton newButton = null;
        for (int i = 0; i < 100; i ++){
            newButton = new JButton(Integer.toString(i));
            buttons.add(newButton);
            newButton.setActionCommand(Integer.toString(i));
            add(newButton);
        }
        
    }

    
}
