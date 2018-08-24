/*

 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MenuPlayPanel extends JPanel implements ActionListener{
    
    private MenuFrame.MenuPanel mainMenu;
    
    private JLabel titleLabel;
    private JLabel messageLabel;
    private JPanel nameLoadPanel;
    private JTextField nameTextField;
    private JButton loadButton;
    private JButton testButton;
    private JPanel testPanel;
    
    public MenuPlayPanel(MenuFrame.MenuPanel mainMenu){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        this.mainMenu = mainMenu;
        titleLabel = new JLabel("Play Level");
        messageLabel = new JLabel("Play a level you made!");
        nameLoadPanel = new JPanel();
        nameTextField = new JTextField();
        loadButton = new JButton("Load");
        testButton = new JButton("Test");
        testPanel = new JPanel();
        
        nameTextField.setColumns(10);
        loadButton.setActionCommand("Load");
        testButton.setActionCommand("Test");
        loadButton.addActionListener(this);
        testButton.addActionListener(this);
        
        add(titleLabel);
        add(messageLabel);
        add(nameLoadPanel);
        add(testPanel);
        testPanel.add(testButton);
        nameLoadPanel.add(nameTextField);
        nameLoadPanel.add(loadButton);
        
        testButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Load")){
            String str = nameTextField.getText();
            if (str.equals("")){
                messageLabel.setText("You need to provide a level name.");
                return;
            }
            
            //test for not finding level***************
            File checkFile = new File("levels/" + str + ".txt");
            if (checkFile.exists()){
                new PlayFrame(str);
                mainMenu.close();
            }
            else{
                //pop-up: does not exist
                JOptionPane.showMessageDialog(mainMenu.getFrame(),"File not found","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
