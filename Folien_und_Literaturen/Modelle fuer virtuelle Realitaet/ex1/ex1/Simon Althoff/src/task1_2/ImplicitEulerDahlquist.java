package task1_2;
import integrator.Integrator;
import function.Function;


public class ImplicitEulerDahlquist extends Integrator {

	
	public double k = 0.0;
	
	@Override
	public double integrationFunction(double currentValue, Function fd,
			double dx) {
		return currentValue/(1+dx*k);
	}

}
