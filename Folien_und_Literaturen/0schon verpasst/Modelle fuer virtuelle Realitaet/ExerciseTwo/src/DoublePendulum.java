import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class DoublePendulum extends SimulationBase {
	
	static private int OBJ_DIAMETER = 10;
	
	static private double START_ANGLE_1 = 90;
	static private double START_VELOCITY_1 = 0;
	static private double START_ANGLE_2 = 90;
	static private double START_VELOCITY_2 = 0;	
	
	// length
	private double l1 = 0.5;
	private double l2 = 0.25;
	
	// mass
	private double m1 = 2;
	private double m2 = 2;
	
	private double damping = 0;
	
	private double angle1;
	private double velocity1;
	private double angle2;
	private double velocity2;
	
	private boolean useWikiEquations = false;
	
	// some GUI elements
	JFormattedTextField txtStartAngle1;
	JFormattedTextField txtStartVelocity1; 
	JFormattedTextField txtStartAngle2;
	JFormattedTextField txtStartVelocity2; 
	JFormattedTextField txtStepSize;
	JFormattedTextField txtLength1;
	JFormattedTextField txtLength2;
	JFormattedTextField txtMass1;
	JFormattedTextField txtMass2;
	JFormattedTextField txtDamping;
	JCheckBox cbUseWikiEquations;
	
	public class Canvas extends JPanel {
		public Canvas() {
			this.setBackground(Color.white);			
		}
		
		public void paint(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

	        g2d.setPaint(Color.black);

	        int w = getWidth();
	        int h = getHeight();
	        
	        double len1 = LENGTH_FACTOR * l1;
	        double len2 = LENGTH_FACTOR * l2;	        
	        
	        double phi1 = -Math.PI/2.0 + angle1;
	        double phi2 = -Math.PI/2.0 + angle2;
	        
	        int x1 = w/2;
	        int y1 = h/2;
	        
	        int x2 = (int) (x1 + len1 * Math.cos(phi1));
	        int y2 = (int) (y1 - len1 * Math.sin(phi1));
	        
	        int x3 = (int) (x2 + len2 * Math.cos(phi2));
	        int y3 = (int) (y2 - len2 * Math.sin(phi2));
	        
	        g2d.draw(new Line2D.Double(x1, y1, x2, y2));	        
	        g2d.fill(new Ellipse2D.Double(x2 - OBJ_DIAMETER/2, y2 - OBJ_DIAMETER/2, OBJ_DIAMETER, OBJ_DIAMETER));
	        g2d.draw(new Line2D.Double(x2, y2, x3, y3));
	        g2d.fill(new Ellipse2D.Double(x3 - OBJ_DIAMETER/2, y3 - OBJ_DIAMETER/2, OBJ_DIAMETER, OBJ_DIAMETER));
		}		
	}
	
	private Canvas canvas;
	
	public DoublePendulum() {		
		angle1 = degreeToRadian(START_ANGLE_1);
//		angle1 = START_ANGLE_1;
		velocity1 = START_VELOCITY_1;
		angle2 = degreeToRadian(START_ANGLE_2);
//		angle2 = START_ANGLE_2;
		velocity2 = START_VELOCITY_2;
		
		this.setLayout(new BorderLayout());
		
		canvas = new Canvas();
		this.add(canvas, BorderLayout.CENTER);
		this.add(this.initControlComponents(), BorderLayout.WEST);
		
		dtTimer = new Timer((int) (dt*1000), (ActionListener) this);
		dtTimer.start();
		
		repaintTimer = new Timer(1000 / REPAINT_RATE, (ActionListener) this);
		repaintTimer.start();
	}
	
	private double getVelocity1() {
		if (useWikiEquations) {
			return (1 - damping) * velocity1 + dt * getSecondDerivative1();
		} else {
			return (1 - damping) * velocity1 + dt * (- g/l1 * angle1 + m2/m1 * g/l2 * (angle2 - angle1));
		}
	}
	
	private double getVelocity2() {
		if (useWikiEquations) {
			return (1 - damping) * velocity2 + dt * getSecondDerivative2();
			
		} else {
			return (1 - damping) * velocity2 + dt * (- g/l2 * (angle2 - angle1));
		}
	}
	
	private double getAngle1() {
		return angle1 + dt * velocity1;
	}
	
	private double getAngle2() {
		return angle2 + dt * velocity2;
	}
	
	private double getSecondDerivative1() {
		return (-m2 * l2 * getSecondDerivative2() * Math.cos(angle1-angle2) - m2 * l2 * Math.pow(velocity2, 2) * Math.sin(angle1-angle2) - g * (m1+m2) * Math.sin(angle1))
				/ ((m1+m2) * l1);
	}
	
	private double getSecondDerivative2() {
		return (Math.pow(m2, 2) * l2 * Math.pow(velocity2, 2) * Math.sin(angle1-angle2) * Math.cos(angle1 - angle2) 
				+ g * m2 * (m1+m2) * Math.cos(angle1-angle2)*Math.sin(angle1) 
				+ m2 * (m1+m2) * l1 * Math.pow(velocity1, 2) * Math.sin(angle1-angle2) 
				- g * m2 * (m1+m2) * Math.sin(angle2))
				/ (- Math.pow(m2, 2) * Math.pow(Math.cos(angle1-angle2), 2) * l2 + m2 * (m1 + m2) * l2);
	}
	
	private JPanel initControlComponents() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		panel.add(Box.createVerticalGlue());
		
		txtStartAngle1 = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStartAngle2 = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStartVelocity1 = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStartVelocity2 = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStepSize = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLength1 = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLength2 = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtMass1 = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtMass2 = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtDamping = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLengthFactor = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLengthFactor.setToolTipText(LENGTH_FACTOR_TOOL_TIP);
		
		this.addTextField(panel, "Initial Angle phi_1:", txtStartAngle1, START_ANGLE_1);
		this.addTextField(panel, "Initial Angle phi_2:", txtStartAngle2, START_ANGLE_2);
		this.addTextField(panel, "Initial Velocity v_1:", txtStartVelocity1, START_VELOCITY_1);
		this.addTextField(panel, "Initial Velocity v_2:", txtStartVelocity2, START_VELOCITY_2);
		this.addTextField(panel, "Step size (dt) in s:", txtStepSize, dt);
		this.addTextField(panel, "Length (l_1):", txtLength1, l1);
		this.addTextField(panel, "Length (l_2):", txtLength2, l2);
		this.addTextField(panel, "Mass (m_1):", txtMass1, m1);
		this.addTextField(panel, "Mass (m_2):", txtMass2, m2);
		this.addTextField(panel, "Damping:", txtDamping, damping);
		this.addTextField(panel, "Length factor:", txtLengthFactor, (double) LENGTH_FACTOR);
		
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		
		cbUseWikiEquations = new JCheckBox();
		cbUseWikiEquations.setSelected(useWikiEquations);
		cbUseWikiEquations.setText("Use derivatives from wikipedia");
		panel.add(cbUseWikiEquations);
		
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setMnemonic(KeyEvent.VK_C);
		btnConfirm.addActionListener(this);
		panel.add(btnConfirm);

		panel.add(Box.createVerticalGlue());
		
		//this.getRootPane().setDefaultButton(btnConfirm);
		
		return panel;		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == dtTimer) {
			velocity1 = this.getVelocity1();
			velocity2 = this.getVelocity2();
	        angle1 = this.getAngle1();   
	        angle2 = this.getAngle2();  
		}
		
		if(e.getSource() == repaintTimer) {
			canvas.repaint();
		}
		
		if(e.getSource() == btnConfirm) {
			try {
				dtTimer.stop();
				
				this.dt = Double.parseDouble(this.txtStepSize.getText().replace(",", ""));		
				dtTimer = new Timer((int) (dt*1000), (ActionListener) this);
				
				this.m1 = Double.parseDouble(this.txtMass1.getText().replace(",", ""));
				this.m2 = Double.parseDouble(this.txtMass2.getText().replace(",", ""));
				this.l1 = Double.parseDouble(this.txtLength1.getText().replace(",", ""));
				this.l2 = Double.parseDouble(this.txtLength2.getText().replace(",", ""));
				this.damping = Double.parseDouble(this.txtDamping.getText().replace(",", ""));
				if(this.damping < 0 || this.damping > 1) {
					this.damping = 0;
					txtDamping.setValue(damping);
					System.out.println("damping has to be a number between 0 and 1");
				}
				
				this.angle1 = degreeToRadian(Double.parseDouble(this.txtStartAngle1.getText().replace(",", "")));
//				this.angle1 = Double.parseDouble(this.txtStartAngle1.getText().replace(",", ""));
				this.velocity1 = Double.parseDouble(this.txtStartVelocity1.getText().replace(",", ""));	
				this.angle2 = degreeToRadian(Double.parseDouble(this.txtStartAngle2.getText().replace(",", "")));
//				this.angle2 = Double.parseDouble(this.txtStartAngle2.getText().replace(",", ""));
				this.velocity2 = Double.parseDouble(this.txtStartVelocity2.getText().replace(",", ""));	
				
				LENGTH_FACTOR = (int) Double.parseDouble(this.txtLengthFactor.getText().replace(",", ""));	
				
				this.useWikiEquations = this.cbUseWikiEquations.isSelected();
				
				dtTimer.start();
				
			} catch (Exception except) {
				except.printStackTrace();
			}
		}
		
	}
}
