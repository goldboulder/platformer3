/*

 */
package gui;

import abstractthings.GameObject;
import abstractthings.Spawnable;
import abstractthings.Spawner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import support.PrototypeProvider;


public class SpawnableSelectionPanel extends JPanel implements ActionListener, DocumentListener{
    
    public static final int PANEL_SIZE = 300;
    private int thickness = 5;
    private int buttonSize = PANEL_SIZE/thickness;
    
    private JFrame frame;
    private Spawner spawner;
    
    private JPanel topPanel;
    private JPanel gridPanel;
    private JLabel maxSpawnsLabel;
    private JTextField maxSpawnsTextField;
    private HashMap<String,JButton> spawnButtons;
    private JButton selectedButton;
    private JScrollPane scrollPane;
    
    private static Color unselectedColor = new Color(230,230,255);
    private static Color selectedColor = new Color(255,255,180);
    
    public SpawnableSelectionPanel(JFrame frame, Spawner spawner){
        this.frame = frame;
        this.spawner = spawner;
        
        topPanel = new JPanel();
        gridPanel = new JPanel();
        scrollPane = new JScrollPane(gridPanel);
        maxSpawnsLabel = new JLabel("Max Spawns");
        maxSpawnsTextField = new JTextField(Integer.toString(spawner.getMaxChildren()));
        
        spawnButtons = new HashMap<>();
        
        for (GameObject object : PrototypeProvider.getPrototypes()){
            if (object instanceof Spawnable){
                JButton newButton = new JButton();
                newButton.setActionCommand(object.getId());
                newButton.setIcon(new ImageIcon(object.getProfileImage().getScaledInstance(buttonSize,buttonSize,Image.SCALE_SMOOTH)));
                newButton.setToolTipText(object.getId());
                newButton.setBackground(unselectedColor);
                spawnButtons.put(object.getId(),newButton);
                newButton.addActionListener(this);
                gridPanel.add(newButton);
            }
        }
        
        if (spawner.getSpawnPrototype() != null){
            selectedButton = spawnButtons.get(spawner.getSpawnPrototype().getId());
            selectedButton.setBackground(selectedColor);
        }
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        topPanel.setPreferredSize(new Dimension(PANEL_SIZE,30));
        topPanel.setLayout(new GridLayout(1,2));
        gridPanel.setLayout(new GridLayout(0,thickness));
        gridPanel.setPreferredSize(new Dimension(PANEL_SIZE,buttonSize * (spawnButtons.size()/thickness)));
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        maxSpawnsTextField.getDocument().addDocumentListener(this);
        
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//have scrollbar in selectionPanel?*
        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//don't need a seperate panel for scrollbar, just room??
        scrollPane.setPreferredSize(new Dimension(PANEL_SIZE + (Integer)UIManager.get("ScrollBar.width"),PANEL_SIZE));
        
        
        topPanel.add(maxSpawnsLabel);
        topPanel.add(maxSpawnsTextField);
        add(topPanel);
        add(scrollPane);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        spawner.setSpawnPrototype((Spawnable) PrototypeProvider.getOriginal(e.getActionCommand()));
        if (selectedButton != null){
            selectedButton.setBackground(unselectedColor);
            
        }
        selectedButton = spawnButtons.get(e.getActionCommand());
        selectedButton.setBackground(selectedColor);
        frame.repaint();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateMaxSpawnsGUI(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateMaxSpawnsGUI(e);//needed?
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateMaxSpawnsGUI(e);
    }
    
    private void updateMaxSpawnsGUI(DocumentEvent e){
        try{
            int i = Integer.parseInt(maxSpawnsTextField.getText());
            spawner.setMaxChildren(i);
            frame.repaint();
        }
        catch(NumberFormatException ex){
            
        }
    }
    
}
