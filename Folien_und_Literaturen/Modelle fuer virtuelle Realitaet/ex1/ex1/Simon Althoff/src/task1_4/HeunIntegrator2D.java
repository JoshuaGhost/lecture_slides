package task1_4;
import integrator.Integrator2D;
import function.Function2D;


public class HeunIntegrator2D extends Integrator2D{

	@Override
	protected double integrationFunction(double currentValue,
			Function2D toIntegrate, double additionalValue,
			Function2D additionalFunction, double dt) {
		double nextValue = currentValue + dt*toIntegrate.valueAt(currentValue,additionalValue);
		double nextAdditionalValue = additionalValue + dt*toIntegrate.valueAt(additionalValue,currentValue);
		return 0.5*currentValue+0.5*(nextValue+dt*toIntegrate.valueAt(nextValue,nextAdditionalValue));
	}

}
