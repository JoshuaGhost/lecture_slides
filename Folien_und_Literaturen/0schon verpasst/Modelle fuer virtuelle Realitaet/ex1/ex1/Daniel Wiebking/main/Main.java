package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Main {

	File file;
	FileWriter writer;
	
	public Main(){
	}

	public void initialize(){
		file = new File("out.csv");
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Main main = new Main();
		main.initialize();
		main.integrate();
		
		String result = "";
		if(main.stabilitätstest(new Expliziter())) {
			result = "ja";
		} else {
			result = "nein";
		}
		System.out.println("expliziter euler stabil: "+result);
		
		if(main.stabilitätstest(new HeunVerfahren())) {
			result = "ja";
		} else {
			result = "nein";
		}
		System.out.println("heun-verfahren stabil: "+result);
		
		if(main.stabilitätstest(new Impliziter())) {
			result = "ja";
		} else {
			result = "nein";
		}
		System.out.println("impliziter euler stabil: "+result);

	}
	public double integrator(double f, int k,  double dt) {
		return 0;
	}
	
	
	public void integrate(){
		double t0=0;
		double t1=10;
		double dt=0.01;
		double f=1.0;
		double y=1.0;
		double g=1.0;
		double z=1.0;
		double fx=1.0;
		double fy=1.0;
		double cx=1.0;
		double cy=0.0;
		double Cx=1.0;
		double Cy=0.0;
		
		print("t;f ;; t;y ;; t;g ;; t;z ;; t;fx;fy ;; t;cx;cy ;; t;Cx;Cy");
		
		for (double t=t0; t<t1; t=t+dt) {
			//Aufgabe 1.1
			//f' + 2*f = 1   umgestellt
			f = f + dt*   (1-2*f);
			//die approximation ist sehr gut
			//die algebraische lösung lautet: f = 0.5*e^(-2t)+1/2
			
			//Aufgabe 1.1
			//y'=y^2
			y = y + dt*   (y*y);
			//die algebraische lösung lautet: y = 1/(1-t)
			//die approximation ist akzeptabel
			//bis zu dem punkt t=1, an dieser stelle hat die funktion y eine polstelle
			//und die numerik versagt
			
			//Aufgabe 1.2
			//g_neu = g_alt + dt*(1-2*g_neu) umstellen :
			g = (g + dt) / (2*dt+1);
			//die approximation ist ähnlich gut wie 1.1
			
			//Aufgabe 1.2
			//z_neu = z_alt + dt*(z_neu^2) umstellen mit pq-Formel:
			z = 1/(2*dt) - Math.sqrt(1/(4*dt*dt)-z/dt);
			//die approximation ist akzeptabel
			//bis zu dem punkt, an dem der Term in der Wurzel negativ wird
			//und die quadratische Gleichung keine Lösung mehr besitzt
			
			//Aufgabe 1.3
			fx = fx + dt*   (-fx*fy);
			fy = fy + dt*   (-fy);
			
			//Aufgabe 1.3 a)
			double tmpX = cx + dt*   (-cy);//da der Wert cx in nächster zeile noch benötigt wird
			double tmpY = cy + dt*   (cx);
			cx = tmpX;
			cy = tmpY;
			
			
			//Aufgabe 1.3 b)
			//algebraische lösung: c(t)=r*(cos(t),sin(t))
			//also der ein kreis mit radius r, (ich habe r=1 gewählt für die startwerte)
			//der plot zeigt, dass der kreis sich nicht ganz schließt
			//für t=10 erhält man numerisch c(10)=(-0,877,-0,58)
			//brechnet man den abstand zum ursprung erhält man |c(10)| = 1,051
			//anstelle von algebraisch 1,000
			
			//Aufgabe 1.4
			//heun-verfahren für c(t)
			//---1--- expliziter euler
			double TmpX = Cx + dt*   (-Cy);
			double TmpY = Cy + dt*   (Cx);
			//---2--- bessere approximation mit temp-werten
			double TmpX2 = 0.5*(Cx + TmpX + dt * (-TmpY));
			double TmpY2 = 0.5*(Cy + TmpY + dt * (TmpX));
			Cx = TmpX2;
			Cy = TmpY2;
			//berechnet man erneut |C(10)| = 1,000
			//diesmal stimmt die approximation bis auf 3 nachkommastellen
			//mit der algebraischen lösung überein
			
			print(String.format("%.3f", t) + " ; " + String.format("%.3f", f) + " ;; " +
				  String.format("%.3f", t) + " ; " + String.format("%.3f", y) + " ;; " +
				  String.format("%.3f", t) + " ; " + String.format("%.3f", g) + " ;; " +
				  String.format("%.3f", t) + " ; " + String.format("%.3f", z) + " ;; " +
				  String.format("%.3f", t) + " ; " +
				  String.format("%.3f", fx) + " ; " + String.format("%.3f", fy) + " ;; " +
				  String.format("%.3f", t) + " ; " +
				  String.format("%.3f", cx) + " ; " + String.format("%.3f", cy) + " ;; " +
				  String.format("%.3f", t) + " ; " +
				  String.format("%.3f", Cx) + " ; " + String.format("%.3f", Cy));
		}
	}
	
	//Aufgabe 1.5 a)
	//es hat sich gezeigt, dass das heunverfahren und
	//der explizite euler den test nicht bestanden haben
	//für den wert k=201 ging f nicht gegen 0
	//der implizite euler hingegen bestand den test
	public boolean stabilitätstest(Main integrator) {
		double t0=0;
		double t1=30;
		double dt=0.01;
		for (double s=1; s<1000000; s=s*1.01){//exponentiell die werte anheben
			int k = (int) s;
			double f=1.0;
			for (double t=t0; t<t1; t=t+dt) {
				f = integrator.integrator(f, k, dt);
			}
			if (f>0.001 || f<-0.001) {
				System.out.println(k);
				return false;
			}
		}
		return true;
	}
	
	//Aufgabe 1.5 b)
	//er sagt aus, dass falls ein integrator stabil ist,
	//dann besteht er auch den test.
	//Umgekehrt heißt, dass NICHT, dass falls ein integrator den test besteht,
	//dass dieser integrator stabil sind muss bzw gut sein muss.
	//Ein beispiel für ein Integrator, der den test besteht, aber trotzdem
	//keine differenzialgleichungen löst wäre einer, der immer konstant null
	//liefert.
	//für diesen wäre das numerische integral von e^(-x*k) die nullfunktion
	//die natürlich für alle k gegen null konvergiert und daher den test besteht
	

	
	
	//schreibt werte in csv-datei
	public void print(String txt){
		try {
			writer.write(txt);
			// Platformunabhängiger Zeilenumbruch wird in den Stream geschrieben
			writer.write(System.getProperty("line.separator"));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
