import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class SpringPendulum extends SimulationBase {
	
	static private int OBJ_LENGTH = 20;
	
	// number of loops of the spring
	static private int LOOP_COUNT = 50;
	
	static private double START_DISTANCE = 0.5;
	static private double START_VELOCITY = 0;
	
	// mass
	private double m = 2;
	
	private double damping = 0;
	
	// spring constant
	private double k = 3;
	
	private double distance;
	private double velocity;
	
	// some GUI elements
	JFormattedTextField txtStartDistance;
	JFormattedTextField txtStartVelocity; 
	JFormattedTextField txtStepSize;
	JFormattedTextField txtMass;
	JFormattedTextField txtSpringConstant;
	JFormattedTextField txtDamping;
	
	public class Canvas extends JPanel {
		public Canvas() {
			this.setBackground(Color.white);			
		}
		
		public void paint(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			
			int w = getWidth();
	        int h = getHeight();
	        
	        double dist = LENGTH_FACTOR * distance;
			
			g2d.setPaint(Color.lightGray);
	        g2d.draw(new Line2D.Double(0, h/2 + OBJ_LENGTH/2, w, h/2 + OBJ_LENGTH/2));

	        g2d.setPaint(Color.black);
	        
	        //g2d.draw(new Line2D.Double(w/2, 0, w/2, h/2 + distance));
	        
	        g2d.fill(new Rectangle2D.Double(w/2 - OBJ_LENGTH/2, h/2 + dist, OBJ_LENGTH, OBJ_LENGTH));   
	        
	        ArrayList<QuadCurve2D> spring = new ArrayList<QuadCurve2D>();
	        
	        double section = (h/2 + dist) / (double) LOOP_COUNT;
	        int sign = -1;
	        for (int i = 0; i < LOOP_COUNT; i++) {
	        	spring.add(new QuadCurve2D.Float());
	        	spring.get(i).setCurve(w/2, i * section, w/2 + sign * 2 * OBJ_LENGTH, (i + 0.5) * section, w/2, (i+1) * section);
	        	g2d.draw(spring.get(i));
	        	sign = sign * -1;
	        }    
		}		
	}
	
	private Canvas canvas;
	
	public SpringPendulum() {		
		distance = START_DISTANCE;
		velocity = START_VELOCITY;
		
		this.setLayout(new BorderLayout());
		
		canvas = new Canvas();
		this.add(canvas, BorderLayout.CENTER);
		this.add(this.initControlComponents(), BorderLayout.WEST);
		
		dtTimer = new Timer((int) (dt*1000), (ActionListener) this);
		dtTimer.start();
		
		repaintTimer = new Timer(1000 / REPAINT_RATE, (ActionListener) this);
		repaintTimer.start();
	}
	
	private double getVelocity() {
		return (1 - damping) * velocity + dt * (- k/m * distance);
	}
	
	private double getDistance() {
		return distance + dt * velocity;
	}
	
	private JPanel initControlComponents() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		panel.add(Box.createVerticalGlue());
		
		txtStartDistance = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStartVelocity = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStepSize = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtMass = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtSpringConstant = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtDamping = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLengthFactor = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		
		this.addTextField(panel, "Initial Distance:", txtStartDistance, START_DISTANCE);
		this.addTextField(panel, "Initial Velocity:", txtStartVelocity, START_VELOCITY);
		this.addTextField(panel, "Step size (dt) in s:", txtStepSize, dt);
		this.addTextField(panel, "Mass (m):", txtMass, m);
		this.addTextField(panel, "Spring constant:", txtSpringConstant, k);
		this.addTextField(panel, "Damping:", txtDamping, damping);
		this.addTextField(panel, "Length factor:", txtLengthFactor, (double) LENGTH_FACTOR);
		txtLengthFactor.setToolTipText(LENGTH_FACTOR_TOOL_TIP);
		
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
			velocity = this.getVelocity();
	        distance = this.getDistance();   
		}
		
		if(e.getSource() == repaintTimer) {
			canvas.repaint();
		}
		
		if(e.getSource() == btnConfirm) {
			try {
				dtTimer.stop();
				
				this.dt = Double.parseDouble(this.txtStepSize.getText().replace(",", ""));		
				dtTimer = new Timer((int) (dt*1000), (ActionListener) this);
				
				this.m = Double.parseDouble(this.txtMass.getText().replace(",", ""));
				this.k = Double.parseDouble(this.txtSpringConstant.getText().replace(",", ""));
				this.damping = Double.parseDouble(this.txtDamping.getText().replace(",", ""));
				if(this.damping < 0 || this.damping > 1) {
					this.damping = 0;
					txtDamping.setValue(damping);
					System.out.println("damping has to be a number between 0 and 1");
				}
				
				this.distance = Double.parseDouble(this.txtStartDistance.getText().replace(",", ""));
				this.velocity = Double.parseDouble(this.txtStartVelocity.getText().replace(",", ""));	
				
				LENGTH_FACTOR = (int) Double.parseDouble(this.txtLengthFactor.getText().replace(",", ""));	
				
				dtTimer.start();
				
			} catch (Exception except) {
				except.printStackTrace();
			}
		}
		
	}
}
