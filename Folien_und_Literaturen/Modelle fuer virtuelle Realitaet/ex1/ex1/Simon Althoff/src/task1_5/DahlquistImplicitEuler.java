package task1_5;

import function.Function;
import integrator.Integrator;

public class DahlquistImplicitEuler extends Integrator {
	
	@Override
	public double integrationFunction(double currentValue, Function fd,
			double dx) {
		DahlquistFunction f = (DahlquistFunction)fd;
		return currentValue/(1+dx*f.getK());
	}

}
