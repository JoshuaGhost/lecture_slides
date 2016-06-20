package task1_1;
import integrator.Integrator;
import function.Function;


public class ExplicitEuler extends Integrator {

	@Override
	public double integrationFunction(double currentValue, Function fd,
			double dx) {
	
		return currentValue + fd.valueAt(currentValue)*dx;
	}

			
}
