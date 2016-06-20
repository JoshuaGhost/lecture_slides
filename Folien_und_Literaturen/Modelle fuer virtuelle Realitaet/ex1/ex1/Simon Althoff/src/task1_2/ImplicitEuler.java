package task1_2;
import integrator.Integrator;
import function.Function;


public class ImplicitEuler extends Integrator {

	//Integriert allgemein nach Implizitem Euler
	//Achtung: Endlosschleife, wenn Newtonverfahren nicht terminiert!
	
	@Override
	public double integrationFunction(double currentValue, Function fd,
			double dx) {
		return NewtonCalculator.newton(currentValue, fd, dx);
	}

}
