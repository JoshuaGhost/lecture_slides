package main;

public class Impliziter extends Main {
	public double integrator(double f, int k,  double dt) {
		//---1--- impliziter euler
		return f/(1+dt*k);
	}
}
