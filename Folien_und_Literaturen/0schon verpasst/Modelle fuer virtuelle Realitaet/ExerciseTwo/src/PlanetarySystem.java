import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.View;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

@SuppressWarnings("serial")
public class PlanetarySystem extends SimulationBase {
	
	private static double LENGTH_FACTOR = 1;
	
	// Gravitational constant
	private static double G = 6.673 * Math.pow(10, -11);
	
	// AE in m
	private static float AE = 149597870.7f * 1000;

	/*
	 * Indices:
	 * 0 - Sun
	 * 1 - Mercury
	 * 2 - Venus
	 * 3 - Earth
	 * 4 - Mars
	 * 5 - Jupiter
	 * 6 - Saturn
	 * 7 - Uranus
	 * 8 - Neptune
	 */
	// radius in m
	private static float[] R = {
		696342f * 1000, 
		2439.7f * 1000, 
		6051.8f * 1000,
		6371f * 1000,
		3389.5f * 1000,
		69911f * 1000,
		58232f * 1000,
		25362f * 1000,
		24622f * 1000};
	// distance to sun in m
	private static float[] D = {
		0f,
		0.387098f * AE,
		0.723327f * AE,
		1f * AE,
		1.523679f * AE,
		5.204267f * AE,
		9.5820172f * AE,
		20.095371f * AE,
		30.070900f * AE};
	// mass in kg
	private static double[] M = {
		1.9884 * Math.pow(10, 30), 
		3.3022 * Math.pow(10, 23),
		4.8676 * Math.pow(10, 24),
		5.974 * Math.pow(10, 24),	
		6.4185 * Math.pow(10, 23),
		1.8986 * Math.pow(10, 27),
	    5.6846 * Math.pow(10, 26),
	    8.6810 * Math.pow(10, 25),
	    1.0243 * Math.pow(10, 26)};
	// velocity in m/s
	private static float[] V = {
		0, 
		47362,
		35020,
		11186,
		24077,
		13070,
		9690,
		6800,
		5430};
	// scale factor for each object
	private static float[] SCALE_FACTOR = {
		10, 
		100,
		100,
		100,
		100,
		100,
		100,
		100,
		100};
	private static String[] textures = {
		"textures/sun.jpeg",
		"textures/mercury.jpeg",
		"textures/venus.jpeg",
		"textures/earth.jpeg",
		"textures/mars.jpeg",
		"textures/jupiter.jpeg",
		"textures/saturn.jpeg",
		"textures/uranus.jpeg",
		"textures/neptune.jpeg"
	};

	// number of astronomical objects (sun + planets)
	private static int NUM_ASTRO_OBJ = 9;
	
	// start position for all planets
	private static Vector2f[] START_POS = {
		new Vector2f(D[0], 0),
		new Vector2f(D[1], 0),
		new Vector2f(D[2], 0),
		new Vector2f(D[3], 0),
		new Vector2f(D[4], 0),
		new Vector2f(D[5], 0),
		new Vector2f(D[6], 0),
		new Vector2f(D[7], 0),
		new Vector2f(D[8], 0)};
	// start velocity for all planets
	private static Vector2f[] START_VEL = {
		new Vector2f(0, V[0]),
		new Vector2f(0, V[1]),
		new Vector2f(0, V[2]),
		new Vector2f(0, V[3]),
		new Vector2f(0, V[4]),
		new Vector2f(0, V[5]),
		new Vector2f(0, V[6]),
		new Vector2f(0, V[7]),
		new Vector2f(0, V[8])};
	
	
	private double dt_y;
	
	// current position of planets
	private Vector2f[] p;
	// current velocity of planets
	private Vector2f[] v;
	
	private double damping = 0;
	
	private boolean drawOrbit = true;
	
