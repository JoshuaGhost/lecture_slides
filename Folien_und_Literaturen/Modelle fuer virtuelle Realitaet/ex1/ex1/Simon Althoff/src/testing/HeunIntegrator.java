package testing;

import integrator.Integrator;
import function.Function;


public class HeunIntegrator extends Integrator{

	@Override
	public double integrationFunction(double currentValue,
			Function toIntegrate, double dt) {
		double nextValue = currentValue + dt*toIntegrate.valueAt(currentValue);
		return 0.5*currentValue+0.5*(nextValue+dt*toIntegrate.valueAt(nextValue));
	}

}