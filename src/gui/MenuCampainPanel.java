/*

 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MenuCampainPanel extends JPanel implements ActionListener{
    private MenuFrame.MenuPanel mainMenu;//needed?
    
    private JLabel titleLabel;
    private JLabel messageLabel;
    private JPanel startPanel;
    private JPanel loadPanel;
    private JTextField nameStartTextField;
    private JTextField nameLoadTextField;
    private JButton loadButton;
    private JButton startButton;
    
    public MenuCampainPanel(MenuFrame.MenuPanel mainMenu){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        this.mainMenu = mainMenu;
        titleLabel = new JLabel("Campain");
        messageLabel = new JLabel("Go on an adventure!");
        startPanel = new JPanel();
        loadPanel = new JPanel();
        nameStartTextField = new JTextField();
        nameLoadTextField = new JTextField();
        loadButton = new JButton("Load");
        startButton = new JButton("New");
        
        nameStartTextField.setColumns(10);
        nameLoadTextField.setColumns(10);
        loadButton.setActionCommand("Load");
        startButton.setActionCommand("Start");
        loadButton.addActionListener(this);
        startButton.addActionListener(this);
        
        add(titleLabel);
        add(messageLabel);
        add(startPanel);
        add(loadPanel);
        startPanel.add(nameStartTextField);
        startPanel.add(startButton);
        loadPanel.add(nameLoadTextField);
        loadPanel.add(loadButton);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
