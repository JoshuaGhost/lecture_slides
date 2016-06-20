import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.vecmath.Vector2d;


@SuppressWarnings("serial")
public class FluidSimulation extends SimulationBase {

	// in pixels
	static private int CEILING_HEIGHT = 10;
	
	static private int START_PARTICLE_NUM = 100;	
	// drop radius in m
	static private double START_RADIUS = 3;
	// fraction of fixed particles 
	static private double FIXED_PARTICLES = 0.05;
	
	static protected int LENGTH_FACTOR = 50;
	
	private double radius;
	
	private double m;
	
	private double damping = 0;
	
	private int numOfParticles;
	private int numOfFixedPart;
	private double fractionOfFixedParticles;
	// particle positions
	private Vector2d[] p;
	// particle velocities
	private Vector2d[] v;
	
	// some GUI elements
	JFormattedTextField txtNumOfPart;
	JFormattedTextField txtFixedPartFrac; 
	JFormattedTextField txtStepSize;
	JFormattedTextField txtMass;
	JFormattedTextField txtDropRadius;
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
//	        int h = getHeight();
	        			
	        // draw the ceiling
			g2d.setPaint(Color.gray);
	        g2d.fillRect(0, 0, w, CEILING_HEIGHT);

	        g2d.setPaint(Color.black);
	        