	// some GUI elements
	private JFormattedTextField txtScaleSun;
	private JFormattedTextField txtScaleMercury;
	private JFormattedTextField txtScaleVenus;
	private JFormattedTextField txtScaleEarth;
	private JFormattedTextField txtScaleMars;
	private JFormattedTextField txtScaleJupiter;
	private JFormattedTextField txtScaleSaturn;
	private JFormattedTextField txtScaleUranus;
	private JFormattedTextField txtScaleNeptune;
	private JFormattedTextField txtStepSize;
	private JFormattedTextField txtDamping;
	private JCheckBox cbDrawOrbit;

	public class Canvas extends Canvas3D {
		public SimpleUniverse _simple_u;
		public BranchGroup _scene;
		public TransformGroup _transform;
		private Transform3D _current_transformation;

		TransformGroup[] tg;
		Transform3D[] t;

		public Canvas() {
			super(SimpleUniverse.getPreferredConfiguration());

			setBackground(Color.black);

			_simple_u = new SimpleUniverse(this);
			_simple_u.getViewingPlatform().setNominalViewingTransform();
			_simple_u.getViewer().getView().setBackClipDistance(100);
			_simple_u.getViewer().getView().setFrontClipDistance(0.005);
		
			_scene = null;
			_transform = null;

			this.getView().setTransparencySortingPolicy(
					View.TRANSPARENCY_SORT_GEOMETRY);

			_current_transformation = new Transform3D();
			
			tg = new TransformGroup[NUM_ASTRO_OBJ];
			t = new Transform3D[NUM_ASTRO_OBJ];

			createScene();
		}

		public void createScene() {
			if (_scene != null) {
				// store old transformation matrix
				_transform.getTransform(_current_transformation);
				_scene.detach();
			}

			_scene = new BranchGroup();
//			_scene.setBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 10000.0));
			_scene.setCapability(BranchGroup.ALLOW_DETACH);

			// ------ Mouse Rotation and Zoom ----------------
			_transform = new TransformGroup();
//			_transform.setBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 10000.0));
			_transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			_transform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

			_scene.addChild(_transform);

			MouseRotate myMouseRotate = new MouseRotate() {
				public void transformChanged(Transform3D transform) {
					// createScene();
				}
			};
			myMouseRotate.setTransformGroup(_transform);
			myMouseRotate.setSchedulingBounds(new BoundingSphere());
			_transform.addChild(myMouseRotate);

			MouseWheelZoom myMouseZoom = new MouseWheelZoom();
			myMouseZoom.setTransformGroup(_transform);
			myMouseZoom.setSchedulingBounds(new BoundingSphere());
			_transform.addChild(myMouseZoom);

			// restore old transformation matrix
			_transform.setTransform(_current_transformation);
			
			// ------ Background ----------------

			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
					0.0), 100.0);
			Background bg = new Background();
			bg.setApplicationBounds(bounds);
			BranchGroup backGeoBranch = new BranchGroup();
			Sphere sphereObj = new Sphere(1.1f, Sphere.GENERATE_NORMALS
					| Sphere.GENERATE_NORMALS_INWARD
					| Sphere.GENERATE_TEXTURE_COORDS, 45);
			Appearance backgroundApp = sphereObj.getAppearance();
			backGeoBranch.addChild(sphereObj);
			bg.setGeometry(backGeoBranch);

