package task1_3;
import integrator.Integrator2D;
import function.Function2D;


public class ExplicitEuler2D extends Integrator2D{

	@Override
	protected double integrationFunction(double currentValue,
			Function2D toIntegrate, double additionalValue,
			Function2D additionalFunction, double dt) {
		return currentValue + toIntegrate.valueAt(currentValue,additionalValue)*dt;
	}
}
