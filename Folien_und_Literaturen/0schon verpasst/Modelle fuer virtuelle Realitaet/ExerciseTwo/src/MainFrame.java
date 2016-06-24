import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	// radio buttons
	JRadioButton btnSpringPendulum;
	JRadioButton btnSimplePendulum;
	JRadioButton btnDoublePendulum;
	JRadioButton btnPlanetarySystem;
	JRadioButton btnFluidSimulation;
	
	public MainFrame() {
		this.setTitle("Second Exercise");
		this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Container pane = this.getContentPane();
        
        pane.setLayout(new BorderLayout());
        
        btnSpringPendulum = new JRadioButton("Spring pendulum");
        btnSpringPendulum.setMnemonic(KeyEvent.VK_S);
        btnSpringPendulum.addActionListener(this);
        btnSpringPendulum.setSelected(true);
        
        btnSimplePendulum = new JRadioButton("Simple pendulum");
        btnSimplePendulum.setMnemonic(KeyEvent.VK_I);
        btnSimplePendulum.addActionListener(this);

        btnDoublePendulum = new JRadioButton("Double pendulum");
        btnDoublePendulum.setMnemonic(KeyEvent.VK_D);
        btnDoublePendulum.addActionListener(this);
        
        btnPlanetarySystem = new JRadioButton("Planetary System");
        btnPlanetarySystem.setMnemonic(KeyEvent.VK_P);
        btnPlanetarySystem.addActionListener(this);
        
        btnFluidSimulation = new JRadioButton("Fluid Simulation");
        btnFluidSimulation.setMnemonic(KeyEvent.VK_F);
        btnFluidSimulation.addActionListener(this);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(btnSpringPendulum);
        group.add(btnSimplePendulum);
        group.add(btnDoublePendulum);
        group.add(btnPlanetarySystem);
        group.add(btnFluidSimulation);
        
        // panel for radio buttons
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        
        northPanel.add(btnSpringPendulum);
        northPanel.add(btnSimplePendulum);
        northPanel.add(btnDoublePendulum);    
        northPanel.add(btnPlanetarySystem);
        northPanel.add(btnFluidSimulation);
        
        pane.add(northPanel, BorderLayout.NORTH);

        pane.add(new SpringPendulum());
        
        this.pack();
        this.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
	        
            @Override
            public void run() {
            	MainFrame ex = new MainFrame();
                ex.setVisible(true);
            }
        });

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.getContentPane().remove(((BorderLayout) this.getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER));
		
		if (e.getSource() == this.btnSpringPendulum) {
			this.getContentPane().add(new SpringPendulum());
		} else if (e.getSource() == this.btnSimplePendulum) {
			this.getContentPane().add(new SimplePendulum());
		} else if (e.getSource() == this.btnDoublePendulum) {
			this.getContentPane().add(new DoublePendulum());
		} else if (e.getSource() == this.btnPlanetarySystem) {
			this.getContentPane().add(new PlanetarySystem());
		} else if (e.getSource() == this.btnFluidSimulation) {
			this.getContentPane().add(new FluidSimulation());
		}					
		
		this.revalidate();		
	}

}
