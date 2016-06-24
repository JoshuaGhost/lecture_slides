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
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class SimplePendulum extends SimulationBase {
	
	static private int OBJ_LENGTH = 20;
	
	static private double START_ANGLE = 30;
	static private double START_VELOCITY = 0;
	
	// mass
	//private double m = 2;
	
	private double damping = 0;
	
	// rod length
	private double l = 1;
	
	private double angle;
	private double velocity;
	
	// some GUI elements
	JFormattedTextField txtStartAngle;
	JFormattedTextField txtStartVelocity; 
	JFormattedTextField txtStepSize;
	//JFormattedTextField txtMass;
	JFormattedTextField txtLength;
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
	        
	        //double phi = -Math.PI/2.0 + degreeToRadian(angle);
	        double phi = -Math.PI/2.0 + angle;
	        
	        double len = LENGTH_FACTOR * l;
	        
	        int x1 = w/2;
	        int y1 = h/4;
	        
	        int x2 = (int) (x1 + len * Math.cos(phi));
	        int y2 = (int) (y1 - len * Math.sin(phi));
	        
	        g2d.setPaint(Color.lightGray);
	        g2d.draw(new Line2D.Double(x1, y1, x1, y1 + len));
			
			g2d.setPaint(Color.gray);
			for (int i = 0; i <= 5; i++) {
				g2d.draw(new Line2D.Double(x1 - 2.4*OBJ_LENGTH + i* 0.2 * 4 * OBJ_LENGTH, y1 - 0.4 * OBJ_LENGTH, x1 - 2*OBJ_LENGTH + i* 0.2 * 4 * OBJ_LENGTH, y1));
			}			
	        g2d.draw(new Line2D.Double(x1 - 2*OBJ_LENGTH, y1, x1 + 2*OBJ_LENGTH, y1));

	        g2d.setPaint(Color.black);
	        
	        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
	        
	        g2d.fill(new Rectangle2D.Double(x2 - OBJ_LENGTH/2, y2, OBJ_LENGTH, OBJ_LENGTH));   
		}		
	}
	
	private Canvas canvas;
	
	public SimplePendulum() {		
		//angle = START_ANGLE;
		angle = degreeToRadian(START_ANGLE);
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
		return (1 - damping) * velocity + dt * (- g/l * angle);
	}
	
	private double getAngle() {
		return angle + dt * velocity;
	}
	
	private JPanel initControlComponents() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		panel.add(Box.createVerticalGlue());
		
		txtStartAngle = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStartVelocity = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStepSize = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		//txtMass = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLength = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtDamping = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLengthFactor = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLengthFactor.setToolTipText(LENGTH_FACTOR_TOOL_TIP);
		
		this.addTextField(panel, "Initial Angle:", txtStartAngle, START_ANGLE);
		this.addTextField(panel, "Initial Velocity:", txtStartVelocity, START_VELOCITY);
		this.addTextField(panel, "Step size (dt) in s:", txtStepSize, dt);
		//this.addTextField(panel, "Mass (m):", txtMass, m);
		this.addTextField(panel, "Rod length:", txtLength, l);
		this.addTextField(panel, "Damping:", txtDamping, damping);
		this.addTextField(panel, "Length factor:", txtLengthFactor, (double) LENGTH_FACTOR);
		
		
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
	        angle = this.getAngle();   
		}
		
		if(e.getSource() == repaintTimer) {
			canvas.repaint();
		}
		
		if(e.getSource() == btnConfirm) {
			try {
				dtTimer.stop();
				
				this.dt = Double.parseDouble(this.txtStepSize.getText().replace(",", ""));		
				dtTimer = new Timer((int) (dt*1000), (ActionListener) this);
				
				//this.m = Double.parseDouble(this.txtMass.getText().replace(",", ""));
				this.l = Double.parseDouble(this.txtLength.getText().replace(",", ""));
				this.damping = Double.parseDouble(this.txtDamping.getText().replace(",", ""));
				if(this.damping < 0 || this.damping > 1) {
					this.damping = 0;
					txtDamping.setValue(damping);
					System.out.println("damping has to be a number between 0 and 1");
				}
				
				//this.angle = Double.parseDouble(this.txtStartAngle.getText().replace(",", ""));
				this.angle = degreeToRadian(Double.parseDouble(this.txtStartAngle.getText().replace(",", "")));
				this.velocity = Double.parseDouble(this.txtStartVelocity.getText().replace(",", ""));	
				
				LENGTH_FACTOR = (int) Double.parseDouble(this.txtLengthFactor.getText().replace(",", ""));	
				
				dtTimer.start();
				
			} catch (Exception except) {
				except.printStackTrace();
			}
		}
		
	}
}