	        for (int i = 0; i < numOfParticles; i++) {
	        	g2d.fillOval((int) (w/2 + p[i].x * LENGTH_FACTOR), (int) (CEILING_HEIGHT + p[i].y * LENGTH_FACTOR), 2, 2);
	        }
	        
		}		
	}
	
	private Canvas canvas;
	
	public FluidSimulation() {		
		this.numOfParticles = START_PARTICLE_NUM;
		this.radius = START_RADIUS;
		this.fractionOfFixedParticles = FIXED_PARTICLES;		
		
		this.initialDistribution();
		
		// calculate initial mass
		// one liter water weights about 1kg and has a volume of about 0.01m^3
		// our hemisphere ha the volume 1/2 * 4/3 * PI * r^3
		m = 2.0/3.0 * Math.PI * Math.pow(radius, 3) / (0.01 * numOfParticles);
		
		this.setLayout(new BorderLayout());
		
		canvas = new Canvas();
		this.add(canvas, BorderLayout.CENTER);
		this.add(this.initControlComponents(), BorderLayout.WEST);
		
		dtTimer = new Timer((int) (dt*1000), (ActionListener) this);
		dtTimer.start();
		
		repaintTimer = new Timer(1000 / REPAINT_RATE, (ActionListener) this);
		repaintTimer.start();
	}
	
	private void initialDistribution() {	
		this.numOfFixedPart = (int) (fractionOfFixedParticles * numOfParticles);
		
		p = new Vector2d[numOfParticles];
		v = new Vector2d[numOfParticles];
		// init zero-velocities
		for (int i = 0; i < v.length; i++) {
			v[i] = new Vector2d();
		}
		
		int numOfArrangedParticles = numOfParticles - numOfFixedPart;
		
		// see the description in my exercise sheet to understand this algorithm
		int n = 0;
		int res;
		int res_old = 0;
		
		// FIXME: might end in an infinite loop -> program is freezing
		while (true) {
			res = 0;
			
			for (int j = 0; j <= n; j++) {
				res += 2* Math.sqrt(n*n - j*j);
			}
			res += n + 1;
			
			if (res > numOfArrangedParticles) {
				if (n > 0 && Math.abs(res_old - numOfArrangedParticles) < Math.abs(res - numOfArrangedParticles)) {
					n--;
				}
				break;
			}
			
			res_old = res;
			n++;
		}
		
		// arrange particles in a grid
		int count = 0;
		int diff;
		int n_j;
		
		for (int j = 0; j <= n; j++) {
			n_j = (int) Math.sqrt(n*n-j*j);
			if (count >= numOfArrangedParticles) {
				break;
			}
			if (count + 2*n_j + 1 > numOfArrangedParticles) {
				diff = numOfArrangedParticles - count;
				// the remaining number of particles is even
				if (diff % 2 == 0) {
					// remove one
					numOfArrangedParticles--;
					numOfFixedPart++;
					diff--;
				}
				n_j = diff/2;
			}
			
			for (int i = -n_j; i <= n_j; i++) {
				p[count] = new Vector2d(i * radius / n, (j+1) * radius / n);
				count++;
			}
		}
		
		if ( count < numOfArrangedParticles) {
			diff = numOfArrangedParticles - count;
			numOfArrangedParticles -= diff;
			numOfFixedPart += diff;
		}
		
		double dr;
		if (numOfFixedPart > 1) {
			dr = 2 * radius / (numOfFixedPart-1);
		} else {
			dr = radius;
		}
		
		for (int i = 0; i < numOfFixedPart; i++) {
			p[count + i] = new Vector2d(-radius + i*dr, 0);
		}
	}
	
	
	private void calculateValocities() {	
		int numOfMovableParticles = numOfParticles - numOfFixedPart;
		Vector2d vectorialDistance;
		Vector2d temp_a;
		// gravitational acceleration
		Vector2d g_a = new Vector2d(0, 1);
		g_a.scale(g);
		double distance;
		
		Vector2d[] temp_v = new Vector2d[numOfMovableParticles];		
		System.arraycopy( v, 0, temp_v, 0, numOfMovableParticles);
		
		for (int i = 0; i < numOfMovableParticles; i++) {
			temp_v[i].scale(1 - damping);
			temp_a = new Vector2d();
			
			for (int j = 0; j < numOfParticles; j++) {
				if (i == j) continue;
				
				vectorialDistance = new Vector2d(p[j]);
				vectorialDistance.sub(p[i]);
				distance = vectorialDistance.length();
				
				if (distance == 0) {
					System.out.println("ERROR");
					continue;
				}
				
				vectorialDistance.scale(20 / Math.pow(distance, 4) - 8 / Math.pow(distance, 6));
				if ( j > numOfMovableParticles) {
					vectorialDistance.scale(1.75);
				}
				
				temp_a.add(vectorialDistance);	
			}	
			
			/*for (int j = numOfMovableParticles; j < numOfParticles; j++) {
				vectorialDistance = new Vector2d(p[j]);
				vectorialDistance.sub(p[i]);
				distance = vectorialDistance.length();
				
				if (distance == 0) {
					System.out.println("ERROR2");
					continue;
				}
				
				// 1.75 * 20 = 35 & 1.75 * 8 = 14
				vectorialDistance.scale(35 / Math.pow(distance, 4) - 14 / Math.pow(distance, 6));
				temp_a.add(vectorialDistance);					
			}	*/
			
			temp_a.scale(1/m);
			temp_a.add(g_a);
			temp_a.scale(dt);
			temp_v[i].add(temp_a);
		}
		
		System.arraycopy( temp_v, 0, v, 0, numOfMovableParticles);	
	}
	
	private void calculatePositions() {
		Vector2d temp;
		for (int i = 0; i < numOfParticles - numOfFixedPart; i++) {
			temp = new Vector2d(v[i]);		
			temp.scale(dt);
			p[i].add(temp);	
		}
	}
	
	private JPanel initControlComponents() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		panel.add(Box.createVerticalGlue());
		
		txtNumOfPart = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtFixedPartFrac = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStepSize = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtMass = new JFormattedTextField(DecimalFormat.getInstance(Locale.US));
		txtDropRadius = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtDamping = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLengthFactor = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		
		this.addTextField(panel, "Number of particles:", txtNumOfPart, (double) numOfParticles);
		this.addTextField(panel, "Fraction of fixed particles:", txtFixedPartFrac, this.fractionOfFixedParticles);
		this.addTextField(panel, "Step size (dt) in s:", txtStepSize, dt);
		this.addTextField(panel, "Particle mass (m):", txtMass, m);
		this.addTextField(panel, "Drop radius:", txtDropRadius, radius);
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
			this.calculateValocities();
			this.calculatePositions();
		}
		
		if(e.getSource() == repaintTimer) {
			canvas.repaint();
		}
		
		if(e.getSource() == btnConfirm) {
			try {
				dtTimer.stop();
				repaintTimer.stop();
				
				this.dt = Double.parseDouble(this.txtStepSize.getText().replace(",", ""));		
				dtTimer = new Timer((int) (dt*1000), (ActionListener) this);
				
				
				this.radius = Double.parseDouble(this.txtDropRadius.getText().replace(",", ""));
				this.damping = Double.parseDouble(this.txtDamping.getText().replace(",", ""));
				if(this.damping < 0 || this.damping > 1) {
					this.damping = 0;
					txtDamping.setValue(damping);
					System.out.println("damping has to be a number between 0 and 1");
				}
				
				this.numOfParticles = (int) Double.parseDouble(this.txtNumOfPart.getText().replace(",", ""));
				this.fractionOfFixedParticles = Double.parseDouble(this.txtFixedPartFrac.getText().replace(",", ""));
				if(this.fractionOfFixedParticles < 0 || this.fractionOfFixedParticles > 1) {
					this.fractionOfFixedParticles = FIXED_PARTICLES;
					txtFixedPartFrac.setValue(fractionOfFixedParticles);
					System.out.println("Fraction of fixed particles has to be a number between 0 and 1");
				}
				
				this.m = Double.parseDouble(this.txtMass.getText().replace(",", ""));
				
//				m = 2.0/3.0 * Math.PI * Math.pow(radius, 3) / (0.01 * numOfParticles);
//				txtMass.setValue(m);
				this.initialDistribution();
				
				LENGTH_FACTOR = (int) Double.parseDouble(this.txtLengthFactor.getText().replace(",", ""));	
				
				repaintTimer.start();
				dtTimer.start();
				
			} catch (Exception except) {
				except.printStackTrace();
			}
		}
		
	}
}
