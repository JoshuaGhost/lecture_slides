package task1_5;
import integrator.Integrator;
import function.Function;


public class CheatingIntegrator extends Integrator {

	@Override
	public double integrationFunction(double currentValue, Function fd,
			double dx) {
		return currentValue*0.1;
	}

}
