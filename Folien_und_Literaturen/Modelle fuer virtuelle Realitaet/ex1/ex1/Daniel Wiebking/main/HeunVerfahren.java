package main;

public class HeunVerfahren extends Main {
	public double integrator(double f, int k,  double dt) {
		//---1--- expliziter euler
		double tmp = f + dt*   (-k*f);
		//---2--- bessere approximation mit temp-werten
		return 0.5*(f + tmp + dt * (-k*tmp));
	}
}
