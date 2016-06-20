package task1_2;
import integrator.Integrator;
import function.Function;


public class ImplicitEulerFunction1 extends Integrator {
	
	@Override
	public double integrationFunction(double currentValue, Function fd,
			double dx) {
		return (dx+currentValue)/(1.0+2.0*dx);
	}

}