			TextureLoader tex = new TextureLoader("textures/milkyWay.jpeg",
					new String("RGB"), this);
			if (tex != null) {
				Texture t = tex.getTexture();
				t.setBoundaryModeS(Texture.WRAP);
				t.setBoundaryModeT(Texture.WRAP);

				// Set up the texture attributes
				// could be REPLACE, BLEND or DECAL instead of MODULATE
				TextureAttributes texAttr = new TextureAttributes();
				texAttr.setTextureMode(TextureAttributes.MODULATE);

				backgroundApp.setTexture(t);
				backgroundApp.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.7f));
				// backgroundApp.setTextureAttributes(texAttr);


				TexCoordGeneration texGen = new TexCoordGeneration(
						TexCoordGeneration.OBJECT_LINEAR,
						TexCoordGeneration.TEXTURE_COORDINATE_2, 
						new Vector4f(2.0f, 0 , 0.5f, 0),
						new Vector4f(0, 2.0f, 0, 0), new Vector4f(0, 0, 0, 0));

			    // assign the TexCoordGeneration to the Appearance
			    backgroundApp.setTexCoordGeneration(texGen);

			}
			_scene.addChild(bg);


			// ------ Sun ----------------

			_transform.addChild(new Sphere(scaleObjectSize(R[0], 0), Sphere.GENERATE_NORMALS + Sphere.GENERATE_TEXTURE_COORDS, 25,	createAppearance(0)));
			
			PointLight light = new PointLight();
			light.setEnable( true );
			light.setColor( new Color3f(Color.WHITE) );
			light.setPosition( new Point3f( 0.0f, 0.0f, 0.0f ) );
			light.setInfluencingBounds(bounds);
			_transform.addChild(light);
			
			// ------ Planets ----------------
			
			for (int i = 1; i < NUM_ASTRO_OBJ; i++) {
				if (drawOrbit) {
					this.plotCircle(_transform, scaleDistance(D[i]), Color.WHITE, .8f);
				}

				tg[i] = new TransformGroup();
				tg[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				tg[i].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				t[i] = new Transform3D();
	
				Vector3f vector = new Vector3f(scaleDistance(D[i]), .0f, .0f);
	
				t[i].setTranslation(vector);
	
				tg[i].setTransform(t[i]);
	
				tg[i].addChild(new Sphere(scaleObjectSize(R[i], i), Sphere.GENERATE_NORMALS + Sphere.GENERATE_TEXTURE_COORDS, 25,
						createAppearance(i)));
	
				_transform.addChild(tg[i]);					
			}
			
			// saturn rings
			int rings = 40;
			double start = 2.5 * R[6];
			double end = 3.5 * R[6];
			double dist = (start - end) / rings;
			for (int i = 0; i < rings; i++) {
				this.plotCircle(tg[6], scaleObjectSize((float) (start + i*dist), 6), new Color(255,204,153), .2f);
			}
			

			_scene.compile();
			_simple_u.addBranchGraph(_scene);
		}

		public void update_view() {
			Vector3f vector;
			
			for (int i = 1; i < NUM_ASTRO_OBJ; i++) {
				vector = new Vector3f(scaleDistance(p[i].x), scaleDistance(p[i].y), .0f);	
				t[i].setTranslation(vector);
				tg[i].setTransform(t[i]);
			}

		}
		
		Appearance createAppearance(int obj) { 
			Appearance appear = new Appearance();
			
			Material material = new Material();
			if (obj != 0) {
				material.setSpecularColor(new Color3f(Color.WHITE));
				material.setDiffuseColor(new Color3f(Color.WHITE));
				appear.setMaterial(material);
			} 
			

			TextureLoader loader = new TextureLoader(textures[obj], "RGB", new Container());
			Texture texture = loader.getTexture();

			// Set up the texture attributes
			// could be REPLACE, BLEND or DECAL instead of MODULATE
			TextureAttributes texAttr = new TextureAttributes();
			texAttr.setTextureMode(TextureAttributes.MODULATE);

			appear.setTexture(texture);
			appear.setTextureAttributes(texAttr);

			return appear;
	  }
		
		private float scaleDistance(float l) {
			return (float) ((l/AE) * LENGTH_FACTOR);
		}
		
		private float scaleObjectSize(float l , int obj) {
			return (float) ((l/AE) * LENGTH_FACTOR * SCALE_FACTOR[obj]);
		}

		private void plotCircle(TransformGroup transform, double radius, Color col, float alpha) {
			LinkedList<Point3f> pointsList = new LinkedList<Point3f>();

			double phi = 0;
			pointsList.add(new Point3f((float) (radius * Math.cos(phi)),
					(float) (radius * Math.sin(phi)), 0.0f));

			while (phi < Math.PI * 2) {
				phi = phi + 0.01;
				pointsList.add(new Point3f((float) (radius * Math.cos(phi)),
						(float) (radius * Math.sin(phi)), 0.0f));
				pointsList.add(pointsList.getLast());
			}

			pointsList.add(pointsList.getFirst());

			if (pointsList.size() > 0) {

				Point3f[] points = new Point3f[pointsList.size()];
				points = pointsList.toArray(points);

				LineArray lineArray = new LineArray(pointsList.size(),
						GeometryArray.COORDINATES);
				lineArray.setCoordinates(0, points);
				
				Color3f color = new Color3f(col);
		        ColoringAttributes colorAtt = new ColoringAttributes(color, ColoringAttributes.SHADE_FLAT);	  
		        Appearance ap = new Appearance();
				ap.setColoringAttributes(colorAtt);	
				ap.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, alpha));

				transform.addChild(new Shape3D(lineArray, ap));	
	        }
		}
	}

	private Canvas canvas;

	public PlanetarySystem() {
		p = new Vector2f[NUM_ASTRO_OBJ];
		v = new Vector2f[NUM_ASTRO_OBJ];
		
		for (int i = 0; i < NUM_ASTRO_OBJ; i++) {
			p[i] = new Vector2f(START_POS[i]);
			v[i] = new Vector2f(START_VEL[i]);
		}
		
//		v[3].y = (float) Math.sqrt(G * M[0] / D[3]);	
		
		// calculating start velocities
		Vector2f vectorialDistance;
		Vector2f gravitationalForce;
		double distanceSquared;
		for (int i = 1; i < NUM_ASTRO_OBJ; i++) {
			gravitationalForce = new Vector2f();
			
			for (int j = 0; j < NUM_ASTRO_OBJ; j++) {
				if (i == j) continue;
				
				vectorialDistance = new Vector2f(p[j]);
				vectorialDistance.sub(p[i]);
				distanceSquared = vectorialDistance.lengthSquared();
				
				vectorialDistance.normalize();
				vectorialDistance.scale((float) (G * M[j] / distanceSquared));
				gravitationalForce.add(vectorialDistance);	
			}	
			
			v[i].x = 0;
			v[i].y = (float) Math.sqrt(gravitationalForce.length() * D[i]);
		}
		
		// translate dt in years
		dt_y = dt * 60 * 60 * 24;

		this.setLayout(new BorderLayout());

		canvas = new Canvas();
		this.add(canvas, BorderLayout.CENTER);

		 this.add(this.initControlComponents(), BorderLayout.WEST);

		dtTimer = new Timer((int) (dt * 1000), (ActionListener) this);
		dtTimer.start();

		repaintTimer = new Timer(1000 / REPAINT_RATE, (ActionListener) this);
		repaintTimer.start();
	}
	
	private void calculateVelocity() {
		Vector2f vectorialDistance;
		double distanceSquared;
		
		Vector2f[] temp_v = new Vector2f[NUM_ASTRO_OBJ];		
		System.arraycopy( v, 0, temp_v, 0, v.length );
		
		for (int i = 1; i < NUM_ASTRO_OBJ; i++) {
			temp_v[i].scale((float) (1 - damping));
			
			for (int j = 0; j < NUM_ASTRO_OBJ; j++) {
				if (i == j) continue;
				
				vectorialDistance = new Vector2f(p[j]);
				vectorialDistance.sub(p[i]);
				distanceSquared = vectorialDistance.lengthSquared();
				
				vectorialDistance.normalize();
				
				vectorialDistance.scale((float) (dt_y * (G * M[j] / distanceSquared)));
				temp_v[i].add(vectorialDistance);	
			}			
		}
		
		System.arraycopy( temp_v, 0, v, 0, v.length );	
	}
	
	private void calculatePosition() {
		Vector2f temp;
		for (int i = 1; i < NUM_ASTRO_OBJ; i++) {
			temp = new Vector2f(v[i]);		
			temp.scale((float) dt_y);
			p[i].add(temp);	
		}
	}
	
	private JPanel initControlComponents() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		panel.add(Box.createVerticalGlue());
		
		txtScaleSun = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtScaleMercury = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtScaleVenus = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtScaleEarth = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtScaleMars = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtScaleJupiter = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtScaleSaturn = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtScaleUranus = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtScaleNeptune = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtStepSize = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtDamping = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		txtLengthFactor = new JFormattedTextField(NumberFormat.getNumberInstance(Locale.US));
		
		this.addTextField(panel, "Scale size of Sun:", txtScaleSun, (double) SCALE_FACTOR[0]);
		this.addTextField(panel, "Scale size of Mercury:", txtScaleMercury, (double) SCALE_FACTOR[1]);
		this.addTextField(panel, "Scale size of Venus:", txtScaleVenus, (double) SCALE_FACTOR[2]);
		this.addTextField(panel, "Scale size of Earth:", txtScaleEarth, (double) SCALE_FACTOR[3]);
		this.addTextField(panel, "Scale size of Mars:", txtScaleMars, (double) SCALE_FACTOR[4]);
		this.addTextField(panel, "Scale size of Jupiter:", txtScaleJupiter, (double) SCALE_FACTOR[5]);
		this.addTextField(panel, "Scale size of Saturn:", txtScaleSaturn, (double) SCALE_FACTOR[6]);
		this.addTextField(panel, "Scale size of Uranus:", txtScaleUranus, (double) SCALE_FACTOR[7]);
		this.addTextField(panel, "Scale size of Neptune:", txtScaleNeptune, (double) SCALE_FACTOR[8]);
		this.addTextField(panel, "Step size (dt) in d:", txtStepSize, dt);
		txtStepSize.setToolTipText("One second in real time is equivalent to one day in this model");
		this.addTextField(panel, "Damping:", txtDamping, damping);
		this.addTextField(panel, "Length factor:", txtLengthFactor, (double) LENGTH_FACTOR);
		
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		
		cbDrawOrbit = new JCheckBox();
		cbDrawOrbit.setSelected(drawOrbit);
		cbDrawOrbit.setText("Draw Orbit");
		panel.add(cbDrawOrbit);
		
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
		if (e.getSource() == dtTimer) {
			this.calculateVelocity();
			this.calculatePosition();
		}

		if (e.getSource() == repaintTimer) {
			canvas.update_view();
		}
		
		if(e.getSource() == btnConfirm) {
			try {
				dtTimer.stop();
				
				this.dt = Double.parseDouble(this.txtStepSize.getText().replace(",", ""));		
				dtTimer = new Timer((int) (dt*1000), (ActionListener) this);
				
				SCALE_FACTOR[0] = (float) Double.parseDouble(this.txtScaleSun.getText().replace(",", ""));
				SCALE_FACTOR[1] = (float) Double.parseDouble(this.txtScaleMercury.getText().replace(",", ""));
				SCALE_FACTOR[2] = (float) Double.parseDouble(this.txtScaleVenus.getText().replace(",", ""));
				SCALE_FACTOR[3] = (float) Double.parseDouble(this.txtScaleEarth.getText().replace(",", ""));
				SCALE_FACTOR[4] = (float) Double.parseDouble(this.txtScaleMars.getText().replace(",", ""));
				SCALE_FACTOR[5] = (float) Double.parseDouble(this.txtScaleJupiter.getText().replace(",", ""));
				SCALE_FACTOR[6] = (float) Double.parseDouble(this.txtScaleSaturn.getText().replace(",", ""));
				SCALE_FACTOR[7] = (float) Double.parseDouble(this.txtScaleUranus.getText().replace(",", ""));
				SCALE_FACTOR[8] = (float) Double.parseDouble(this.txtScaleNeptune.getText().replace(",", ""));
				
				this.damping = Double.parseDouble(this.txtDamping.getText().replace(",", ""));
				if(this.damping < 0 || this.damping > 1) {
					this.damping = 0;
					txtDamping.setValue(damping);
					System.out.println("damping has to be a number between 0 and 1");
				}
				
				LENGTH_FACTOR = Double.parseDouble(this.txtLengthFactor.getText().replace(",", ""));	
				
				this.drawOrbit = cbDrawOrbit.isSelected();
				
				canvas.createScene();
				
				dtTimer.start();
				
			} catch (Exception except) {
				except.printStackTrace();
			}
		}
	}

}
