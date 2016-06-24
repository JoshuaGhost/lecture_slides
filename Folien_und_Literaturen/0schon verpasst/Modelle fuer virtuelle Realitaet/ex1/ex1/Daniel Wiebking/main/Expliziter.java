package main;

public class Expliziter extends Main {
	
	public double integrator(double f, int k,  double dt) {
		//---1--- expliziter euler
		return f + dt*   (-k*f);
	}
	
}
