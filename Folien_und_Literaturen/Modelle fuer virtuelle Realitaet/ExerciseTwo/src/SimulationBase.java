import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public abstract class SimulationBase extends JPanel implements ActionListener {
	// frames per second
	static protected int REPAINT_RATE = 30;
	
	// there is problem, that the length in real world (in m) does not match the unit pixels which is used when we are drawing the rods. So we use this factor, which means 1 m = LENGTH_FACTOR * 1 pixel
	static protected int LENGTH_FACTOR = 500;
	static protected String LENGTH_FACTOR_TOOL_TIP = "Since we cannot use the unit meter to draw the simulated models, we need a conversion from meter to the screen unit in pixel. 1m = LENGTH_FACTOR * 1 pixel.";
	
	// gravitational acceleration
	static protected double g = 9.81;
	
	// step size in s
	protected double dt = 0.05;
	
	// timer to recalculate 
	protected Timer dtTimer;	
	
	// timer to recalculate 
	protected Timer repaintTimer;	
	
	protected JFormattedTextField txtLengthFactor;
	protected JButton btnConfirm;
	
	protected double degreeToRadian(double angle) {
		return angle/360.0 * 2 * Math.PI;
	}
	
	protected void addTextField(JPanel panel, String description, JFormattedTextField textField, Double value) {
		final int textFielHeight = 25; 
		
		JLabel lbl = new JLabel(description);
		lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(lbl);
		
		textField.setValue(value);
		textField.setMaximumSize(new Dimension(Short.MAX_VALUE, textFielHeight));
		panel.add(textField);
		
		panel.add(Box.createRigidArea(new Dimension(0,5)));
	}

}
