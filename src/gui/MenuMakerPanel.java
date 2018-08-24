/*

 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MenuMakerPanel extends JPanel implements ActionListener{
    
    private MenuFrame.MenuPanel mainMenu;//needed?
    
    private JLabel titleLabel;
    private JLabel messageLabel;
    private JPanel nameLoadPanel;
    private JTextField nameTextField;
    private JButton loadButton;
    private JButton newButton;
    private JPanel newPanel;
    
    public MenuMakerPanel(MenuFrame.MenuPanel mainMenu){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        this.mainMenu = mainMenu;
        titleLabel = new JLabel("Make Level");
        messageLabel = new JLabel("Create your own level!");
        nameLoadPanel = new JPanel();
        nameTextField = new JTextField();
        loadButton = new JButton("Load");
        newButton = new JButton("New");
        newPanel = new JPanel();
        
        nameTextField.setColumns(10);
        loadButton.setActionCommand("Load");
        newButton.setActionCommand("New");
        loadButton.addActionListener(this);
        newButton.addActionListener(this);
        
        add(titleLabel);
        add(messageLabel);
        add(nameLoadPanel);
        add(newPanel);
        newPanel.add(newButton);
        nameLoadPanel.add(nameTextField);
        nameLoadPanel.add(loadButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New")){
            new MakerFrame();
            mainMenu.close();
        }
        if (e.getActionCommand().equals("Load")){
            String str = nameTextField.getText();
            if (str.equals("")){
                messageLabel.setText("You need to provide a level name.");
                return;
            }
            
            try {
                new MakerFrame(str);
                mainMenu.close();
            } catch (FileNotFoundException ex) {
                messageLabel.setText("File not found");
            }
            
            
        }
    }
    
    
}
