/*

 */
package gui;

import abstractthings.GameObject;
import actions.FollowPath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import paths.Path;
import support.PrototypeProvider;


public class PathSelectionPanel extends JPanel implements ActionListener, DocumentListener{
    
    public static final int PANEL_WIDTH = 300;
    public static final int PANEL_HEIGHT = 300;
    
    private JFrame frame;
    private FollowPath followPath;
    
    private HashMap<String,Path> paths;
    
    private JPanel currentPathPanel;
    
    private JComboBox pathComboBox;
    private JPanel speedPanel;
    private JLabel speedLabel;
    private JTextField speedTextField;
    
    public PathSelectionPanel(JFrame frame, FollowPath followPath){
        this.frame = frame;
        this.followPath = followPath;
        
        pathComboBox = new JComboBox(PrototypeProvider.getPathNames());
        pathComboBox.setActionCommand("Combo Box");
        pathComboBox.addActionListener(this);
        
        speedPanel = new JPanel();
        speedLabel = new JLabel("Speed");
        speedTextField = new JTextField("0");
        
        paths = new HashMap<>();
        
        for (Path path : PrototypeProvider.getPaths()){//don't need to convert to array list
            paths.put(path.getId(), (Path)path.getCopy());
        }
        
        speedPanel.add(speedLabel);
        speedPanel.add(speedTextField);
        
        add(pathComboBox);
        add(speedPanel);
        addInitialPanel(followPath);
        
        speedTextField.getDocument().addDocumentListener(this);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    }
    
    private void addInitialPanel(FollowPath followPath){
        //System.out.println(owner.hasPathAction());
        
        speedTextField.setText(Double.toString(followPath.getSpeed()));
        
        // replace hash map entry with the path it already has
        paths.replace(followPath.getPath().getId(), followPath.getPath());
        // add the appropiate panel
        quickChangeComboBox(followPath);
        currentPathPanel = followPath.getPath().getPanel(frame);
        add(currentPathPanel);
        
        
        
    }
    
    private void quickChangeComboBox(FollowPath pathAction){
        pathComboBox.setActionCommand("");
        pathComboBox.setSelectedItem(pathAction.getPath().getId());
        pathComboBox.setActionCommand("Combo Box");
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public FollowPath getPathAction(){
        return followPath;
    }
    
    private void hideCurrentPathPanel(){
        if (currentPathPanel != null){
            remove(currentPathPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Combo Box")){
            hideCurrentPathPanel();
            //shows selected path panel
            JComboBox cb = (JComboBox)e.getSource();
            String pathName = (String)cb.getSelectedItem();
            
            currentPathPanel = paths.get(pathName).getPanel(frame);
            add(currentPathPanel);
            revalidate();
            
            setPath();
        }
        
    }
    
    private void updatePathSpeed(){
        try{
            followPath.setSpeed(Double.parseDouble(speedTextField.getText()));
        }
        catch(NumberFormatException e){
            //nothing
        }
        
    }
    
    public void setPath() {
        followPath.setPath(paths.get((String)pathComboBox.getSelectedItem()));
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updatePathSpeed();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updatePathSpeed();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updatePathSpeed();
    }

    

    
    
    
    
}
