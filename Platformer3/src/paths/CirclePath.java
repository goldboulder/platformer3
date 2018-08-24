/*

 */
package paths;

import abstractthings.GameObject;
import gui.PathSelectionPanel;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class CirclePath extends Path{
    
    private double radius;
    private double startAngle;
    private boolean clockwise;

    public CirclePath(double radius, double startAngle, boolean clockwise) {
        this.radius = radius;
        this.startAngle = startAngle;
        this.clockwise = clockwise;
    }

    @Override
    public Point2D.Double getPathPosition(double time) {
        if (clockwise){
            time = -time;
        }
        return new Point2D.Double(radius * Math.cos((time/radius) + startAngle), radius * Math.sin((time/radius) + startAngle));
    }
    
    public double getRadius(){
        return radius;
    }
    
    public void setRadius(double radius){
        this.radius = radius;
    }
    
    public double getStartAngle(){
        return startAngle;
    }
    
    public void setStartAngle(double startAngle){
        this.startAngle = startAngle;
    }
    
    public boolean isClockwise(){
        return clockwise;
    }
    
    public void setClockwise(boolean clockwise){
        this.clockwise = clockwise;
    }

    @Override
    public Object getCopy() {
        return new CirclePath(radius,startAngle,clockwise);
    }

    

    @Override
    public void buildPath(String[] params) {//radius, strt angle, clockwise
        System.out.println(Arrays.toString(params));
        radius = Double.parseDouble(params[0]);
        startAngle = Math.toRadians(Double.parseDouble(params[1]));
        clockwise = Boolean.parseBoolean(params[2]);
    }

    @Override
    public String[] getParameters() {
        return new String[]{Double.toString(radius),Double.toString(Math.toDegrees(startAngle)),Boolean.toString(clockwise)};
    }

    @Override
    public double xNormalVector(double time) {
        double ans = -Math.sin((time/radius) + startAngle);
        if (clockwise){
            ans = -ans;
        }
        return ans;
    }

    @Override
    public double yNormalVector(double time) {
        double ans = Math.cos((time/radius) + startAngle);//test
        if (clockwise){
            ans = -ans;
        }
        return ans;
    }
    
    
    @Override
    public JPanel getPanel(JFrame frame) {
        return new EditPanel(frame);
    }

    @Override
    public void draw(Graphics g) {
        //picture?
    }
    
    
    
    private class EditPanel extends JPanel implements ActionListener, DocumentListener{//impliments listener?
        
        private JFrame frame;
        
        private JPanel radiusPanel;
        private JPanel startAnglePanel;
        
        private JLabel radiusLabel;
        private JLabel startAngleLabel;
        private JTextField radiusTextField;
        private JTextField startAngleTextField;
        private JCheckBox clockwiseCheckBox;
        
        public EditPanel(JFrame frame){
            this.frame = frame;
            //System.out.println(this);
            radiusPanel = new JPanel();
            startAnglePanel = new JPanel();
            
            radiusLabel = new JLabel("Radius:");
            startAngleLabel = new JLabel("Start Angle:");
            radiusTextField = new JTextField(Double.toString(radius));
            radiusTextField.getDocument().addDocumentListener(this);
            startAngleTextField = new JTextField(Double.toString(Math.toDegrees(startAngle)));
            startAngleTextField.getDocument().addDocumentListener(this);
            clockwiseCheckBox = new JCheckBox("Clockwise",clockwise);
            clockwiseCheckBox.addActionListener(this);
            clockwiseCheckBox.setActionCommand("clockwise");
            
            radiusPanel.add(radiusLabel);
            radiusPanel.add(radiusTextField);
            startAnglePanel.add(startAngleLabel);
            startAnglePanel.add(startAngleTextField);
            
            add(radiusPanel);
            add(startAnglePanel);
            add(clockwiseCheckBox);
            
            //set size elsewhere?
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        }
        
        private void updateParameters(){
            try{
                radius = Double.parseDouble(radiusTextField.getText());
            }
            catch(NumberFormatException e){
                //nothing
            }
            try{
                startAngle = Math.toRadians(Double.parseDouble(startAngleTextField.getText()));
            }
            catch(NumberFormatException e){
                //nothing
            }
            frame.repaint();
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("clockwise")){
                clockwise = clockwiseCheckBox.isSelected();
            }
            frame.repaint();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateParameters();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateParameters();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateParameters();
        }
        
    }

    
    
}
