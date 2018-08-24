/*

 */
package paths;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//goes from origin to a point defined in polar coordinates, then back again

public class SimpleLinePath extends Path{
    
    private double angle;
    private double length;

    public SimpleLinePath(double angle, double length) {
        this.angle = angle;
        this.length = length;
    }

    @Override
    public Point2D.Double getPathPosition(double time) {
        double truncTime = time % (2 * length);
        if (truncTime > length){
            truncTime = -(truncTime - length) + length;
        }
        
        return new Point2D.Double(truncTime * Math.cos(angle),truncTime * Math.sin(angle));
    }
    
    public double getAngle(){
        return angle;
    }
    
    public void setRadius(double angle){
        this.angle = angle;
    }
    
    public double getLength(){
        return length;
    }
    
    public void setLength(double length){
        this.length = length;
    }

    @Override
    public Object getCopy() {
        return new SimpleLinePath(angle,length);
    }

    
    

    @Override
    public void buildPath(String[] params) {// angle in degrees, length
        angle = Math.toRadians(Double.parseDouble(params[0]));
        length = Double.parseDouble(params[1]);
    }

    @Override
    public String[] getParameters() {
        return new String[]{Double.toString(Math.toDegrees(angle)),Double.toString(length)};
    }

    @Override
    public double xNormalVector(double time) {
        double ans = Math.cos(angle);
        if (time % (2 * length) > length){
            ans = -ans;
        }
        return ans;
    }

    @Override
    public double yNormalVector(double time) {
        double ans = Math.sin(angle);
        if (time % (2 * length) > length){
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
    
    private class EditPanel extends JPanel implements DocumentListener{//class for this type of input?
        
        private JFrame frame;
        
        private JPanel anglePanel;
        private JPanel lengthPanel;
        
        private JLabel angleLabel;
        private JLabel lengthLabel;
        private JTextField angleTextField;
        private JTextField lengthTextField;
        
        public EditPanel(JFrame frame){
            this.frame = frame;
            
            anglePanel = new JPanel();
            lengthPanel = new JPanel();
            
            angleLabel = new JLabel("Angle");
            lengthLabel = new JLabel("Length");
            angleTextField = new JTextField(Double.toString(Math.toDegrees(angle)));
            angleTextField.getDocument().addDocumentListener(this);
            lengthTextField = new JTextField(Double.toString(length));
            lengthTextField.getDocument().addDocumentListener(this);
            
            anglePanel.add(angleLabel);
            anglePanel.add(angleTextField);
            lengthPanel.add(lengthLabel);
            lengthPanel.add(lengthTextField);
            
            add(anglePanel);
            add(lengthPanel);
            
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        }
        
        private void updateParameters(){
            try{
                angle = Math.toRadians(Double.parseDouble(angleTextField.getText()));
            }
            catch(NumberFormatException e){
                //nothing
            }
            try{
                length = Double.parseDouble(lengthTextField.getText());
            }
            catch(NumberFormatException e){
                //nothing
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
